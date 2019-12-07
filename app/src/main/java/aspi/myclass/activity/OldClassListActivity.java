package aspi.myclass.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
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


public class OldClassListActivity extends Activity implements  DatePickerDialog.OnDateSetListener {

    public static String Name_class, id_class, refresh = "";
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
        name_class = (TextView) findViewById(R.id.show_list_old_class_name_class);
        recyclerView2 = (RecyclerView) findViewById(R.id.show_list_old_class_recyclerview);
        linearLayoutManager = new LinearLayoutManager(OldClassListActivity.this);
        //***********************************************************************
        name_class.setTypeface(MainActivity.FONTS);
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
                            IndicatorHelper.IndicatorCreate(OldClassListActivity.this,"درحال دریافت اطلاعات","لطفا صبر کنید ...!");
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
                            recyclerView2.setAdapter(new ListCreateClassAdapter(List, OldClassListActivity.this, OldClassListActivity.this));

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
                        MessageHelper.Toast(OldClassListActivity.this, "هیچ جلسه ای برای کلاس تشکیل نشده...!");
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

    }
}

