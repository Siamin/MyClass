package aspi.myclass.activity;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import aspi.myclass.Helpers.DialogHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.R;
import aspi.myclass.Services.FireBaseAnalyticsService;
import aspi.myclass.Tools.Tools;

public class LoginActivity extends AppCompatActivity {

    private Button zero, one, tow, three, four, five, six, seven, eight, nine, claer, login;
    private String get_password = "", set_password = "";
    private TextView show, forget;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FireBaseAnalyticsService fireBaseAnalyticsService = new FireBaseAnalyticsService();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
        initView();
        getpassword();
        //******************************************
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!SharedPreferencesHelper.get_Data("Email", "", LoginActivity.this).equals("")) {
                    DialogHelper.ForgotPassword(LoginActivity.this);
                }

            }
        });
        //******************************************
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPasswordUser("0");
            }
        });
        //******************************************
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPasswordUser("1");
            }
        });
        //******************************************
        tow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPasswordUser("2");

            }
        });
        //******************************************
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPasswordUser("3");

            }
        });
        //******************************************
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPasswordUser("4");

            }
        });
        //******************************************
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPasswordUser("5");

            }
        });
        //******************************************
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPasswordUser("6");
            }
        });
        //******************************************
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPasswordUser("7");
            }
        });
        //******************************************
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPasswordUser("8");
            }
        });
        //******************************************
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPasswordUser("9");
            }
        });
        //******************************************
        claer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_password = "";
                show.setText("");
            }
        });
        //******************************************
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireBaseAnalyticsService.CustomEventFireBaseAnalytics(mFirebaseAnalytics, String.valueOf(login.getId()), "login", "Button");
                if (get_password.equals(set_password)) {
                    Go_to_main();
                } else {
                    MessageHelper.Toast(LoginActivity.this, "رمزاشتباه است");
                }
            }
        });
        //******************************************

    }

    void Go_to_main() {
        Intent Main = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(Main);
        finish();
    }

    void GetPasswordUser(String number) {
        if (show.getText().toString().equals("رمز پیش فرض '0000'")) {
            show.setText("*");
        } else {
            show.setText(show.getText().toString() + "*");
        }
        get_password += number;


    }

    void getpassword() {
        String password_chek = SharedPreferencesHelper.get_Data("Password_App", "null", LoginActivity.this);
        if (password_chek.equals("null")) {
            Go_to_main();
        } else {
            show.setText("");
            set_password = password_chek;
        }
    }

    void initView() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        //******************************************
        show = (TextView) findViewById(R.id.key_text);
        forget = (TextView) findViewById(R.id.forget);
        //******************************************
        zero = (Button) findViewById(R.id.key_0);
        one = (Button) findViewById(R.id.key_1);
        tow = (Button) findViewById(R.id.key_2);
        three = (Button) findViewById(R.id.key_3);
        four = (Button) findViewById(R.id.key_4);
        five = (Button) findViewById(R.id.key_5);
        six = (Button) findViewById(R.id.key_6);
        seven = (Button) findViewById(R.id.key_7);
        eight = (Button) findViewById(R.id.key_8);
        nine = (Button) findViewById(R.id.key_9);
        //******************************************
        claer = (Button) findViewById(R.id.key_c);
        login = (Button) findViewById(R.id.key_enter);
        //******************************************
    }

}
