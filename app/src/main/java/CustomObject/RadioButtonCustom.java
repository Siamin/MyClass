package CustomObject;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRadioButton;

import aspi.myclass.Helpers.LanguageHelper;
import aspi.myclass.Tools.Tools;

public class RadioButtonCustom extends AppCompatRadioButton {


    public RadioButtonCustom(Context context, AttributeSet attis) {
        super(context, attis);
        Tools tools = new Tools();

        String lang = LanguageHelper.loadLanguage(context);
        this.setTypeface(tools.SetFont(context, (lang.equals("fa") ? "font1" : "en")));
    }
}
