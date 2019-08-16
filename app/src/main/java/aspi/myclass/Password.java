package aspi.myclass;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

public class Password extends AppCompatActivity {

    private Button zero, one, tow, three, four, five, six, seven, eight, nine, claer, login;
    private String get_password = "", set_password = "";
    private TextView show,forget;
    private SharedPreferences sp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
        config();
        getpassword();
        //******************************************
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sp = getApplicationContext().getSharedPreferences("myclass", 0);
                    if (!sp.getString("Email", "").equals(""))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Password.this, R.style.MyAlertDialogStyle);
                        builder.setTitle("ارسال رمز عبور").setMessage("آیا دستگاه شما به اینترنت متصل است؟");
                        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                sp = getApplicationContext().getSharedPreferences("myclass", 0);
                                String password_chek = sp.getString("Password_App", "null");
                                BackgroundMail.newBuilder(Password.this)
                                        .withUsername("amin.syahi.1369@gmail.com")
                                        .withPassword("919121318")
                                        .withMailto(sp.getString("Email", ""))
                                        .withType(BackgroundMail.TYPE_PLAIN)
                                        .withSubject("رمز عبور نرم افزار دفتر نمره حضور و غیاب "+sp.getString("Email", ""))
                                        .withBody(" با سلام رمز عبور شما \n" + password_chek)
                                        .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                            @Override
                                            public void onSuccess() {
                                                Toast toast = Toast.makeText(Password.this, "ایمیل ارسال شد...!", Toast.LENGTH_LONG);
                                                TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                                                textView.setTextColor(getResources().getColor(R.color.toast));
                                                textView.setTypeface(Main.FONTS);
                                                textView.setTextSize(18);
                                                textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                                View view = toast.getView();
                                                view.setBackgroundResource(R.drawable.toast);
                                                toast.show();
                                            }
                                        })
                                        .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                            @Override
                                            public void onFail() {
                                                Toast toast = Toast.makeText(Password.this, "خطا در ارسال ایمیل...!", Toast.LENGTH_LONG);
                                                TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                                                textView.setTextColor(getResources().getColor(R.color.toast));
                                                textView.setTypeface(Main.FONTS);
                                                textView.setTextSize(18);
                                                textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                                View view = toast.getView();
                                                view.setBackgroundResource(R.drawable.toast);
                                                toast.show();
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
                }catch (Exception e){

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
                if (get_password.equals(set_password)) {
                    Go_to_main();
                } else {
                    TOAST("رمزاشتباه است");
                }
            }
        });
        //******************************************

    }

    private void Go_to_main() {
        Intent Main = new Intent(Password.this, Main.class);
        startActivity(Main);
        finish();
    }

    private void GetPasswordUser(String number) {
        if (show.getText().toString().equals("رمز پیش فرض '0000'")) {
            show.setText("*");
        } else {
            show.setText(show.getText().toString() + "*");
        }
        get_password += number;


    }

    private void getpassword() {
        sp = getApplicationContext().getSharedPreferences("myclass", 0);
        String password_chek = sp.getString("Password_App", "null");
        if (password_chek.equals("null")) {
            Go_to_main();
        } else {
            show.setText("");
            set_password = password_chek;
        }
    }

    private void config() {
        //******************************************
        show = (TextView) findViewById(R.id.key_text);
        forget= (TextView) findViewById(R.id.forget);
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

}
