package aspi.myclass.activity;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import aspi.myclass.model.AbsentPersentModel;
import aspi.myclass.R;
import aspi.myclass.adapter.StudentViewAdapter;
import aspi.myclass.Helpers.DatabasesHelper;

public class MettingLastActivity extends Activity {

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
        name_class = findViewById(R.id.new_class_name_class);
        DATA = findViewById(R.id.new_class_data);
        Time = findViewById(R.id.old_classsss_time);
        Titel = findViewById(R.id.New_class_titel);
        recyclerView_Old = findViewById(R.id.recyclerview_new_classsss);
        linearLayoutManagers = new LinearLayoutManager(this);
        Search = findViewById(R.id.newclass_search);
        //*************************************************************
        Titel.setText(getResources().getString(R.string.TitleOldClassActivity));
        Time.setText(getResources().getString(R.string.time) + ": " + HOUR);
        name_class.setText(getResources().getString(R.string.Class) + " " + Name_class);
        DATA.setText(Data_class);
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
        recyclerView_Old.setAdapter(new StudentViewAdapter(ListClass, MettingLastActivity.this));
    }


    void Data() {
        try {
            List.clear();

            data.open();
            List = data.OldClassByQuery("SELECT  r.id,r.sno,r.abs,r.am,r.iddars,r.jalase,k.id,k.name,k.family,k.sno,k.tx,k.did" +
                            " FROM klas AS k,rollcall AS r WHERE r.iddars= " + Integer.parseInt(did_class) +
                            " AND r.jalase= '" + Jalase + "' AND r.sno = k.sno AND r.iddars = k.did  ORDER BY k.family ASC ,k.name ASC "
                    , getResources().getString(R.string.Score));
            data.close();

            ShowAdapter(List);

//**************************************************************************************************
        } catch (Exception e) {
            Log.i(TAG, "Error" + e.toString());
        }
//**************************************************************************************************
    }
}
