package Object;

import android.content.Context;
import android.util.AttributeSet;

import aspi.myclass.OtherMetod;

public class MyRadioButton extends android.support.v7.widget.AppCompatRadioButton {


    public MyRadioButton(Context context, AttributeSet attis) {
        super(context, attis);
        OtherMetod om = new OtherMetod();

        this.setTypeface(om.SetFont(context, "fa"));
    }
}
