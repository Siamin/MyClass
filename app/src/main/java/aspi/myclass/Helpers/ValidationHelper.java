package aspi.myclass.Helpers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;

import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.regex.Pattern;

import aspi.myclass.activity.MainActivity;
import aspi.myclass.activity.SettingActivity;
import aspi.myclass.model.ReportDataModel;

public class ValidationHelper {

    static String TAG = "TAG_ValidationHelper";

    public static boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static boolean isValidationNull(String text) {
        if (text.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validationSnoInModel(List<ReportDataModel> model, String Sno) {

        if (model.size() > 0) {

            for (int i = 0; i < model.size(); i++) {
                if (model.get(i).sno.equals(Sno)) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }

    }

    public static int validationDateInModel(List<ReportDataModel> model, String DateNew) {

        int i = 0;

        for (; i < model.size(); i++) {
            if (model.get(i).date.equals(DateNew)) break;
        }
        return i;
    }

    public static boolean checkPerimission(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }

    }

    public static boolean isValidationInternet(Context context) {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec
                = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }

    public static boolean validSetEmail(Context context) {
        if (SharedPreferencesHelper.get_Data("EC_Email", "null", context).equals("null")
                && SharedPreferencesHelper.get_Data("save_Email", "0", context).equals("1")
                && ValidationHelper.isValidEmailId(SharedPreferencesHelper.get_Data("Email", "", context))) {

            return true;
        } else {
            return false;
        }
    }

    public static boolean validBuyApp(Context context) {

        return SharedPreferencesHelper.get_Data("‌Buy_App", "NO", context).equals("Buy_App") ? true : false;

    }

}
