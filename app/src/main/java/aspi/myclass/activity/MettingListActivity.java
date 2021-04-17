package aspi.myclass.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import aspi.myclass.Helpers.IndicatorHelper;
import aspi.myclass.Helpers.MessageHelper;

import aspi.myclass.model.OldClassModel;
import aspi.myclass.R;
import aspi.myclass.adapter.ListCreateClassAdapter;
import aspi.myclass.Helpers.DatabasesHelper;


public class MettingListActivity extends Activity
        implements DatePickerDialog.OnDateSetListener,android.app.DatePickerDialog.OnDateSetListener {

    public static String Name_class, id_class, refresh = "", sessions;
    TextView name_class;
    DatabasesHelper data;
    RecyclerView recyclerView2;
    LinearLayoutManager linearLayoutManager;
    java.util.List<OldClassModel> List = new ArrayList<>();
    public static Timer time;
    int cunters = 0;
    boolean view = false;
    String TAG = "TAG_OldClassListActivity";
    ImageView backPage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldclasslist);
        data = new DatabasesHelper(this);
        initView();
        //************************************************
        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void initView() {
        backPage = findViewById(R.id.activity_listoldclass_back);
        name_class = (TextView) findViewById(R.id.LessonName);
        recyclerView2 = (RecyclerView) findViewById(R.id.show_list_old_class_recyclerview);
        linearLayoutManager = new LinearLayoutManager(MettingListActivity.this);
        //***********************************************************************
        //***********************************************************************
        name_class.setText("" + Name_class);
        //***********************************************************************
        Start();
        refresh();
    }

    public void refresh() {
        time = new Timer();
        time.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (refresh.equals("1")) {
                            cunters = 0;
                            refresh = "";
                        }
                    }
                });
            }
        }, 1, 50);
    }

    void Start() {
        final Timer time = new Timer();
        time.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        cunters += 1;
                        if (cunters == 1) {
                            IndicatorHelper.IndicatorCreate(MettingListActivity.this, getResources().getString(R.string.gettingData), getResources().getString(R.string.pleaseWait));
                            new Thread(new Runnable() {
                                public void run() {
                                    // get_database();
                                    Qury_database();
                                }
                            }).start();
                        }
                        if (view) {
                            IndicatorHelper.IndicatorDismiss();
                            view = false;
                            recyclerView2.setLayoutManager(linearLayoutManager);
                            recyclerView2.setHasFixedSize(true);
                            recyclerView2.setAdapter(new ListCreateClassAdapter(List, MettingListActivity.this));

                        }
                    }
                });
            }
        }, 1, 1000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void Qury_database() {
        try {
            List.clear();

            data.open();
            List = data.ListOldClssByIdClass(id_class);
            data.close();


            if (!List.isEmpty()) {

                view = true;
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        MessageHelper.Toast(MettingListActivity.this, getResources().getString(R.string.notCreateMate));
                        finish();
                    }
                });
            }

        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "Error" + e.toString());
                }
            });
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.i(TAG, "onDateSet FA" + year);

        updateDate(year, monthOfYear, dayOfMonth);
    }


    void updateDate(int year, int monthOfYear, int dayOfMonth) {
        try {
            data.open();
            data.update("day", String.valueOf(year), "month", String.valueOf(monthOfYear + 1), "year", String.valueOf(dayOfMonth), "jalase=" + sessions);
            data.close();
            MessageHelper.Toast(this, getResources().getString(R.string.editingDoneSuccessfully));
            MettingListActivity.refresh = "1";
        } catch (Exception e) {
            Log.i(TAG, "Error" + e.toString());
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        Log.i(TAG, "onDateSet En" + year);

        updateDate(year, monthOfYear, dayOfMonth);
    }
}

