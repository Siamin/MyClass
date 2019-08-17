package aspi.myclass.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import aspi.myclass.R;
import aspi.myclass.class_.OtherMetod;
import aspi.myclass.class_.dbstudy;


public class AddStudentActivity extends Activity {

    private Button save, cancel, download, Reload;
    private TextView nameclass, starttime, locationclass, snostudent, namestudent, familystudent, numbers[];
    private EditText sno_student[], name_student[], family_student[];
    private dbstudy data;
    public static String id_class, Name_class, Start_time, location;
    private int not_save = 0;
    private String Upload = "", TAG = "TAG_AddStudentActivity";
    OtherMetod om = new OtherMetod();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_addstudent);
        data = new dbstudy(this);
        config();
        //***********************************************
        download.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        download.setBackgroundResource(R.drawable.download2);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        download.setBackgroundResource(R.drawable.download1);
                        if (MainActivity.BUYAPP.equals("Buy_App")) {
                            ReadFileExternal("/App_class/IO.txt");
                            om.Mesage(AddStudentActivity.this,"برای وارد کردن فهرست دانشجویان ابتدا باید یک فایل متنی ایجاد کرد و اطلاعات را بصورت زیر وارد کنید." + "\nشماره دانشجویی-نام-نام خانوادگی" + "\nنام فایل راIO.txt گذاشته و در فولدر App_class ذخیره کنید" + "  ویا اسامی دانشجویان خود را در داخل فایل Excel بصورتی که شماره ی دانشجوی را در ستون A و نام دانشجو را در ستون B و نام خانوادگی دانشجو در ستون C به همین ترتیب وارد کنید و با نام IO.xls در پوشه ی App_class ذخیره کنید.\n" + "آموزش کامل تصویری این قسمت در منو کشویی قرار دارد");
                        } else {
                            om.Toast(AddStudentActivity.this, "برای استفاده از این امکانات باید نسخه ای کامل برنامه را خریداری کنید.");
                        }
                        break;
                    }
                }
                return false;
            }
        });
        //***********************************************
        Reload.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Reload.setBackgroundResource(R.drawable.reload2);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        Reload.setBackgroundResource(R.drawable.reload1);
                        if (!Upload.equals(""))
                            get_upload(Upload);
                        break;
                    }
                }
                return false;
            }
        });
        //***********************************************
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!sno_student[0].getText().toString().equals("") && !name_student[0].getText().toString().equals("") && !family_student[0].getText().toString().equals("")) {
                    if (save()) {
                        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
                        float amozesh = sp.getFloat("LerningActivity", 0);
                        om.Toast(AddStudentActivity.this, "ذخیره شد...!");
                        if (amozesh == 4 || amozesh == 5) {
                            SetCode(6);
                            Amozesh(false);
                        }

                    }
                    if (not_save > 0) {
                        om.Toast(AddStudentActivity.this, "اطلاعاتی که بعد از ذخیره پاک نشده اند قبلا شماره آنها وارد شده است...!");
                    }
                } else {
                    om.Toast(AddStudentActivity.this, "لطفا تمامی مشخصات را به ترتیب وارد نمایید...!");
                }
            }
        });
        //***********************************************
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Go_main();
            }
        });

        //***********************************************
    }

    void config() {
        numbers = new TextView[20];
        sno_student = new EditText[20];
        name_student = new EditText[20];
        family_student = new EditText[20];
        //**********************************************************
        save = (Button) findViewById(R.id.add_student_save);
        cancel = (Button) findViewById(R.id.add_student_cancel);
        download = (Button) findViewById(R.id.add_student_download_);
        Reload = (Button) findViewById(R.id.add_student_reload);
        Reload.setVisibility(View.INVISIBLE);
        //**********************************************************
        nameclass = (TextView) findViewById(R.id.add_student_name_class);
        starttime = (TextView) findViewById(R.id.add_student_start_time);
        locationclass = (TextView) findViewById(R.id.add_student_location);
        snostudent = (TextView) findViewById(R.id.add_student_sno);
        namestudent = (TextView) findViewById(R.id.add_student_name_student);
        familystudent = (TextView) findViewById(R.id.add_student_family_student);
        numbers[0] = (TextView) findViewById(R.id.add_student_number0);
        numbers[1] = (TextView) findViewById(R.id.add_student_number1);
        numbers[2] = (TextView) findViewById(R.id.add_student_number2);
        numbers[3] = (TextView) findViewById(R.id.add_student_number3);
        numbers[4] = (TextView) findViewById(R.id.add_student_number4);
        numbers[5] = (TextView) findViewById(R.id.add_student_number5);
        numbers[6] = (TextView) findViewById(R.id.add_student_number6);
        numbers[7] = (TextView) findViewById(R.id.add_student_number7);
        numbers[8] = (TextView) findViewById(R.id.add_student_number8);
        numbers[9] = (TextView) findViewById(R.id.add_student_number9);
        numbers[10] = (TextView) findViewById(R.id.add_student_number10);
        numbers[11] = (TextView) findViewById(R.id.add_student_number11);
        numbers[12] = (TextView) findViewById(R.id.add_student_number12);
        numbers[13] = (TextView) findViewById(R.id.add_student_number13);
        numbers[14] = (TextView) findViewById(R.id.add_student_number14);
        numbers[15] = (TextView) findViewById(R.id.add_student_number15);
        numbers[16] = (TextView) findViewById(R.id.add_student_number16);
        numbers[17] = (TextView) findViewById(R.id.add_student_number17);
        numbers[18] = (TextView) findViewById(R.id.add_student_number18);
        numbers[19] = (TextView) findViewById(R.id.add_student_number19);
        //**********************************************************
        sno_student[0] = (EditText) findViewById(R.id.add_student_sno1);
        sno_student[1] = (EditText) findViewById(R.id.add_student_sno2);
        sno_student[2] = (EditText) findViewById(R.id.add_student_sno3);
        sno_student[3] = (EditText) findViewById(R.id.add_student_sno4);
        sno_student[4] = (EditText) findViewById(R.id.add_student_sno5);
        sno_student[5] = (EditText) findViewById(R.id.add_student_sno6);
        sno_student[6] = (EditText) findViewById(R.id.add_student_sno7);
        sno_student[7] = (EditText) findViewById(R.id.add_student_sno8);
        sno_student[8] = (EditText) findViewById(R.id.add_student_sno9);
        sno_student[9] = (EditText) findViewById(R.id.add_student_sno10);
        sno_student[10] = (EditText) findViewById(R.id.add_student_sno11);
        sno_student[11] = (EditText) findViewById(R.id.add_student_sno12);
        sno_student[12] = (EditText) findViewById(R.id.add_student_sno13);
        sno_student[13] = (EditText) findViewById(R.id.add_student_sno14);
        sno_student[14] = (EditText) findViewById(R.id.add_student_sno15);
        sno_student[15] = (EditText) findViewById(R.id.add_student_sno16);
        sno_student[16] = (EditText) findViewById(R.id.add_student_sno17);
        sno_student[17] = (EditText) findViewById(R.id.add_student_sno18);
        sno_student[18] = (EditText) findViewById(R.id.add_student_sno19);
        sno_student[19] = (EditText) findViewById(R.id.add_student_sno20);
        //**********************************************************
        name_student[0] = (EditText) findViewById(R.id.add_student_name1);
        name_student[1] = (EditText) findViewById(R.id.add_student_name2);
        name_student[2] = (EditText) findViewById(R.id.add_student_name3);
        name_student[3] = (EditText) findViewById(R.id.add_student_name4);
        name_student[4] = (EditText) findViewById(R.id.add_student_name5);
        name_student[5] = (EditText) findViewById(R.id.add_student_name6);
        name_student[6] = (EditText) findViewById(R.id.add_student_name7);
        name_student[7] = (EditText) findViewById(R.id.add_student_name8);
        name_student[8] = (EditText) findViewById(R.id.add_student_name9);
        name_student[9] = (EditText) findViewById(R.id.add_student_name10);
        name_student[10] = (EditText) findViewById(R.id.add_student_name11);
        name_student[11] = (EditText) findViewById(R.id.add_student_name12);
        name_student[12] = (EditText) findViewById(R.id.add_student_name13);
        name_student[13] = (EditText) findViewById(R.id.add_student_name14);
        name_student[14] = (EditText) findViewById(R.id.add_student_name15);
        name_student[15] = (EditText) findViewById(R.id.add_student_name16);
        name_student[16] = (EditText) findViewById(R.id.add_student_name17);
        name_student[17] = (EditText) findViewById(R.id.add_student_name18);
        name_student[18] = (EditText) findViewById(R.id.add_student_name19);
        name_student[19] = (EditText) findViewById(R.id.add_student_name20);
        //**********************************************************
        family_student[0] = (EditText) findViewById(R.id.add_student_family1);
        family_student[1] = (EditText) findViewById(R.id.add_student_family2);
        family_student[2] = (EditText) findViewById(R.id.add_student_family3);
        family_student[3] = (EditText) findViewById(R.id.add_student_family4);
        family_student[4] = (EditText) findViewById(R.id.add_student_family5);
        family_student[5] = (EditText) findViewById(R.id.add_student_family6);
        family_student[6] = (EditText) findViewById(R.id.add_student_family7);
        family_student[7] = (EditText) findViewById(R.id.add_student_family8);
        family_student[8] = (EditText) findViewById(R.id.add_student_family9);
        family_student[9] = (EditText) findViewById(R.id.add_student_family10);
        family_student[10] = (EditText) findViewById(R.id.add_student_family11);
        family_student[11] = (EditText) findViewById(R.id.add_student_family12);
        family_student[12] = (EditText) findViewById(R.id.add_student_family13);
        family_student[13] = (EditText) findViewById(R.id.add_student_family14);
        family_student[14] = (EditText) findViewById(R.id.add_student_family15);
        family_student[15] = (EditText) findViewById(R.id.add_student_family16);
        family_student[16] = (EditText) findViewById(R.id.add_student_family17);
        family_student[17] = (EditText) findViewById(R.id.add_student_family18);
        family_student[18] = (EditText) findViewById(R.id.add_student_family19);
        family_student[19] = (EditText) findViewById(R.id.add_student_family20);
        //**********************************************************
        FONT(MainActivity.FONTS);
        //**********************************************************
        nameclass.setText("" + Name_class);
        starttime.setText("" + Start_time);
        locationclass.setText("" + location);
    }

    void FONT(Typeface font_text) {
        nameclass.setTypeface(font_text);
        starttime.setTypeface(font_text);
        locationclass.setTypeface(font_text);
        snostudent.setTypeface(font_text);
        namestudent.setTypeface(font_text);
        familystudent.setTypeface(font_text);
        save.setTypeface(font_text);
        cancel.setTypeface(font_text);
        download.setTypeface(font_text);
        for (int i = 0; i < 20; i++) {
            numbers[i].setTypeface(font_text);
            sno_student[i].setTypeface(font_text);
            name_student[i].setTypeface(font_text);
            family_student[i].setTypeface(font_text);
        }

    }

    void Go_main() {
        finish();
    }

    boolean save() {
        boolean resualt, check = false;
        int res = 0;
        not_save = 0;
        try {
            data.open();
            for (int i = 0; i < 20; i++) {
                check = false;
                if (!sno_student[i].getText().toString().equals("") && !name_student[i].getText().toString().equals("") && !family_student[i].getText().toString().equals("")) {

                    try {
                        check = data.insert_student(sno_student[i].getText().toString(), family_student[i].getText().toString(), name_student[i].getText().toString(), id_class);
                    } catch (Exception e) {
                        Log.i(TAG, "Error" + e.toString());
                    }

                    if (check) {
                        sno_student[i].setText("");
                        name_student[i].setText("");
                        family_student[i].setText("");
                        res += 1;
                    } else {
                        not_save += 1;
                    }
                }
            }
            data.close();
        } catch (Exception e) {
        }
        if (res > 0) {
            resualt = true;
        } else {
            resualt = false;
        }
        return resualt;
    }

    void ReadFileExternal(final String path) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        final TextView input = new TextView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setText("آیا میخواهید اسامی دانشجویان را از حافظه ای  " + MainActivity.Address_file_app + "در فایل Text با نام " + "IO.txt" + " و یا در فایل Excel با نام " + "IO.xls" + " دریافت کنید؟ ");
        input.setTypeface(MainActivity.FONTS);
        input.setTextSize(15);
        input.setPadding(10, 10, 5, 0);
        builder1.setView(input);
        builder1.setTitle("دانشجویان");
        builder1.setPositiveButton("فایل text", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                String Return = "", Line = "", External = Environment.getExternalStorageDirectory().toString();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(External + path));

                    while ((Line = bufferedReader.readLine()) != null) {
                        Return += ((Line.replace("%", "")).replace("null", "")) + "%";
                    }
                    get_upload(Return.replace("-", "~"));
                } catch (Exception e) {
                }
            }
        });
        builder1.setNegativeButton("فایل Excel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                readExcelFile(Environment.getExternalStorageDirectory() + "/App_class/IO.xls");
            }
        });
        AlertDialog aler1 = builder1.create();
        aler1.show();
    }

    void get_upload(String GET) {
        String[] All = GET.split("%");

        for (int i = 0; i < 20; i++) {
            sno_student[i].setText("");
            name_student[i].setText("");
            family_student[i].setText("");
        }
        int min = All.length;
        if (min > 20) min = 20;
        for (int i = 0; i < min; i++) {
            String item[] = All[i].split("~");
            try {
                int no = Integer.parseInt(item[0]);
                sno_student[i].setText(item[0]);
            } catch (Exception e) {
                sno_student[i].setText(item[0].substring(0, (item[0].length() - 2)).replace(".", ""));
            }
            name_student[i].setText(item[1]);
            family_student[i].setText(item[2]);
        }
        Upload = "";
        if (All.length > 20) {
            Reload.setVisibility(View.VISIBLE);
            for (int i = 20; i < All.length; i++) {
                Upload += All[i] + "%";
            }
        } else {
            Reload.setVisibility(View.INVISIBLE);
        }
    }

    void Amozesh(final boolean chek) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
        float amozesh = sp.getFloat("LerningActivity", 0);
        if (amozesh > 5) {
            if (amozesh > 6) {

            } else {
                om.Mesage(AddStudentActivity.this, "همچنین شما می توانید با انتخاب ایکون سبز رنگ بالای صفحه،اطلاعات دانشجویان را بصورت لیست وارد برنامه کنید");
                SetCode(7);
            }
        } else {
            om.Mesage(AddStudentActivity.this, "در این قسمت شما می توانید اطلاعات دانشجویان را بصورت دستی وارد کنید.اطلاعات چند دانشجو را وارد کرده و گزینه ی ذخیره را انتخاب کنید.");
        }
    }

    protected void onResume() {
        super.onResume();
        Amozesh(false);
    }

    void SetCode(float code) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat("LerningActivity", code);
        edit.commit();
    }

    void readExcelFile(String filename) {


        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            return;
        }

        try {

            File file = new File(filename);

            FileInputStream myInput = new FileInputStream(file);

            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            /** We now need something to iterate through the cells.**/
            Iterator rowIter = mySheet.rowIterator();


            String Uploads = "";
            int i = 0;

            while (rowIter.hasNext()) {

                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();

                    if (i < 2) {
                        Uploads += ((myCell.toString().replace("%", "")).replace("null", "")) + "~";
                        i++;
                    } else {
                        Uploads += ((myCell.toString().replace("%", "")).replace("null", "")) + "%";
                        i = 0;
                    }
                }

            }
            get_upload(Uploads);
        } catch (Exception e) {
            om.Toast(AddStudentActivity.this, "لظفا فایل Excel را با فرمت 97-2003 ذخیره و سپس import کنید.");
        }

    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


}
