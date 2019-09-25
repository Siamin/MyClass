package aspi.myclass.Helpers;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import aspi.myclass.activity.MainActivity;


public class EmailHelper {


    public static void SendEmail(final Context context, String MailTo, String Subject, String Body, final String Message_sucsess, final int Action, final String... param) {

        BackgroundMail.newBuilder(context)
                .withUsername("amin.syahi.1369@gmail.com")
                .withPassword("942134025")
                .withMailto(MailTo)
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject(Subject)
                .withBody(Body)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        MessageHelper.Toast(context, Message_sucsess);
                        if (Action > 0) SucsessMail(context, Action, param);
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        MessageHelper.Toast(context, "خطا در ارسال ایمیل...!");
                    }
                })
                .send();

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
