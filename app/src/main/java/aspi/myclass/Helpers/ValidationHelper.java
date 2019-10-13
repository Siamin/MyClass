package aspi.myclass.Helpers;

import android.content.Context;

import java.util.List;
import java.util.regex.Pattern;

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

    public static boolean isValidationBuyApp(Context context, String name) {

        if (SharedPreferencesHelper.get_Data(name, "null", context).equals("Buy_App")) {
            return true;
        }
        return false;
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

}
