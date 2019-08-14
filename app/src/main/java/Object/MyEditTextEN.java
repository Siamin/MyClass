package Object;

import android.content.Context;
import android.util.AttributeSet;

import aspi.myclass.OtherMetod;

/**
 * Created by AmIn on 12/21/2018.
 */

public class MyEditTextEN extends android.support.v7.widget.AppCompatEditText {


     public MyEditTextEN(final Context context, AttributeSet attis) {
        super(context, attis);
        final OtherMetod om = new OtherMetod();

        this.setTypeface(om.SetFont(context, "en"));

    }

}