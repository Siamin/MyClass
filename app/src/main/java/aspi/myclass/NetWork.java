package aspi.myclass;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetWork {

    String TAG = "TAG_NetWork";
    OkHttpClient client = new OkHttpClient();


    public String Post(FormBody.Builder formBuilder, String Header, String URL) {
        try {
            RequestBody formBody = formBuilder.build();
            Request request = new Request.Builder()
                    .url(URL)
                    .post(formBody)
                    .header("Content-Type", "application/json")
//                    .header("Authorization", Header)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            Log.i(TAG, "Post :" + e.toString());
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
