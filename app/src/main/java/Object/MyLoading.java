package Object;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.race604.drawable.wave.WaveDrawable;

import aspi.myclass.R;

/**
 * Created by AmIn on 12/20/2018.
 */

public class MyLoading extends android.support.v7.widget.AppCompatImageView {

    WaveDrawable mWaveDrawable;

    public MyLoading(Context context) {
        super(context);

       // mWaveDrawable = new WaveDrawable(context,R.drawable.icon_app);
        //this.setImageDrawable(getResources().getDrawable(R.drawable.icon_app));
        /*int color = context.getResources().getColor(R.color.colorAccent);
        WaveDrawable colorWave = new WaveDrawable(new ColorDrawable(color));
        colorWave.setIndeterminate(true);
        mWaveDrawable.setIndeterminate(true);*/
    }

}
