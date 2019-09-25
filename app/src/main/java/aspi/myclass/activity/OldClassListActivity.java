package aspi.myclass.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import aspi.myclass.Helpers.IndicatorHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Tools.Tools;
import aspi.myclass.content.OldClassContent;
import aspi.myclass.R;
import aspi.myclass.adapter.ListCreateClassAdapter;
import aspi.myclass.Helpers.DatabasesHelper;


public class OldClassListActivity extends Activity implements  DatePickerDialog.OnDateSetListener {

    public static String Name_class, id_class, refresh = "";
    TextView name_class;
    DatabasesHelper data;
    RecyclerView recyclerView2;
    LinearLayoutManager linearLayoutManager;
    java.util.List<OldClassContent> List = new ArrayList<>();
    public static Timer time;
    int cunters = 0;
    boolean view = false;
    String TAG = "TAG_OldClassListActivity";
    Tools om = new Tools();
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
                            recyclerView2 = (RecyclerView) findViewById(R.id.show_list_old_class_recyclerview);
                            linearLayoutManager = new LinearLayoutManager(OldClassListActivity.this);
                            recyclerView2.setLayoutManager(linearLayoutManager);
                            recyclerView2.setHasFixedSize(true);
                            recyclerView2.setAdapter(new ListCreateClassAdapter(List, OldClassListActivity.this, OldClassListActivity.this));

                        }
                    }
                });
            }
        }, 1, 1000);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    void Qury_database() {
        try {
            List.clear();
            String jalase_old = "0", DATA = "", jalase = "", HOUR = "", Row = "", Res = "";
            int cual = 0;
            boolean STATUS = false;
            data.open();
            Res = data.Get_old_class(id_class);
            data.close();
            if (!Res.equals("")) {
                STATUS = true;

                for (int i = 0, j = 0, start = 0; i < Res.length(); i++) {

                    if (Res.charAt(i) == '|') {

                        String TEXT_RES = Res.substring(start, i);
                        start = i + 1;
                        if (j == 0) {
                            DATA = TEXT_RES;
                            j += 1;
                        } else if (j == 1) {
                            jalase = TEXT_RES;
                            j += 1;
                        } else if (j == 2) {
                            HOUR = TEXT_RES;
                            j += 1;
                        } else if (j == 3) {
                            cual = Integer.parseInt(TEXT_RES);
                            j += 1;
                        }

                    }

                }
            }


            if (STATUS) {

                String[] Data, Jalase, Hour, row;
                Data = DATA.split("~");
                Jalase = jalase.split("~");
                Hour = HOUR.split("~");
                //row = Row.split("~");

                for (int i = 0; i < cual; i++) {

                    OldClassContent content = new OldClassContent();
                    content.DATA = Data[i];
                    content.jalase = Jalase[i];
                    content.Hour = Hour[i];
                    content.Row_rollcall = String.valueOf(i);
                    List.add(content);

                }
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

