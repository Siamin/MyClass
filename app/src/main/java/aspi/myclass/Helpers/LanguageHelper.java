package aspi.myclass.Helpers;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

public class LanguageHelper {



    public static void setLocal(Context context,String Lang){

        Locale locale = new Locale(Lang);
        locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration,context.getResources().getDisplayMetrics());


        SharedPreferencesHelper.SetCode("myLanguage",Lang,context);

    }

    public static String loadLanguage(Context context){

        String language = SharedPreferencesHelper.get_Data("myLanguage","fa",context);

        setLocal(context , language);

        return language;
    }



}
