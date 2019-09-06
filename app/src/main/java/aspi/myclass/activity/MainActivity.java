package aspi.myclass.activity;


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
import android.widget.Button;
import android.widget.TextView;

import com.farsitel.bazaar.IUpdateCheckService;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import aspi.myclass.Helpers.DialogHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.content.ClassContent;
import aspi.myclass.Tools.Tools;
import aspi.myclass.R;
import aspi.myclass.Helpers.DatabasesHelper;
import aspi.myclass.adapter.ClassViewAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {


    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Toolbar toolbar;
    public static Typeface FONTS;
    SharedPreferences sp;
    public static int DAY, refresh = 0;
    Button Saturday, Sunday, Monday, Tuesday, Wednesday, Thursday, Friday;
    TextView text_day_class;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<ClassContent> List = new ArrayList<>();
    DatabasesHelper data;
    public static String BUYAPP = "", status_number = "off";
    public static Timer time;
    public static File Backup_File_App = new File(Environment.getExternalStorageDirectory(), "BackupClass"), Address_file_app = new File(Environment.getExternalStorageDirectory(), "App_class");
    IUpdateCheckService service;
    UpdateServiceConnection connection;
    private static final String TAG = "TAG_Main";
    Tools om = new Tools();
    int t = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);

        data = new DatabasesHelper(this);
        data.database();

        initView();

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
            DialogHelper.Abute(MainActivity.this);
        } else if (id == R.id.nav_coment) {
            startActivity(new Intent(this, CommentActivity.class));
            finish();
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(this, SettingActivity.class));
            finish();
        } else if (id == R.id.nav_exit) {
            finish();
        } else if (id == R.id.nav_ClearDataBase) {
            DialogHelper.cleanDatabase(MainActivity.this, data);
        } else if (id == R.id.nav_backup) {
            if (SharedPreferencesHelper.get_Data("‌Buy_App", "NO", MainActivity.this).equals("Buy_App")) {
                DialogHelper.backupFile(MainActivity.this, "ایا شما میخواهید از اطلاعات خود فایل پشتیبان در حافظه ای زیر ایجاد کنید؟" + "\n" + Backup_File_App, data);
            } else {
                MessageHelper.Toast(MainActivity.this, "برای استفاده از این امکانات باید نسخه ای کامل برنامه را خریداری کنید.");
            }

        } else if (id == R.id.nav_upload) {
            if (SharedPreferencesHelper.get_Data("‌Buy_App", "NO", MainActivity.this).equals("Buy_App")) {
                DialogHelper.uploadBackupFile(MainActivity.this, "ایا شما میخواهید از حافظه ای  " + "زیر" + " اطلاعات خود را بازخوانی کنید؟" + "\n" + Backup_File_App, data);
            } else {
                MessageHelper.Toast(MainActivity.this, "برای استفاده از این امکانات باید نسخه ای کامل برنامه را خریداری کنید.");
            }
        } else if (id == R.id.nav_amozesh) {
            startActivity(new Intent(this, LerningActivity.class));
        } else if (id == R.id.buyapp) {
            if (SharedPreferencesHelper.get_Data("‌Buy_App", "NO", MainActivity.this).equals("NO")) {
                startActivity(new Intent(this, BuyAppActivity.class));
                finish();
            } else {
                MessageHelper.Toast(MainActivity.this, "شما قبلا برنامه را خریداری کرده اید.");
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void initView() {
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

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    class UpdateServiceConnection implements ServiceConnection {
        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = IUpdateCheckService.Stub
                    .asInterface((IBinder) boundService);
            try {
                long vCode = service.getVersionCode("aspi.myclass");
                if (vCode != -1) {
                    DialogHelper.Qusins(MainActivity.this);
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
                                                            MessageHelper.Mesage(MainActivity.this, "موفق و پیروز باشید!");
                                                            SetCode(37);
                                                        } else if (amozesh == 35.0) {
                                                            drawer.openDrawer(GravityCompat.START);
                                                            MessageHelper.Mesage(MainActivity.this, "شما همچنین می توانید فایل پشتیبانی هر ترم را در داخل پوشه ی BackupClass قرار دهد و با انتخاب گزینه ی بارگیری فایل پشتیبانی اطلاعات ترم مورد نظرتان را به برنامه اضافه کنید.گزینه ی بارگیری فایل پشتیبانی را انتخاب کنید.");
                                                        }
                                                    } else if (amozesh == 34.0) {
                                                        drawer.openDrawer(GravityCompat.START);
                                                        MessageHelper.Mesage(MainActivity.this, "شما می توانید زمانی پایان ترم از اطلاعات تمامی کلاس های تشکیل شده در برنامه فایل پشتیبانی تهیه کنید .از منو کشویی برنامه گزینه ی پشتیبان گیری از نرم افزار را انتخاب کنید ،با انتخاب این گزینه برای شما سه فایل پشتیبان گیری از اطلاعات کلاس های شما گرفته می شود و در حافظه ی اصلی گوشی در پوشه ی  BackupClass ذخیره می شود.");
                                                    }
                                                } else if (amozesh == 27.0) {
                                                    MessageHelper.Mesage(MainActivity.this, "درس را انتخاب کنید.");
                                                    SetCode(28);
                                                }
                                            } else if (amozesh == 25.0) {
                                                MessageHelper.Mesage(MainActivity.this, "پس از ثبت کلاس در دیگر ایام هفته تمام اطلاعات کلاس از جمله اطلاعات دانشجویان و جلسات برگزار شده انتقال می یابد." + "\n درس را انتخاب کنید.");
                                                SetCode(26);
                                            }
                                        } else if (amozesh == 23.0) {
                                            MessageHelper.Mesage(MainActivity.this, "درس را انتخاب کنید.");
                                            SetCode(24);
                                        }
                                    } else if (amozesh == 21.0) {
                                        MessageHelper.Mesage(MainActivity.this, "درس را انتخاب کنید.");
                                        SetCode(22);
                                    }
                                } else if (amozesh == 19.0) {
                                    MessageHelper.Mesage(MainActivity.this, "درس را انتخاب کنید.");
                                    SetCode(20);
                                }
                            } else if (amozesh == 15.0) {
                                MessageHelper.Mesage(MainActivity.this, "با ایجاد جلسه جدید کلاس شما می توانید به جلسات گذشته دسترسی داشته باشید. درس را انتخاب کنید.");
                                SetCode(16);
                            }
                        } else if (amozesh == 7.0) {
                            MessageHelper.Mesage(MainActivity.this, "اکنون شما می توانید برای کلاس خود جلسه تشکیل دهد.درس را انتخاب کنید.");
                        }
                    } else if (chek) {
                        MessageHelper.Mesage(MainActivity.this, "کلاس را انتخاب کنید تا برای شما گزینه های مختلف نشان داده شود.");
                    }
                } else {
                    MessageHelper.Mesage(MainActivity.this, "روزی که کلاس را در آن ثبت کرده اید انتخاب کنید.");
                }
            } else {
                drawer.openDrawer(GravityCompat.START);
                MessageHelper.Mesage(MainActivity.this, "برای ایجاد کلاس جدید از منو کشویی برنامه گزینه اضافه کردن کلاس جدید را انتخاب کنید");
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

    protected void onResume() {
        super.onResume();
        Amozesh(false);
    }

    void SetCode(float code) {
        SharedPreferencesHelper.SetCode("LerningActivity",String.valueOf(code),MainActivity.this);
    }


}
