package aspi.myclass.activity;


import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;

import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.farsitel.bazaar.IUpdateCheckService;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import aspi.myclass.Helpers.DialogHelper;
import aspi.myclass.Helpers.LanguageHelper;
import aspi.myclass.Helpers.MessageHelper;

import aspi.myclass.Helpers.ValidationHelper;
import aspi.myclass.Interface.RecyclerViewInteface;
import aspi.myclass.MyActivity;
import aspi.myclass.Services.FireBaseAnalyticsService;
import aspi.myclass.model.LessonModel;
import aspi.myclass.R;
import aspi.myclass.Helpers.DatabasesHelper;
import aspi.myclass.adapter.ClassViewAdapter;


public class MainActivity extends MyActivity
        implements NavigationView.OnNavigationItemSelectedListener, TimePickerDialog.OnTimeSetListener
        , DatePickerDialog.OnDateSetListener, RecyclerViewInteface {


    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private int DAY;
    private Button Saturday, Sunday, Monday, Tuesday, Wednesday, Thursday, Friday;
    private TextView text_day_class;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<LessonModel> List = new ArrayList<>();

    private File Backup_File_App = new File(Environment.getExternalStorageDirectory()
            , "BackupClass"), Address_file_app = new File(Environment.getExternalStorageDirectory(), "App_class");
    private IUpdateCheckService service;
    private UpdateServiceConnection connection;
    private String TAG = "TAG_Main";
    private FirebaseAnalytics mFirebaseAnalytics;
    private FireBaseAnalyticsService fireBaseAnalyticsService = new FireBaseAnalyticsService();
    private int STORAGE_PERMISSION_CODE = 101;
    String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        LanguageHelper.loadLanguage(MainActivity.this);

        setContentView(R.layout.main);

        initView();

        //******************************************************************************************
        Saturday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getLessonData(0);
            }
        });
        //******************************************************************************************
        Sunday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getLessonData(1);
            }
        });
        //******************************************************************************************
        Monday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getLessonData(2);
            }
        });
        //******************************************************************************************
        Tuesday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getLessonData(3);
            }
        });
        //******************************************************************************************
        Wednesday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getLessonData(4);
            }
        });
        //******************************************************************************************
        Thursday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getLessonData(5);
            }
        });
        //******************************************************************************************
        Friday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getLessonData(6);
            }
        });
        //******************************************************************************************

        initService();
    }

    public void helpMe(View v) {
        if (ValidationHelper.validSetEmail(MainActivity.this)) {
            startActivity(new Intent(MainActivity.this, CrispActivity.class));
            finish();
        } else {
            messageHelper.Mesage(getResources().getString(R.string.errorAddEmail));
        }
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

        fireBaseAnalyticsService.CustomEventFireBaseAnalytics(mFirebaseAnalytics, String.valueOf(id), item.getTitle().toString(), "menu");
        if (id == R.id.nav_add_class) {
            GoPage(new Intent(this, LessonAddActivity.class));
        } else if (id == R.id.nav_abute) {
            DialogHelper.Abute(MainActivity.this);

        } else if (id == R.id.nav_coment) {

            if (ValidationHelper.validSetEmail(MainActivity.this)) {
                GoPage(new Intent(this, CommentActivity.class));
            } else {
                messageHelper.Mesage(getResources().getString(R.string.errorAddEmail));
            }

        } else if (id == R.id.nav_setting) {
            GoPage(new Intent(this, SettingActivity.class));

        } else if (id == R.id.changeLanguage) {
            DialogHelper.ChangeLanguage(MainActivity.this);

        } else if (id == R.id.nav_ClearDataBase) {
            DialogHelper.cleanDatabase(MainActivity.this, data);

        } else if (id == R.id.nav_backup) {
            if (ValidationHelper.validBuyApp(MainActivity.this)) {
                if (ValidationHelper.checkPerimission(MainActivity.this)) {
                    DialogHelper.backupFile(MainActivity.this, getResources().getString(R.string.titleBackupDialog) + "\n" + Backup_File_App, data);

                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, STORAGE_PERMISSION_CODE);
                }
            } else {
                MessageHelper.Toast(MainActivity.this, getResources().getString(R.string.ErrorBuyApplication));
            }

        } else if (id == R.id.nav_upload) {
            if (ValidationHelper.validBuyApp(MainActivity.this)) {

                if (ValidationHelper.checkPerimission(MainActivity.this)) {
                    DialogHelper.uploadBackupFile(MainActivity.this, getResources().getString(R.string.titleUploadDataDialog) + "\n" + Backup_File_App, data);

                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, STORAGE_PERMISSION_CODE);
                }

            } else {
                MessageHelper.Toast(MainActivity.this, getResources().getString(R.string.ErrorBuyApplication));
            }

        } else if (id == R.id.nav_amozesh) {
            //startActivity(new Intent(MainActivity.this, LerningActivity.class));
            MessageHelper.Toast(MainActivity.this, getResources().getString(R.string.ComingSoon));

        } else if (id == R.id.buyapp) {
            if (!ValidationHelper.validBuyApp(MainActivity.this)) {
                startActivity(new Intent(MainActivity.this, BazarActivity.class));
                finish();
            } else {
                MessageHelper.Toast(MainActivity.this, getResources().getString(R.string.errorBuyApp));
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    void initView() {

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Saturday = findViewById(R.id.main_Saturday);
        Sunday = findViewById(R.id.main_Sunday);
        Monday = findViewById(R.id.main_Monday);
        Tuesday = findViewById(R.id.main_Tuesday);
        Wednesday = findViewById(R.id.main_Wednesday);
        Thursday = findViewById(R.id.main_Thursday);
        Friday = findViewById(R.id.main_Friday);
        text_day_class = findViewById(R.id.main_text_day_class);
        recyclerView = findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);

        toolbar.setTitle(getResources().getString(R.string.app_name));

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        Calendar calendar = Calendar.getInstance();
        DAY = calendar.get(Calendar.DAY_OF_WEEK);
        if (DAY == 7) DAY = 0;
        getLessonData(DAY);
        refreshList();


        if (!Address_file_app.exists()) {
            Address_file_app.mkdir();
        }
        if (!Backup_File_App.exists()) {
            Backup_File_App.mkdir();
        }

    }

    void setDayText() {
        String[] weekName = getResources().getStringArray(R.array.weekName);
        String lang = LanguageHelper.loadLanguage(MainActivity.this);
        if (lang.equals("fa")) {
            text_day_class.setText(getResources().getString(R.string.TextWeekend) + " " + weekName[DAY]);

        } else {
            text_day_class.setText(weekName[DAY] + " " + getResources().getString(R.string.TextWeekend));

        }
    }

    void getLessonData(int day) {

        try {
            DAY = day;
//            data.open();
//            List = data.getLessonByDay(String.valueOf(day));
//            data.close();

            List = lessonController.findByDayOfWeek(String.valueOf(day));

            if (List.size()==0)
                List.clear();

            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new ClassViewAdapter(List, MainActivity.this));

            setDayText();
        } catch (Exception e) {
            Log.i(TAG, e.toString());

        }

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void refreshList() {
        getLessonData(DAY);
    }


    class UpdateServiceConnection implements ServiceConnection {
        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = IUpdateCheckService.Stub
                    .asInterface((IBinder) boundService);
            try {
                long vCode = service.getVersionCode("aspi.myclass");
                if (vCode != -1) {
                    DialogHelper.UpdateApplication(MainActivity.this);
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

    protected void onResume() {
        super.onResume();

    }


}
