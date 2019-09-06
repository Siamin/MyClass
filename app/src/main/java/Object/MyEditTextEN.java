package Object;

import android.content.Context;
import android.util.AttributeSet;

import aspi.myclass.Tools.Tools;

/**
 * Created by AmIn on 12/21/2018.
 */

public class MyEditTextEN extends android.support.v7.widget.AppCompatEditText {


     public MyEditTextEN(final Context context, AttributeSet attis) {
        super(context, attis);
        final Tools om = new Tools();

        this.setTypeface(om.SetFont(context, "en"));

    }

}