package aspi.myclass.activity;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.farsitel.bazaar.IUpdateCheckService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import aspi.myclass.content.ClassContent;
import aspi.myclass.class_.OtherMetod;
import aspi.myclass.R;
import aspi.myclass.class_.dbstudy;
import aspi.myclass.adapter.ClassViewAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Toolbar toolbar;
    public static Typeface font_1, font_2, font_3, FONTS;
    SharedPreferences sp;
    String Font, BACKUP_class = "", BACKUP_student = "", BACKUP_roll;
    public static int Size_Text, DAY, refresh = 0;
    Button Saturday, Sunday, Monday, Tuesday, Wednesday, Thursday, Friday;
    TextView text_day_class;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<ClassContent> List = new ArrayList<>();
    dbstudy data;
    public static String BUYAPP = "", status_number = "off";
    public static Timer time;
    public static File Backup_File_App = new File(Environment.getExternalStorageDirectory(), "BackupClass"), Address_file_app = new File(Environment.getExternalStorageDirectory(), "App_class");
    IUpdateCheckService service;
    UpdateServiceConnection connection;
    private static final String TAG = "TAG_Main";
    OtherMetod om = new OtherMetod();
    int t = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);
        data = new dbstudy(this);
        data.database();
        Config();
        //******************************************************************************************
        Saturday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                get_data_class(0);
            }
        });
        //******************************************************************************************
        Sunday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                get_data_class(1);
            }
        });
        //******************************************************************************************
        Monday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                get_data_class(2);
            }
        });
        //******************************************************************************************
        Tuesday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                get_data_class(3);
            }
        });
        //******************************************************************************************
        Wednesday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                get_data_class(4);
            }
        });
        //******************************************************************************************
        Thursday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                get_data_class(5);
            }
        });
        //******************************************************************************************
        Friday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                get_data_class(6);
            }
        });
        //******************************************************************************************
        initService();
    }

    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        sp = getApplicationContext().getSharedPreferences("myclass", 0);
        final float amozesh = sp.getFloat("LerningActivity", 0);
        if (id == R.id.nav_add_class) {
            if (amozesh == 0.5) SetCode(1);
            startActivity(new Intent(this, AddClassActivity.class));
        } else if (id == R.id.nav_abute) {
            om.Abute(MainActivity.this);
        } else if (id == R.id.nav_coment) {
            startActivity(new Intent(this, CommentActivity.class));
            finish();
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(this, SettingActivity.class));
            finish();
        } else if (id == R.id.nav_exit) {
            finish();
        } else if (id == R.id.nav_ClearDataBase) {
            Clear_DataBaese();
        } else if (id == R.id.nav_backup) {
            if (om.get_Data("‌Buy_App", "NO", MainActivity.this).equals("Buy_App")) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                final TextView input = new TextView(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setText("ایا شما میخواهید از اطلاعات خود فایل پشتیبان در حافظه ای زیر ایجاد کنید؟" + "\n" + Backup_File_App);
                input.setTypeface(FONTS);
                input.setTextSize(15);
                input.setTextColor(getResources().getColor(R.color.toast));
                input.setPadding(10, 10, 5, 0);
                builder1.setView(input);
                builder1.setTitle("پشتیبانی اطلاعات");
                builder1.setPositiveButton("پشتیبانی", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        BackUpDataBase();
                        if (amozesh == 34) {
                            SetCode(35);
                            Amozesh(false);
                        }
                    }
                });
                builder1.setNegativeButton("انصراف", null);
                AlertDialog aler1 = builder1.create();
                aler1.show();
            } else {
                om.Toast(MainActivity.this, "برای استفاده از این امکانات باید نسخه ای کامل برنامه را خریداری کنید.");
            }

        } else if (id == R.id.nav_upload) {
            if (om.get_Data("‌Buy_App", "NO", MainActivity.this).equals("Buy_App")) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                final TextView input = new TextView(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setText("ایا شما میخواهید از حافظه ای  " + "زیر" + " اطلاعات خود را بازخوانی کنید؟" + "\n" + Backup_File_App);
                input.setTypeface(FONTS);
                input.setTextSize(15);
                input.setTextColor(getResources().getColor(R.color.toast));
                input.setPadding(10, 10, 5, 0);
                builder1.setView(input);
                builder1.setTitle("بازخوانی اطلاعات");
                builder1.setPositiveButton("بازخوانی", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        upload_backup();
                        if (amozesh == 35) {
                            SetCode(36);
                            Amozesh(false);
                        }
                    }
                });
                builder1.setNegativeButton("انصراف", null);
                AlertDialog aler1 = builder1.create();
                aler1.show();
            } else {
                om.Toast(MainActivity.this, "برای استفاده از این امکانات باید نسخه ای کامل برنامه را خریداری کنید.");
            }
        } else if (id == R.id.nav_amozesh) {
            startActivity(new Intent(this, LerningActivity.class));
        } else if (id == R.id.buyapp) {
            if (om.get_Data("‌Buy_App", "NO", MainActivity.this).equals("NO")) {
                startActivity(new Intent(this, BuyAppActivity.class));
                finish();
            } else {
                om.Toast(MainActivity.this, "شما قبلا برنامه را خریداری کرده اید.");
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void Clear_DataBaese() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        final TextView input = new TextView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setText("با پاک کردن حافظه نرم افزار تمام اسامی ، درس ها و جلسات برگزار شده در نرم افزار ،حذف می شوند!");
        input.setTypeface(FONTS);
        input.setTextSize(15);
        input.setTextColor(getResources().getColor(R.color.toast));
        input.setPadding(10, 10, 5, 0);
        builder1.setView(input);
        builder1.setTitle("پاک کردن حافظه نرم افزار");
        builder1.setPositiveButton("پاک کردن", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Clear_databaese();
            }
        });
        builder1.setNegativeButton("انصراف", null);
        AlertDialog aler1 = builder1.create();
        aler1.show();
    }

    void Clear_databaese() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, R.style.NewDialog);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setProgress(0);
        progressDialog.setProgressDrawable(getResources().getDrawable(R.drawable.dialog));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("پاک کردن حافظه");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("لطفا صبر کنید ...!");
        progressDialog.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    data.open();
                    data.delete_("klas", "");
                    data.delete_("dars", "");
                    data.delete_("rollcall", "");
                    data.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            om.Toast(MainActivity.this, "حافظه نرم افزار با موفقیت پاک شد...!");
                            refresh = 1;
                        }
                    });
                } catch (Exception e) {

                }
                progressDialog.cancel();
            }
        }).start();

    }

    void Config() {
        sp = getApplicationContext().getSharedPreferences("myclass", 0);
        BUYAPP = sp.getString("‌Buy_App", "NO");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Saturday = (Button) findViewById(R.id.main_Saturday);
        Sunday = (Button) findViewById(R.id.main_Sunday);
        Monday = (Button) findViewById(R.id.main_Monday);
        Tuesday = (Button) findViewById(R.id.main_Tuesday);
        Wednesday = (Button) findViewById(R.id.main_Wednesday);
        Thursday = (Button) findViewById(R.id.main_Thursday);
        Friday = (Button) findViewById(R.id.main_Friday);
        text_day_class = (TextView) findViewById(R.id.main_text_day_class);
        //****************************************************************
        Calendar calendar = Calendar.getInstance();
        DAY = calendar.get(Calendar.DAY_OF_WEEK);
        if (DAY == 7) DAY = 0;
        get_data_class(DAY);
        refresh();
        //****************************************************************
        if (!Address_file_app.exists()) {
            Address_file_app.mkdir();
        }
        if (!Backup_File_App.exists()) {
            Backup_File_App.mkdir();
        }

    }

    void Set_day() {
        if (DAY == 0) {
            text_day_class.setText(" " + "کلاس های روز شنبه" + " ");
        } else if (DAY == 1) {
            text_day_class.setText(" " + "کلاس های روز یکشنبه" + " ");
        } else if (DAY == 2) {
            text_day_class.setText(" " + "کلاس های روز دوشنبه" + " ");
        } else if (DAY == 3) {
            text_day_class.setText(" " + "کلاس های روز سه شنبه" + " ");
        } else if (DAY == 4) {
            text_day_class.setText(" " + "کلاس های روز چهارشنبه" + " ");
        } else if (DAY == 5) {
            text_day_class.setText(" " + "کلاس های روز پنج شنبه" + " ");
        } else if (DAY == 6) {
            text_day_class.setText(" " + "کلاس های روز جمعه" + " ");
        }
    }

    void get_data_class(int day) {

        try {
            DAY = day;
            String Name = "", Start = "", End = "", Id = "", Location = "", Class_ = "", Id_class = "", Characteristic = "", txt_class = "";
            data.open();
            int cunt = data.count("dars");
            boolean chek = false;
            for (int i = 0; i < cunt; i++) {
                int Day = Integer.parseInt(data.Display("dars", i, 2));
                if (Day == day) {
                    Name += data.Display("dars", i, 1) + "~";
                    Start += data.Display("dars", i, 3) + "~";
                    End += data.Display("dars", i, 5) + "~";
                    Id += data.Display("dars", i, 0) + "~";
                    Id_class += data.Display("dars", i, 8) + "~";
                    Location += data.Display("dars", i, 4) + "~";
                    Class_ += data.Display("dars", i, 9) + "~";
                    Characteristic += data.Display("dars", i, 6) + "~";
                    txt_class += data.Display("dars", i, 7) + " ~";
                    chek = true;
                }
            }
            data.close();

            if (chek) {
                sp = getApplicationContext().getSharedPreferences("myclass", 0);
                float amozesh = sp.getFloat("LerningActivity", 0);
                if (amozesh == 2.0) SetCode(3);
                String[] name, start, end, id, location, class_, id_class, characteristic, Text_class;
                name = Name.split("~");
                start = Start.split("~");
                end = End.split("~");
                id = Id.split("~");
                id_class = Id_class.split("~");
                location = Location.split("~");
                class_ = Class_.split("~");
                characteristic = Characteristic.split("~");
                Text_class = txt_class.split("~");
                List.clear();
                for (int i = 0; i < name.length; i++) {
                    ClassContent content = new ClassContent();
                    content.APP = chek;
                    content.name_class = name[i];
                    content.time_start = start[i];
                    content.time_end = end[i];
                    content.id = id[i];
                    content.location = location[i];
                    content.Class_ = class_[i];
                    content.id_class = id_class[i];
                    content.characteristic = characteristic[i];
                    content.text_class = Text_class[i];
                    List.add(content);
                }
                recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                linearLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(new ClassViewAdapter(List, MainActivity.this, MainActivity.this));
                Amozesh(true);
            } else {
                List.clear();
                recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                linearLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(new ClassViewAdapter(List, MainActivity.this, MainActivity.this));
            }
            Set_day();
        } catch (Exception e) {
        }

    }

    public void refresh() {

        time = new Timer();
        time.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.i(TAG, "Time : " + t++);
                        if (refresh == 1) {
                            get_data_class(DAY);
                            refresh = 0;
                            Amozesh(false);
                        } else if (refresh == 2) {
                            refresh = 0;
                            finish();
                        }
                    }
                });
            }
        }, 1, 1000);
    }

    void write(String name, String Text) {
        try {
            File Backups = new File(Backup_File_App, name);
            FileWriter fileWriter = new FileWriter(Backups);
            fileWriter.append(Text);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    void BackUpDataBase() {
        try {
            try {
                data.open();
                int cunt_class = data.count("dars");
                int cunt_student = data.count("klas");
                int cunt_roll = data.count("rollcall");
                BACKUP_class = "";
                for (int i = 0; i < cunt_class; i++) {
                    BACKUP_class += data.Display_all("dars", i, 1, 9) + "\n";
                }
                for (int i = 0; i < cunt_student; i++) {
                    BACKUP_student += data.Display_all("klas", i, 1, 8) + "\n";
                }
                for (int i = 0; i < cunt_roll; i++) {
                    BACKUP_roll += data.Display_all("rollcall", i, 1, 9) + "\n";
                }
                data.close();
                write("class", BACKUP_class);
                write("student", BACKUP_student);
                write("rollcall", BACKUP_roll);
                om.Toast(MainActivity.this, "فایل پشتیبانی گرفته شد...!");
            } catch (Exception e) {
                Log.i(TAG, e.toString());
            }
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }

    void upload_backup() {
        Calendar c = Calendar.getInstance();
        final String NC = "" + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND) + "";
        final String Class[] = ReadFileExternal("/class").split("%");
        final String Student[] = ReadFileExternal("/student").split("%");
        final String Rollcall[] = ReadFileExternal("/rollcall").split("%");
        new Thread(new Runnable() {
            public void run() {
                try {
                    data.open();
                    for (int i = 0; i < Class.length; i++) {
                        String[] item = Class[i].split("~");
                        data.insert_class2(item[0], item[1], item[2], item[3], item[4], item[5], item[6], NC + item[7], item[8]);
                    }
                    for (int i = 0; i < Student.length; i++) {
                        String[] item = Student[i].split("~");
                        data.insert_student2(item[0], item[1], item[2], item[3], NC + item[4]);
                    }
                    for (int i = 0; i < Rollcall.length; i++) {
                        String[] item = Rollcall[i].split("~");
                        boolean status = true;
                        if (item[1].equals("0")) status = false;
                        data.insert_Rollcall(item[0], status, item[2], item[3], item[4], item[5], NC + item[6], item[7] + NC, item[8]);
                    }
                    data.close();
                    om.Toast(MainActivity.this, "بارگزاری انجام شد...!");
                    refresh = 1;
                } catch (Exception e) {
                }
            }
        }).start();
    }

    String ReadFileExternal(String name) {
        String Return = "", Line = "", External = Backup_File_App.toString();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(External + name));
            while ((Line = bufferedReader.readLine()) != null) {
                Return += Line.replace("%", "").replace("null", "") + "%";
            }
        } catch (Exception e) {
            Return = "";
        }
        return Return;
    }

    class UpdateServiceConnection implements ServiceConnection {
        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = IUpdateCheckService.Stub
                    .asInterface((IBinder) boundService);
            try {
                long vCode = service.getVersionCode("aspi.myclass");
                if (vCode != -1) {
                    om.Qusins(MainActivity.this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onServiceConnected(): Connected");
        }

        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Log.d(TAG, "onServiceDisconnected(): Disconnected");
        }
    }

    void initService() {
        Log.i(TAG, "initService()");
        connection = new UpdateServiceConnection();
        Intent i = new Intent("com.farsitel.bazaar.service.UpdateCheckService.BIND");
        i.setPackage("com.farsitel.bazaar");
        boolean ret = bindService(i, connection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "initService() bound value: " + ret);
    }

    void Amozesh(final boolean chek) {
        sp = getApplicationContext().getSharedPreferences("myclass", 0);
        float amozesh = sp.getFloat("LerningActivity", 0);
        if (amozesh > 0) {
            if (amozesh > 0.5) {
                if (amozesh > 2.0) {
                    if (amozesh > 3.0) {
                        if (amozesh > 7.0) {
                            if (amozesh > 15.0) {
                                if (amozesh > 19.0) {
                                    if (amozesh > 21.0) {
                                        if (amozesh > 23.0) {
                                            if (amozesh > 25.0) {
                                                if (amozesh > 27.0) {
                                                    if (amozesh > 34.0) {
                                                        if (amozesh == 36.0) {
                                                            Mesage("موفق و پیروز باشید!");
                                                            SetCode(37);
                                                        } else if (amozesh == 35.0) {
                                                            drawer.openDrawer(GravityCompat.START);
                                                            Mesage("شما همچنین می توانید فایل پشتیبانی هر ترم را در داخل پوشه ی BackupClass قرار دهد و با انتخاب گزینه ی بارگیری فایل پشتیبانی اطلاعات ترم مورد نظرتان را به برنامه اضافه کنید.گزینه ی بارگیری فایل پشتیبانی را انتخاب کنید.");
                                                        }
                                                    } else if (amozesh == 34.0) {
                                                        drawer.openDrawer(GravityCompat.START);
                                                        Mesage("شما می توانید زمانی پایان ترم از اطلاعات تمامی کلاس های تشکیل شده در برنامه فایل پشتیبانی تهیه کنید .از منو کشویی برنامه گزینه ی پشتیبان گیری از نرم افزار را انتخاب کنید ،با انتخاب این گزینه برای شما سه فایل پشتیبان گیری از اطلاعات کلاس های شما گرفته می شود و در حافظه ی اصلی گوشی در پوشه ی  BackupClass ذخیره می شود.");
                                                    }
                                                } else if (amozesh == 27.0) {
                                                    Mesage("درس را انتخاب کنید.");
                                                    SetCode(28);
                                                }
                                            } else if (amozesh == 25.0) {
                                                Mesage("پس از ثبت کلاس در دیگر ایام هفته تمام اطلاعات کلاس از جمله اطلاعات دانشجویان و جلسات برگزار شده انتقال می یابد." + "\n درس را انتخاب کنید.");
                                                SetCode(26);
                                            }
                                        } else if (amozesh == 23.0) {
                                            Mesage("درس را انتخاب کنید.");
                                            SetCode(24);
                                        }
                                    } else if (amozesh == 21.0) {
                                        Mesage("درس را انتخاب کنید.");
                                        SetCode(22);
                                    }
                                } else if (amozesh == 19.0) {
                                    Mesage("درس را انتخاب کنید.");
                                    SetCode(20);
                                }
                            } else if (amozesh == 15.0) {
                                Mesage("با ایجاد جلسه جدید کلاس شما می توانید به جلسات گذشته دسترسی داشته باشید. درس را انتخاب کنید.");
                                SetCode(16);
                            }
                        } else if (amozesh == 7.0) {
                            Mesage("اکنون شما می توانید برای کلاس خود جلسه تشکیل دهد.درس را انتخاب کنید.");
                        }
                    } else if (chek) {
                        Mesage("کلاس را انتخاب کنید تا برای شما گزینه های مختلف نشان داده شود.");
                    }
                } else {
                    Mesage("روزی که کلاس را در آن ثبت کرده اید انتخاب کنید.");
                }
            } else {
                drawer.openDrawer(GravityCompat.START);
                Mesage("برای ایجاد کلاس جدید از منو کشویی برنامه گزینه اضافه کردن کلاس جدید را انتخاب کنید");
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setIcon(R.drawable.play);

            builder.setTitle("آموزش").setMessage("آیا می خواهید آموزش برنامه اجرا شود؟");
            builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    SetCode((float) 0.5);
                    Amozesh(false);
                }
            });
            builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    SetCode(500);
                }
            });
            AlertDialog aler = builder.create();
            aler.show();
        }
    }

    void Mesage(String text) {
        final Dialog massege = new Dialog(MainActivity.this, R.style.NewDialog);
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

    protected void onResume() {
        super.onResume();
        Amozesh(false);
    }

    void SetCode(float code) {
        sp = getApplicationContext().getSharedPreferences("myclass", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat("LerningActivity", code);
        edit.commit();
    }


}
