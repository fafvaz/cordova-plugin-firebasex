package org.apache.cordova.firebase;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class OnNotificationOpenReceiver extends BroadcastReceiver {

    // Called on tapping foreground notification
    @Override
    public void onReceive(Context context, Intent intent) {
        PackageManager pm = context.getPackageManager();

        Intent launchIntent = pm.getLaunchIntentForPackage(context.getPackageName());
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        Bundle data = intent.getExtras();
        if(!data.containsKey("messageType")) data.putString("messageType", "notification");
        data.putString("tap", FirebasePlugin.inBackground() ? "background" : "foreground");

        FirebasePlugin.sendMessage(data, context);

        launchIntent.putExtras(data);
        context.startActivity(launchIntent);
    }
}
