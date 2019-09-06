package aspi.myclass.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    static SharedPreferences sp;
    static String packages = "myclass";

    public static void SetCode(String name, String code, Context context) {
        sp = context.getSharedPreferences(packages, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(name, code);
        edit.commit();
    }

    public static String get_Data(String name, String Null, Context context) {
        sp = context.getSharedPreferences(packages, 0);
        return sp.getString(name, Null);
    }

}
