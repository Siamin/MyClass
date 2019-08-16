package aspi.myclass.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import java.util.Calendar;

import aspi.myclass.R;

public class SettingActivity extends Activity {

    private SharedPreferences sp;
    private Button save, cancel, Bmail;
    private EditText old_password, new_password1, new_password2, email;
    private CheckBox checkBox_password, font1, font2, font3, on;
    private TextView text_old_password, text_new_password1, text_new_password2, test_text;
    private String password_chek, Font, Number;
    private int Size_Text, refresh = 0;
    private Typeface font_1, font_2, font_3;
    static Timer time;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_setting);
        config();

        font_1 = Typeface.createFromAsset(getAssets(), "Font/font1.ttf");
        font_2 = Typeface.createFromAsset(getAssets(), "Font/font2.ttf");
        font_3 = Typeface.createFromAsset(getAssets(), "Font/font3.ttf");

        getpassword();


        on.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (on.isChecked()) {
                    Number = "on";
                } else {
                    Number = "off";
                }
            }
        });


        checkBox_password.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (checkBox_password.isChecked()) {
                    setEnabled(false);
                } else {
                    sp = getApplicationContext().getSharedPreferences("myclass", 0);
                    if (!sp.getString("EC_Email", "null").equals("null") && sp.getString("save_Email", "0").equals("1") && !sp.getString("Email", "").equals("")) {
                        setEnabled(true);
                    } else {
                        TOAST("  برای استفاده از این قسمت باید ایمیل خود را در سیستم ثبت کنید. ");
                        setEnabled(false);
                        checkBox_password.setChecked(true);
                    }
                }
            }
        });

        font1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (font1.isChecked()) {
                    font2.setChecked(false);
                    font3.setChecked(false);
                    test_text.setTypeface(font_1);
                }
            }
        });

        font2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (font2.isChecked()) {
                    font1.setChecked(false);
                    font3.setChecked(false);
                    test_text.setTypeface(font_2);
                }
            }
        });

        font3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (font3.isChecked()) {
                    font1.setChecked(false);
                    font2.setChecked(false);
                    test_text.setTypeface(font_3);
                }
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (save()) {
                    Go_main();
                    TOAST("ذخیره شد...!");
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Go_main();
            }
        });
        //******************************************************************************************
        Bmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this, R.style.MyAlertDialogStyle);
                    //builder.setIcon(R.drawable.abute);
                    builder.setTitle("ارسال کد تایید").setMessage("آیا میخواهید کد تایید برای ایمیل شما ارسال شود؟");
                    builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            String model = android.os.Build.MODEL + " " + android.os.Build.BRAND + " (" + android.os.Build.VERSION.RELEASE + ")" + " API-" + android.os.Build.VERSION.SDK_INT;
                            Calendar c = Calendar.getInstance();
                            int x = c.get(Calendar.DAY_OF_YEAR);
                            x *= c.get(Calendar.SECOND);
                            x -= c.get(Calendar.MINUTE);
                            x /= c.get(Calendar.DAY_OF_MONTH);
                            x += c.get(Calendar.HOUR_OF_DAY);
                            final int codes = x;
                            BackgroundMail.newBuilder(SettingActivity.this)
                                    .withUsername("amin.syahi.1369@gmail.com")
                                    .withPassword("919121318")
                                    .withMailto(email.getText().toString())
                                    .withType(BackgroundMail.TYPE_PLAIN)
                                    .withSubject("کد تایید نرم افزار دفتر نمره حضور و غیاب ")
                                    .withBody(email.getText().toString() + "\n" + model + "\nکد تایید\n" + x + "\n ایمیل شما \n" + email.getText().toString())
                                    .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                        public void onSuccess() {
                                            Toast toast = Toast.makeText(SettingActivity.this, "  کد برای ایمیل شما ارسال شد...!  ", Toast.LENGTH_LONG);
                                            TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                                            textView.setTextColor(getResources().getColor(R.color.toast));
                                            textView.setTypeface(MainActivity.FONTS);
                                            textView.setTextSize(18);
                                            textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                            View view = toast.getView();
                                            view.setBackgroundResource(R.drawable.toast);
                                            toast.show();
                                            SetCode("myclass", "EC_Email", String.valueOf(codes));
                                            SetCode("myclass", "Email", email.getText().toString());
                                            SetCode("myclass", "save_Email", "0");
                                            refresh = 1;
                                        }
                                    })
                                    .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                        public void onFail() {
                                            Toast toast = Toast.makeText(SettingActivity.this, "  خطا در ارسال ایمیل...!  ", Toast.LENGTH_LONG);
                                            TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                                            textView.setTextColor(getResources().getColor(R.color.toast));
                                            textView.setTypeface(MainActivity.FONTS);
                                            textView.setTextSize(18);
                                            textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                            View view = toast.getView();
                                            view.setBackgroundResource(R.drawable.toast);
                                            toast.show();
                                            refresh = 0;
                                        }
                                    })
                                    .send();
                        }
                    });
                    builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            refresh = 0;
                        }
                    });
                    AlertDialog aler = builder.create();
                    aler.show();
                } catch (Exception e) {

                }
            }
        });

    }

    void config() {
        email = (EditText) findViewById(R.id.setting_Email);
        old_password = (EditText) findViewById(R.id.setting_password_old);
        new_password1 = (EditText) findViewById(R.id.setting_password1_new);
        new_password2 = (EditText) findViewById(R.id.setting_password2_new);
        checkBox_password = (CheckBox) findViewById(R.id.setting_check);
        font1 = (CheckBox) findViewById(R.id.setting_font1);
        font2 = (CheckBox) findViewById(R.id.setting_font2);
        font3 = (CheckBox) findViewById(R.id.setting_font3);
        text_old_password = (TextView) findViewById(R.id.setting_text_old_password);
        text_new_password1 = (TextView) findViewById(R.id.setting_text_new_password1);
        text_new_password2 = (TextView) findViewById(R.id.setting_text_new_password2);
        test_text = (TextView) findViewById(R.id.setting_text_test);
        save = (Button) findViewById(R.id.setting_save);
        Bmail = (Button) findViewById(R.id.setting_bmail);
        cancel = (Button) findViewById(R.id.setting_cancel);
        on = (CheckBox) findViewById(R.id.setting_numberon);
        test_text.setText("متن تست 1" + "text test 1");
        refresh();
    }

    void setEnabled(boolean status) {
        text_old_password.setEnabled(status);
        text_new_password1.setEnabled(status);
        text_new_password2.setEnabled(status);
        old_password.setEnabled(status);
        new_password1.setEnabled(status);
        new_password2.setEnabled(status);
    }

    private void getpassword() {
        sp = getApplicationContext().getSharedPreferences("myclass", 0);
        password_chek = sp.getString("Password_App", "null");
        Size_Text = Integer.parseInt(sp.getString("Size_Text_App", "15"));
        Font = sp.getString("Font_App", "font1");
        String Code_save = sp.getString("EC_Email", "null");
        String status_email = sp.getString("save_Email", "0");


        if (status_email.equals("1")) {
            email.setText(sp.getString("Email", ""));
        }

        if (sp.getString("status_number", "off").equals("off")) {
            on.setChecked(false);
        } else {
            on.setChecked(true);
        }
        if (password_chek.equals("null")) {
            checkBox_password.setChecked(true);
            password_chek = "0000";
            old_password.setText(password_chek);
            setEnabled(false);
        } else {
            checkBox_password.setChecked(false);
            setEnabled(true);
        }

        if (Font.equals("font1")) {
            font1.setChecked(true);
            font2.setChecked(false);
            font3.setChecked(false);
            test_text.setTypeface(font_1);
        } else if (Font.equals("font2")) {
            font1.setChecked(false);
            font2.setChecked(true);
            font3.setChecked(false);
            test_text.setTypeface(font_2);
        } else if (Font.equals("font3")) {
            font1.setChecked(false);
            font2.setChecked(false);
            font3.setChecked(true);
            test_text.setTypeface(font_3);
        }

        if (!Code_save.equals("null")) {
            Qusion();
        }

    }

    private void SetCode(String App, String name, String code) {
        sp = getApplicationContext().getSharedPreferences(App, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(name, code);
        edit.commit();
    }

    private void Go_main() {
        Intent setting = new Intent(this, MainActivity.class);
        startActivity(setting);
        finish();
    }

    private boolean save() {
        boolean res = true;
        if (!checkBox_password.isChecked()) {
            if (password_chek.equals(old_password.getText().toString())) {
                if (new_password1.getText().toString().equals(new_password2.getText().toString())) {
                    SetCode("myclass", "Password_App", new_password1.getText().toString());
                } else if (!new_password1.getText().toString().equals("") && !new_password2.getText().toString().equals("")) {
                    new_password1.setText("");
                    new_password2.setText("");
                    TOAST("رمز جدید را مجددا وارد کنید...!");
                    res = false;
                }
            } else if (!old_password.getText().toString().equals("")) {
                res = false;
                old_password.setText("");
                TOAST("رمز قدیمی را مجددا وارد کنید...!");
            }
        } else {
            SetCode("myclass", "Password_App", "null");
        }
        String font_set = "font1";
        if (font2.isChecked()) {
            font_set = "font2";
        } else if (font3.isChecked()) {
            font_set = "font3";
        }
        SetCode("myclass", "Size_Text_App", String.valueOf(Size_Text));
        SetCode("myclass", "Font_App", font_set);
        SetCode("myclass", "status_number", Number);
        return res;
    }

    private void TOAST(String TEXT) {
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

    void refresh() {
        time = new Timer();
        time.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (refresh == 1) {
                            refresh = 0;
                            Qusion();
                        }
                    }
                });
            }
        }, 1, 1000);
    }

    void Qusion() {
        try {
            sp = getApplicationContext().getSharedPreferences("myclass", 0);
            final String Code_save = sp.getString("EC_Email", "null");
            final Dialog kelas = new Dialog(SettingActivity.this, R.style.MyAlertDialogStyle);
            kelas.setContentView(R.layout.dialog_verifycode);
            kelas.setCancelable(true);
            kelas.setCanceledOnTouchOutside(true);
            kelas.show();
            final EditText ECode = (EditText) kelas.findViewById(R.id.setcode_code);
            final TextView OK = (TextView) kelas.findViewById(R.id.set_ok);
            final TextView Cancel = (TextView) kelas.findViewById(R.id.set_cancel);
            //******************************************************************************************
            OK.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (Code_save.equals(ECode.getText().toString())) {
                        SetCode("myclass", "EC_Email", "null");
                        SetCode("myclass", "save_Email", "1");
                        email.setText(sp.getString("Email", ""));
                        TOAST("ایمیل شما ثبت شد...!");
                        kelas.dismiss();
                    } else {
                        TOAST("کد وارد شده اشتباه است...!");
                    }
                }
            });
            //******************************************************************************************
            Cancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    kelas.dismiss();
                }
            });
            //******************************************************************************************
        } catch (Exception e) {

        }
    }
}
