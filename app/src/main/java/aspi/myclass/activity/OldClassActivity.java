package aspi.myclass.activity;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import aspi.myclass.model.AbsentPersentModel;
import aspi.myclass.R;
import aspi.myclass.adapter.StudentViewAdapter;
import aspi.myclass.Helpers.DatabasesHelper;

public class OldClassActivity extends Activity {

    private TextView name_class, DATA, Time, Titel;
    public static String Name_class, did_class, Data_class, Jalase, HOUR;
    private DatabasesHelper data;
    private RecyclerView recyclerView_Old;
    private LinearLayoutManager linearLayoutManagers;
    public static java.util.List<AbsentPersentModel> List = new ArrayList<>();
    String TAG = "TAG_OldClassActivity";
    ImageView backPage;
    EditText Search;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_newclass);
        data = new DatabasesHelper(this);
        initView();
        //************************************************
        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(Search.getText().toString());
                    return true;
                }
                return false;
            }

        });
    }

    void performSearch(String search) {
        java.util.List<AbsentPersentModel> searchList = new ArrayList<>();
        for (int i = 0; i < List.size(); i++) {
            if (List.get(i).family.contains(search)) {
                searchList.add(List.get(i));
            }
        }

        ShowAdapter(searchList.size() > 0 ? searchList : List);

    }

    void initView() {
        backPage = findViewById(R.id.activity_newclass_back);
        name_class = (TextView) findViewById(R.id.new_class_name_class);
        DATA = (TextView) findViewById(R.id.new_class_data);
        Time = (TextView) findViewById(R.id.old_classsss_time);
        Titel = (TextView) findViewById(R.id.New_class_titel);
        recyclerView_Old = (RecyclerView) findViewById(R.id.recyclerview_new_classsss);
        linearLayoutManagers = new LinearLayoutManager(this);
        Search = findViewById(R.id.newclass_search);
        //*************************************************************
        Titel.setText("لیست گذشته کلاس");
        Time.setText("ساعت: " + HOUR);
        name_class.setText("کلاس " + Name_class);
        DATA.setText("" + Data_class);
        //*************************************************************
        Data();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void ShowAdapter(List<AbsentPersentModel> ListClass) {
        recyclerView_Old.setLayoutManager(linearLayoutManagers);
        recyclerView_Old.setHasFixedSize(true);
        recyclerView_Old.setAdapter(new StudentViewAdapter(ListClass, OldClassActivity.this));
    }


    void Data() {
        try {
            List.clear();

            data.open();
            List = data.OldClassByQuery("SELECT  r.id,r.sno,r.abs,r.am,r.iddars,r.jalase,k.id,k.name,k.family,k.sno,k.tx,k.did" +
                    " FROM klas AS k,rollcall AS r WHERE r.iddars= " + Integer.parseInt(did_class) +
                    " AND r.jalase= '" + Jalase + "' AND r.sno = k.sno AND r.iddars = k.did  ORDER BY k.family ASC ,k.name ASC ");
            data.close();

            ShowAdapter(List);

//**************************************************************************************************
        } catch (Exception e) {
            Log.i(TAG, "Error" + e.toString());
        }
//**************************************************************************************************
    }
}
