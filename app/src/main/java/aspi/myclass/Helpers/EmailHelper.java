package aspi.myclass.Helpers;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import aspi.myclass.NetWork;
import aspi.myclass.R;
import aspi.myclass.activity.MainActivity;
import okhttp3.FormBody;


public class EmailHelper {

    static String TAG = "TAG_EmailHelper";
    static NetWork netWork = new NetWork();

    public static void SendEmail(final Context context, final String MailTo, final String Subject, final String Body, final String Message_sucsess, final int Action, final String... param) {
        IndicatorHelper.IndicatorCreate(context, "", "لطفا صبر کنید");
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... p) {
                FormBody.Builder formBuilder = new FormBody.Builder();
                formBuilder.add("Email", MailTo);
                formBuilder.add("subject", Subject);
                formBuilder.add("body", Body);
                return netWork.Post(formBuilder, "", p[0]);
            }

            @Override
            protected void onPostExecute(String result) {
                Log.i(TAG, result);
                IndicatorHelper.IndicatorDismiss();
                if (result.equals("1")) {
                    MessageHelper.Toast(context, Message_sucsess);
                    if (Action > 0) SucsessMail(context, Action, param);
                } else {
                    MessageHelper.Toast(context, "خطا در ارسال ایمیل...!");

                }

            }

        }.execute(context.getResources().getString(R.string.ApiSendMail));

    }

    static void SucsessMail(final Context context, int Action, String... param) {

        if (Action == 1) {
            SharedPreferencesHelper.SetCode("EC_Email", param[0], context);
            SharedPreferencesHelper.SetCode("Email", param[1], context);
            SharedPreferencesHelper.SetCode("save_Email", "0", context);
            DialogHelper.VirifyCodeEmail(context);
        } else if (Action == 2) {
            ((Activity) context).startActivity(new Intent(context, MainActivity.class));
            ((Activity) context).finish();
        }


    }


}
