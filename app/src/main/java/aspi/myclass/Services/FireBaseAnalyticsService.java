package aspi.myclass.Services;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FireBaseAnalyticsService {

    private String ID = "212448941";

    public void EventFireBaseAnalytics(FirebaseAnalytics firebaseAnalytics,String id,String name,String type){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, type);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void CustomEventFireBaseAnalytics(FirebaseAnalytics firebaseAnalytics,String id,String name,String type){
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("name", name);
        bundle.putString("type", type);
        firebaseAnalytics.logEvent("round_completed", bundle);
    }
}
