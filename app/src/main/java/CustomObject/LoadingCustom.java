package CustomObject;

import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;

import com.race604.drawable.wave.WaveDrawable;

/**
 * Created by AmIn on 12/20/2018.
 */

public class LoadingCustom extends AppCompatImageView {

    WaveDrawable mWaveDrawable;

    public LoadingCustom(Context context) {
        super(context);

       // mWaveDrawable = new WaveDrawable(context,R.drawable.icon_app);
        //this.setImageDrawable(getResources().getDrawable(R.drawable.icon_app));
        /*int color = context.getResources().getColor(R.color.colorAccent);
        WaveDrawable colorWave = new WaveDrawable(new ColorDrawable(color));
        colorWave.setIndeterminate(true);
        mWaveDrawable.setIndeterminate(true);*/
    }

}
