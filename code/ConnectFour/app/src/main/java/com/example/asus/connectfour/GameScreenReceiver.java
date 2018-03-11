package com.example.asus.connectfour;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Asus on 22.02.2018.
 */


public class GameScreenReceiver extends BroadcastReceiver {

    public static boolean screenOn = true;

    /**
     * Oluşan actionlar ile ekranın kapalı yada açık olduğunu algılar.
     * @param context Uygulamanın herhangi bir zamandaki durumunu tutan objedir.
     * @param intent Activiteler arasında yapılan bağlama.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
            screenOn = false;
        else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
            screenOn = true;

    }


}