package com.gwp.lifestyle.jour5;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.gwp.lifestyle.R;

public class InternetCheckService extends Service {
    private static final String CHANNEL_ID = "internet_check_channel";
    private Handler handler = new Handler();
    private boolean wasConnected = true; // État précédent de la connexion

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1, getForegroundNotification());

        // Vérifier la connexion toutes les 10 secondes
        handler.postDelayed(checkInternetRunnable, 10000);
    }

    // Vérification de l'Internet
    private final Runnable checkInternetRunnable = new Runnable() {
        @Override
        public void run() {
            boolean isConnected = isInternetAvailable();
            if (!isConnected && wasConnected) {
                showNoInternetNotification();
            }
            wasConnected = isConnected;
            handler.postDelayed(this, 10000); // Vérifie toutes les 10 secondes
        }
    };

    // Méthode pour vérifier la connexion Internet
    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
            } else {
                android.net.NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnected();
            }
        }
        return false;
    }

    // Notification en mode foreground
    private Notification getForegroundNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Surveillance de la connexion")
                .setContentText("Le service surveille la connexion Internet...")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
    }

    // Créer une notification lorsque Internet ne fonctionne pas
    private void showNoInternetNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Problème de connexion")
                .setContentText("Internet ne fonctionne pas !")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(2, builder.build());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(checkInternetRunnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

