package aspi.myclass.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import aspi.myclass.Helpers.DialogHelper;
import aspi.myclass.Helpers.IndicatorHelper;
import aspi.myclass.Helpers.TableHelper;
import aspi.myclass.Helpers.ValidationHelper;
import aspi.myclass.MyActivity;
import aspi.myclass.R;
import aspi.myclass.Helpers.DatabasesHelper;
import aspi.myclass.model.ReportDataModel;

public class ReportClassActivity extends MyActivity {

    TableLayout table;
    boolean STATUS;
    ImageView save;
    TextView titel;
    List<ReportDataModel> reportModels = new ArrayList<>();
    String Name_class, Id_class, Did_class;
    String TAG = "TAG_ReportClassActivity";
    ImageView backPage;
    private int STORAGE_PERMISSION_CODE = 101;
    private TableHelper tableHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportclass);

        tableHelper = new TableHelper(this);
        initView();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ValidationHelper.checkPerimission(ReportClassActivity.this)) {
                    DialogHelper.qustionSaveExcll(ReportClassActivity.this, reportModels, titel.getText().toString(), Name_class, STATUS);
                } else {
                    // Requesting the permission
                    String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
                    ActivityCompat.requestPermissions(ReportClassActivity.this, new String[]{permission}, STORAGE_PERMISSION_CODE);
                }
            }
        });


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


        Bundle bundle = getIntent().getExtras();

        Name_class = bundle.getString("className");
        Id_class = bundle.getString("classId");
        Did_class = bundle.getString("classDid");
        STATUS = bundle.getBoolean("status");

        if (STATUS) {
            titel.setText(getResources().getString(R.string.absenceList));
        } else {
            titel.setText(getResources().getString(R.string.scoreList));
        }


        Start();
    }


    void Start() {

        IndicatorHelper.IndicatorCreate(ReportClassActivity.this, getResources().getString(R.string.gettingData), getResources().getString(R.string.pleaseWait));
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
                tableHelper.creatTabel(reportModels, STATUS, table);
            } else {
                finish();
            }

        } catch (final Exception e) {
            Log.i(TAG, "Error" + e.toString());
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }


}
