package Object;

import android.content.Context;
import android.util.AttributeSet;
import aspi.myclass.OtherMetod;

public class MyTextVerionApp extends android.support.v7.widget.AppCompatTextView {


    public MyTextVerionApp(Context context, AttributeSet attis) {
        super(context, attis);
        OtherMetod om = new OtherMetod();
        this.setText("نسخه "+om.GetVer(context));
        this.setTypeface(om.SetFont(context, om.get_Data("Font_App","font1",context)));
    }

}