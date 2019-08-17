package aspi.myclass.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import aspi.myclass.R;
import aspi.myclass.class_.OtherMetod;
import aspi.myclass.class_.dbstudy;


public class EditClassActivity extends Activity implements TimePickerDialog.OnTimeSetListener {

    TextView title, textTimeStart, textTimeEnd;
    EditText name_edit, code_edit, location_edit, class_edit;
    Spinner day_spinner;
    LinearLayout time_start, time_end;
    dbstudy data;
    public static String[] Day_of_week = {"شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنج شنبه", "جمعه"};
    public static String Name_Class, Start_Class, End_Class, ID_Class, Location_Class, Code_class, day, Class;
    OtherMetod om = new OtherMetod();
    ImageView backPage, save_data;
    String TIMEPICKER = "TimePickerDialog",TAG = "TAG_EditClassActivity";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_addclass);
        data = new dbstudy(this);
        initView();
        //******************************************************************************************
        save_data.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (updata()) {
                    om.Toast(EditClassActivity.this, "ویرایش با موفقیت انجام شد...!");
                    Go_main();
                } else {
                    om.Toast(EditClassActivity.this, "هیچ تغییری برای ویرایش اعمال نشده ...!");
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
                SetTimeByDialog(textTimeStart);
            }
        });

        time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetTimeByDialog(textTimeEnd);
            }
        });
    }

    void SetTimeByDialog(final TextView text) {
        PersianCalendar now = new PersianCalendar();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                EditClassActivity.this,
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
        save_data = findViewById(R.id.activity_addclass_save);
        backPage = findViewById(R.id.activity_addclass_back);
        class_edit = (EditText) findViewById(R.id.add_class_class_edit);
        name_edit = (EditText) findViewById(R.id.add_class_name_edit);
        code_edit = (EditText) findViewById(R.id.add_class_code_edit);
        location_edit = (EditText) findViewById(R.id.add_class_potion_edit);
        day_spinner = (Spinner) findViewById(R.id.add_class_spinner);
        title = findViewById(R.id.add_class_title);
        time_start = findViewById(R.id.activity_addclass_starttime);
        time_end = findViewById(R.id.activity_addclass_endtime);

        textTimeStart = findViewById(R.id.activity_addclass_starttimetext);
        textTimeEnd = findViewById(R.id.activity_addclass_endtimetext);

        title.setText("ویرایش کلاس");

        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Day_of_week);
        day_spinner.setAdapter(a);
        set_item();
    }

    void set_item() {
        try {
            String timestart[], timeend[];
            data.open();
            int cunt = data.count("dars");
            for (int i = 0; i < cunt; i++) {
                if (ID_Class.equals(data.Display("dars", i, 0))) {
                    day = data.Display("dars", i, 2);
                    Code_class = data.Display("dars", i, 6);
                    Class = data.Display("dars", i, 9);
                    break;
                }
            }
            data.close();
            textTimeEnd.setText(End_Class);
            textTimeStart.setText(Start_Class);
            class_edit.setText("" + Class);
            name_edit.setText("" + Name_Class);
            code_edit.setText("" + Code_class);
            location_edit.setText("" + Location_Class);
            day_spinner.setSelection(Integer.parseInt(day));
        } catch (Exception e) {
            Log.i(TAG,"Error"+e.toString());
        }
    }

    boolean updata() {
        boolean resualt = false;
        int day_of = day_spinner.getSelectedItemPosition();

        try {
            if (!name_edit.getText().toString().equals(Name_Class)) {
                try {
                    data.open();
                    data.update_one("dars", "ndars", name_edit.getText().toString(), Integer.parseInt(ID_Class));
                    data.close();
                    resualt = true;
                } catch (Exception e) {
                }
            }
            if (!code_edit.getText().toString().equals(Code_class)) {
                try {
                    data.open();
                    data.update_one("dars", "Characteristic", code_edit.getText().toString(), Integer.parseInt(ID_Class));
                    data.close();
                    resualt = true;
                } catch (Exception e) {
                }
            }
            if (!location_edit.getText().toString().equals(Location_Class)) {
                try {
                    data.open();
                    data.update_one("dars", "location", location_edit.getText().toString(), Integer.parseInt(ID_Class));
                    data.close();
                    resualt = true;
                } catch (Exception e) {
                }
            }
            if (!class_edit.getText().toString().equals(Class)) {
                try {
                    data.open();
                    data.update_one("dars", "class", class_edit.getText().toString(), Integer.parseInt(ID_Class));
                    data.close();
                    resualt = true;
                } catch (Exception e) {
                }
            }
            if (day_of != Integer.parseInt(day)) {
                try {
                    data.open();
                    data.update_one("dars", "dey", String.valueOf(day_of), Integer.parseInt(ID_Class));
                    data.close();
                    resualt = true;
                } catch (Exception e) {
                }
            }
            if (!Start_Class.equals(textTimeStart.getText().toString())) {
                try {
                    data.open();
                    data.update_one("dars", "time", textTimeStart.getText().toString(), Integer.parseInt(ID_Class));
                    data.close();
                    resualt = true;
                } catch (Exception e) {
                }
            }
            if (!End_Class.equals(textTimeEnd.getText().toString())) {
                try {
                    data.open();
                    data.update_one("dars", "Minute", textTimeEnd.getText().toString(), Integer.parseInt(ID_Class));
                    data.close();
                    resualt = true;
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
        }
        return resualt;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

    }

}
