package aspi.myclass.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import aspi.myclass.Helpers.DateTimePickerHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.MyActivity;
import aspi.myclass.R;
import aspi.myclass.model.LessonModel;


public class LessonEditActivity extends MyActivity implements TimePickerDialog.OnTimeSetListener {

    private EditText LessonName, LessonCode, LessonEducation, LessonClassNumber;
    private Spinner dayOfWeek;
    private LinearLayout time_start, time_end;
    private TextView LessonStartTime, LessonEndTime;
    private String[] arrDayOfWeek;
    private LessonModel model;
    String TIMEPICKER = "TimePickerDialog",TAG = "TAG_EditClassActivity";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addclass);


        initView();
        //******************************************************************************************
        time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTimeByDialog(LessonStartTime);
            }
        });

        time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTimeByDialog(LessonEndTime);
            }
        });
    }

    void SetTimeByDialog(final TextView text) {
        PersianCalendar now = new PersianCalendar();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                LessonEditActivity.this,
                now.get(PersianCalendar.HOUR_OF_DAY),
                now.get(PersianCalendar.MINUTE), true);
        tpd.setThemeDark(true);
        tpd.show(getFragmentManager(), TIMEPICKER);
        tpd.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
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

                text.setText(Hour + ":" + Min);
            }
        });
    }

    void Go_main() {
        finish();
    }

    void initView() {
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

        TextView title = findViewById(R.id.LessonTitlePage);
        title.setText(getResources().getString(R.string.classEdit_));

        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrDayOfWeek);
        dayOfWeek.setAdapter(a);

        Bundle extras = getIntent().getExtras();
        getLessonData(extras.getString("id"));

    }

    void getLessonData(String id) {
        try {
            model = lessonController.findByid(id);
            LessonEndTime.setText(model.endTime);
            LessonStartTime.setText(model.startTime);
            LessonClassNumber.setText(model.classNumber);
            LessonName.setText(model.lessonName);
            LessonCode.setText(model.lessonCode);
            LessonEducation.setText(model.education);
            dayOfWeek.setSelection(Integer.parseInt(model.dayOfWeek));
        } catch (Exception e) {
            Log.i(TAG,"Error"+e.toString());
        }
    }

    boolean updata() {
        return true;
//        boolean resualt = false;
//        int day_of = day_spinner.getSelectedItemPosition();
//
//        try {
//            if (!name_edit.getText().toString().equals(Name_Class)) {
//                try {
//                    data.open();
//                    data.update_one("dars", "ndars", name_edit.getText().toString(), Integer.parseInt(ID_Class));
//                    data.close();
//                    resualt = true;
//                } catch (Exception e) {
//                }
//            }
//            if (!code_edit.getText().toString().equals(Code_class)) {
//                try {
//                    data.open();
//                    data.update_one("dars", "Characteristic", code_edit.getText().toString(), Integer.parseInt(ID_Class));
//                    data.close();
//                    resualt = true;
//                } catch (Exception e) {
//                }
//            }
//            if (!location_edit.getText().toString().equals(Location_Class)) {
//                try {
//                    data.open();
//                    data.update_one("dars", "location", location_edit.getText().toString(), Integer.parseInt(ID_Class));
//                    data.close();
//                    resualt = true;
//                } catch (Exception e) {
//                }
//            }
//            if (!class_edit.getText().toString().equals(Class)) {
//                try {
//                    data.open();
//                    data.update_one("dars", "class", class_edit.getText().toString(), Integer.parseInt(ID_Class));
//                    data.close();
//                    resualt = true;
//                } catch (Exception e) {
//                }
//            }
//            if (day_of != Integer.parseInt(day)) {
//                try {
//                    data.open();
//                    data.update_one("dars", "dey", String.valueOf(day_of), Integer.parseInt(ID_Class));
//                    data.close();
//                    resualt = true;
//                } catch (Exception e) {
//                }
//            }
//            if (!Start_Class.equals(textTimeStart.getText().toString())) {
//                try {
//                    data.open();
//                    data.update_one("dars", "time", textTimeStart.getText().toString(), Integer.parseInt(ID_Class));
//                    data.close();
//                    resualt = true;
//                } catch (Exception e) {
//                }
//            }
//            if (!End_Class.equals(textTimeEnd.getText().toString())) {
//                try {
//                    data.open();
//                    data.update_one("dars", "Minute", textTimeEnd.getText().toString(), Integer.parseInt(ID_Class));
//                    data.close();
//                    resualt = true;
//                } catch (Exception e) {
//                }
//            }
//
//        } catch (Exception e) {
//        }
//        return resualt;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

    }

    public void SaveDate(View view) {
        if (updata()) {
            MessageHelper.Toast(LessonEditActivity.this, getResources().getString(R.string.editingDoneSuccessfully));
            Go_main();
        } else {
            MessageHelper.Toast(LessonEditActivity.this, getResources().getString(R.string.noEditsHaveBeenMade));
        }
    }

    public void BackPage(View view) {
        onBackPressed();
    }
}
