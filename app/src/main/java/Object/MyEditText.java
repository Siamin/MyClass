package Object;

import android.content.Context;
import android.util.AttributeSet;

import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.Tools.Tools;

/**
 * Created by AmIn on 12/21/2018.
 */

public class MyEditText extends android.support.v7.widget.AppCompatEditText {


     public MyEditText(final Context context, AttributeSet attis) {
            super(context, attis);
            final Tools om = new Tools();

            this.setTypeface(om.SetFont(context, SharedPreferencesHelper.get_Data("Font_App","font1",context)));

     }

    }