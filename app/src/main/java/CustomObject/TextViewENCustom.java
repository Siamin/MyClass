package CustomObject;

import android.content.Context;
import android.util.AttributeSet;

import aspi.myclass.Tools.Tools;

/**
 * Created by AmIn on 12/21/2018.
 */

public class TextViewENCustom extends android.support.v7.widget.AppCompatTextView {


    public TextViewENCustom(Context context, AttributeSet attis) {
        super(context, attis);
        Tools om = new Tools();

        this.setTypeface(om.SetFont(context, "en"));
    }

}