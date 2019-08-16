package aspi.myclass;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class Add_class extends Activity {

    private Button save,cancel;
    private TextView name_text,code_text,timeS_text,timeE_text,location_text,day_of_week_text,class_text;
    private EditText name_edit,code_edit,location_edit,class_edit;
    private Spinner day_spinner;
    private TimePicker time_start,time_end;
    private dbstudy data;
    public static String[] Day_of_week={"شنبه","یکشنبه","دوشنبه","سه شنبه","چهارشنبه","پنج شنبه","جمعه"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_addclass);
        data=new dbstudy(this);
        config();
        //******************************************************************************************
        save.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (!name_edit.getText().toString().equals("")){
                    if (!code_edit.getText().toString().equals("")){
                        if (!location_edit.getText().toString().equals("")){
                            if (!class_edit.getText().toString().equals("")){
                                if (save()) {
                                    SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
                                    float amozesh = sp.getFloat("Amozesh",0);
                                    if (amozesh==1.0) SetCode(2);
                                    Main.refresh=1;
                                    Go_main();
                                }
                            }else{
                                TOAST("لطفا شماره کلاس را وارد نمایید...!");
                            }
                        }else{
                            TOAST("لطفا مکان برگزاری کلاس را وارد نمایید...!");
                        }
                    }else{
                        TOAST("لطفا کد مشخصه درس را وارد نمایید...!");
                    }
                }else{
                    TOAST("لطفا نام درس را وارد نمایید...!");
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
        name_edit= (EditText) findViewById(R.id.add_class_name_edit);
        class_edit= (EditText) findViewById(R.id.add_class_class_edit);
        code_edit= (EditText) findViewById(R.id.add_class_code_edit);
        location_edit= (EditText) findViewById(R.id.add_class_potion_edit);
        day_spinner= (Spinner) findViewById(R.id.add_class_spinner);
        time_start= (TimePicker) findViewById(R.id.add_class_start_time_picker);
        time_end= (TimePicker) findViewById(R.id.add_class_end_time_picker);
        ArrayAdapter<String> a=new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Day_of_week);
        day_spinner.setAdapter(a);
        FONT(Main.FONTS);
        time_start.setIs24HourView(true);
        time_end.setIs24HourView(true);

    }
    void FONT(Typeface font_text){
        class_edit.setTypeface(font_text);
        class_text.setTypeface(font_text);
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
    boolean save(){
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
        boolean resualt;
        try{
            data.open();
            data.insert_class(name_edit.getText().toString().replace("~",""),String.valueOf(day_of),String.valueOf(HS)+":"+String.valueOf(MS),location_edit.getText().toString().replace("~",""),String.valueOf(HE)+":"+String.valueOf(ME),code_edit.getText().toString(),class_edit.getText().toString().replace("~",""),"");
            data.close();
            resualt=true;
        }catch (Exception e){
            resualt=false;
        }
        return resualt;
    }
    void TOAST(String TEXT) {
        Toast toast = Toast.makeText(this, "" + TEXT, Toast.LENGTH_LONG);
        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
        textView.setTextColor(getResources().getColor(R.color.toast));
        textView.setTypeface(Main.FONTS);
        textView.setTextSize(18);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast);
        toast.show();
    }
    void Amozesh(){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
        float amozesh = sp.getFloat("Amozesh",0);
        if (amozesh==1){
           Mesage("ابتدا اطلاعات درس را وارد کنید سپس با انتخاب گزینه ذخیره اطلاعات مورد نظر را در برنامه ذخیره کنید.");
        }
    }
    void Mesage(String text){
        final Dialog massege = new Dialog(Add_class.this,R.style.MyAlertDialogStyle);
        massege.setContentView(R.layout.dialog_message);
        massege.setCancelable(false);
        massege.setCanceledOnTouchOutside(false);
        massege.show();
        final TextView ok = (TextView) massege.findViewById(R.id.massge_btn);
        final TextView txt = (TextView) massege.findViewById(R.id.massge_text);
        //**********************************************************************
        txt.setText(""+text);
        txt.setTypeface(Typeface.createFromAsset(getAssets(), "Font/font2.ttf"));
        ok.setTypeface(Typeface.createFromAsset(getAssets(), "Font/font2.ttf"));
        //**********************************************************************
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                massege.dismiss();
            }
        });
    }
    protected void onResume() {
        super.onResume();
        Amozesh();

    }
    void SetCode( float code) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat("Amozesh", code);
        edit.commit();
    }
}
