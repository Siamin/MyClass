package Object;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import aspi.myclass.OtherMetod;

/**
 * Created by AmIn on 12/21/2018.
 */

public class MyButton extends android.support.v7.widget.AppCompatButton {


    public MyButton(Context context, AttributeSet attis) {
        super(context, attis);
        OtherMetod om = new OtherMetod();

        this.setTypeface(om.SetFont(context, om.get_Data("Font_App","font1",context)));
    }
}
