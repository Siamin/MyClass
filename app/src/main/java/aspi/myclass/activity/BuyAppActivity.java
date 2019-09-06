package aspi.myclass.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import java.util.ArrayList;
import java.util.Calendar;

import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.R;
import aspi.myclass.Tools.Tools;
import util.IabHelper;
import util.IabResult;
import util.Inventory;
import util.Purchase;
import util.SkuDetails;


public class BuyAppActivity extends Activity {

    static final String TAG = "amin.syahi.69@chmail.ir";
    static final String SKU_PREMIUM = "buyApp";
    boolean mIsPremium = false;
    static final int RC_REQUEST = 0;
    IabHelper buyhelper;
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener;
    String AppKey = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwCym3I91/oYrhQAd5WdKBQrgG+N4oUOPP5QnDhoUsfwXbMZBbIiCABpKS1JYpG7fIbeXICuVnVedPwYdUeZbi954gxXZ25dDq0bR2TPIfYKSnycJKa+OMWjRIzUff2nPFDET6p/zebhyu36hOmvVr56OzA+H2WNpVl/a4+1RToYi85oBLGb9fe2KNlpQPWsZV22uJO7mFbOtZl1m1/glkAqW0zkBAin/Zy6Dy4HveECAwEAAQ==";
    private TextView textView;
    private Button buy, cancel;
    private SharedPreferences sp;
    private ProgressDialog progressDialog;
    Tools om = new Tools();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_buyapp);
        try {
            config();
            buyhelper = new IabHelper(this, AppKey);
            progressDialog = new ProgressDialog(BuyAppActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle("در حال دریافت اطلاعات از بازار");
            progressDialog.setCancelable(false);
            progressDialog.setMessage("لطفا صبر کنید ...!");
            progressDialog.show();
            new Thread(new Runnable() {
                public void run() {
                    get_price();
                }
            }).start();
        } catch (Exception e) {
        }

        buy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Buy_App();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Back();
            }
        });


    }

    void Buy_App() {
        try {
            buyhelper.launchPurchaseFlow(this, SKU_PREMIUM, 1001,
                    new IabHelper.OnIabPurchaseFinishedListener() {
                        @Override
                        public void onIabPurchaseFinished(IabResult result, Purchase info) {
                            if (result.isSuccess()) {
                                SharedPreferences sp = getApplicationContext().getSharedPreferences("myclass", 0);
                                String model = android.os.Build.MODEL + " " + android.os.Build.BRAND +" (" + android.os.Build.VERSION.RELEASE+")" + " API-" + android.os.Build.VERSION.SDK_INT;
                                BackgroundMail.newBuilder(BuyAppActivity.this)
                                        .withUsername("amin.syahi.1369@gmail.com")
                                        .withPassword("942134025")
                                        .withMailto("aspi.program@gmail.com")
                                        .withType(BackgroundMail.TYPE_PLAIN)
                                        .withSubject("خرید برنامه")
                                        .withBody("\n خریداری شده توسط = " + sp.getString("Email", "")+"\n  در تاریخ = "+date_iran()+"\n مدل دستگاه = "+model)
                                        .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                            public void onSuccess() {
                                                SetCode("myclass", "‌Buy_App", "Buy_App");
                                                MessageHelper.Toast(BuyAppActivity.this,"با تشکر از خرید شما...!");
                                                Back();
                                            }
                                        })
                                        .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                            public void onFail() {
                                                SetCode("myclass", "‌Buy_App", "Buy_App");
                                                MessageHelper.Toast(BuyAppActivity.this,"با تشکر از خرید شما...!");
                                                Back();
                                            }
                                        })
                                        .send();
                            } else {
                                MessageHelper.Toast(BuyAppActivity.this,"عملیات خرید ناموفق ...!");
                                Back();
                            }

                        }
                    });
        } catch (Exception e) {
            MessageHelper.Toast(BuyAppActivity.this,"لطفا به حساب کافه بازاری خود در نرم افزار بازار متصل شوید.");
        }
    }

    void get_price() {
        try {
            buyhelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    if (result.isSuccess()) {
                        ArrayList<String> moreSkus = new ArrayList<String>();
                        moreSkus.add(SKU_PREMIUM);
                        buyhelper.queryInventoryAsync(true, moreSkus, new IabHelper.QueryInventoryFinishedListener() {
                            public void onQueryInventoryFinished(final IabResult result, Inventory inv) {
                                if (result.isSuccess()) {
                                    SkuDetails details = inv.getSkuDetails(SKU_PREMIUM);
                                    textView.setText(details.getTitle() + "\n\n" + details.getDescription() + "\n" + "\n قیمت :" + details.getPrice());
                                    progressDialog.cancel();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            MessageHelper.Toast(BuyAppActivity.this,"مشکل ارتباط با کافه بازار...!");
                                        }
                                    });
                                    progressDialog.cancel();
                                }
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            MessageHelper.Toast(BuyAppActivity.this,"لطفا به حساب کافه بازاری خود در نرم افزار بازار متصل شوید.");
        }

    }

    void SetCode(String App, String name, String code) {
        sp = getApplicationContext().getSharedPreferences(App, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(name, code);
        edit.commit();
    }

    void config() {
        textView = (TextView) findViewById(R.id.buyapp_text);
        buy = (Button) findViewById(R.id.buyapp_buy);
        cancel = (Button) findViewById(R.id.buyapp_cancel);
        textView.setTypeface(MainActivity.FONTS);
        textView.setText("");
    }

    void Back() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    String date_iran() {
        Calendar c = Calendar.getInstance();
        int year = 0, month = 0, day = 0;
        int y = c.get(Calendar.YEAR);
        int x = c.get(Calendar.DAY_OF_YEAR);
        int MINUTE = c.get(Calendar.MINUTE);
        int HOUR = c.get(Calendar.HOUR_OF_DAY);
        //*******************************
        if (x >= 0 && x <= 20) {
            year = y - 622;
        } else if (x >= 21 && x <= 50) {
            year = y - 622;
        } else if (x >= 51 && x <= 79) {
            year = y - 622;
        } else if (x >= 80 && x <= 266) {
            year = y - 621;
        } else if (x >= 267 && x <= 365) {
            year = y - 621;
        }
        int mod = year % 33, kabise = 0;
        if (mod == 1 || mod == 5 || mod == 9 || mod == 13 || mod == 17 || mod == 22 || mod == 26 || mod == 30) {
            kabise = 1;
        } else {
            kabise = 0;
        }
        //*******************************
        if (x >= 0 && x <= 20) {
            month = 10;
            day = x + 10;
        } else if (x >= 21 && x <= 50) {
            month = 11;
            day = x - 20;
        } else if (x >= 51 && x <= 79 && kabise == 0) {
            month = 12;
            day = x - 40;
        } else if (x >= 51 && x <= 80 && kabise == 1) {
            month = 12;
            day = x - 49;
        } else if (x >= 80 && x <= 266 && kabise == 0) {
            x = x - 80;
            month = (x / 31) + 1;
            day = (x % 31) + 1;
        } else if (x >= 81 && x <= 266 && kabise == 1) {
            x = x - 79;
            month = (x / 31) + 1;
            day = (x % 31);
        } else if (x >= 267 && x <= 365) {
            x = x - 266;
            month = (x / 30) + 7;
            day = (x % 30) + 1;
        }
        String data = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day) + "\n  در ساعت = "
                + String.valueOf(HOUR) + ":" + String.valueOf(MINUTE) + ":" + String.valueOf(c.get(Calendar.SECOND));
        return data;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            buyhelper.handleActivityResult(requestCode, resultCode, data);
        }catch (Exception e){
        }
    }
}
