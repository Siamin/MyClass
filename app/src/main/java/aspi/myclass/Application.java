package aspi.myclass;


//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;

import im.crisp.sdk.Crisp;


public class Application extends android.app.Application {

//    RefWatcher refwatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        Crisp.initialize(this);
        Crisp.getInstance().setWebsiteId(getResources().getString(R.string.CrispWebId));

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        refwatcher = LeakCanary.install(this);
    }


}
