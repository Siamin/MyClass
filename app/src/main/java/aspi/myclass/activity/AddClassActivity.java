package aspi.myclass.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import aspi.myclass.R;
import aspi.myclass.class_.OtherMetod;
import aspi.myclass.class_.dbstudy;


public class AddClassActivity extends Activity {

    EditText name_edit, code_edit, location_edit, class_edit;
    Spinner day_spinner;
    TimePicker time_start, time_end;
    dbstudy data;
    public static String[] Day_of_week = {"شنبه", "یکشنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنج شنبه", "جمعه"};
    OtherMetod om = new OtherMetod();
    ImageView backPage,save_data;

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
        time_start = (TimePicker) findViewById(R.id.add_class_start_time_picker);
        time_end = (TimePicker) findViewById(R.id.add_class_end_time_picker);
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Day_of_week);
        day_spinner.setAdapter(a);
        FONT(MainActivity.FONTS);
        time_start.setIs24HourView(true);
        time_end.setIs24HourView(true);

    }

    void FONT(Typeface font_text) {
        class_edit.setTypeface(font_text);
        name_edit.setTypeface(font_text);
        code_edit.setTypeface(font_text);
        location_edit.setTypeface(font_text);
    }

    boolean save() {
        int day_of = day_spinner.getSelectedItemPosition();
        int hour_start = time_start.getCurrentHour();
        int minute_start = time_start.getCurrentMinute();
        int hour_end = time_end.getCurrentHour();
        int minute_end = time_end.getCurrentMinute();
        String HS = String.valueOf(hour_start);
        String MS = String.valueOf(minute_start);
        String HE = String.valueOf(hour_end);
        String ME = String.valueOf(minute_end);
        if (HE.length() == 1) {
            HE = "0" + HE;
        }
        if (HS.length() == 1) {
            HS = "0" + HS;
        }
        if (MS.length() == 1) {
            MS = "0" + MS;
        }
        if (ME.length() == 1) {
            ME = "0" + ME;
        }
        boolean resualt;
        try {
            data.open();
            data.insert_class(name_edit.getText().toString().replace("~", ""), String.valueOf(day_of), String.valueOf(HS) + ":" + String.valueOf(MS), location_edit.getText().toString().replace("~", ""), String.valueOf(HE) + ":" + String.valueOf(ME), code_edit.getText().toString(), class_edit.getText().toString().replace("~", ""), "");
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
            om.Mesage(AddClassActivity.this,"ابتدا اطلاعات درس را وارد کنید سپس با انتخاب گزینه ذخیره اطلاعات مورد نظر را در برنامه ذخیره کنید.");
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
}
