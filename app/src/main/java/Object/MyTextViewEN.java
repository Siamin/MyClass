package Object;

import android.content.Context;
import android.util.AttributeSet;

import aspi.myclass.class_.OtherMetod;

/**
 * Created by AmIn on 12/21/2018.
 */

public class MyTextViewEN extends android.support.v7.widget.AppCompatTextView {


    public MyTextViewEN(Context context, AttributeSet attis) {
        super(context, attis);
        OtherMetod om = new OtherMetod();

        this.setTypeface(om.SetFont(context, "en"));
    }

}