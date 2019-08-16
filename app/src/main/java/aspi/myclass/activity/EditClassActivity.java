package aspi.myclass.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import aspi.myclass.R;
import aspi.myclass.class_.dbstudy;


public class EditClassActivity extends Activity {

    private Button save,cancel;
    private TextView name_text,code_text,timeS_text,timeE_text,location_text,day_of_week_text,class_text,title;
    private EditText name_edit,code_edit,location_edit,class_edit;
    private Spinner day_spinner;
    private TimePicker time_start,time_end;
    private dbstudy data;
    public static String[] Day_of_week={"شنبه","یکشنبه","دوشنبه","سه شنبه","چهارشنبه","پنج شنبه","جمعه"};
    public static String Name_Class,Start_Class,End_Class,ID_Class,Location_Class,Code_class,day,Class;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_addclass);
        data=new dbstudy(this);
        config();
        //******************************************************************************************
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (updata()){
                    TOAST("ویرایش با موفقیت انجام شد...!");
                    Go_main();
                }else{
                    TOAST("هیچ تغییری برای ویرایش اعمال نشده ...!");
                }
            }
        });
        //******************************************************************************************
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Go_main();
            }
        });
        //******************************************************************************************
    }
    void Go_main(){
        finish();
    }
    void config(){
        save= (Button) findViewById(R.id.add_class_save);
        cancel= (Button) findViewById(R.id.add_class_cancel);
        class_text= (TextView) findViewById(R.id.add_class_class_text);
        name_text= (TextView) findViewById(R.id.add_class_name_class_text);
        code_text= (TextView) findViewById(R.id.add_class_code_class_text);
        timeS_text= (TextView) findViewById(R.id.add_class_start_time_text);
        timeE_text= (TextView) findViewById(R.id.add_class_end_time_text);
        location_text= (TextView) findViewById(R.id.add_class_potion_text);
        day_of_week_text=(TextView) findViewById(R.id.add_class_day_text);
        class_edit= (EditText) findViewById(R.id.add_class_class_edit);
        name_edit= (EditText) findViewById(R.id.add_class_name_edit);
        code_edit= (EditText) findViewById(R.id.add_class_code_edit);
        location_edit= (EditText) findViewById(R.id.add_class_potion_edit);
        day_spinner= (Spinner) findViewById(R.id.add_class_spinner);
        time_start= (TimePicker) findViewById(R.id.add_class_start_time_picker);
        time_end= (TimePicker) findViewById(R.id.add_class_end_time_picker);
        title= (TextView) findViewById(R.id.add_class_title);
        ArrayAdapter<String> a=new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Day_of_week);
        day_spinner.setAdapter(a);
        FONT(MainActivity.FONTS);
        time_start.setIs24HourView(true);
        time_end.setIs24HourView(true);
        set_item();
    }
    void FONT(Typeface font_text){
        class_text.setTypeface(font_text);
        class_edit.setTypeface(font_text);
        name_text.setTypeface(font_text);
        code_text.setTypeface(font_text);
        timeS_text.setTypeface(font_text);
        timeE_text.setTypeface(font_text);
        location_text.setTypeface(font_text);
        name_edit.setTypeface(font_text);
        code_edit.setTypeface(font_text);
        location_edit.setTypeface(font_text);
        save.setTypeface(font_text);
        cancel.setTypeface(font_text);
        day_of_week_text.setTypeface(font_text);
        //*************************************
    }
    void set_item(){
        try{
            String timestart[],timeend[];
            data.open();
            int cunt=data.count("dars");
            for (int i=0;i<cunt;i++){
                if (ID_Class.equals(data.Display("dars",i,0))){
                    day=data.Display("dars",i,2);
                    Code_class=data.Display("dars",i,6);
                    Class=data.Display("dars",i,9);
                    break;
                }
            }
            data.close();
            title.setText("ویرایش کلاس");
            timeend=End_Class.split(":");
            timestart=Start_Class.split(":");
            class_edit.setText(""+Class);
            name_edit.setText(""+Name_Class);
            code_edit.setText(""+Code_class);
            location_edit.setText(""+Location_Class);
            day_spinner.setSelection(Integer.parseInt(day));
            time_start.setCurrentHour(Integer.parseInt(timestart[0]));
            time_end.setCurrentHour(Integer.parseInt(timeend[0]));
            time_start.setCurrentMinute(Integer.parseInt(timestart[1]));
            time_end.setCurrentMinute(Integer.parseInt(timeend[1]));
        }catch (Exception e){
        }
    }
    boolean updata(){
        boolean resualt=false;
        int day_of=day_spinner.getSelectedItemPosition();
        int hour_start=time_start.getCurrentHour();
        int minute_start=time_start.getCurrentMinute();
        int hour_end=time_end.getCurrentHour();
        int minute_end=time_end.getCurrentMinute();
        String HS=String.valueOf(hour_start);
        String MS=String.valueOf(minute_start);
        String HE=String.valueOf(hour_end);
        String ME=String.valueOf(minute_end);
        if (HE.length()==1){
            HE="0"+HE;
        }
        if (HS.length()==1){
            HS="0"+HS;
        }
        if (MS.length()==1){
            MS="0"+MS;
        }
        if (ME.length()==1){
            ME="0"+ME;
        }
        try {
            if (!name_edit.getText().toString().equals(Name_Class)){
                try {
                    data.open();
                    data.update_one("dars","ndars",name_edit.getText().toString(),Integer.parseInt(ID_Class));
                    data.close();
                    resualt=true;
                }catch (Exception e){
                }
            }
            if (!code_edit.getText().toString().equals(Code_class)){
                try {
                    data.open();
                    data.update_one("dars","Characteristic",code_edit.getText().toString(),Integer.parseInt(ID_Class));
                    data.close();
                    resualt=true;
                }catch (Exception e){
                }
            }
            if (!location_edit.getText().toString().equals(Location_Class)){
                try {
                    data.open();
                    data.update_one("dars","location",location_edit.getText().toString(),Integer.parseInt(ID_Class));
                    data.close();
                    resualt=true;
                }catch (Exception e){
                }
            }
            if (!class_edit.getText().toString().equals(Class)){
                try {
                    data.open();
                    data.update_one("dars","class",class_edit.getText().toString(),Integer.parseInt(ID_Class));
                    data.close();
                    resualt=true;
                }catch (Exception e){
                }
            }
            if (day_of!=Integer.parseInt(day)){
                try {
                    data.open();
                    data.update_one("dars","dey",String.valueOf(day_of),Integer.parseInt(ID_Class));
                    data.close();
                    resualt=true;
                }catch (Exception e){
                }
            }
            if (!Start_Class.equals(String.valueOf(hour_start)+":"+String.valueOf(minute_start))){
                try {
                    data.open();
                    data.update_one("dars","time",String.valueOf(HS)+":"+String.valueOf(MS),Integer.parseInt(ID_Class));
                    data.close();
                    resualt=true;
                }catch (Exception e){
                }
            }
            if (!End_Class.equals(String.valueOf(hour_end)+":"+String.valueOf(minute_end))){
                try {
                    data.open();
                    data.update_one("dars","Minute",String.valueOf(HE)+":"+String.valueOf(ME),Integer.parseInt(ID_Class));
                    data.close();
                    resualt=true;
                }catch (Exception e){
                }
            }

        }catch (Exception e){
        }
        return resualt;
    }
    void TOAST(String TEXT) {
        Toast toast = Toast.makeText(this, "" + TEXT, Toast.LENGTH_LONG);
        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
        textView.setTextColor(getResources().getColor(R.color.toast));
        textView.setTypeface(MainActivity.FONTS);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextSize(18);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast);
        toast.show();
    }
}
