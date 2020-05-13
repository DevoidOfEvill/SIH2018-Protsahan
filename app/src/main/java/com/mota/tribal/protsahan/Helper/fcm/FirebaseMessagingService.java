package com.mota.tribal.protsahan.Helper.fcm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {


    private static final String TAG = "Ayush";
    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("Ayush", "Msg received");
        String activity = remoteMessage.getData().get("click_action");
        Intent resultIntent = new Intent(activity);
        showNotificationMessage(getApplicationContext(), remoteMessage.getData().get("title"), remoteMessage.getData().get("body")
                , remoteMessage.getData().get("Icon"), resultIntent);
    }

    private void showNotificationMessage(Context context, String title, String message, String iconUrl, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        notificationUtils.showNotificationMessage(title, message, iconUrl, intent);
    }
}