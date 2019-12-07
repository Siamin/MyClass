package aspi.myclass.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.annotation.Nullable;

import aspi.myclass.Helpers.EmailHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.Helpers.ValidationHelper;
import aspi.myclass.R;

public class CommentActivity extends Activity {

    EditText subject, body, name, email;
    ImageView ok, cancel;
    String model = "";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        //******************************************************************************************
        initView();

        email.setText(SharedPreferencesHelper.get_Data("Email", "",CommentActivity.this));
        model = android.os.Build.MODEL + " " + android.os.Build.BRAND + " (" + android.os.Build.VERSION.RELEASE + ")" + " API-" + android.os.Build.VERSION.SDK_INT;
        //******************************************************************************************
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendComment();
            }
        });
        //*************************************************************************************_cancel form
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Back();
            }
        });
    }

    void sendComment(){
        if (ValidationHelper.isValidationNull(name.getText().toString())) {
            if (ValidationHelper.isValidationNull(email.getText().toString())) {
                if (ValidationHelper.isValidationNull(subject.getText().toString())) {
                    if (ValidationHelper.isValidationNull(body.getText().toString())) {
                        if (ValidationHelper.isValidEmailId(email.getText().toString())) {
                            String Body = body.getText().toString() + "\n ارسال شده از  طرف \n" + name.getText().toString() + "\n ایمیل \n" + email.getText().toString() + "\n مدل دستگاه = " + model;
                            EmailHelper.SendEmail(CommentActivity.this, "amin.syahi.69@gmail.com", subject.getText().toString(), Body, " نظر ارسال شد ", 2,null);
                        } else {
                            MessageHelper.Toast(CommentActivity.this, getResources().getString(R.string.ErrorValidEmail));
                        }
                    } else {
                        MessageHelper.Toast(CommentActivity.this, getResources().getString(R.string.ErrorTextComment));
                    }
                } else {
                    MessageHelper.Toast(CommentActivity.this, getResources().getString(R.string.ErrorSubject));
                }
            } else {
                MessageHelper.Toast(CommentActivity.this, getResources().getString(R.string.ErrorEnterEmail));
            }
        } else {
            MessageHelper.Toast(CommentActivity.this, getResources().getString(R.string.ErrorEnterNameAndLastname));
        }
    }

    void Back() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    void initView() {
        subject = (EditText) findViewById(R.id.coment_subject);
        name = (EditText) findViewById(R.id.coment_name);
        email = (EditText) findViewById(R.id.coment_email);
        body = (EditText) findViewById(R.id.coment_body);
        ok = (ImageView) findViewById(R.id.coment_send);
        cancel = (ImageView) findViewById(R.id.coment_cancel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Back();
    }
}
