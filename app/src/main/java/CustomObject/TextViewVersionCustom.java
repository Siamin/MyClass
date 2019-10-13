package CustomObject;

import android.content.Context;
import android.util.AttributeSet;

import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.Tools.Tools;

public class TextViewVersionCustom extends android.support.v7.widget.AppCompatTextView {


    public TextViewVersionCustom(final Context context, AttributeSet attis) {
        super(context, attis);
        final Tools om = new Tools();

        this.setTypeface(om.SetFont(context, SharedPreferencesHelper.get_Data("Font_App","font1",context)));
        this.setText("نگارش "+om.GetVer(context));

    }

}