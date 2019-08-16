package aspi.myclass.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import aspi.myclass.content.OldClassContent;
import aspi.myclass.R;
import aspi.myclass.adapter.ListCreateClassAdapter;
import aspi.myclass.class_.dbstudy;


public class OldClassListActivity extends Activity {

    public static String Name_class, id_class, refresh = "";
    private TextView name_class;
    private dbstudy data;
    private RecyclerView recyclerView2;
    private LinearLayoutManager linearLayoutManager;
    private java.util.List<OldClassContent> List = new ArrayList<>();
    public static Timer time;
    private ProgressDialog progressDialog;
    private int cunters = 0;
    private boolean view = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldclasslist);
        data = new dbstudy(this);
        config();
        //************************************************
    }

    void config() {
        name_class = (TextView) findViewById(R.id.show_list_old_class_name_class);
        //***********************************************************************
        name_class.setTypeface(MainActivity.FONTS);
        //***********************************************************************
        name_class.setText("" + Name_class);
        //***********************************************************************
        Start();
        refresh();
    }

    void get_database() {
        try {
            List.clear();
            String jalase_old = "0", DATA = "", jalase = "", HOUR = "", Row = "";
            boolean STATUS = false;
            data.open();
            int cunt_rll = data.count("rollcall"), cual = 0;
            progressDialog.setMax(cunt_rll);
            for (int i = 0; i < cunt_rll; i++) {
                String GET[] = data.Display_Rullcall("rollcall", i).split("~");
                if (!jalase_old.equals(GET[0]) && id_class.equals(GET[1])) {
                    jalase_old = GET[0];
                    DATA += GET[2] + "/" + GET[3] + "/" + GET[4] + "~";
                    jalase += GET[0] + "~";
                    HOUR += GET[5] + "~";
                    Row += String.valueOf(i) + "~";
                    STATUS = true;
                    cual += 1;
                    progressDialog.setMax(cunt_rll + cual);
                }
                progressDialog.setProgress(i);
            }
            data.close();
            if (STATUS) {
                String[] Data, Jalase, Hour, row;
                Data = DATA.split("~");
                Jalase = jalase.split("~");
                Hour = HOUR.split("~");
                row = Row.split("~");
                for (int i = 0; i < cual; i++) {
                    OldClassContent content = new OldClassContent();
                    content.DATA = Data[i];
                    content.jalase = Jalase[i];
                    content.Hour = Hour[i];
                    content.Row_rollcall = row[i];
                    List.add(content);
                    progressDialog.setProgress(cunt_rll + i);
                }
                view = true;
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        TOAST("هیچ جلسه ای برای کلاس تشکیل نشده...!");
                        finish();
                    }
                });
            }
        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //    TOAST("" + e.toString());
                }
            });
        }
    }

    public void refresh() {
        time = new Timer();
        time.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (refresh.equals("1")) {
                            cunters = 0;
                            refresh = "";
                        }
                    }
                });
            }
        }, 1, 50);
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

    void Start() {
        final Timer time = new Timer();
        time.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        cunters += 1;
                        if (cunters == 1) {
                            progressDialog = new ProgressDialog(OldClassListActivity.this);
                            //progressDialog.setProgress(0);
                            //progressDialog.setProgressDrawable(getResources().getDrawable(R.drawable.dialog));
                            //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressDialog.setTitle("درحال دریافت اطلاعات");
                            progressDialog.setCancelable(false);
                            progressDialog.setMessage("لطفا صبر کنید ...!");
                            progressDialog.show();
                            new Thread(new Runnable() {
                                public void run() {
                                    // get_database();
                                    Qury_database();
                                }
                            }).start();
                        }
                        if (view) {
                            view = false;
                            recyclerView2 = (RecyclerView) findViewById(R.id.show_list_old_class_recyclerview);
                            linearLayoutManager = new LinearLayoutManager(OldClassListActivity.this);
                            recyclerView2.setLayoutManager(linearLayoutManager);
                            recyclerView2.setHasFixedSize(true);
                            recyclerView2.setAdapter(new ListCreateClassAdapter(List, OldClassListActivity.this, OldClassListActivity.this));
                            progressDialog.cancel();
                            Amozesh(false);
                        }
                    }
                });
            }
        }, 1, 1000);
    }

    void Amozesh(final boolean chek) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
        float amozesh = sp.getFloat("LerningActivity", 0);
        if (amozesh == 17.0)
            Mesage("شما در این قسمت از برنامه می توانید با انتخاب گزینه ی حذف،جلسه ی مورد نظرتان را حذف کنید و یا با انتخاب گزینه ی نمایش،اطلاعات جلسه ی مورد نظرتان را مشاهده کنید و تغییرات مورد نظرتان را اعمال کنید و نیز می توانید با انتخاب تاریخ هر جلسه ، تاریخ را ویرایش کنید.گزینه ی نمایش را انتخاب کند.");
    }

    void Mesage(String text) {
        final Dialog massege = new Dialog(OldClassListActivity.this, R.style.NewDialog);
        massege.requestWindowFeature(Window.FEATURE_NO_TITLE);
        massege.setContentView(R.layout.dialog_message);
        massege.setCancelable(false);
        massege.setCanceledOnTouchOutside(false);
        massege.show();
        final TextView ok = (TextView) massege.findViewById(R.id.massge_btn);
        final TextView txt = (TextView) massege.findViewById(R.id.massge_text);
        //**********************************************************************
        txt.setText("" + text);
        txt.setTypeface(Typeface.createFromAsset(getAssets(), "Font/font2.ttf"));
        ok.setTypeface(Typeface.createFromAsset(getAssets(), "Font/font2.ttf"));
        //**********************************************************************
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                massege.dismiss();
            }
        });
    }

    void SetCode(float code) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat("LerningActivity", code);
        edit.commit();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    void Qury_database() {
        try {
            List.clear();
            String jalase_old = "0", DATA = "", jalase = "", HOUR = "", Row = "", Res = "";
            int cual=0;
            boolean STATUS = false;
            data.open();
            Res = data.Get_old_class(id_class);
            data.close();
            if(!Res.equals("")){
                STATUS = true;

                for(int i=0,j=0,start=0;i<Res.length();i++){

                    if(Res.charAt(i)=='|'){

                        String TEXT_RES=Res.substring(start,i);
                        start=i+1;
                        if(j==0){
                            DATA=TEXT_RES;
                            j+=1;
                        }else if(j==1){
                            jalase=TEXT_RES;
                            j+=1;
                        }else if(j==2){
                            HOUR=TEXT_RES;
                            j+=1;
                        }else if(j==3){
                            cual=Integer.parseInt(TEXT_RES);
                            j+=1;
                        }

                    }

                }
            }
  /*          final String TO=String.valueOf(cual);
            runOnUiThread(new Runnable() {
                public void run() {
                    TOAST(""+TO);
                }
            });
*/

            if (STATUS) {

                String[] Data, Jalase, Hour, row;
                Data = DATA.split("~");
                Jalase = jalase.split("~");
                Hour = HOUR.split("~");
                //row = Row.split("~");

                for (int i = 0; i < cual; i++) {

                    OldClassContent content = new OldClassContent();
                    content.DATA = Data[i];
                    content.jalase = Jalase[i];
                    content.Hour = Hour[i];
                    content.Row_rollcall = String.valueOf(i);
                    List.add(content);

                }
                view = true;
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        TOAST("هیچ جلسه ای برای کلاس تشکیل نشده...!");
                        finish();
                    }
                });
            }

        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TOAST("" + e.toString());
                }
            });
        }
    }
}

