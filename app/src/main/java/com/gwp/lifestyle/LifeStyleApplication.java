package com.gwp.lifestyle;

import android.app.Application;
import android.content.SharedPreferences;

public class LifeStyleApplication extends Application{
    private SharedPreferences prefs;
    public static LifeStyleApplication instance;
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

    }

    private void firstLaunch(){
        boolean fisrt = prefs.getBoolean(Prefs.FIRST_LAUNCH,false);
        if(!fisrt){
            prefs.edit()
                    .putInt(Prefs.LIMIT_CAT,10)
                    .putInt(Prefs.SKIP_CAT,0)
                    .putBoolean(Prefs.FIRST_LAUNCH,true)
                    .apply();
        }


    }
}
