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

import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import aspi.myclass.R;
import aspi.myclass.class_.OtherMetod;
import aspi.myclass.class_.dbstudy;


public class AddClassActivity extends Activity implements TimePickerDialog.OnTimeSetListener {

    EditText name_edit, code_edit, location_edit, class_edit;
    Spinner day_spinner;
    LinearLayout time_start, time_end;
    TextView textTimeStart, textTimeEnd;
    dbstudy data;
    public static String[] Day_of_week = {"شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنج شنبه", "جمعه"};
    OtherMetod om = new OtherMetod();
    ImageView backPage, save_data;
    String TIMEPICKER = "TimePickerDialog";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_addclass);
        data = new dbstudy(this);
        initView();
        //******************************************************************************************
        save_data.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (!name_edit.getText().toString().equals("")) {
                    if (!code_edit.getText().toString().equals("")) {
                        if (!location_edit.getText().toString().equals("")) {
                            if (!class_edit.getText().toString().equals("")) {
                                if (save()) {
                                    SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
                                    float amozesh = sp.getFloat("LerningActivity", 0);
                                    if (amozesh == 1.0) SetCode(2);
                                    MainActivity.refresh = 1;
                                    om.Toast(AddClassActivity.this,"کلاس "+name_edit.getText().toString()+" با موفقیت ثبت شد.");
                                    Go_main();
                                }
                            } else {
                                om.Toast(AddClassActivity.this, "لطفا شماره کلاس را وارد نمایید...!");
                            }
                        } else {
                            om.Toast(AddClassActivity.this, "لطفا مکان برگزاری کلاس را وارد نمایید...!");
                        }
                    } else {
                        om.Toast(AddClassActivity.this, "لطفا کد مشخصه درس را وارد نمایید...!");
                    }
                } else {
                    om.Toast(AddClassActivity.this, "لطفا نام درس را وارد نمایید...!");
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
                AddClassActivity.this,
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
        name_edit = (EditText) findViewById(R.id.add_class_name_edit);
        class_edit = (EditText) findViewById(R.id.add_class_class_edit);
        code_edit = (EditText) findViewById(R.id.add_class_code_edit);
        location_edit = (EditText) findViewById(R.id.add_class_potion_edit);
        day_spinner = (Spinner) findViewById(R.id.add_class_spinner);

        time_start = findViewById(R.id.activity_addclass_starttime);
        time_end = findViewById(R.id.activity_addclass_endtime);

        textTimeStart = findViewById(R.id.activity_addclass_starttimetext);
        textTimeEnd = findViewById(R.id.activity_addclass_endtimetext);


        textTimeStart.setText(om.Get_Time());
        textTimeEnd.setText(om.Get_Time());

        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Day_of_week);
        day_spinner.setAdapter(a);

    }

    boolean save() {
        int day_of = day_spinner.getSelectedItemPosition();
        boolean resualt;
        try {
            data.open();
            data.insert_class(name_edit.getText().toString().replace("~", ""), String.valueOf(day_of), textTimeStart.getText().toString(), location_edit.getText().toString().replace("~", ""), textTimeEnd.getText().toString(), code_edit.getText().toString(), class_edit.getText().toString().replace("~", ""), "");
            data.close();
            resualt = true;
        } catch (Exception e) {
            resualt = false;
        }
        return resualt;
    }

    void Amozesh() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
        float amozesh = sp.getFloat("LerningActivity", 0);
        if (amozesh == 1) {
            om.Mesage(AddClassActivity.this, "ابتدا اطلاعات درس را وارد کنید سپس با انتخاب گزینه ذخیره اطلاعات مورد نظر را در برنامه ذخیره کنید.");
        }
    }

    protected void onResume() {
        super.onResume();
        Amozesh();

    }

    void SetCode(float code) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat("LerningActivity", code);
        edit.commit();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

    }
}
