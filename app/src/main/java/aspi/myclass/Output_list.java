package aspi.myclass;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.record.formula.functions.Na;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;

public class Output_list extends Activity {

    private TableRow.LayoutParams llp;
    private LinearLayout cell;
    private TableLayout table;
    private dbstudy data;
    public static boolean STATUS;
    private Button save;
    private TextView titel;
    private ProgressDialog progressDialog;
    public static String Name_class, Id_class, Did_class, Resualt;
    private boolean view = false;
    private int cunters = 0;
    String[] name, family, sno, status_, nomreh_, Data_;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output_list);
        data = new dbstudy(this);
        config();
        //******************************************************************************************
        save.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        save.setBackgroundResource(R.drawable.save_y);
                        Question();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        save.setBackgroundResource(R.drawable.save_g);
                        break;
                    }
                }
                return false;
            }
        });
        //******************************************************************************************
    }

    private void config() {
        save = (Button) findViewById(R.id.output_list_save);
        titel = (TextView) findViewById(R.id.output_list_titel);
        table = (TableLayout) findViewById(R.id.output_list_table);
        //*****************************************************
        if (STATUS) {
            titel.setText("لیست حضور و غیاب");
        } else {
            titel.setText("لیست نمرات");
        }
        //*****************************************************
        Start();
    }

    private void TOAST(String TEXT) {
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

    private void Get_Data_Base() {
       /* try {
            data.open();
            String name_ = "", family_ = "", sno_ = "", data_ = "", old_j = "";
            int cunter = 0, cunt_student = data.count("klas"), cunt_rollcal = data.count("rollcall");
            progressDialog.setMax(cunt_student + cunt_rollcal);
            for (int i = 0; i < cunt_student; i++) {
                String Class[] = data.Display_Class("klas", i).split("~");
                if (Did_class.equals(Class[5])) {
                    sno_ += Class[1] + "~";
                    family_ += Class[2] + "~";
                    name_ += Class[3] + "~";
                    cunter += 1;
                }
                progressDialog.setProgress(i);
            }
            if (cunter > 0) {
                sno = sno_.split("~");
                family = family_.split("~");
                name = name_.split("~");
                status_ = new String[name.length];
                nomreh_ = new String[name.length];
                cunter = 1;
                int j = 0;
                for (int i = 0; i < cunt_rollcal; i++) {
                    String Class[] = data.Display_all("rollcall", i, 0, 9).split("~");
                    if (i == 0) old_j = Class[8];
                    if (Did_class.equals(Class[7])) {
                        if (j == 0 || old_j.equals(Class[8])) {
                            if (j == 0) data_ += Class[6] + "/" + Class[5] + "/" + Class[4] + "~";
                            status_[j] += Class[2] + "~";
                            nomreh_[j] += Class[3] + "~";
                            j++;
                            if (j == name.length) {
                                j = 0;
                                cunter += 1;
                            }
                            old_j = Class[8];
                        } else {
                            for (; j < name.length; j++) {
                                status_[j] += "-~";
                                nomreh_[j] += "-~";
                            }
                            j = 0;
                            data_ += Class[6] + "/" + Class[5] + "/" + Class[4] + "~";
                            status_[j] += Class[2] + "~";
                            nomreh_[j] += Class[3] + "~";
                            j++;
                            old_j = Class[8];
                        }

                    }
                    progressDialog.setProgress(cunt_student + i);
                }
                data.close();
                for (; j < name.length; j++) {
                    status_[j] += "-~";
                    nomreh_[j] += "-~";
                }
                Data_ = data_.split("~");
                cunter -= 1;
                if (cunter > 0) {
                    view = true;
                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            TOAST("هیچ جلسه ای برای این کلاس ثبت نشده...!");
                        }
                    });
                    finish();
                }
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        TOAST("هیچ دانشجویی در این کلاس ثبت نشده است...!");
                    }
                });
                finish();
            }
        } catch (final Exception e) {
        }
        */
    }

    private void Start() {
        final Timer time = new Timer();
        time.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        cunters += 1;
                        if (cunters == 1) {
                            progressDialog = new ProgressDialog(Output_list.this);
                            //progressDialog.setProgress(0);
                            //progressDialog.setProgressDrawable(getResources().getDrawable(R.drawable.dialog));
                            //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            progressDialog.setTitle("درحال دریافت اطلاعات");
                            progressDialog.setCancelable(false);
                            progressDialog.setMessage("لطفا صبر کنید ...!");
                            progressDialog.show();
                            new Thread(new Runnable() {
                                public void run() {
                                    //Get_Data_Base();
                                    Get_qury_Data_Base();
                                }
                            }).start();
                        }
                        if (view) {
                            view = false;
                            creat_tabel();
                            progressDialog.cancel();
                            time.cancel();
                        }
                    }
                });
            }
        }, 1, 1000);
    }

    private void creat_tabel() {
        try {
            for (int row = 0; row < (sno.length + 1); row++) {

                TableRow tr = new TableRow(this);
                tr.setBackgroundColor(Color.BLACK);
                tr.setPadding(0, 0, 0, 2);

                llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                llp.setMargins(0, 0, 0, 0);
                cell = new LinearLayout(this);
                if (row == 0) {
                    cell.setBackgroundColor(getResources().getColor(R.color.table1));
                } else if (row % 2 == 0) {
                    cell.setBackgroundColor(getResources().getColor(R.color.table2));
                } else if (row % 2 == 1) {
                    cell.setBackgroundColor(getResources().getColor(R.color.table3));
                }
                cell.setLayoutParams(llp);
                cell.setPadding(2, 0, 0, 0);

                for (int fild = 0; fild < (Data_.length + 1); fild++) {

                    TextView tv = new TextView(this);
                    String[] Status_table = new String[0], Nomreh_table = new String[0];
                    if (row > 0) {
                        Status_table = status_[row - 1].split("~");
                        Nomreh_table = nomreh_[row - 1].split("~");
                    }
                    if (row == 0 && fild == 0) {
                        tv.setText("نام و نام خانوادگی");
                        tv.setPadding(0, 0, 4, 3);
                        tv.setTextSize(15);
                        tv.setTypeface(Main.FONTS);
                        tv.setLayoutParams(new TableRow.LayoutParams(250, TableRow.LayoutParams.FILL_PARENT));
                    } else if (fild == 0) {
                        tv.setText(name[row - 1] + " " + family[row - 1]);//name & family
                        tv.setPadding(0, 0, 4, 3);
                        tv.setTextSize(15);
                        tv.setTypeface(Main.FONTS);
                        tv.setLayoutParams(new TableRow.LayoutParams(250, TableRow.LayoutParams.FILL_PARENT));

                    } else if (row > 0) {
                        if (STATUS) {
                            Status_table[fild - 1] = Status_table[fild - 1].replace("null", "");
                            if (Status_table[fild - 1].equals("1")) {
                                tv.setText("√");
                            } else if (Status_table[fild - 1].equals("0")) {
                                tv.setText("غ");
                            } else {
                                tv.setText("-");
                            }
                        } else if (!STATUS) {
                            if (Nomreh_table[fild - 1].replace("null", "").length() > 0) {
                                tv.setText(Nomreh_table[fild - 1].replace("null", ""));
                            } else {
                                tv.setText("");
                            }

                        }
                        tv.setPadding(0, 0, 4, 3);
                        tv.setTextSize(12);
                        tv.setTypeface(Main.FONTS);
                        tv.setLayoutParams(new TableRow.LayoutParams(50, TableRow.LayoutParams.FILL_PARENT));

                    } else {
                        tv.setText(Data_[fild - 1].replace("/", "\n-\n"));
                        tv.setPadding(0, 0, 4, 3);
                        tv.setTextSize(12);
                        tv.setTypeface(Main.FONTS);
                        tv.setLayoutParams(new TableRow.LayoutParams(50, TableRow.LayoutParams.FILL_PARENT));
                    }

                    tv.setGravity(Gravity.CENTER);
                    cell.addView(tv);

                }
                tr.addView(cell);
                table.addView(tr);
            }
        } catch (Exception e) {
            //TOAST("tabel \n" + e.toString());
        }
    }

    boolean Excel(int ROW, int FILD) {
        boolean success = false;
        //******************************************************************************New Workbook
        Workbook wb = new HSSFWorkbook();
        Cell c = null;
        //*****************************************************************Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //*********************************************************************************New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet(Name_class);
        //******************************************************************Generate column headings
        try {


            for (int Row = 0; Row < ROW; Row++) {
                Row row = sheet1.createRow(Row);
                for (int fild = 0; fild < FILD; fild++) {
                    String[] Status_table = new String[0], Nomreh_table = new String[0];
                    if (Row > 0) {
                        Status_table = status_[Row - 1].split("~");
                        Nomreh_table = nomreh_[Row - 1].split("~");
                    }
                    c = row.createCell(fild);
                    c.setCellStyle(cs);
                    if (Row == 0 && fild == 0) {
                        c.setCellValue("نام و نام خانوادگی");
                    } else if (Row == 0 && fild > 0) {
                        c.setCellValue("" + Data_[fild - 1]);
                    } else if (Row > 0 && fild == 0) {
                        c.setCellValue(name[Row - 1] + " " + family[Row - 1]);
                    } else {
                        if (STATUS) {
                            Status_table[fild - 1] = Status_table[fild - 1].replace("null", "");
                            if (Status_table[fild - 1].equals("1")) {
                                c.setCellValue("√");
                            } else if (Status_table[fild - 1].equals("0")) {
                                c.setCellValue("غ");
                            } else {
                                c.setCellValue("-");
                            }
                        } else if (!STATUS) {
                            if (Nomreh_table[fild - 1].replace("null", "").length() > 0) {
                                c.setCellValue(Nomreh_table[fild - 1].replace("null", ""));
                            } else {
                                c.setCellValue("-");
                            }

                        }
                    }
                    sheet1.setColumnWidth(fild, (15 * 350));
                }
            }

            File file = new java.io.File(Main.Address_file_app, Name_class + " " + titel.getText().toString() + ".xls");
            FileOutputStream os = null;

            os = new FileOutputStream(file);
            wb.write(os);

            success = true;
        } catch (Exception e) {
            //TOAST(e.toString());
        }
        return success;
    }

    void Question() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        final TextView text = new TextView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        text.setLayoutParams(lp);
        text.setText("آیا می خواهید اطلاعات کلاس " + Name_class + " را در یک فایل اکسل ذخیره کنید؟");
        text.setTypeface(Main.FONTS);
        text.setTextColor(getResources().getColor(R.color.toast));
        text.setTextSize(15);
        text.setTextColor(getResources().getColor(R.color.toast));
        text.setPadding(10, 5, 10, 0);
        builder1.setView(text);
        builder1.setTitle("ذخیره اطلاعات");
        builder1.setPositiveButton("بله", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                if (Excel((sno.length + 1), (Data_.length + 1))) {
                    TOAST(Main.Address_file_app + "/" + Name_class + " " + titel.getText().toString() + ".xls");
                } else {
                    if (!Main.Address_file_app.exists()) {
                        TOAST("مشکل در ایجاد پوشه ی  " + Main.Address_file_app + " در حافظه ی گوشی ");
                    } else {
                        TOAST("مشکل در ذخیره فایل در حافظه گوشی");
                    }
                }
            }
        });
        builder1.setNegativeButton("خیر", null);
        AlertDialog aler1 = builder1.create();
        aler1.show();
    }

    void Amozesh(final boolean chek) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
        float amozesh = sp.getFloat("Amozesh", 0);
        if (amozesh > 31) {
            if (amozesh == 33) {
                Mesage("شما می توانید در این قسمت از برنامه لیست نمرات کل جلسات برگزار شده کلاس خود را مشاهده کنید و همچنین می توانید با انتخاب گزینه ی سبز رنگ بالای صفحه اطلاعات کلاس خود را در یک فایل اکسل در حافظه ی گوشی در پوشه ی App_class با نام درس انتخاب شده ی خود ذخیره کنید.");
                SetCode(34);
            }
        } else if (amozesh == 31) {
            Mesage("شما می توانید در این قسمت از برنامه لیست حضور و غیاب کل جلسات برگزار شده کلاس خود را مشاهده کنید و همچنین می توانید با انتخاب گزینه ی سبز رنگ بالای صفحه اطلاعات کلاس خود را در یک فایل اکسل در حافظه ی گوشی در پوشه ی App_class با نام درس انتخاب شده ی خود ذخیره کنید.");
            SetCode(32);
        }
    }

    void Mesage(String text) {
        final Dialog massege = new Dialog(Output_list.this, R.style.MyAlertDialogStyle);
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
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void Get_qury_Data_Base() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    data.open();
                    //******************************************************************************
                    Resualt = data.Qury_output_list("SELECT  r.sno,r.abs,r.am,r.day,r.month,r.year,r.iddars,r.jalase,r.HOUR,k.name,k.family,k.sno,k.did" +
                            " FROM klas AS k,rollcall AS r WHERE r.iddars= " + Integer.parseInt(Did_class) + " AND r.sno = k.sno AND r.iddars = k.did AND k.did= "
                            + Integer.parseInt(Did_class) + " ORDER BY k.family ASC , k.name ASC , r.jalase ASC", Did_class);
                    data.close();
                    if (!Resualt.equals("")) {
                        //******************************************************************************
                        int cunt_jalase = 0, k = 0, cunt_stud = 0;
                        String sno_ = " ", name_ = " ", family_ = " ", data_ = " ", status = "", nomre = " ";
                        //******************************************************************************
                        for (int i = 0, start = 0, j = 0; i < Resualt.length(); i++) {
                            if (Resualt.charAt(i) == '|') {
                                String getR = Resualt.substring(start, i);
                                start = i + 1;
                                if (j == 0) {
                                    cunt_jalase = Integer.parseInt(getR);
                                    j += 1;
                                } else if (j == 1) {
                                    cunt_stud = Integer.parseInt(getR);
                                    j += 1;
                                } else if (j == 2) {
                                    data_ = getR;
                                    k = i + 1;
                                    j += 1;
                                    break;
                                }
                            }
                        }
                        //******************************************************************************
                        Resualt = Resualt.substring(k, Resualt.length());
                        Data_ = new String[cunt_jalase];
                        status_ = new String[cunt_stud];
                        nomreh_ = new String[cunt_stud];
                        //******************************************************************************
                        for (int i = 0, j = 0, start = 0; i < data_.length(); i++) {
                            if (data_.charAt(i) == '^') {
                                Data_[j++] = data_.substring(start, i);
                                start = i + 1;
                            }
                        }
                        //******************************************************************************
                        for (int i = 0, start = 0, p = -1; i < Resualt.length(); i++) {
                            if (Resualt.charAt(i) == '|') {
                                if (p >= 0) {
                                    status = status.substring(0, status.length() - 1) + "|";
                                    nomre = nomre.substring(0, nomre.length() - 1) + "|";
                                }
                                p += 1;
                                sno_ += Resualt.substring(start, i) + "~";
                                start = i + 1;
                            } else if (Resualt.charAt(i) == '^') {
                                name_ += Resualt.substring(start, i) + "~";
                                start = i + 1;
                            } else if (Resualt.charAt(i) == '*') {
                                family_ += Resualt.substring(start, i) + "~";
                                start = i + 1;
                            } else if (Resualt.charAt(i) == '$') {
                                status += Resualt.substring(start, i) + "~";
                                start = i + 1;
                            } else if (Resualt.charAt(i) == '#') {
                                String no = Resualt.substring(start, i);
                                if (no.length() > 0) {
                                    nomre += no + "~";
                                } else {
                                    nomre += no + "-~";
                                }
                                start = i + 1;
                            }
                        }
                        status = status.substring(0, status.length() - 1) + "|";
                        nomre = nomre.substring(0, nomre.length() - 1) + "|";
                        //******************************************************************************
                        for (int i = 0, j = 0, start = 0; i < status.length(); i++) {
                            if (status.charAt(i) == '|') {
                                status_[j++] = status.substring(start, i);
                                start = i + 1;
                            }
                        }
                        //******************************************************************************
                        for (int i = 0, j = 0, start = 0; i < nomre.length(); i++) {
                            if (nomre.charAt(i) == '|') {
                                nomreh_[j++] = nomre.substring(start, i);
                                start = i + 1;
                            }
                        }
                        //******************************************************************************
                        sno_ = sno_.substring(0, sno_.length() - 1);
                        name_ = name_.substring(0, name_.length() - 1);
                        family_ = family_.substring(0, family_.length() - 1);
                        //******************************************************************************
                        name = name_.split("~");
                        family = family_.split("~");
                        sno = sno_.split("~");
                        //******************************************************************************
                        for (int i = 0; i < cunt_stud; i++) {
                            int len = status_[i].length() - (status_[i].length() / 2);
                            if (len < cunt_jalase) {
                                for (; len < cunt_jalase; len++) {
                                    status_[i] = "-~" + status_[i];
                                    nomreh_[i] = "-~" + nomreh_[i];
                                }
                            }
                        }
                        view = true;
                        //******************************************************************************
                    } else {
                        //TOAST("خطا در ثبت جلسه...!");
                        finish();
                    }

                }
            });
        } catch (final Exception e) {
            TOAST("qure \n" + e.toString());
        }
    }

}