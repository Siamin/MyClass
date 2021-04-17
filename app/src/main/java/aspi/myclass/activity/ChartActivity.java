package aspi.myclass.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import aspi.myclass.Helpers.IndicatorHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.model.StatisticsModel;
import aspi.myclass.R;
import aspi.myclass.adapter.StatusticsAdapter;
import aspi.myclass.Helpers.DatabasesHelper;


public class ChartActivity extends Activity {


    public static String Name_class, Id_class, Did_class;
    DatabasesHelper data;
    int cunters = 0;
    RecyclerView recyclerView_show_student;
    LinearLayoutManager linearLayoutManager_show_student;
    boolean view = false;
    java.util.List<StatisticsModel> List = new ArrayList<>();
    ImageView backPage;
    String TAG = "TAG_StatisticsActivity";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        data = new DatabasesHelper(this);
        backPage = findViewById(R.id.statistics_back);
        GetData();

        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }


    void GetData() {
        try {
            List.clear();
            data.open();
            List = data.Statistics("SELECT  r.sno,r.abs,r.iddars,r.jalase,k.name,k.family,k.sno" +
                    " FROM klas AS k,rollcall AS r WHERE r.iddars= " + Integer.parseInt(Did_class) +
                    " AND r.sno = k.sno AND r.iddars = k.did ORDER BY k.family ASC,k.name ASC,r.sno ASC ", Did_class);
            data.close();


            if (!List.isEmpty()) {

                recyclerView_show_student = findViewById(R.id.show_student_recyclerview);
                linearLayoutManager_show_student = new LinearLayoutManager(ChartActivity.this);
                recyclerView_show_student.setLayoutManager(linearLayoutManager_show_student);
                recyclerView_show_student.setAdapter(new StatusticsAdapter(List));

                IndicatorHelper.IndicatorDismiss();

            } else {

                runOnUiThread(new Runnable() {
                    public void run() {
                        MessageHelper.Toast(ChartActivity.this, getResources().getString(R.string.ToastNoData));
                        onBackPressed();
                    }
                });
            }


        } catch (final Exception e) {
            Log.i(TAG, e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
