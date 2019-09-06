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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import java.util.Calendar;

import aspi.myclass.Helpers.DialogHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.Helpers.ValidationHelper;
import aspi.myclass.R;
import aspi.myclass.Tools.Tools;

public class SettingActivity extends Activity {


    ImageView save, cancel;
    Button Bmail;
    EditText old_password, new_password1, new_password2;
    static EditText email;
    CheckBox checkBox_password, font1, font2, font3, on;
    TextView text_old_password, text_new_password1, text_new_password2, test_text;
    String password_chek, Font, Number;
    int Size_Text, refresh = 0;
    Typeface font_1, font_2, font_3;
    static Timer time;
    Tools om = new Tools();
    String TAG = "TAG_SettingActivity";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_setting);
        initView();

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
                    if (SharedPreferencesHelper.get_Data("EC_Email", "null", SettingActivity.this).equals("null")
                            && SharedPreferencesHelper.get_Data("save_Email", "0", SettingActivity.this).equals("1")
                            && ValidationHelper.isValidEmailId(SharedPreferencesHelper.get_Data("Email", "", SettingActivity.this))) {
                        setEnabled(true);
                    } else {
                        MessageHelper.Toast(SettingActivity.this, "  برای استفاده از این قسمت باید ایمیل خود را در سیستم ثبت کنید. ");
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
                    MessageHelper.Toast(SettingActivity.this, "ذخیره شد...!");
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
                if (!email.getText().toString().isEmpty()) {
                    if (ValidationHelper.isValidEmailId(email.getText().toString())) {
                        try {
                            DialogHelper.sendEmail(SettingActivity.this, email.getText().toString());
                        } catch (Exception e) {
                            Log.i(TAG, "Error " + e.toString());
                        }
                    } else {
                        MessageHelper.Toast(SettingActivity.this, "ایمیل را به درستی وارد کنید.");
                    }
                } else {
                    MessageHelper.Toast(SettingActivity.this, "ایمیل را وارد کنید.");
                }


            }
        });

    }

    void initView() {
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
        save = (ImageView) findViewById(R.id.setting_save);
        Bmail = (Button) findViewById(R.id.setting_bmail);
        cancel = (ImageView) findViewById(R.id.setting_cancel);
        on = (CheckBox) findViewById(R.id.setting_numberon);
        test_text.setText("متن تست 1" + "text test 1");

    }

    void setEnabled(boolean status) {
        text_old_password.setEnabled(status);
        text_new_password1.setEnabled(status);
        text_new_password2.setEnabled(status);
        old_password.setEnabled(status);
        new_password1.setEnabled(status);
        new_password2.setEnabled(status);
    }

    void getpassword() {

        password_chek = SharedPreferencesHelper.get_Data("Password_App", "null", SettingActivity.this);

        Size_Text = Integer.parseInt(SharedPreferencesHelper.get_Data("Size_Text_App", "14", SettingActivity.this));
        Font = SharedPreferencesHelper.get_Data("Font_App", "font1", SettingActivity.this);
        String Code_save = SharedPreferencesHelper.get_Data("EC_Email", "null", SettingActivity.this);
        String status_email = SharedPreferencesHelper.get_Data("save_Email", "0", SettingActivity.this);


        if (status_email.equals("1")) {
            email.setText(SharedPreferencesHelper.get_Data("Email", "", SettingActivity.this));
        }

        if (SharedPreferencesHelper.get_Data("status_number", "status_number", SettingActivity.this).equals("off")) {
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
            DialogHelper.VirifyCodeEmail(SettingActivity.this);
        }

    }

    void SetCode(String name, String code) {
        SharedPreferencesHelper.SetCode(name, code, SettingActivity.this);
    }

    void Go_main() {
        Intent setting = new Intent(this, MainActivity.class);
        startActivity(setting);
        finish();
    }

    boolean save() {
        boolean res = true;
        if (!checkBox_password.isChecked()) {
            if (password_chek.equals(old_password.getText().toString())) {
                if (new_password1.getText().toString().equals(new_password2.getText().toString())) {
                    SetCode("Password_App", new_password1.getText().toString());
                } else if (!new_password1.getText().toString().equals("") && !new_password2.getText().toString().equals("")) {
                    new_password1.setText("");
                    new_password2.setText("");
                    MessageHelper.Toast(SettingActivity.this, "رمز جدید را مجددا وارد کنید...!");
                    res = false;
                }
            } else if (!old_password.getText().toString().equals("")) {
                res = false;
                old_password.setText("");
                MessageHelper.Toast(SettingActivity.this, "رمز قدیمی را مجددا وارد کنید...!");
            }
        } else {
            SetCode("Password_App", "null");
        }
        String font_set = "font1";
        if (font2.isChecked()) {
            font_set = "font2";
        } else if (font3.isChecked()) {
            font_set = "font3";
        }
        SetCode("Size_Text_App", String.valueOf(Size_Text));
        SetCode("Font_App", font_set);
        SetCode("status_number", Number);
        return res;
    }

    public static void SetEmail(String Email) {
        email.setText(Email);
    }


}
