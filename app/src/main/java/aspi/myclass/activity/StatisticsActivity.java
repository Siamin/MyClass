package aspi.myclass.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import aspi.myclass.Helpers.IndicatorHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Tools.Tools;
import aspi.myclass.model.StatisticsModel;
import aspi.myclass.R;
import aspi.myclass.adapter.StatusticsAdapter;
import aspi.myclass.Helpers.DatabasesHelper;


public class StatisticsActivity extends Activity {


    public static String Name_class, Id_class, Did_class;
    DatabasesHelper data;
    int cunters = 0;
    RecyclerView recyclerView_show_student;
    LinearLayoutManager linearLayoutManager_show_student;
    boolean view = false;
    java.util.List<StatisticsModel> List = new ArrayList<>();
    Tools tools = new Tools();
    ImageView backPage;
    String TAG = "TAG_StatisticsActivity";


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
                            IndicatorHelper.IndicatorCreate(StatisticsActivity.this,"در حال دریافت اطلاعات","لطفا صبر کنید ...!");
                            new Thread(new Runnable() {
                                public void run() {
                                    //Get_Data_Base();
                                    GetData();
                                }
                            }).start();
                        }
                        if (view) {
                            recyclerView_show_student = (RecyclerView) findViewById(R.id.show_student_recyclerview);
                            linearLayoutManager_show_student = new LinearLayoutManager(StatisticsActivity.this);
                            recyclerView_show_student.setLayoutManager(linearLayoutManager_show_student);
                            recyclerView_show_student.setAdapter(new StatusticsAdapter(List, StatisticsActivity.this));
                            time.cancel();
                            IndicatorHelper.IndicatorDismiss();
                        }
                    }
                });
            }
        }, 1, 1000);
    }

    void GetData() {
        try {
            List.clear();
            data.open();
            List = data.Statistics("SELECT  r.sno,r.abs,r.iddars,r.jalase,k.name,k.family,k.sno" +
                    " FROM klas AS k,rollcall AS r WHERE r.iddars= " + Integer.parseInt(Did_class) +
                    " AND r.sno = k.sno AND r.iddars = k.did ORDER BY k.family ASC,k.name ASC,r.sno ASC ", Did_class);
            data.close();



            if(!List.isEmpty()){

                view = true;

            }else{

                runOnUiThread(new Runnable() {
                    public void run() {
                        MessageHelper.Toast(StatisticsActivity.this," برای آمار حضور و غیاب شما باید جلسه برای کلاس خود تشکیل دهید...! ");
                    }
                });
            }



        } catch (final Exception e) {
            Log.i(TAG,e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
