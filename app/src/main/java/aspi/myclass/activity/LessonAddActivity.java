package aspi.myclass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;

import aspi.myclass.Helpers.DateHelper;
import aspi.myclass.Helpers.DateTimePickerHelper;
import aspi.myclass.MyActivity;
import aspi.myclass.R;
import aspi.myclass.model.LessonModel;


public class LessonAddActivity extends MyActivity implements TimePickerDialog.OnTimeSetListener,android.app.TimePickerDialog.OnTimeSetListener {

    private EditText LessonName, LessonCode, LessonEducation, LessonClassNumber;
    private Spinner dayOfWeek;
    private LinearLayout time_start, time_end;
    private TextView LessonStartTime, LessonEndTime;
    private String[] arrDayOfWeek;
    private DateTimePickerHelper dateTimePickerHelper = new DateTimePickerHelper();
    private TextView textView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addclass);

        initView();

        //******************************************************************************************

        time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView = LessonStartTime;
                dateTimePickerHelper.getTime(LessonAddActivity.this);
            }
        });

        time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView = LessonEndTime;
                dateTimePickerHelper.getTime(LessonAddActivity.this);
            }
        });


    }

    private void initView() {
        arrDayOfWeek = getResources().getStringArray(R.array.weekName);
        backPage = new Intent(this,MainActivity.class);

        LessonName =  findViewById(R.id.LessonName);
        LessonClassNumber =  findViewById(R.id.LessonClassNumber);
        LessonCode =  findViewById(R.id.LessonCode);
        LessonEducation =  findViewById(R.id.LessonEducation);
        dayOfWeek =  findViewById(R.id.LessonDayOfWeek);

        time_start = findViewById(R.id.activity_addclass_starttime);
        time_end = findViewById(R.id.activity_addclass_endtime);

        LessonStartTime = findViewById(R.id.LessonStartTime);
        LessonEndTime = findViewById(R.id.LessonEndTime);


        LessonStartTime.setText(DateHelper.Get_Time());
        LessonEndTime.setText(DateHelper.Get_Time());

        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrDayOfWeek);
        dayOfWeek.setAdapter(a);

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        setTime(hourOfDay, minute);
    }

    private void setTime(int hourOfDay, int minute) {
        String Hour = "00", Min = "00";


        if (hourOfDay < 10) {
            Hour = "0" + String.valueOf(hourOfDay);
        } else {
            Hour = String.valueOf(hourOfDay);
        }

        if (minute < 10) {
            Min = "0" + String.valueOf(minute);
        } else {
            Min = String.valueOf(minute);
        }

        textView.setText(Hour + ":" + Min);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        setTime(hourOfDay, minute);
    }

    public void BackPage(View view) {
        GoPage(new Intent(this,MainActivity.class));
    }

    public void SaveDate(View view) {
        //lessonName, startTime, endTime, id, education, classNumber, parentId, lessonCode, description, dayOfWeek
        LessonModel model = new LessonModel(
                LessonName.getText().toString().replace("~", ""),
                LessonStartTime.getText().toString(),
                LessonEndTime.getText().toString(),
                null,
                LessonEducation.getText().toString().replace("~", ""),
                LessonClassNumber.getText().toString().replace("~", ""),
                "0",
                LessonCode.getText().toString(),
                "",
                String.valueOf(dayOfWeek.getSelectedItemPosition())

        );

        if(lessonController.insertLesson(model))
            onBackPressed();
    }
}
