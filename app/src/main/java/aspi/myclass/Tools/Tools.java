package aspi.myclass.Tools;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;

import java.io.File;
import java.util.Random;

import aspi.myclass.Helpers.MessageHelper;
import aspi.myclass.SplashScreanActivity;

public class Tools {


    public static String Pin_Cod(int counter) {
        Random random = new Random();
        String Resualt = "";
        for (int i = 0; i < counter; i++) {
            Resualt += (random.nextInt(9) + 0);
        }
        return Resualt;
    }


    public Typeface SetFont(Context context, String name) {
        return Typeface.createFromAsset(context.getAssets(), "Font/" + name + ".ttf");
    }

    public String GetVer(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo("aspi.myclass", 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void openApplicationByUriAndPackage(Context context, String uri, String Package) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri));
            intent.setPackage(Package);
            context.startActivity(intent);
        } catch (Exception e) {
            MessageHelper.Toast(context, "لطفا برنامه بازار را نصب کنید...!");
        }
    }

    public static boolean checkFileInFolder(File folder, String name) {

        File file = new File(folder.toString() + name);

        return file.exists();
    }

    public static void restartApplication(Context context){
        Intent mStartActivity = new Intent(context, SplashScreanActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId, mStartActivity,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);    }
}
