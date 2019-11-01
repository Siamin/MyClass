package aspi.myclass.Helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import aspi.myclass.NetWork;
import okhttp3.FormBody;

public class RequestHelper {

    static String TAG = "TAG_RequestHelper";
    static NetWork netWork = new NetWork();

//    public static void SendMail(final Context context, String Url) {
//        IndicatorHelper.IndicatorCreate(context,"","لطفا صبر کنید");
//        new AsyncTask<String, Void, String>() {
//
//            @Override
//            protected String doInBackground(String... p) {
//                return netWork.Post(new FormBody.Builder(),"", p[0]);
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                Log.i(TAG, result);
//                IndicatorHelper.IndicatorDismiss();
//                if (result.equals("1")){
//
//                }else{
//
//                }
//
//            }
//
//        }.execute(Url);
//    }


}
