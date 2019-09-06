package aspi.myclass.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Tools.Tools;
import aspi.myclass.content.StatisticsContent;
import aspi.myclass.R;
import aspi.myclass.adapter.StatusticsAdapter;
import aspi.myclass.Helpers.DatabasesHelper;


public class StatisticsActivity extends Activity {


    public static String Name_class, Id_class, Did_class;
    private DatabasesHelper data;
    private ProgressDialog progressDialog;
    private int cunters = 0;
    private RecyclerView recyclerView_show_student;
    private LinearLayoutManager linearLayoutManager_show_student;
    private boolean view = false;
    private java.util.List<StatisticsContent> List = new ArrayList<>();
    Tools om = new Tools();
    ImageView backPage;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        data = new DatabasesHelper(this);
        backPage = findViewById(R.id.statistics_back);
        Start();

        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    void Start() {
        final Timer time = new Timer();
        time.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        cunters += 1;
                        if (cunters == 1) {
                            progressDialog = new ProgressDialog(StatisticsActivity.this);
                           /* progressDialog.setProgress(0);
                            progressDialog.setProgressDrawable(getResources().getDrawable(R.drawable.dialog));
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);*/
                            progressDialog.setTitle("درحال دریافت اطلاعات");
                            progressDialog.setCancelable(false);
                            progressDialog.setMessage("لطفا صبر کنید ...!");
                            progressDialog.show();
                            new Thread(new Runnable() {
                                public void run() {
                                    //Get_Data_Base();
                                    Get_Qury_database();
                                }
                            }).start();
                        }
                        if (view) {
                            recyclerView_show_student = (RecyclerView) findViewById(R.id.show_student_recyclerview);
                            linearLayoutManager_show_student = new LinearLayoutManager(StatisticsActivity.this);
                            recyclerView_show_student.setLayoutManager(linearLayoutManager_show_student);
                            recyclerView_show_student.setAdapter(new StatusticsAdapter(List, StatisticsActivity.this));
                            time.cancel();
                            progressDialog.cancel();
                        }
                    }
                });
            }
        }, 1, 1000);
    }

    void Get_Qury_database() {
        try {
            data.open();
            String Res = data.Qury_amar_class("SELECT  r.sno,r.abs,r.iddars,r.jalase,k.name,k.family,k.sno" +
                    " FROM klas AS k,rollcall AS r WHERE r.iddars= " + Integer.parseInt(Did_class) +
                    " AND r.sno = k.sno AND r.iddars = k.did ORDER BY k.family ASC,k.name ASC,r.sno ASC ", Did_class);
            data.close();

            String name = "", family = "", per = "", abs = "",max="";
            String[] Name, Family, Per, Abs;
            int Cunt = 0;

            if(!Res.equals("")){

                for (int i = 0, j = 0, start = 0; i < Res.length(); i++) {

                    if (Res.charAt(i) == '|') {
                        String TE = Res.substring(start, i);
                        start = i + 1;

                        if (j == 0) {
                            abs = TE;
                            j += 1;
                        } else if (j == 1) {
                            per = TE;
                            j += 1;
                        } else if (j == 2) {
                            name = TE;
                            j += 1;
                        } else if (j == 3) {
                            family = TE;
                            j += 1;
                        } else if (j == 4) {
                            Cunt = Integer.parseInt(TE);
                            j += 1;
                        } else if (j == 5) {
                            max = TE;
                            j += 1;
                        }
                    }

                }

                Name=name.split("~");
                Family=family.split("~");
                Per=per.split("~");
                Abs=abs.split("~");

                for (int i = 0; i < Cunt; i++) {
                    StatisticsContent content = new StatisticsContent();
                    content.name = Name[i];
                    content.family = Family[i];
                    content.max = Integer.parseInt(max);
                    content.set =Integer.parseInt(Abs[i]);
                    content.per = Integer.parseInt(Per[i]);
                    List.add(content);
                }

                view = true;

            }else{

                runOnUiThread(new Runnable() {
                    public void run() {
                        MessageHelper.Toast(StatisticsActivity.this," برای آمار حضور و غیاب شما باید جلسه برای کلاس خود تشکیل دهید...! ");
                    }
                });
            }



        } catch (final Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
