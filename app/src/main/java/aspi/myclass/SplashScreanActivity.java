package aspi.myclass;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.race604.drawable.wave.WaveDrawable;

import aspi.myclass.activity.LoginActivity;
import aspi.myclass.class_.OtherMetod;
import tyrantgit.explosionfield.ExplosionField;

public class SplashScreanActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback, PermissionResultCallback {

    int time = 0;
    ImageView mImageView;
    WaveDrawable mWaveDrawable;
    ExplosionField mExplosionField;
    PermissionUtils permissionUtils;
    ArrayList<String> permissions = new ArrayList<>();
    OtherMetod om = new OtherMetod();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscrean);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        //*********************************************************** PermissionUtils
        permissionUtils = new PermissionUtils(SplashScreanActivity.this);
        permissions.add(Manifest.permission.INTERNET);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.ACCESS_NETWORK_STATE);
        permissionUtils.check_permission(permissions, "برای استفاده از برنامه بایستی تمامی مجوزها را تایید نمایید", 1);
        //***********************************************************

        mExplosionField = ExplosionField.attach2Window(this);

        mImageView = (ImageView) findViewById(R.id.flash_img);
        mWaveDrawable = new WaveDrawable(this, R.drawable.aspiy);
        mImageView.setImageDrawable(mWaveDrawable);
        int color = getResources().getColor(R.color.colorAccent);
        WaveDrawable colorWave = new WaveDrawable(new ColorDrawable(color));
        colorWave.setIndeterminate(true);
        mWaveDrawable.setIndeterminate(true);

    }

    void Timer() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        time++;
                        Log.i("TAG_Flash", "Timer :" + time);
                        if (time == 5) {
                            mWaveDrawable.setIndeterminate(false);
                            timer.cancel();
                            startActivity(new Intent(SplashScreanActivity.this, LoginActivity.class));
                            finish();
                        }

                    }
                });
            }
        }, 1, 1000);
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Boolean isGranted = true;
        for (int i = 0; i < permissions.length; i++) {
            Log.i("TAG_Premissions", "" + permissions);
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                isGranted = false;
            }

            permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (!isGranted) {
                om.SetCode("permissions", "1", SplashScreanActivity.this);
                Timer();


            }
        }

    }

    @Override
    public void PermissionGranted(int request_code) {
        Timer();
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {

    }

    @Override
    public void PermissionDenied(int request_code) {

    }

    @Override
    public void NeverAskAgain(int request_code) {

    }


}
