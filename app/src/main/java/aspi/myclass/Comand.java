package aspi.myclass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

public class Comand extends Activity {

    EditText subject,body,name,email;
    Button ok,cancel;
    String model="";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coment);
        //******************************************************************************************
        config();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
        email.setText(sp.getString("Email", ""));
        model = android.os.Build.MODEL + " " + android.os.Build.BRAND +" (" + android.os.Build.VERSION.RELEASE+")" + " API-" + android.os.Build.VERSION.SDK_INT;
        //******************************************************************************************
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                BackgroundMail.newBuilder(Comand.this)
                        .withUsername("amin.syahi.1369@gmail.com")
                        .withPassword("919121318")
                        .withMailto("aspi.program@gmail.com")
                        .withType(BackgroundMail.TYPE_PLAIN)
                        .withSubject(subject.getText().toString())
                        .withBody(body.getText().toString()+"\n ارسال شده از  طرف \n"+name.getText().toString()+"\n ایمیل \n"+email.getText().toString()+"\n مدل دستگاه = "+model)
                        .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                            public void onSuccess() {
                                Toast toast = Toast.makeText(Comand.this, " نظر ارسال شد ", Toast.LENGTH_LONG);
                                TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                                textView.setTextColor(getResources().getColor(R.color.toast));
                                textView.setTypeface(Main.FONTS);
                                textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                textView.setTextSize(18);
                                View view = toast.getView();
                                view.setBackgroundResource(R.drawable.toast);
                                toast.show();
                                Back();
                            }
                        })
                        .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                            public void onFail() {
                                Toast toast = Toast.makeText(Comand.this, " خطا در ارسال نظر ", Toast.LENGTH_LONG);
                                TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                                textView.setTextColor(getResources().getColor(R.color.toast));
                                textView.setTypeface(Main.FONTS);
                                textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                textView.setTextSize(18);
                                View view = toast.getView();
                                view.setBackgroundResource(R.drawable.toast);
                                toast.show();
                            }
                        })
                        .send();
            }
        });
        //*************************************************************************************_cancel form
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Back();
            }
        });
    }

    void Back(){
        startActivity(new Intent(this, Main.class));
        finish();
    }
    void config(){
        subject = (EditText) findViewById(R.id.coment_subject);
        name = (EditText) findViewById(R.id.coment_name);
        email = (EditText) findViewById(R.id.coment_email);
        body = (EditText) findViewById(R.id.coment_body);
        ok = (Button) findViewById(R.id.coment_send);
        cancel = (Button) findViewById(R.id.coment_cancel);
    }

}
