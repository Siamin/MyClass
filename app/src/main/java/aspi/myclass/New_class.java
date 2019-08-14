package aspi.myclass;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class New_class extends Activity {

    private TextView name_class, DATA;
    public static String Name_class, did;
    public static Typeface FONT;
    private dbstudy data;
    private RecyclerView recyclerView1;
    private LinearLayoutManager linearLayoutManager;
    public static List<Content_new_class> List = new ArrayList<>();
    private int MINUTE, HOUR;
    private String[] DATA_IRAN, data_;
    public Timer time;
    private ProgressDialog progressDialog;
    private int cunters = 0;
    private boolean view = false;
    private Cursor cursor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.new_class);
        //******************************************************************************************
        data = new dbstudy(this);
        config();
        //******************************************************************************************
    }

    void config() {
        name_class = (TextView) findViewById(R.id.new_class_name_class);
        DATA = (TextView) findViewById(R.id.new_class_data);
        //*************************************************************
        name_class.setTypeface(FONT);
        DATA.setTypeface(FONT);
        //*************************************************************
        name_class.setText("کلاس " + Name_class);
        DATA.setText("" + date_iran());
        //*************************************************************
        Start();
    }


    void get_data_for_database() {
       /* try {
            List.clear();
            String name_ = "", family_ = "", sno_ = "", text_ = "", id_ = "", abs = "", per = "";
            data.open();
            int cunt1 = 0, cunt = data.count("klas");
            progressDialog.setMax(cunt);
            for (int i = 0; i < cunt; i++) {
                String Class[] = data.Display_Class("klas", i).split("~");
                if (did.equals(Class[5])) {
                    id_ += Class[0] + "~";
                    sno_ += Class[1] + "~";
                    family_ += Class[2] + "~";
                    name_ += Class[3] + "~";
                    text_ += Class[4] + " ~";
                    abs += Class[7] + " ~";
                    per += Class[8] + " ~";
                    cunt1 += 1;
                    progressDialog.setMax(cunt + cunt1);
                }
                progressDialog.setProgress(i);
            }
            data.close();
            if (cunt1 > 0) {
                //**********************************************************************************
                String[] Name, Family, Sno, Text, Id, Abs, Per;
                Name = name_.split("~");
                Family = family_.split("~");
                Sno = sno_.split("~");
                Text = text_.split("~");
                Id = id_.split("~");
                Abs = abs.split("~");
                Per = per.split("~");
                DATA_IRAN = date_iran().split("/");
                data.open();
                int cunt_roll = data.count("rollcall");
                int jalase = 1;
                if (cunt_roll > 0) {
                    jalase = Integer.parseInt(data.Display("rollcall", (cunt_roll - 1), 8)) + 1;
                }
                for (int i = 0; i < cunt1; i++) {
                    Content_new_class content = new Content_new_class();
                    content.name = Name[i];
                    content.family = Family[i];
                    content.sno = Sno[i];
                    content.id = Id[i];
                    content.text = Text[i];
                    content.status = "1";
                    content.absent = Abs[i];
                    content.prezent = Per[i];
                    data.insert_Rollcall(Sno[i], true, "", DATA_IRAN[0], DATA_IRAN[1], DATA_IRAN[2], did, String.valueOf(jalase), String.valueOf(HOUR) + ":" + String.valueOf(MINUTE));
                    content.id_rull = data.Display("rollcall", cunt_roll, 0);
                    cunt_roll += 1;
                    List.add(content);
                    progressDialog.setProgress(cunt + i);
                }
                data.close();
                view = true;
                //**********************************************************************************
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        TOAST("هیچ دانشجویی برای این کلاس ثبت نشده...!");
                        go_main();
                    }
                });
            }
        } catch (Exception e) {
        }
*/
    }

    void go_main() {
        finish();
    }

    String date_iran() {
        Calendar c = Calendar.getInstance();
        int year = 0, month = 0, day = 0;
        int y = c.get(Calendar.YEAR);
        int x = c.get(Calendar.DAY_OF_YEAR);
        MINUTE = c.get(Calendar.MINUTE);
        HOUR = c.get(Calendar.HOUR_OF_DAY);
        //*******************************
        if (x >= 0 && x <= 20) {
            year = y - 622;
        } else if (x >= 21 && x <= 50) {
            year = y - 622;
        } else if (x >= 51 && x <= 79) {
            year = y - 622;
        } else if (x >= 80 && x <= 266) {
            year = y - 621;
        } else if (x >= 267 && x <= 365) {
            year = y - 621;
        }
        int mod = year % 33, kabise = 0;
        if (mod == 1 || mod == 5 || mod == 9 || mod == 13 || mod == 17 || mod == 22 || mod == 26 || mod == 30) {
            kabise = 1;
        } else {
            kabise = 0;
        }
        //*******************************
        if (x >= 0 && x <= 20) {
            month = 10;
            day = x + 10;
        } else if (x >= 21 && x <= 50) {
            month = 11;
            day = x - 20;
        } else if (x >= 51 && x <= 79 && kabise == 0) {
            month = 12;
            day = x - 40;
        } else if (x >= 51 && x <= 80 && kabise == 1) {
            month = 12;
            day = x - 49;
        } else if (x >= 80 && x <= 266 && kabise == 0) {
            x = x - 80;
            month = (x / 31) + 1;
            day = (x % 31) + 1;
        } else if (x >= 81 && x <= 266 && kabise == 1) {
            x = x - 79;
            month = (x / 31) + 1;
            day = (x % 31);
        } else if (x >= 267 && x <= 365) {
            x = x - 266;
            month = (x / 30) + 7;
            day = (x % 30) + 1;
        }
        String data = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
        return data;
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

    void Start() {
        final Timer time = new Timer();
        time.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        cunters += 1;
                        if (cunters == 1) {
                            progressDialog = new ProgressDialog(New_class.this);
                            //progressDialog.setProgress(0);
                            //progressDialog.setProgressDrawable(getResources().getDrawable(R.drawable.dialog));
                            //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressDialog.setTitle("درحال دریافت اطلاعات");
                            progressDialog.setCancelable(false);
                            progressDialog.setMessage("لطفا صبر کنید ...!");
                            progressDialog.show();
                            new Thread(new Runnable() {
                                public void run() {
                                    //get_data_for_database();
                                    GET_QURY();
                                }
                            }).start();
                        }
                        if (view) {
                            view = false;
                            recyclerView1 = (RecyclerView) findViewById(R.id.recyclerview_new_classsss);
                            linearLayoutManager = new LinearLayoutManager(New_class.this);
                            recyclerView1.setLayoutManager(linearLayoutManager);
                            recyclerView1.setHasFixedSize(true);
                            recyclerView1.setAdapter(new Recyclerview_content_show_new_class(List, FONT, New_class.this));
                            progressDialog.cancel();
                            time.cancel();
                        }
                    }
                });
            }
        }, 1, 1000);
    }

    void Amozesh(final boolean chek) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
        float amozesh = sp.getFloat("Amozesh", 0);
        if (amozesh == 9)
            Mesage("شما می توانید در این صفحه حضور،غیاب و نمرات دانشجویان را وارد کنید.وهمچنین می توانید با انتخاب تیک که روبه روی اسم دانشجو قرار دارد برای دانشجو غیبت وارد لیست نمایید.");
    }

    void Mesage(String text) {
        final Dialog massege = new Dialog(New_class.this, R.style.MyAlertDialogStyle);
        massege.setContentView(R.layout.massge);
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

    void GET_QURY() {
        try {
            int c = 0;
            List.clear();
            String name_ = "", family_ = "", sno_ = "", text_ = "", id_ = "", abs = "", per = "", Status = "", cun = "";

            data.open();
            Status = data.Get_name_student_of_class(did);
            data.close();

            if (!Status.equals("")) {

                for (int i = 0, j = 0, start = 0; i < Status.length(); i++) {

                    if (Status.charAt(i) == '|') {
                        String TE = Status.substring(start, i);
                        start = i + 1;

                        if (j == 0) {
                            id_ = TE;
                            j += 1;
                        } else if (j == 1) {
                            sno_ = TE;
                            j += 1;
                        } else if (j == 2) {
                            family_ = TE;
                            j += 1;
                        } else if (j == 3) {
                            name_ = TE;
                            j += 1;
                        } else if (j == 4) {
                            text_ = TE;
                            j += 1;
                        } else if (j == 5) {
                            abs = TE;
                            j += 1;
                        } else if (j == 6) {
                            per = TE;
                            j += 1;
                        } else if (j == 7) {
                            cun = TE;
                            j += 1;
                        }
                    }

                }

                c = Integer.parseInt(cun);

            } else {
                c = 0;
            }
            data.close();

            if (c > 0) {
                //**********************************************************************************
                String[] Name, Family, Sno, Text, Id, Abs, Per;
                Name = name_.split("~");
                Family = family_.split("~");
                Sno = sno_.split("~");
                Text = text_.split("~");
                Id = id_.split("~");
                Abs = abs.split("~");
                Per = per.split("~");

                DATA_IRAN = date_iran().split("/");

                data.open();
                int cunt_roll = data.count("rollcall");
                int jalase = 1;
                if (cunt_roll > 0) {
                    jalase = Integer.parseInt(data.Display("rollcall", (cunt_roll - 1), 8)) + 1;
                }

                //cunt_roll=Integer.parseInt(data.Display("rollcall", cunt_roll-1, 0))+1;
                for (int i = 0; i < c; i++) {
                    Content_new_class content = new Content_new_class();
                    content.name = Name[i];
                    content.family = Family[i];
                    content.sno = Sno[i];
                    content.id = Id[i];
                    content.text = Text[i];
                    content.status = "1";
                    content.absent = Abs[i];
                    content.prezent = Per[i];
                    data.insert_Rollcall(Sno[i], true, "", DATA_IRAN[0], DATA_IRAN[1], DATA_IRAN[2], did, String.valueOf(jalase), String.valueOf(HOUR) + ":" + String.valueOf(MINUTE));
                    content.id_rull = content.id_rull = data.Display("rollcall", cunt_roll, 0);
                    cunt_roll += 1;
                    List.add(content);
                }
                data.close();
                view = true;
                //**********************************************************************************
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        TOAST("هیچ دانشجویی برای این کلاس ثبت نشده...!");
                        go_main();
                    }
                });
            }

        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                public void run() {
                    // TOAST(""+e.toString());
                }
            });
        }
    }

    protected void onResume() {
        super.onResume();
        Amozesh(false);
    }

    void SetCode(float code) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat("Amozesh", code);
        edit.commit();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(New_class.this, R.style.MyAlertDialogStyle);
            final TextView input = new TextView(New_class.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            input.setText("تمام اطلاعات ذخیره شده است با زدن دکمه بازگشت اطلاعات حذف نمی گردد و در زمان ورود مجدد تغییرات حضور و غیاب جدید اعمال می گردد.");
            input.setTypeface(FONT);
            input.setTextSize(15);
            input.setTextColor(getResources().getColor(R.color.toast));
            input.setPadding(10, 10, 5, 0);
            builder1.setView(input);
            builder1.setTitle("بازگشت");
            builder1.setPositiveButton("بازگشت", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    go_main();
                }
            });
            builder1.setNegativeButton("ماندن در این صفحه", null);
            AlertDialog aler1 = builder1.create();
            aler1.show();
        }
        return super.onKeyDown(keyCode, event);
    }


}
