package aspi.myclass.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import aspi.myclass.Helpers.DialogHelper;
import aspi.myclass.Helpers.IndicatorHelper;
import aspi.myclass.Helpers.TableHelper;
import aspi.myclass.R;
import aspi.myclass.Helpers.DatabasesHelper;
import aspi.myclass.model.ReportDataModel;

public class ReportClassActivity extends Activity {

    TableLayout table;
    DatabasesHelper data;
    public static boolean STATUS;
    ImageView save;
    TextView titel;
    List<ReportDataModel> reportModels = new ArrayList<>();
    public static String Name_class, Id_class, Did_class;
    String TAG = "TAG_ReportClassActivity";
    ImageView backPage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportclass);
        data = new DatabasesHelper(this);
        initView();
        //******************************************************************************************
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper.qustionSaveExcll(ReportClassActivity.this, reportModels, titel.getText().toString(), Name_class, STATUS);
            }
        });
        //******************************************************************************************
        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    void initView() {
        save = findViewById(R.id.output_list_save);
        titel = findViewById(R.id.output_list_titel);
        table = findViewById(R.id.output_list_table);
        backPage = findViewById(R.id.outputlist_back);
        //*****************************************************
        if (STATUS) {
            titel.setText("لیست حضور و غیاب");
        } else {
            titel.setText("لیست نمرات");
        }
        //*****************************************************
        Start();
    }

    void Start() {

        IndicatorHelper.IndicatorCreate(ReportClassActivity.this, "در حال دریافت اطلاعات", "لطفا صبر کنید ...!");
        GetData();
        IndicatorHelper.IndicatorDismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void GetData() {
        try {

            data.open();
            //******************************************************************************
            reportModels = data.ReportCalss("SELECT  r.sno,r.abs,r.am,r.day,r.month,r.year,r.iddars,r.jalase,r.HOUR,k.name,k.family,k.sno,k.did" +
                    " FROM klas AS k,rollcall AS r WHERE r.iddars= " + Integer.parseInt(Did_class) + " AND r.sno = k.sno AND r.iddars = k.did AND k.did= "
                    + Integer.parseInt(Did_class)
                    + " ORDER BY r.day ASC ,r.month ASC ,r.year ASC ,r.HOUR ASC ,k.family ASC , k.name ASC , r.jalase ASC");
            data.close();
            if (reportModels.size() > 0) {
                TableHelper.creatTabel(ReportClassActivity.this, reportModels, STATUS,table);
            } else {
                finish();
            }

        } catch (final Exception e) {
            Log.i(TAG, "Error" + e.toString());
        }
    }

}