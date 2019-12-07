package aspi.myclass.activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import org.json.JSONObject;

import aspi.myclass.Helpers.SharedPreferencesHelper;
import aspi.myclass.R;
import im.crisp.sdk.Crisp;

public class CrispActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crisp);

        try {

            Crisp.User.setEmail(SharedPreferencesHelper.get_Data("Email", "", CrispActivity.this));
            Crisp.Session.pushEvent(getResources().getString(R.string.app_name), new JSONObject());
            Crisp.User.setAvatar("https://cafedata.kise.roo.cloud/upload/icons/aspi.myclass.png?Signature=ySOfBc9x32ci5Ti3AfmdfFS8Ky8%3D&Expires=1573696589&AWSAccessKeyId=HC2ZX52BSCF01R06IBBW");

        } catch (Exception e) {
            onBackPressed();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CrispActivity.this, MainActivity.class));
        finish();
    }
}
