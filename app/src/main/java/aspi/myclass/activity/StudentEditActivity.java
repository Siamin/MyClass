package aspi.myclass.activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import aspi.myclass.Helpers.DatabasesHelper;
import aspi.myclass.R;
import aspi.myclass.adapter.EditStudentAdapter;
import aspi.myclass.model.AbsentPersentModel;

public class StudentEditActivity extends Activity {

    ImageView back;
    RecyclerView adapter;
    DatabasesHelper databasesHelper;
    List<AbsentPersentModel> studentModel = new ArrayList<>();
    String TAG = "TAG_EditStudentActivity";
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editstudent);
        initView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    void initView() {
        back = findViewById(R.id.editstudent_back);
        adapter = findViewById(R.id.editstudent_adapter);
        linearLayoutManager = new LinearLayoutManager(this);

        databasesHelper = new DatabasesHelper(this);

        Bundle extras = getIntent().getExtras();
        getData(extras.getString("idClass"));
    }

    void getData(String idClass) {
        databasesHelper.open();
        studentModel = databasesHelper.getStudentClass(idClass, 0);
        databasesHelper.close();

        if (studentModel.size() > 0) {
            adapter.setLayoutManager(linearLayoutManager);
            adapter.setHasFixedSize(true);
            adapter.setAdapter(new EditStudentAdapter(studentModel, StudentEditActivity.this));
        } else {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(StudentEditActivity.this,MainActivity.class));
        finish();
    }
}
