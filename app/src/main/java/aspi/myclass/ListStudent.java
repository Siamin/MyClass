package aspi.myclass;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;


public class ListStudent extends Activity {

    TextView titr;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_student);
        config();
    }

    void config(){
        titr = (TextView) findViewById(R.id.list_student_titr);
        recyclerView = (RecyclerView) findViewById(R.id.list_student_recyclerview);
        //****************************************************************************
        Bundle bundle = getIntent().getExtras();
        String titrs = titr.getText().toString()+bundle.getString("name_Class");
        String id_class = bundle.getString("id_Class");
        titr.setText(titrs);

    }
}
