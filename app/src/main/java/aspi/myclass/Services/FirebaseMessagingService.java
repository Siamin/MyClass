package aspi.myclass.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import aspi.myclass.activity.MainActivity;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {


    static String TAG = "TAG_FirebaseMessagingService";
    String NotifacationChannelId = "myclass-f2fed";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        ShowNotification(remoteMessage.getData());


    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.i(TAG, "TokenFirebase");
    }

    private void ShowNotification(Map<String, String> Data) {

        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NotifacationChannelId, "Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("MyClass");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        final NotificationCompat.Builder notificatioBuilder = new NotificationCompat.Builder(this, NotifacationChannelId);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        notificatioBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentText(Data.get("title"))
                .setContentText(Data.get("body"))
                .setContentIntent(pendingIntent)
                .setContentInfo("Info");

        if (!Data.get("imageURL").isEmpty()) {
            ImageRequest imageRequest = new ImageRequest(Data.get("imageURL"), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    notificatioBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(response));
                    notificationManager.notify(0, notificatioBuilder.build());
                }
            }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        } else {
            notificationManager.notify(0, notificatioBuilder.build());
        }


    }
}