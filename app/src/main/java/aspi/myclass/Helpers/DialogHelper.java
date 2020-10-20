package aspi.myclass.Helpers;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.List;

import aspi.myclass.Interface.RequestInterface;
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
    static Tools tools = new Tools();

    public static void uploadBackupFile(final Context context, String _body, final DatabasesHelper data) {
        final Dialog dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();


        TextView title = dialog.findViewById(R.id.dialog_custom_title);
        TextView body = dialog.findViewById(R.id.dialog_custom_bodetxt);
        title.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.clouddownload), null);
        final TextView cancle = dialog.findViewById(R.id.dialog_custom_cancle);
        TextView okey = dialog.findViewById(R.id.dialog_custom_okey);


        title.setText(context.getResources().getString(R.string.uploadBackupApplication));
        body.setText(_body);
        cancle.setText(context.getResources().getString(R.string.cancle));
        okey.setText(context.getResources().getString(R.string.Upload));


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

                    if (Class.length > 0 && Tools.checkFileInFolder(Backup_File_App, "/class")) {
                        data.open();
                        for (int i = 0; i < Class.length; i++) {
                            String[] item = Class[i].split("~");
                            data.insert_class2(item[0], item[1], item[2], item[3], item[4], item[5], item[6], NC + item[7], item[8]);
                        }

                        if (Student.length > 0 && Tools.checkFileInFolder(Backup_File_App, "/student")) {
                            for (int i = 0; i < Student.length; i++) {
                                String[] item = Student[i].split("~");
                                data.insert_student2(item[0], item[1], item[2], item[3], NC + item[4]);
                            }

                            if (Rollcall.length > 0 && Tools.checkFileInFolder(Backup_File_App, "/rollcall")) {
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
                                MessageHelper.Toast(context, context.getResources().getString(R.string.successUpload));
                            }
                        });
                    } else {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MessageHelper.Toast(context, context.getResources().getString(R.string.notDataUpload));
                            }
                        });
                    }
                    dialog.dismiss();

                } catch (Exception e) {
                    Log.i(TAG, e.toString());
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MessageHelper.Toast(context, context.getResources().getString(R.string.worngUpload));
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

                MessageHelper.Toast(context, context.getResources().getString(R.string.successBackup));
            } else {
                MessageHelper.Toast(context, context.getResources().getString(R.string.notDataBackup));
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


        TextView title = dialog.findViewById(R.id.dialog_custom_title);
        TextView body = dialog.findViewById(R.id.dialog_custom_bodetxt);
        TextView cancle = dialog.findViewById(R.id.dialog_custom_cancle);
        TextView okey = dialog.findViewById(R.id.dialog_custom_okey);


        title.setText(context.getResources().getString(R.string.backupApplication));
        body.setText(_body);
        cancle.setText(context.getResources().getString(R.string.cancle));
        okey.setText(context.getResources().getString(R.string.Backup));


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
        title.setText(context.getResources().getString(R.string.titleClearSoftwareDialog));
        body.setText(context.getResources().getString(R.string.bodyClearSoftwareDialog));
        cancle.setText(context.getResources().getString(R.string.Clear));
        okey.setText(context.getResources().getString(R.string.cancle));
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
        progressDialog.setCancelable(false);
        progressDialog.setMessage(context.getResources().getString(R.string.pleaseWait));
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
                            MessageHelper.Toast(context, context.getResources().getString(R.string.successclearDatabase));
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


        TextView title = dialog.findViewById(R.id.dialog_custom_title);
        TextView body = dialog.findViewById(R.id.dialog_custom_bodetxt);
        TextView cancle = dialog.findViewById(R.id.dialog_custom_cancle);
        TextView okey = dialog.findViewById(R.id.dialog_custom_okey);


        title.setText(context.getResources().getString(R.string.aboutUs));
        body.setText(context.getResources().getString(R.string.bodyAboutus) + "\n" + "\n"
                + context.getResources().getString(R.string.app_name) + " " + version);
        cancle.setText(context.getResources().getString(R.string.cancle));
        okey.setText(context.getResources().getString(R.string.RateApp));


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


        TextView title = dialog.findViewById(R.id.dialog_custom_title);
        TextView body = dialog.findViewById(R.id.dialog_custom_bodetxt);
        TextView cancle = dialog.findViewById(R.id.dialog_custom_cancle);
        TextView okey = dialog.findViewById(R.id.dialog_custom_okey);


        title.setText(context.getResources().getString(R.string.TitleDialogSendValidationEmail));
        body.setText(context.getResources().getString(R.string.BodyDialogSendValidationEmail));
        cancle.setText(context.getResources().getString(R.string.cancle));
        okey.setText(context.getResources().getString(R.string.okey));


        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String model = android.os.Build.MODEL + " " + android.os.Build.BRAND + " (" + android.os.Build.VERSION.RELEASE + ")" + " API-" + android.os.Build.VERSION.SDK_INT;
                final String codes = Tools.Pin_Cod(4);

                String Subject = context.getResources().getString(R.string.SubjectDialogSendValidationEmail);
                String Body = Email
                        + "\n" + model
                        + "\n" + context.getResources().getString(R.string.CodeDialog)
                        + "\n" + codes
                        + "\n" + context.getResources().getString(R.string.YourEmailDialog)
                        + "\n" + Email;
                EmailHelper.SendEmail(context, Email, Subject, Body, context.getResources().getString(R.string.errorSendCodeDialogSuccess), 1, dialog, codes, Email);


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


        final EditText ECode = dialog.findViewById(R.id.setcode_code);
        TextView OK = dialog.findViewById(R.id.set_ok);
        TextView Cancel = dialog.findViewById(R.id.set_cancel);


        OK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Code_save.equals(ECode.getText().toString())) {
                    SharedPreferencesHelper.SetCode("EC_Email", "null", context);
                    SharedPreferencesHelper.SetCode("save_Email", "1", context);
                    SettingActivity.SetEmail(SharedPreferencesHelper.get_Data("Email", "", context));
                    MessageHelper.Toast(context, context.getResources().getString(R.string.SuccessRegistered));
                    dialog.dismiss();
                } else {
                    MessageHelper.Toast(context, context.getResources().getString(R.string.errorValidCode));
                }
            }
        });


        Cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


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
        title.setText(context.getResources().getString(R.string.SendPassword));
        body.setText(context.getResources().getString(R.string.QuestionConnenctionNet));
        cancle.setText(context.getResources().getString(R.string.no));
        okey.setText(context.getResources().getString(R.string.yes));
        //*************************************************
        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Body = context.getResources().getString(R.string.HelloIsPassword) + " \n" + SharedPreferencesHelper.get_Data("Password_App", "null", context);
                String Subject = context.getResources().getString(R.string.SubjectForgotPassword);
                String MailTo = SharedPreferencesHelper.get_Data("Email", "", context);
                EmailHelper.SendEmail(context, MailTo, Subject, Body, context.getResources().getString(R.string.SendEmail), 0, dialog);

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
        title.setText(context.getResources().getString(R.string.addStudent));
        body.setText(context.getResources().getString(R.string.textGetExcellFile1)
                + MainActivity.Address_file_app
                + context.getResources().getString(R.string.textGetExcellFile2));
        cancle.setText(context.getResources().getString(R.string.cancle));
        excllFile.setText(context.getResources().getString(R.string.save));
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
        title.setText(context.getResources().getString(R.string.TitleDialogdeleteMateing));
        body.setText(Body);
        Delete.setText(context.getResources().getString(R.string.delete));
        cancle.setText(context.getResources().getString(R.string.cancle));
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
        String lang = LanguageHelper.loadLanguage(context);
        if (lang.equals("fa")) {
            sno.setHint(context.getResources().getString(R.string.studentCode) + " " + context.getResources().getString(R.string.EnterText));
        } else {
            sno.setHint(context.getResources().getString(R.string.EnterText) + " " + context.getResources().getString(R.string.studentCode));
        }
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
                        MessageHelper.Toast(context, context.getResources().getString(R.string.ErrorStudentCode));
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
        String lang = LanguageHelper.loadLanguage(context);
        if (lang.equals("fa")) {
            name.setHint(context.getResources().getString(R.string.studentName) + " " + context.getResources().getString(R.string.EnterText));
        } else {
            name.setHint(context.getResources().getString(R.string.EnterText) + " " + context.getResources().getString(R.string.studentName));
        }
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
        String lang = LanguageHelper.loadLanguage(context);
        if (lang.equals("fa")) {
            family.setHint(context.getResources().getString(R.string.studentFamily) + " " + context.getResources().getString(R.string.EnterText));
        } else {
            family.setHint(context.getResources().getString(R.string.EnterText) + " " + context.getResources().getString(R.string.studentFamily));
        }
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
        if (content.text.isEmpty()) {
            String lang = LanguageHelper.loadLanguage(context);
            if (lang.equals("fa")) {
                description.setHint(context.getResources().getString(R.string.studentDescription) + " " + context.getResources().getString(R.string.EnterText));
            } else {
                description.setHint(context.getResources().getString(R.string.EnterText) + " " + context.getResources().getString(R.string.studentDescription));
            }
        } else
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
                        Description.setText(description.getText().toString().replace("\n", "  ").substring(0, 50) + " ...");
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
        title.setText(context.getResources().getString(R.string.save));
        body.setText(context.getResources().getString(R.string.bodyExcllDialog1) + NameClass + context.getResources().getString(R.string.bodyExcllDialog2));
        cancle.setText(context.getResources().getString(R.string.cancle));
        save.setText(context.getResources().getString(R.string.save));
        //************************************************
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ExcellHelper.ExcelSaveDataClassByTypePage(context, model, NameClass, NameClass + " " + Title, TypePage)) {
                    MessageHelper.Toast(context, MainActivity.Address_file_app + "/" + NameClass + " " + Title + ".xls");
                } else {
                    if (!MainActivity.Address_file_app.exists()) {
                        MessageHelper.Toast(context, context.getResources().getString(R.string.ErrorSaveFile));
                    } else {
                        MessageHelper.Toast(context, context.getResources().getString(R.string.ErrorCreadetFolder));
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

    public static void ChangeLanguage(final Context context) {
        final String[] listLang = {"English", "فارسی"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.chooseLanguage));
        builder.setSingleChoiceItems(listLang, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        LanguageHelper.setLocal(context, "en");
                        break;
                    case 1:
                        LanguageHelper.setLocal(context, "fa");
                        break;
                }
                dialogInterface.dismiss();
                Tools.restartApplication(context);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public static void errorReport(final Context context, String ActivityName, String methodName, String error) {

        final String Error = tools.getDeviceModel()
                + "\nin => " + ActivityName
                + "\nmethodName =>" + methodName
                + "\nError is:\n" + error;

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
        TextView report = dialog.findViewById(R.id.dialog_custom_okey);
        //*************************************************
        title.setText("");
        body.setText(context.getResources().getString(R.string.bodyReport));
        report.setText(context.getResources().getString(R.string.report));
        cancle.setText(context.getResources().getString(R.string.cancle));


        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                EmailHelper.SendEmail_(context, context.getResources().getString(R.string.Email), "Error Application", Error, null);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

}
