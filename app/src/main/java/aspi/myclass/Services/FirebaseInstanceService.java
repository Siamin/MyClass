package aspi.myclass.Services;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class FirebaseInstanceService extends FirebaseInstanceIdService {


    static String TAG = "TAG_FirebaseInstanceService";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        Log.i(TAG,FirebaseInstanceId.getInstance().getToken());
    }
}
