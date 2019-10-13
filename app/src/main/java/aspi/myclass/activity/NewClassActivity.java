package aspi.myclass.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import aspi.myclass.Helpers.DateHelper;
import aspi.myclass.Helpers.IndicatorHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Tools.Tools;
import aspi.myclass.model.AbsentPersentModel;
import aspi.myclass.R;
import aspi.myclass.adapter.StudentViewAdapter;
import aspi.myclass.Helpers.DatabasesHelper;


public class NewClassActivity extends Activity {

    TextView name_class, DATA;
    public static String Name_class, did;
    DatabasesHelper data;
    RecyclerView recyclerView1;
    LinearLayoutManager linearLayoutManager;
    public static List<AbsentPersentModel> absentPersentModels = new ArrayList<>();
    int MINUTE, HOUR;
    String[] DATA_IRAN;
    public Timer time;
    int cunters = 0;
    boolean view = false;
    ImageView backPage;
    boolean statusBackPage = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_newclass);
        //******************************************************************************************
        initView();
        //******************************************************************************************
        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    void initView() {
        data = new DatabasesHelper(this);

        backPage = findViewById(R.id.activity_newclass_back);
        name_class = (TextView) findViewById(R.id.new_class_name_class);
        DATA = (TextView) findViewById(R.id.new_class_data);
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerview_new_classsss);
        linearLayoutManager = new LinearLayoutManager(NewClassActivity.this);
        //*************************************************************
        name_class.setText("کلاس " + Name_class);
        DATA.setText("" + DateHelper.date_iran());
        HOUR = Integer.parseInt(DateHelper.Get_Time().split(":")[0]);
        MINUTE = Integer.parseInt(DateHelper.Get_Time().split(":")[1]);
        //*************************************************************
        Start();
    }

    void go_main() {
        finish();
    }

    void Start() {
        final Timer time = new Timer();
        time.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        cunters += 1;
                        if (cunters == 1) {
                            IndicatorHelper.IndicatorCreate(NewClassActivity.this,"در حال دریافت اطلاعات","لطفا صبر کنید ...!");
                            new Thread(new Runnable() {
                                public void run() {
                                    Data();
                                }
                            }).start();
                        }
                        if (view) {
                            view = false;
                            recyclerView1.setLayoutManager(linearLayoutManager);
                            recyclerView1.setHasFixedSize(true);
                            recyclerView1.setAdapter(new StudentViewAdapter(absentPersentModels, NewClassActivity.this));
                            IndicatorHelper.IndicatorDismiss();
                            time.cancel();
                        }
                    }
                });
            }
        }, 1, 1000);
    }

    void Data() {
        try {

            absentPersentModels.clear();

            data.open();
            absentPersentModels = data.NameStudentClassByModel(did);
            data.close();

            if (absentPersentModels.size() > 0) {

                DATA_IRAN = DateHelper.date_iran().split("/");

                data.open();
                int cunt_roll = data.count("rollcall");
                int jalase = 1;
                if (cunt_roll > 0) {
                    jalase = Integer.parseInt(data.Display("rollcall", (cunt_roll - 1), 8)) + 1;
                }

                for (int i = 0; i < absentPersentModels.size(); i++) {
                    data.insert_Rollcall(absentPersentModels.get(i).sno, true, "", DATA_IRAN[0], DATA_IRAN[1], DATA_IRAN[2], did, String.valueOf(jalase), String.valueOf(HOUR) + ":" + String.valueOf(MINUTE));
                    absentPersentModels.get(i).id_rull = data.Display("rollcall", cunt_roll, 0);
                    cunt_roll += 1;
                }
                data.close();
                view = true;
                //**********************************************************************************
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        MessageHelper.Toast(NewClassActivity.this, "هیچ دانشجویی برای این کلاس ثبت نشده...!");
                        go_main();
                    }
                });
            }

        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                public void run() {
                    // TOAST(""+e.toString());
                }
            });
        }
    }

    @Override
    public void onBackPressed() {

        if (statusBackPage){
            super.onBackPressed();
            go_main();
        }else{
            MessageHelper.Toast(NewClassActivity.this,"برای بازگشت دوباره کلیک کنید!");
            statusBackPage = true;
        }
    }


}
