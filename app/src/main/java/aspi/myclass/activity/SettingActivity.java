package aspi.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import aspi.myclass.Helpers.DialogHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.Helpers.ValidationHelper;
import aspi.myclass.R;
import aspi.myclass.Services.FireBaseAnalyticsService;

public class SettingActivity extends Activity {


    ImageView save, cancel;
    Button Bmail;
    EditText old_password, new_password1, new_password2;
    static EditText email;
    CheckBox checkBox_password, on;
    TextView text_old_password, text_new_password1, text_new_password2;
    String password_chek, Number;
    int Size_Text;
    String TAG = "TAG_SettingActivity";
    private FirebaseAnalytics mFirebaseAnalytics;
    private FireBaseAnalyticsService fireBaseAnalyticsService = new FireBaseAnalyticsService();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_setting);

        initView();

        getpassword();


        on.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fireBaseAnalyticsService.CustomEventFireBaseAnalytics(mFirebaseAnalytics, String.valueOf(on.getId()), on.getText().toString(), "CheckBox");

                if (on.isChecked()) {
                    Number = "on";

                } else {
                    Number = "off";
                }
                SharedPreferencesHelper.SetCode("status_number", Number, SettingActivity.this);
            }
        });


        checkBox_password.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (checkBox_password.isChecked()) {
                    setEnabled(false);
                } else {
                    if (ValidationHelper.validSetEmail(SettingActivity.this)) {
                        setEnabled(true);
                    } else {
                        MessageHelper.Toast(SettingActivity.this, getResources().getString(R.string.errorSubmitEmail));
                        setEnabled(false);
                        checkBox_password.setChecked(true);
                    }
                }
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (save()) {
                    onBackPressed();
                    MessageHelper.Toast(SettingActivity.this, getResources().getString(R.string.Saved));
                }

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });




        Bmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!email.getText().toString().isEmpty()) {

                    if (ValidationHelper.isValidEmailId(email.getText().toString())) {

                        try {
                            DialogHelper.sendCodeEmail(SettingActivity.this, email.getText().toString());
                        } catch (Exception e) {
                            Log.i(TAG, "Error " + e.toString());
                        }

                    } else {

                        MessageHelper.Toast(SettingActivity.this, getResources().getString(R.string.ErrorValidEmail));

                    }

                } else {

                    MessageHelper.Toast(SettingActivity.this, getResources().getString(R.string.ErrorEnterEmail));

                }


            }
        });

    }

    void initView() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        email = findViewById(R.id.setting_Email);
        old_password = findViewById(R.id.setting_password_old);
        new_password1 = findViewById(R.id.setting_password1_new);
        new_password2 = findViewById(R.id.setting_password2_new);
        checkBox_password = findViewById(R.id.setting_check);
        text_old_password = findViewById(R.id.setting_text_old_password);
        text_new_password1 = findViewById(R.id.setting_text_new_password1);
        text_new_password2 = findViewById(R.id.setting_text_new_password2);

        save = findViewById(R.id.setting_save);
        Bmail = findViewById(R.id.setting_bmail);
        cancel = findViewById(R.id.setting_cancel);
        on = findViewById(R.id.setting_numberon);


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


        if (!Code_save.equals("null")) {
            DialogHelper.VirifyCodeEmail(SettingActivity.this);
        }

    }

    boolean save() {

        if (!checkBox_password.isChecked()) {

            if (password_chek.equals(old_password.getText().toString())) {

                if (new_password1.getText().toString().equals(new_password2.getText().toString())) {

                    SharedPreferencesHelper.SetCode("Password_App", new_password1.getText().toString(), SettingActivity.this);
                    return true;

                } else if (new_password1.getText().toString().isEmpty() || new_password2.getText().toString().isEmpty() || !new_password1.getText().toString().equals(new_password2.getText().toString())) {

                    new_password1.setText("");
                    new_password2.setText("");
                    MessageHelper.Toast(SettingActivity.this, getResources().getString(R.string.errorConfirmPassword));
                    return false;

                } else {
                    MessageHelper.Toast(SettingActivity.this, getResources().getString(R.string.Error));
                    return false;
                }

            } else if (old_password.getText().toString().isEmpty() || !password_chek.equals(old_password.getText().toString())) {
                old_password.setText("");
                MessageHelper.Toast(SettingActivity.this, getResources().getString(R.string.errorValidOldPassword));
                return false;

            } else {
                MessageHelper.Toast(SettingActivity.this, getResources().getString(R.string.Error));
                return false;
            }

        } else {

            SharedPreferencesHelper.SetCode("Password_App", "null", SettingActivity.this);
            return false;

        }


    }

    public static void SetEmail(String Email) {
        email.setText(Email);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent setting = new Intent(this, MainActivity.class);
        startActivity(setting);
        finish();
    }
}
