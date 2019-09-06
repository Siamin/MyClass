package aspi.myclass.Helpers;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;

import aspi.myclass.R;
import aspi.myclass.Tools.Tools;
import aspi.myclass.activity.MainActivity;
import aspi.myclass.activity.SettingActivity;

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
        } catch (Exception e) {
            Return = "";
        }
        return Return;
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
                    dialog.dismiss();
                    MessageHelper.Toast(context, "بارگزاری انجام شد...!");
                } catch (Exception e) {
                    Log.i(TAG, e.toString());
                    MessageHelper.Toast(context, "فایل پشتیبانی خراب است");
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
            try {
                data.open();
                int cunt_class = data.count("dars");
                int cunt_student = data.count("klas");
                int cunt_roll = data.count("rollcall");

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
                dialog.dismiss();
                MessageHelper.Toast(context, "فایل پشتیبانی گرفته شد...!");
            } catch (Exception e) {
                Log.i(TAG, e.toString());
            }
        } catch (Exception e) {
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
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo("aspi.myclass", 0);
            String version = pInfo.versionName;
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
            builder.setIcon(R.drawable.abute);

            builder.setTitle("درباره ما").setMessage("نرم افزار حضور وغیاب برای راحتی کار اساتید و معلمین گرامی طراحی و ساخته شده است برای حمایت از ما در نرم افزار بازار به ما رای بدهید" + "\n" + "\n" + "نرم افزار حضور و غیاب اساتید" + version);
            builder.setPositiveButton("امتیاز دادن به برنامه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Tools.openApplicationByUriAndPackage(context, context.getResources().getString(R.string.uri_bazar_my_application_class), context.getResources().getString(R.string.package_bazar));
                }
            });
            builder.setNegativeButton("دیگر برنامه های ما", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Tools.openApplicationByUriAndPackage(context, context.getResources().getString(R.string.uri_bazar_my_application), context.getResources().getString(R.string.package_bazar));

                }
            });
            AlertDialog aler = builder.create();
            aler.show();
        } catch (Exception e) {
        }
    }

    public static void Qusins(final Context context) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
            builder.setIcon(R.drawable.abute);

            builder.setTitle("بروزرسانی").setMessage("آیا مایلید برنامه حضور و غیاب را بروزرسانی کنید ؟");
            builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Tools.openApplicationByUriAndPackage(context, context.getResources().getString(R.string.uri_bazar_my_application_class), context.getResources().getString(R.string.package_bazar));
                }
            });
            builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });
            AlertDialog aler = builder.create();
            aler.show();
        } catch (Exception e) {

        }
    }

    public static void sendEmail(final Context context, final String Email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
        builder.setTitle("ارسال کد تایید").setMessage("آیا میخواهید کد تایید برای ایمیل شما ارسال شود؟");
        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                String model = android.os.Build.MODEL + " " + android.os.Build.BRAND + " (" + android.os.Build.VERSION.RELEASE + ")" + " API-" + android.os.Build.VERSION.SDK_INT;
                final String codes = Tools.Pin_Cod(4);

                String Subject = "کد تایید نرم افزار دفتر نمره حضور و غیاب ";
                String Body = Email + "\n" + model + "\nکد تایید\n" + codes + "\n ایمیل شما \n" + Email;

                BackgroundMail.newBuilder(context)
                        .withUsername("amin.syahi.1369@gmail.com")
                        .withPassword("942134025")
                        .withMailto(Email)
                        .withType(BackgroundMail.TYPE_PLAIN)
                        .withSubject(Subject)
                        .withBody(Body)
                        .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                            public void onSuccess() {
                                MessageHelper.Toast(context, "کد برای ایمیل شما ارسال شد...!");
                                SharedPreferencesHelper.SetCode("EC_Email", codes, context);
                                SharedPreferencesHelper.SetCode("Email", Email, context);
                                SharedPreferencesHelper.SetCode("save_Email", "0", context);
                                VirifyCodeEmail(context);

                            }
                        })
                        .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                            public void onFail() {

                                MessageHelper.Toast(context, "خطا در ارسال ایمیل...!");
                            }
                        })
                        .send();
            }
        });
        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        AlertDialog aler = builder.create();
        aler.show();
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

}
