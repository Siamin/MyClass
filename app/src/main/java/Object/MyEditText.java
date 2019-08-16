package Object;

import android.content.Context;
import android.util.AttributeSet;

import aspi.myclass.class_.OtherMetod;

/**
 * Created by AmIn on 12/21/2018.
 */

public class MyEditText extends android.support.v7.widget.AppCompatEditText {


     public MyEditText(final Context context, AttributeSet attis) {
            super(context, attis);
            final OtherMetod om = new OtherMetod();

            this.setTypeface(om.SetFont(context, om.get_Data("Font_App","font1",context)));

     }

    }