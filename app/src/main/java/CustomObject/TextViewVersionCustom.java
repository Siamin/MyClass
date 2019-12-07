package CustomObject;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import aspi.myclass.Helpers.LanguageHelper;
import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.R;
import aspi.myclass.Tools.Tools;

public class TextViewVersionCustom extends AppCompatTextView {


    public TextViewVersionCustom(final Context context, AttributeSet attis) {
        super(context, attis);
        final Tools tools = new Tools();

        String lang = LanguageHelper.loadLanguage(context);
        this.setTypeface(tools.SetFont(context, (lang.equals("fa") ? "font1" : "en")));

        this.setText(context.getResources().getString(R.string.Version) + " " + tools.GetVer(context));

    }

}