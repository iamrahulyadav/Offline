package com.portalidea.roundtableitalia.FCM;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.portalidea.roundtableitalia.Activity.MainActivity;
import com.portalidea.roundtableitalia.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by archirayan on 03-Oct-16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public String title, message, link;


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

//        Log.e(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

//        Log.e(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            try {
                JSONObject object = new JSONObject(remoteMessage.getData());
                title = object.getString("push_title");
                message = object.getString("push_message");
                link = object.getString("push_links");
//                Log.e("HelloJItesh", object.toString());
            } catch (JSONException e) {
//                Log.e("Exception", e.toString() + " -- hello " + remoteMessage.getNotification().getBody());
                e.printStackTrace();
            }
            sendNotification();
//            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());


//            try {
//                JSONObject object = new JSONObject(remoteMessage.getNotification().getBody());
//                title = object.getString("title");
//                message = object.getString("message");
//                link = object.getString("urllink");
////                Log.e("HelloJItesh", object.toString());
//            } catch (JSONException e) {
//                Log.e("Exception", e.toString() + " -- hello " + remoteMessage.getNotification().getBody());
//                e.printStackTrace();
//            }
//            sendNotification();
//        }

        // Also if you intend on generating your own notifications as a resu    lt of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

   /* */

    /**
     * Create and show a simple notification containing the received FCM message.
     */
    private void sendNotification() {
        PendingIntent pendingIntent = null;
        /* = new Intent(this, MainActivity.class);*/
        if (link.length() > 0) {
            String urlString = link;
            if (urlString != null) {
                if (urlString.length() > 3) {
                    if (urlString.startsWith("http")) {
                        Intent intent;
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(urlString));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                                PendingIntent.FLAG_ONE_SHOT);
                    } else {
                        String url = "http://" + urlString;
                        Intent intent;
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                                PendingIntent.FLAG_ONE_SHOT);
                    }
                }
            }

//            intent.putExtra("Data", link);


        } else {
//            Intent intent;
//            intent = new Intent(this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                    PendingIntent.FLAG_ONE_SHOT);
//        }
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setTicker(title)
//                .setAutoCancel(true)
//                .setContentTitle(title)
//                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                .setContentText(message)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());


            Intent intent = new Intent(this, MainActivity.class);
            if (link.length() > 0) {
                intent.putExtra("Data", link);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker(title)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
    }
}
