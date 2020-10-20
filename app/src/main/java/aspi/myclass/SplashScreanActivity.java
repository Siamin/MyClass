package aspi.myclass;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import java.util.Timer;
import java.util.TimerTask;

import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.race604.drawable.wave.WaveDrawable;

import aspi.myclass.Helpers.LanguageHelper;
import aspi.myclass.activity.LoginActivity;
import tyrantgit.explosionfield.ExplosionField;

public class SplashScreanActivity extends Activity {

    ImageView mImageView;
    WaveDrawable mWaveDrawable;
    ExplosionField mExplosionField;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LanguageHelper.loadLanguage(SplashScreanActivity.this);

        setContentView(R.layout.activity_splashscrean);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //***********************************************************

        mExplosionField = ExplosionField.attach2Window(this);

        mImageView = findViewById(R.id.flash_img);
        mWaveDrawable = new WaveDrawable(this, R.drawable.aspiy);
        mImageView.setImageDrawable(mWaveDrawable);
        int color = getResources().getColor(R.color.colorAccent);
        WaveDrawable colorWave = new WaveDrawable(new ColorDrawable(color));
        colorWave.setIndeterminate(true);
        mWaveDrawable.setIndeterminate(true);

        Timer();

    }

    void Timer() {

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        mWaveDrawable.setIndeterminate(false);
                        startActivity(new Intent(SplashScreanActivity.this, LoginActivity.class));
                        finish();
                    }
                }, 5000);

    }


}
