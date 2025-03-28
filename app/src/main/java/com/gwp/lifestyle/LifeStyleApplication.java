package com.gwp.lifestyle;

import static com.gwp.lifestyle.Prefs.NUMBER_CAT_DEFAULT;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.core.app.NotificationManagerCompat;

public class LifeStyleApplication extends Application{
    private SharedPreferences prefs;
    public static LifeStyleApplication instance;
    public static final String CHANNEL_ID = Prefs.ROOT_PREFS;
    private static final int NOTIFICATION_ID = 1;
    // Déclaration des paramètres comme constants statiques
    public static final String CHANNEL_NAME = "My Channel";
    public static final String CHANNEL_DESCRIPTION = "Description du canal";
    public static final int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;

//    LifeStyleApplication.instance.getPrefs()

    public SharedPreferences getPrefs() {
        return prefs;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = getSharedPreferences(Prefs.ROOT_PREFS,MODE_PRIVATE);
        instance = this;
        firstLaunch();
        createNotificationChannel();

    }

    private void firstLaunch(){
        boolean fisrt = prefs.getBoolean(Prefs.FIRST_LAUNCH,false);
        if(!fisrt){
            prefs.edit()
                    .putInt(Prefs.NUMBER_CAT,NUMBER_CAT_DEFAULT)
                    .putInt(Prefs.LIMIT_CAT,10)
                    .putInt(Prefs.SKIP_CAT,0)
                    .putBoolean(Prefs.FIRST_LAUNCH,true)
                    .apply();
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Utilisation des constantes statiques
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE);
            channel.setDescription(CHANNEL_DESCRIPTION);

            // Créer le NotificationManager et enregistrer le canal
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
