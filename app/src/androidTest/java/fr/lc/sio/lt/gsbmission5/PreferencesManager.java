package fr.lc.sio.lt.gsbmission5;

import android.content.Context;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesManager {

    private static PreferencesManager mInstance;
    private SharedPreferences mPreferences;

    private static final String PREFERENCE_NAME = "com.mypreferences";

    private PreferencesManager(Context context){
        mPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);

    }


}
