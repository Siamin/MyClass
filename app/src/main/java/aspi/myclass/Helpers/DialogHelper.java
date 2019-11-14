package aspi.myclass.Helpers;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.List;

import aspi.myclass.R;
import aspi.myclass.Tools.Tools;

import aspi.myclass.activity.MainActivity;
import aspi.myclass.activity.SettingActivity;
import aspi.myclass.adapter.ListCreateClassAdapter;
import aspi.myclass.model.AbsentPersentModel;
import aspi.myclass.model.ReportDataModel;

public class DialogHelper {


    static String TAG = "TAG_DialogHelper";
    static File Backup_File_App = new File(Environment.getExternalStorageDirectory(), "BackupClass");
    File Address_file_app = new File(Environment.getExternalStorageDirectory(), "App_class");


    public static void uploadBackupFile(final Context context, String _body, final DatabasesHelper data) {
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //************************************************
        TextView title = dialog.findViewById(R.id.dialog_custom_title);
        TextView body = dialog.findViewById(R.id.dialog_custom_bodetxt);
        title.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.clouddownload), null);
        final TextView cancle = dialog.findViewById(R.id.dialog_custom_cancle);
        TextView okey = dialog.findViewById(R.id.dialog_custom_okey);
        //*************************************************
        title.setText("بازخوانی اطلاعات");
        body.setText(_body);
        cancle.setText("انصراف");
        okey.setText("بازخوانی");
        //*************************************************
        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                upload_backup(data, context, dialog);

            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    static String ReadFileExternal(String name) {
        String Return = "", Line = "", External = Backup_File_App.toString();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(External + name));
            while ((Line = bufferedReader.readLine()) != null) {
                Return += Line.replace("%", "").replace("null", "") + "%";
            }
            return Return;
        } catch (Exception e) {
            return "";
        }

    }

    static void upload_backup(final DatabasesHelper data, final Context context, final Dialog dialog) {
        Calendar c = Calendar.getInstance();
        final String NC = "" + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND) + "";
        final String Class[] = ReadFileExternal("/class").split("%");
        final String Student[] = ReadFileExternal("/student").split("%");
        final String Rollcall[] = ReadFileExternal("/rollcall").split("%");
        new Thread(new Runnable() {
            public void run() {
                try {

                    if (Class.length > 0 && Tools.checkFileInFolder(Backup_File_App,"/class")) {
                        data.open();
                        for (int i = 0; i < Class.length; i++) {
                            String[] item = Class[i].split("~");
                            data.insert_class2(item[0], item[1], item[2], item[3], item[4], item[5], item[6], NC + item[7], item[8]);
                        }

                        if (Student.length > 0 && Tools.checkFileInFolder(Backup_File_App,"/student")) {
                            for (int i = 0; i < Student.length; i++) {
                                String[] item = Student[i].split("~");
                                data.insert_student2(item[0], item[1], item[2], item[3], NC + item[4]);
                            }

                            if (Rollcall.length > 0 && Tools.checkFileInFolder(Backup_File_App,"/rollcall")) {
                                for (int i = 0; i < Rollcall.length; i++) {
                                    String[] item = Rollcall[i].split("~");
                                    boolean status = true;
                                    if (item[1].equals("0")) status = false;
                                    data.insert_Rollcall(item[0], status, item[2], item[3], item[4], item[5], NC + item[6], item[7] + NC, item[8]);
                                }



                            }
                        }

                        data.close();
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MessageHelper.Toast(context, "بارگزاری انجام شد...!");
                            }
                        });
                    } else {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MessageHelper.Toast(context, "اطلاعاتی برای بازیابی وجود ندارد...!");
                            }
                        });
                    }
                    dialog.dismiss();

                } catch (Exception e) {
                    Log.i(TAG, e.toString());
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MessageHelper.Toast(context, "فایل پشتیبانی خراب است");
                        }
                    });

                    dialog.dismiss();
                }
            }
        }).start();
    }

    static void write(String name, String Text) {
        try {
            File Backups = new File(Backup_File_App, name);
            FileWriter fileWriter = new FileWriter(Backups);
            fileWriter.append(Text);
            fileWriter.close();
        } catch (Exception e) {
        }
    }

    static void BackUpDataBase(DatabasesHelper data, Context context, Dialog dialog) {
        String BACKUP_class = "", BACKUP_student = "", BACKUP_roll = "";
        try {
            data.open();
            int count_class = data.count("dars");
            int count_student = data.count("klas");
            int count_roll = data.count("rollcall");

            Log.i(TAG, "dars : " + count_class);
            Log.i(TAG, "klas : " + count_student);
            Log.i(TAG, "rollcall : " + count_roll);

            if (count_class > 0) {

                for (int i = 0; i < count_class; i++) {
                    BACKUP_class += data.Display_all("dars", i, 1, 9) + "\n";
                }


                if (count_student > 0) {
                    for (int i = 0; i < count_student; i++) {
                        BACKUP_student += data.Display_all("klas", i, 1, 8) + "\n";
                    }

                    if (count_roll > 0) {
                        for (int i = 0; i < count_roll; i++) {
                            BACKUP_roll += data.Display_all("rollcall", i, 1, 9) + "\n";
                        }

                        write("rollcall", BACKUP_roll);

                    }
                    write("student", BACKUP_student);
                }
                write("class", BACKUP_class);

                MessageHelper.Toast(context, "فایل پشتیبانی گرفته شد...!");
            } else {
                MessageHelper.Toast(context, "اطلاعاتی برای پشتیبانی گرفت ثبت نشده!");
            }
            dialog.dismiss();
            data.close();
        } catch (Exception e) {
            dialog.dismiss();
            Log.i(TAG, e.toString());
        }
    }

    public static void backupFile(final Context context, String _body, final DatabasesHelper data) {
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //************************************************
        TextView title = dialog.findViewById(R.id.dialog_custom_title);
        TextView body = dialog.findViewById(R.id.dialog_custom_bodetxt);
        TextView cancle = dialog.findViewById(R.id.dialog_custom_cancle);
        TextView okey = dialog.findViewById(R.id.dialog_custom_okey);
        //*************************************************
        title.setText("پشتیبانی اطلاعات");
        title.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.ic_check_black_24dp), null);
        body.setText(_body);
        cancle.setText("انصراف");
        okey.setText("پشتیبانی");
        //*************************************************
        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackUpDataBase(data, context, dialog);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public static void cleanDatabase(final Context context, final DatabasesHelper data) {
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        //************************************************
        TextView title = dialog.findViewById(R.id.dialog_custom_title);
        TextView body = dialog.findViewById(R.id.dialog_custom_bodetxt);
        TextView cancle = dialog.findViewById(R.id.dialog_custom_cancle);
        TextView okey = dialog.findViewById(R.id.dialog_custom_okey);
        //*************************************************
        title.setText("پاک کردن حافظه نرم افزار");
        body.setText("با پاک کردن حافظه نرم افزار تمام اسامی ، درس ها و جلسات برگزار شده در نرم افزار ،حذف می شوند!");
        cancle.setText("پاک کردن");
        okey.setText("انصراف");
        //*************************************************
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Clear_databaese(data, context, dialog);
            }
        });

        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    static void Clear_databaese(final DatabasesHelper data, final Context context, final Dialog dialog) {
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.NewDialog);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setProgress(0);
        progressDialog.setProgressDrawable(context.getResources().getDrawable(R.drawable.dialog));
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
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MessageHelper.Toast(context, "حافظه نرم افزار با موفقیت پاک شد...!");
                            dialog.dismiss();
                        }
                    });
                } catch (Exception e) {

                }
                progressDialog.cancel();
            }
        }).start();

    }

    public static void Abute(final Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo("aspi.myclass", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //************************************************
        TextView title = dialog.findViewById(R.id.dialog_custom_title);
        TextView body = dialog.findViewById(R.id.dialog_custom_bodetxt);
        TextView cancle = dialog.findViewById(R.id.dialog_custom_cancle);
        TextView okey = dialog.findViewById(R.id.dialog_custom_okey);
        //*************************************************
        title.setText("درباره ما");
        body.setText("نرم افزار حضور وغیاب برای راحتی کار اساتید و معلمین گرامی طراحی و ساخته شده است برای حمایت از ما در نرم افزار بازار به ما رای بدهید" + "\n" + "\n" + "نرم افزار حضور و غیاب اساتید" + version);
        cancle.setText("امتیاز دادن");
        okey.setText("دیگر برنامه ها");
        cancle.setTextColor(context.getResources().getColor(R.color.green));
//        title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.abute, 0);
        //*************************************************
        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.openApplicationByUriAndPackage(context, context.getResources().getString(R.string.uri_bazar_my_application), context.getResources().getString(R.string.package_bazar));

            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.openApplicationByUriAndPackage(context, context.getResources().getString(R.string.uri_bazar_my_application_class), context.getResources().getString(R.string.package_bazar));
            }
        });

    }

    public static void UpdateApplication(final Context context) {

        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //************************************************
        TextView title = dialog.findViewById(R.id.dialog_custom_title);
        TextView body = dialog.findViewById(R.id.dialog_custom_bodetxt);
        TextView cancle = dialog.findViewById(R.id.dialog_custom_cancle);
        TextView okey = dialog.findViewById(R.id.dialog_custom_okey);
        //*************************************************
        title.setText("بروزرسانی");
        body.setText("آیا مایلید برنامه حضور و غیاب را بروزرسانی کنید ؟");
        cancle.setText("خیر");
        okey.setText("بله");
        //*************************************************
        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.openApplicationByUriAndPackage(context, context.getResources().getString(R.string.uri_bazar_my_application_class), context.getResources().getString(R.string.package_bazar));

            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public static void sendCodeEmail(final Context context, final String Email) {
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //************************************************
        TextView title = dialog.findViewById(R.id.dialog_custom_title);
        TextView body = dialog.findViewById(R.id.dialog_custom_bodetxt);
        TextView cancle = dialog.findViewById(R.id.dialog_custom_cancle);
        TextView okey = dialog.findViewById(R.id.dialog_custom_okey);
        //*************************************************
        title.setText("ارسال کد تایید");
        body.setText("آیا میخواهید کد تایید برای ایمیل شما ارسال شود؟");
        cancle.setText("خیر");
        okey.setText("بله");
        //*************************************************
        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String model = android.os.Build.MODEL + " " + android.os.Build.BRAND + " (" + android.os.Build.VERSION.RELEASE + ")" + " API-" + android.os.Build.VERSION.SDK_INT;
                final String codes = Tools.Pin_Cod(4);

                String Subject = "کد تایید نرم افزار دفتر نمره حضور و غیاب ";
                String Body = Email + "\n" + model + "\nکد تایید\n" + codes + "\n ایمیل شما \n" + Email;
                EmailHelper.SendEmail(context, Email, Subject, Body, "کد برای ایمیل شما ارسال شد...!", 1,dialog, codes, Email);

            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public static void VirifyCodeEmail(final Context context) {

        final String Code_save = SharedPreferencesHelper.get_Data("EC_Email", "null", context);
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_verifycode);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        final EditText ECode = (EditText) dialog.findViewById(R.id.setcode_code);
        TextView OK = (TextView) dialog.findViewById(R.id.set_ok);
        TextView Cancel = (TextView) dialog.findViewById(R.id.set_cancel);
        //******************************************************************************************
        OK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Code_save.equals(ECode.getText().toString())) {
                    SharedPreferencesHelper.SetCode("EC_Email", "null", context);
                    SharedPreferencesHelper.SetCode("save_Email", "1", context);
                    SettingActivity.SetEmail(SharedPreferencesHelper.get_Data("Email", "", context));
                    MessageHelper.Toast(context, "ایمیل شما ثبت شد...!");
                    dialog.dismiss();
                } else {
                    MessageHelper.Toast(context, "کد وارد شده اشتباه است...!");
                }
            }
        });
        //******************************************************************************************
        Cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //******************************************************************************************

    }

    public static void ForgotPassword(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //************************************************
        TextView title = dialog.findViewById(R.id.dialog_custom_title);
        TextView body = dialog.findViewById(R.id.dialog_custom_bodetxt);
        TextView cancle = dialog.findViewById(R.id.dialog_custom_cancle);
        TextView okey = dialog.findViewById(R.id.dialog_custom_okey);
        //*************************************************
        title.setText("ارسال رمز عبور");
        body.setText("آیا دستگاه شما به اینترنت متصل است؟");
        cancle.setText("خیر");
        okey.setText("بله");
        //*************************************************
        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Body = " با سلام رمز عبور شما \n" + SharedPreferencesHelper.get_Data("Password_App", "null", context);
                String Subject = "رمز عبور نرم افزار دفتر نمره حضور و غیاب " + SharedPreferencesHelper.get_Data("Email", "", context);
                String MailTo = SharedPreferencesHelper.get_Data("Email", "", context);
                EmailHelper.SendEmail(context, MailTo, Subject, Body, "ایمیل ارسال شد...!", 0,dialog);

            }
        });
    }

    public static void Import(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //************************************************
        TextView title = dialog.findViewById(R.id.dialog_custom_title);
        TextView body = dialog.findViewById(R.id.dialog_custom_bodetxt);
        TextView cancle = dialog.findViewById(R.id.dialog_custom_cancle);
        TextView excllFile = dialog.findViewById(R.id.dialog_custom_okey);
        //*************************************************
        title.setText("اضافه کردن دانشجو");
        body.setText("آیا میخواهید اسامی دانشجویان را از حافظه ای  " + MainActivity.Address_file_app + "در فایل Text با نام " + "IO.txt" + " و یا در فایل Excel با نام " + "IO.xls" + " دریافت کنید؟ ");
        cancle.setText("انصراف");
        excllFile.setText("فایل اکسل");
        //************************************************
        excllFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExcellHelper.readExcelFile(Environment.getExternalStorageDirectory() + "/App_class/IO.xls", context, dialog);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    public static void DeleteOldSession(final Context context, String Body, final String session) {
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //************************************************
        TextView title = dialog.findViewById(R.id.dialog_custom_title);
        TextView body = dialog.findViewById(R.id.dialog_custom_bodetxt);
        TextView Delete = dialog.findViewById(R.id.dialog_custom_cancle);
        TextView cancle = dialog.findViewById(R.id.dialog_custom_okey);
        //*************************************************
        title.setText("حذف جلسه");
        body.setText(Body);
        Delete.setText("حذف");
        cancle.setText("انصراف");
        //************************************************
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListCreateClassAdapter.Delete_rollcall(context, session, dialog);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    public static void SnoStudent(final Context context, String Title, final TextView Sno, final DatabasesHelper data, final AbsentPersentModel content, final String did) {
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_score);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //************************************************
        TextView title = dialog.findViewById(R.id.dialog_score_tite);
        final EditText sno = dialog.findViewById(R.id.dialog_score_score);
        TextView submit = dialog.findViewById(R.id.dialog_score_save);
        TextView cancle = dialog.findViewById(R.id.dialog_score_cancle);
        //*************************************************
        title.setText(Title);
        sno.setText(content.sno);
        //************************************************
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    data.open();

                    if (data.Update_sno(sno.getText().toString(), did, content.sno)) {
                        Sno.setText(sno.getText().toString());
                        content.sno = sno.getText().toString();
                    } else {
                        MessageHelper.Toast(context, "این شماره برای دانشجویی دیگه ثبت شده!");
                    }

                    data.close();
                    dialog.dismiss();
                } catch (Exception e) {

                }
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public static void NameStudent(final Context context, String Title, final TextView Name, final DatabasesHelper data, final AbsentPersentModel content) {
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_score);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //************************************************
        TextView title = dialog.findViewById(R.id.dialog_score_tite);
        final EditText name = dialog.findViewById(R.id.dialog_score_score);
        TextView submit = dialog.findViewById(R.id.dialog_score_save);
        TextView cancle = dialog.findViewById(R.id.dialog_score_cancle);
        //*************************************************
        title.setText(Title);
        name.setHint("نام را وارد کنید");
        name.setText(content.name);
        //************************************************
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    data.open();
                    data.update_one("klas", "name", name.getText().toString(), Integer.parseInt(content.id));
                    data.close();
                    Name.setText("" + name.getText().toString());
                    content.name = name.getText().toString();
                    dialog.dismiss();
                } catch (Exception e) {
                }
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public static void FamilyStudent(final Context context, String Title, final TextView Family, final DatabasesHelper data, final AbsentPersentModel content) {
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_score);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //************************************************
        TextView title = dialog.findViewById(R.id.dialog_score_tite);
        final EditText family = dialog.findViewById(R.id.dialog_score_score);
        TextView submit = dialog.findViewById(R.id.dialog_score_save);
        TextView cancle = dialog.findViewById(R.id.dialog_score_cancle);
        //*************************************************
        title.setText(Title);
        family.setHint("نام خانوادگی را وارد کنید");
        family.setText(content.family);
        //************************************************
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    data.open();
                    data.update_one("klas", "family", family.getText().toString(), Integer.parseInt(content.id));
                    data.close();
                    Family.setText("" + family.getText().toString());
                    content.family = family.getText().toString();
                    dialog.dismiss();
                } catch (Exception e) {
                }
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public static void ScoreStudent(final Context context, String Title, final TextView Score, final DatabasesHelper data, final AbsentPersentModel content) {
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_score);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //************************************************
        TextView title = dialog.findViewById(R.id.dialog_score_tite);
        final EditText score = dialog.findViewById(R.id.dialog_score_score);
        TextView submit = dialog.findViewById(R.id.dialog_score_save);
        TextView cancle = dialog.findViewById(R.id.dialog_score_cancle);
        //*************************************************
        title.setText(Title);
        //************************************************
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    data.open();
                    data.update_one("rollcall", "am", score.getText().toString(), Integer.parseInt(content.id_rull));
                    data.close();
                    Score.setText("" + score.getText().toString());
                    content.nomreh = score.getText().toString();
                    dialog.dismiss();
                } catch (Exception e) {
                }
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public static void DescriptionStudent(final Context context, String Title, final TextView Description, final DatabasesHelper data, final AbsentPersentModel content) {
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_score);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //************************************************
        TextView title = dialog.findViewById(R.id.dialog_score_tite);
        final EditText description = dialog.findViewById(R.id.dialog_score_score);
        TextView submit = dialog.findViewById(R.id.dialog_score_save);
        TextView cancle = dialog.findViewById(R.id.dialog_score_cancle);
        //*************************************************
        title.setText(Title);
        if (content.text.isEmpty())
            description.setText("توضیحات...");
        else
            description.setText(content.text);
        //************************************************
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    data.open();
                    data.update_one("klas", "tx", description.getText().toString(), Integer.parseInt(content.id));
                    data.close();
                    if (description.getText().toString().replace("\n", "  ").length() > 50) {
                        Description.setText(description.getText().toString().replace("\n", "  ").substring(0, 50) + "  توضایحات ادامه دارد...");
                    } else {
                        Description.setText(description.getText().toString().replace("\n", "  "));
                    }
                    content.text = description.getText().toString();

                    dialog.dismiss();

                } catch (Exception e) {
                }
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public static void qustionSaveExcll(final Context context, final List<ReportDataModel> model, final String Title, final String NameClass, final boolean TypePage) {
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //************************************************
        TextView title = dialog.findViewById(R.id.dialog_custom_title);
        TextView body = dialog.findViewById(R.id.dialog_custom_bodetxt);
        TextView cancle = dialog.findViewById(R.id.dialog_custom_cancle);
        TextView save = dialog.findViewById(R.id.dialog_custom_okey);
        //*************************************************
        title.setText("ذخیره اطلاعات");
        body.setText("آیا می خواهید اطلاعات کلاس " + NameClass + " را در یک فایل اکسل ذخیره کنید؟");
        cancle.setText("انصراف");
        save.setText("ذخیره");
        //************************************************
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ExcellHelper.ExcelSaveDataClassByTypePage(context, model, NameClass, NameClass + " " + Title, TypePage)) {
                    MessageHelper.Toast(context, MainActivity.Address_file_app + "/" + NameClass + " " + Title + ".xls");
                } else {
                    if (!MainActivity.Address_file_app.exists()) {
                        MessageHelper.Toast(context, "مشکل در ایجاد پوشه ی  " + MainActivity.Address_file_app + " در حافظه ی گوشی ");
                    } else {
                        MessageHelper.Toast(context, "مشکل در ذخیره فایل در حافظه گوشی");
                    }
                }
                dialog.dismiss();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

}
