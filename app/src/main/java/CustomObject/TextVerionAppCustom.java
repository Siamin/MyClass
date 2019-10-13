package CustomObject;

import android.content.Context;
import android.util.AttributeSet;

import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.Tools.Tools;

public class TextVerionAppCustom extends android.support.v7.widget.AppCompatTextView {


    public TextVerionAppCustom(Context context, AttributeSet attis) {
        super(context, attis);
        Tools om = new Tools();
        this.setText("نسخه "+om.GetVer(context));
        this.setTypeface(om.SetFont(context, SharedPreferencesHelper.get_Data("Font_App","font1",context)));
    }

}