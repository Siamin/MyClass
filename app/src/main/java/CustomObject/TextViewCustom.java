package CustomObject;


import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import aspi.myclass.Helpers.LanguageHelper;
import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.Tools.Tools;

public class TextViewCustom extends AppCompatTextView {


    public TextViewCustom(Context context, AttributeSet attis) {
        super(context, attis);
        Tools tools = new Tools();

        String lang = LanguageHelper.loadLanguage(context);
        this.setTypeface(tools.SetFont(context, (lang.equals("fa") ? "font1" : "en")));
    }

}







