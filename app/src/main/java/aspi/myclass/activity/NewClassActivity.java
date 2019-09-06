package aspi.myclass.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Tools.Tools;
import aspi.myclass.content.AbsentPersentContent;
import aspi.myclass.R;
import aspi.myclass.adapter.StudentViewAdapter;
import aspi.myclass.Helpers.DatabasesHelper;


public class NewClassActivity extends Activity {

    TextView name_class, DATA;
    public static String Name_class, did;
    DatabasesHelper data;
    RecyclerView recyclerView1;
    LinearLayoutManager linearLayoutManager;
    public static List<AbsentPersentContent> List = new ArrayList<>();
    int MINUTE, HOUR;
    String[] DATA_IRAN, data_;
    public Timer time;
    ProgressDialog progressDialog;
    int cunters = 0;
    boolean view = false;
    Tools om = new Tools();
    ImageView backPage;
    boolean statusBackPage = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_newclass);
        //******************************************************************************************
        data = new DatabasesHelper(this);
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
        backPage = findViewById(R.id.activity_newclass_back);
        name_class = (TextView) findViewById(R.id.new_class_name_class);
        DATA = (TextView) findViewById(R.id.new_class_data);
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
                            progressDialog = new ProgressDialog(NewClassActivity.this);
                            progressDialog.setTitle("درحال دریافت اطلاعات");
                            progressDialog.setCancelable(false);
                            progressDialog.setMessage("لطفا صبر کنید ...!");
                            progressDialog.show();
                            new Thread(new Runnable() {
                                public void run() {
                                    //get_data_for_database();
                                    GET_QURY();
                                }
                            }).start();
                        }
                        if (view) {
                            view = false;
                            recyclerView1 = (RecyclerView) findViewById(R.id.recyclerview_new_classsss);
                            linearLayoutManager = new LinearLayoutManager(NewClassActivity.this);
                            recyclerView1.setLayoutManager(linearLayoutManager);
                            recyclerView1.setHasFixedSize(true);
                            recyclerView1.setAdapter(new StudentViewAdapter(List, NewClassActivity.this));
                            progressDialog.cancel();
                            time.cancel();
                        }
                    }
                });
            }
        }, 1, 1000);
    }

    void Amozesh(final boolean chek) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
        float amozesh = sp.getFloat("LerningActivity", 0);
        if (amozesh == 9)
            MessageHelper.Mesage(NewClassActivity.this,"شما می توانید در این صفحه حضور،غیاب و نمرات دانشجویان را وارد کنید.وهمچنین می توانید با انتخاب تیک که روبه روی اسم دانشجو قرار دارد برای دانشجو غیبت وارد لیست نمایید.");
    }

    void GET_QURY() {
        try {
            int c = 0;
            List.clear();
            String name_ = "", family_ = "", sno_ = "", text_ = "", id_ = "", abs = "", per = "", Status = "", cun = "";

            data.open();
            Status = data.Get_name_student_of_class(did);
            data.close();

            if (!Status.equals("")) {

                for (int i = 0, j = 0, start = 0; i < Status.length(); i++) {

                    if (Status.charAt(i) == '|') {
                        String TE = Status.substring(start, i);
                        start = i + 1;

                        if (j == 0) {
                            id_ = TE;
                            j += 1;
                        } else if (j == 1) {
                            sno_ = TE;
                            j += 1;
                        } else if (j == 2) {
                            family_ = TE;
                            j += 1;
                        } else if (j == 3) {
                            name_ = TE;
                            j += 1;
                        } else if (j == 4) {
                            text_ = TE;
                            j += 1;
                        } else if (j == 5) {
                            abs = TE;
                            j += 1;
                        } else if (j == 6) {
                            per = TE;
                            j += 1;
                        } else if (j == 7) {
                            cun = TE;
                            j += 1;
                        }
                    }

                }

                c = Integer.parseInt(cun);

            } else {
                c = 0;
            }
            data.close();

            if (c > 0) {
                //**********************************************************************************
                String[] Name, Family, Sno, Text, Id, Abs, Per;
                Name = name_.split("~");
                Family = family_.split("~");
                Sno = sno_.split("~");
                Text = text_.split("~");
                Id = id_.split("~");
                Abs = abs.split("~");
                Per = per.split("~");

                DATA_IRAN = DateHelper.date_iran().split("/");

                data.open();
                int cunt_roll = data.count("rollcall");
                int jalase = 1;
                if (cunt_roll > 0) {
                    jalase = Integer.parseInt(data.Display("rollcall", (cunt_roll - 1), 8)) + 1;
                }

                //cunt_roll=Integer.parseInt(data.Display("rollcall", cunt_roll-1, 0))+1;
                for (int i = 0; i < c; i++) {
                    AbsentPersentContent content = new AbsentPersentContent();
                    content.name = Name[i];
                    content.family = Family[i];
                    content.sno = Sno[i];
                    content.id = Id[i];
                    content.text = Text[i];
                    content.status = "1";
                    content.absent = Abs[i];
                    content.prezent = Per[i];
                    data.insert_Rollcall(Sno[i], true, "", DATA_IRAN[0], DATA_IRAN[1], DATA_IRAN[2], did, String.valueOf(jalase), String.valueOf(HOUR) + ":" + String.valueOf(MINUTE));
                    content.id_rull = content.id_rull = data.Display("rollcall", cunt_roll, 0);
                    cunt_roll += 1;
                    List.add(content);
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

    protected void onResume() {
        super.onResume();
        Amozesh(false);
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
