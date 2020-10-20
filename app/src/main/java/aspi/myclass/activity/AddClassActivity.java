package aspi.myclass.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import aspi.myclass.Helpers.DateHelper;
import aspi.myclass.Helpers.DateTimePickerHelper;
import aspi.myclass.Helpers.DialogHelper;
import aspi.myclass.Helpers.LanguageHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.R;
import aspi.myclass.Tools.Tools;
import aspi.myclass.Helpers.DatabasesHelper;


public class AddClassActivity extends Activity implements TimePickerDialog.OnTimeSetListener,android.app.TimePickerDialog.OnTimeSetListener {

    private EditText name_edit, code_edit, location_edit, class_edit;
    private Spinner day_spinner;
    private LinearLayout time_start, time_end;
    private TextView textTimeStart, textTimeEnd;
    private DatabasesHelper data;
    private String[] Day_of_week;
    private ImageView backPage, save_data;
    private DateTimePickerHelper dateTimePickerHelper = new DateTimePickerHelper();
    private TextView textView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        LanguageHelper.loadLanguage(AddClassActivity.this);
        setContentView(R.layout.activity_addclass);
        data = new DatabasesHelper(this);
        Day_of_week = getResources().getStringArray(R.array.weekName);

        initView();

        //******************************************************************************************
        save_data.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (!name_edit.getText().toString().isEmpty()) {
                    if (!code_edit.getText().toString().isEmpty()) {
                        if (!location_edit.getText().toString().isEmpty()) {
                            if (!class_edit.getText().toString().isEmpty()) {
                                if (save()) {

                                    MainActivity.refresh = 1;
                                    MessageHelper.Toast(AddClassActivity.this,
                                            getResources().getString(R.string.Class) + " "
                                                    + name_edit.getText().toString() + " "
                                                    + getResources().getString(R.string.SuccessRegistered));
                                    Go_main();
                                }
                            } else {
                                MessageHelper.Toast(AddClassActivity.this, getResources().getString(R.string.PleaseEnterClassNumber));
                            }
                        } else {
                            MessageHelper.Toast(AddClassActivity.this, getResources().getString(R.string.PleaseEnterClassLocation));
                        }
                    } else {
                        MessageHelper.Toast(AddClassActivity.this, getResources().getString(R.string.PleaseEnterClassAttribute));
                    }
                } else {
                    MessageHelper.Toast(AddClassActivity.this, getResources().getString(R.string.PleaseEnterClassName));
                }
            }
        });
        //******************************************************************************************
        backPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Go_main();
            }
        });
        //******************************************************************************************

        time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView = textTimeStart;
                dateTimePickerHelper.getTime(AddClassActivity.this);
            }
        });

        time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView = textTimeEnd;
                dateTimePickerHelper.getTime(AddClassActivity.this);
            }
        });


    }

    private void Go_main() {
        finish();
    }

    private void initView() {
        save_data = findViewById(R.id.activity_addclass_save);
        backPage = findViewById(R.id.activity_addclass_back);
        name_edit = (EditText) findViewById(R.id.add_class_name_edit);
        class_edit = (EditText) findViewById(R.id.add_class_class_edit);
        code_edit = (EditText) findViewById(R.id.add_class_code_edit);
        location_edit = (EditText) findViewById(R.id.add_class_potion_edit);
        day_spinner = (Spinner) findViewById(R.id.add_class_spinner);

        time_start = findViewById(R.id.activity_addclass_starttime);
        time_end = findViewById(R.id.activity_addclass_endtime);

        textTimeStart = findViewById(R.id.activity_addclass_starttimetext);
        textTimeEnd = findViewById(R.id.activity_addclass_endtimetext);


        textTimeStart.setText(DateHelper.Get_Time());
        textTimeEnd.setText(DateHelper.Get_Time());

        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Day_of_week);
        day_spinner.setAdapter(a);

    }

    private boolean save() {
        int day_of = day_spinner.getSelectedItemPosition();
        try {
            data.open();
            data.insert_class(name_edit.getText().toString().replace("~", ""), String.valueOf(day_of), textTimeStart.getText().toString(), location_edit.getText().toString().replace("~", ""), textTimeEnd.getText().toString(), code_edit.getText().toString(), class_edit.getText().toString().replace("~", ""), "");
            data.close();
            return true;
        } catch (Exception e) {

            DialogHelper.errorReport(AddClassActivity.this,"AddClassActivity","save",e.toString());
            return false;
        }

    }

    protected void onResume() {
        super.onResume();

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

}
