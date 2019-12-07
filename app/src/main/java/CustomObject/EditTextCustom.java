package CustomObject;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import aspi.myclass.Helpers.LanguageHelper;
import aspi.myclass.Tools.Tools;

/**
 * Created by AmIn on 12/21/2018.
 */

public class EditTextCustom extends AppCompatEditText {


    public EditTextCustom(final Context context, AttributeSet attis) {
        super(context, attis);
        Tools tools = new Tools();

        String lang = LanguageHelper.loadLanguage(context);
        this.setTypeface(tools.SetFont(context, (lang.equals("fa") ? "font1" : "en")));
    }

}