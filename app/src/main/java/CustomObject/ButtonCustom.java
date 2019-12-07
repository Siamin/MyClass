package CustomObject;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

import aspi.myclass.Helpers.LanguageHelper;
import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.Tools.Tools;

/**
 * Created by AmIn on 12/21/2018.
 */

public class ButtonCustom extends AppCompatButton {


    public ButtonCustom(Context context, AttributeSet attis) {
        super(context, attis);
        Tools tools = new Tools();

        String lang = LanguageHelper.loadLanguage(context);
        this.setTypeface(tools.SetFont(context, (lang.equals("fa") ? "font1" : "en")));
    }
}
