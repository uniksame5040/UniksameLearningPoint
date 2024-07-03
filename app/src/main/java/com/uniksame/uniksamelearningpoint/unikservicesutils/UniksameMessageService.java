package com.uniksame.uniksamelearningpoint.unikservicesutils;

import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.uniksame.uniksamelearningpoint.R;
import com.uniksame.uniksamelearningpoint.ThoughtShareActivity;

public class UniksameMessageService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    public void showNotification(String title,String message){
        Intent intentSolution = new Intent(this, ThoughtShareActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,
                intentSolution,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"myNotification")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.getty)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }

}
