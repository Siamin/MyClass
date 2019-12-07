package aspi.myclass;


import im.crisp.sdk.Crisp;


public class Application extends android.app.Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Crisp.initialize(this);
        Crisp.getInstance().setWebsiteId(getResources().getString(R.string.CrispWebId));



    }


}
