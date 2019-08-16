package aspi.myclass.activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import aspi.myclass.content.AbsentPersentContent;
import aspi.myclass.R;
import aspi.myclass.adapter.StudentViewAdapter;
import aspi.myclass.class_.dbstudy;

public class OldClassActivity extends Activity {

    private TextView name_class, DATA,Time,Titel;
    public static String Name_class, did_class,Data_class,Jalase,HOUR;
    public static int Row;
    private dbstudy data;
    private RecyclerView recyclerView_Old;
    private LinearLayoutManager linearLayoutManagers;
    public static java.util.List<AbsentPersentContent> List = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_newclass);
        data=new dbstudy(this);
        config();
        //************************************************
    }

    void config() {
        name_class = (TextView) findViewById(R.id.new_class_name_class);
        DATA = (TextView) findViewById(R.id.new_class_data);
        Time= (TextView) findViewById(R.id.old_classsss_time);
        Titel= (TextView) findViewById(R.id.New_class_titel);
        //*************************************************************
        name_class.setTypeface(MainActivity.FONTS);
        DATA.setTypeface(MainActivity.FONTS);
        Time.setTypeface(MainActivity.FONTS);
        //*************************************************************
        Titel.setText("لیست گذشته کلاس");
        Time.setText("ساعت: "+HOUR);
        name_class.setText("کلاس " + Name_class);
        DATA.setText("" + Data_class);
        //*************************************************************
        //Get_database();
        Get_qury();
    }
    void TOAST(String TEXT) {
        Toast toast = Toast.makeText(this, "" + TEXT, Toast.LENGTH_LONG);
        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
        textView.setTextColor(getResources().getColor(R.color.toast));
        textView.setTypeface(MainActivity.FONTS);
        textView.setTextSize(18);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast);
        toast.show();
    }
    void Get_database(){
       /* try {
            String Id_rull="",Sno="",Status="",Nomreh="",Id_student = "",Name="",Family="",Text="";
            String[] id_rull,sno,status,nomreh,id_student,name,family,text;
            data.open();
            int cunt_rull=data.count("rollcall"),Cunter=0;
//**************************************************************************************************
            for (int i=Row;i<cunt_rull;i++){
                String GET[]=data.Display_Rullcall2old(i).split("~");
                if (did_class.equals(GET[5]) && Jalase.equals(GET[6]) && Data_class.equals(GET[4])){
                    Id_rull+=GET[0]+"~";
                    Sno+=GET[1]+"~";
                    Status+=GET[2]+"~";
                    Nomreh+=GET[3]+" ~";
                    Cunter+=1;
                }else{
                    break;
                }
            }
//**************************************************************************************************
            sno=Sno.split("~");
            id_rull=Id_rull.split("~");
            status=Status.split("~");
            nomreh=Nomreh.split("~");
//**************************************************************************************************
            int cunt_student=data.count("klas");
            for (int i=0,j=0;i<cunt_student;i++){
                String[] GET=data.Display_Class("klas",i).split("~");
                for (int z=0;z<Cunter;z++){
                    if (sno[z].equals(GET[1]) && did_class.equals(GET[5])){
                        Id_student+=GET[0]+"~";
                        Family+=GET[2]+"~";
                        Name+=GET[3]+"~";
                        Text+=GET[4]+" ~";
                        j+=1;
                        break;
                    }
                }
                if (j==Cunter) break;
            }
            data.close();
//**************************************************************************************************
            id_student=Id_student.split("~");
            family=Family.split("~");
            name=Name.split("~");
            text=Text.split("~");
//**************************************************************************************************
            List.clear();
            for (int i = 0; i < Cunter; i++) {
                AbsentPersentContent content = new AbsentPersentContent();
                content.id_rull=id_rull[i];
                content.id=id_student[i];
                content.family=family[i];
                content.name=name[i];
                content.status=status[i];
                content.nomreh=nomreh[i];
                content.text=text[i];
                content.sno=sno[i];
                List.add(content);
            }
            recyclerView_Old = (RecyclerView) findViewById(R.id.recyclerview_new_classsss);
            linearLayoutManagers = new LinearLayoutManager(this);
            recyclerView_Old.setLayoutManager(linearLayoutManagers);
            recyclerView_Old.setHasFixedSize(true);
            recyclerView_Old.setAdapter(new StudentViewAdapter(List, MainActivity.FONTS, OldClassActivity.this));
            LerningActivity(false);
//**************************************************************************************************
        }catch (Exception e){
            TOAST(e.toString());
        }*/
//**************************************************************************************************
    }
    void Amozesh(final boolean chek){
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
        float amozesh = sp.getFloat("LerningActivity",0);
        if (amozesh==18){
            Mesage("شما در این صفحه اطلاعات مربوط به جلسه ی برگزار شده تاریخ مورد نظرتان را مشاهده می کنین و می توانید تغییرات خود را اعمال کنید.پس از ثبت تغییرات خود به صفحه ی اصلی برنامه برگردید.");
            SetCode(19);
        }
    }
    void Mesage(String text){
        final Dialog massege = new Dialog(OldClassActivity.this,R.style.MyAlertDialogStyle);
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
    void SetCode( float code) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat("LerningActivity", code);
        edit.commit();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    void Get_qury(){
        try {
            List.clear();
            data.open();
            String Res=data.Qury_old_class("SELECT  r.id,r.sno,r.abs,r.am,r.iddars,r.jalase,k.id,k.name,k.family,k.sno,k.tx,k.did" +
                    " FROM klas AS k,rollcall AS r WHERE r.iddars= " + Integer.parseInt(did_class) +
                    " AND r.jalase= '"+Jalase+"' AND r.sno = k.sno AND r.iddars = k.did ");
            data.close();

            String Id_rull="",Sno="",Status="",Nomreh="",Id_student = "",Name="",Family="",Text="";
            String[] id_rull,sno,status,nomreh,id_student,name,family,text;
            int Cunter=0;

            if(!Res.equals("")){
                for (int i = 0, j = 0, start = 0; i < Res.length(); i++) {
                    if (Res.charAt(i) == '|') {
                        String TE = Res.substring(start, i);
                        start = i + 1;
                        if (j == 0) {
                            Id_rull = TE;
                            j += 1;
                        } else if (j == 1) {
                            Sno = TE;
                            j += 1;
                        } else if (j == 2) {
                            Status = TE;
                            j += 1;
                        } else if (j == 3) {
                            Nomreh = TE;
                            j += 1;
                        } else if (j == 4) {
                            Id_student = TE;
                            j += 1;
                        } else if (j == 5) {
                            Family = TE;
                            j += 1;
                        } else if (j == 6) {
                            Name = TE;
                            j += 1;
                        } else if (j == 7) {
                            Text = TE;
                            j += 1;
                        } else if (j == 8) {
                            Cunter = Integer.parseInt(TE);
                            j += 1;
                        }
                    }

                }
            }

            sno=Sno.split("~");
            id_rull=Id_rull.split("~");
            status=Status.split("~");
            nomreh=Nomreh.split("~");
            id_student=Id_student.split("~");
            family=Family.split("~");
            name=Name.split("~");
            text=Text.split("~");
            for (int i = 0; i < Cunter; i++) {
                AbsentPersentContent content = new AbsentPersentContent();
                content.id_rull=id_rull[i];
                content.id=id_student[i];
                content.family=family[i];
                content.name=name[i];
                content.status=status[i];
                content.nomreh=nomreh[i]+" ";
                content.text=text[i]+" ";
                content.sno=sno[i];
                List.add(content);
            }
            recyclerView_Old = (RecyclerView) findViewById(R.id.recyclerview_new_classsss);
            linearLayoutManagers = new LinearLayoutManager(this);
            recyclerView_Old.setLayoutManager(linearLayoutManagers);
            recyclerView_Old.setHasFixedSize(true);
            recyclerView_Old.setAdapter(new StudentViewAdapter(List, MainActivity.FONTS, OldClassActivity.this));
            Amozesh(false);

//**************************************************************************************************
        }catch (Exception e){
            TOAST(e.toString());
        }
//**************************************************************************************************
    }
}
