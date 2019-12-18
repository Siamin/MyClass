package aspi.myclass.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import aspi.myclass.Helpers.DateHelper;
import aspi.myclass.Helpers.EmailHelper;
import aspi.myclass.Helpers.IndicatorHelper;
import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.R;

import aspi.myclass.adapter.BazarAdapter;
import aspi.myclass.model.BazarModel;
import util.IabHelper;
import util.IabResult;
import util.Inventory;
import util.Purchase;
import util.SkuDetails;

public class BazarActivity extends Activity {

    static String TAG = "TAG_BuyAppActivity";
    String SKU_PREMIUM[] = {"buyApp"};
    static IabHelper buyhelper;
    ImageView cancel;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_bazar);
        try {
            initView();
            buyhelper = new IabHelper(this, getResources().getString(R.string.KeyBazar));
            IndicatorHelper.IndicatorCreate(BazarActivity.this, getResources().getString(R.string.gettingData), getResources().getString(R.string.pleaseWait));
            new Thread(new Runnable() {
                public void run() {
                    get_price();
                }
            }).start();
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }


        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Back();
            }
        });


    }

    public static void Buy_App(String SKU_PREMIUM, final Context context) {
        try {
            buyhelper.launchPurchaseFlow(((Activity) context), SKU_PREMIUM, 1001,
                    new IabHelper.OnIabPurchaseFinishedListener() {
                        @Override
                        public void onIabPurchaseFinished(IabResult result, Purchase info) {
                            if (result.isSuccess()) {
                                Log.i(TAG, "getMessage : " + result.getMessage());

                                String model = android.os.Build.MODEL + " " + android.os.Build.BRAND + " (" + android.os.Build.VERSION.RELEASE + ")" + " API-" + android.os.Build.VERSION.SDK_INT;
                                SharedPreferencesHelper.SetCode("‌Buy_App", "Buy_App", context);
                                String Body = "\n خریداری شده توسط = " + SharedPreferencesHelper.get_Data("Email", "", context) + "\n  در تاریخ = " + DateHelper.date_iran() + "\n مدل دستگاه = " + model;

                                EmailHelper.SendEmail(context, "amin.syahi.69@gmail.com", "خرید برنامه", Body, context.getResources().getString(R.string.thanksForYourPurchase), 0, null);

                                ((Activity) context).startActivity(new Intent(context, MainActivity.class));
                                ((Activity) context).finish();
                            } else {
                                MessageHelper.Toast(context, context.getResources().getString(R.string.failedPurchaseOperation));
                                ((Activity) context).startActivity(new Intent(context, MainActivity.class));
                                ((Activity) context).finish();
                            }

                        }
                    });
        } catch (Exception e) {
            MessageHelper.Toast(context, context.getResources().getString(R.string.errorConnectAccountBazar));
        }
    }

    void get_price() {
        try {
            buyhelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    if (result.isSuccess()) {
                        final ArrayList<String> moreSkus = new ArrayList<String>();
                        for (int i = 0; i < SKU_PREMIUM.length; i++)
                            moreSkus.add(SKU_PREMIUM[i]);

                        buyhelper.queryInventoryAsync(true, moreSkus, new IabHelper.QueryInventoryFinishedListener() {
                            public void onQueryInventoryFinished(final IabResult result, Inventory inv) {
                                if (result.isSuccess()) {

                                    List<BazarModel> models = new ArrayList<BazarModel>();

                                    for (int i = 0; i < moreSkus.size(); i++) {

                                        BazarModel model = new BazarModel();
                                        SkuDetails details = inv.getSkuDetails(SKU_PREMIUM[i]);

                                        model.title = details.getTitle();
                                        model.description = details.getDescription();
                                        model.price = details.getPrice();
                                        model.Sku = details.getSku();
                                        model.Type = details.getType();

                                        models.add(model);

                                    }

                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setAdapter(new BazarAdapter(models, BazarActivity.this));

                                    IndicatorHelper.IndicatorDismiss();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            MessageHelper.Toast(BazarActivity.this, getResources().getString(R.string.problemConnectingToBazar));
                                        }
                                    });
                                    IndicatorHelper.IndicatorDismiss();
                                }
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            MessageHelper.Toast(BazarActivity.this, getResources().getString(R.string.errorConnectAccountBazar));
        }

    }

    void initView() {
        cancel = findViewById(R.id.buyapp_cancel);
        recyclerView = findViewById(R.id.buyapp_recyc);
        linearLayoutManager = new LinearLayoutManager(this);

    }

    void Back() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            buyhelper.handleActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
        }
    }

}
