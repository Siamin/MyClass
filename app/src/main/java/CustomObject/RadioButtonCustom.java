package CustomObject;

import android.content.Context;
import android.util.AttributeSet;

import aspi.myclass.Tools.Tools;

public class RadioButtonCustom extends android.support.v7.widget.AppCompatRadioButton {


    public RadioButtonCustom(Context context, AttributeSet attis) {
        super(context, attis);
        Tools om = new Tools();

        this.setTypeface(om.SetFont(context, "fa"));
    }
}
