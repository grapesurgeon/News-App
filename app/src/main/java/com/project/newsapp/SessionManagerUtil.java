package com.project.newsapp;

import static com.project.newsapp.Constants.REMEMBER_ME;
import static com.project.newsapp.Constants.USER_PREFERENCE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class SessionManagerUtil {

    public static final String SESSION_PREFERENCE = "com.example.basicandroid.SessionManagerUtil.SESSION_PREFERENCE";
    public static final String SESSION_TOKEN = "com.example.basicandroid.SessionManagerUtil.SESSION_TOKEN";
    public static final String SESSION_EXPIRY_TIME = "com.example.basicandroid.SessionManagerUtil.SESSION_EXPIRY_TIME";

    private static SessionManagerUtil INSTANCE;
    public static SessionManagerUtil getInstance(){
        if (INSTANCE == null){
            INSTANCE = new SessionManagerUtil();
        }
        return INSTANCE;
    }

    public void startUserSession(Context context, int expiredIn){
        Calendar calendar = Calendar.getInstance();
        Date userLoggedTime = calendar.getTime();
        calendar.setTime(userLoggedTime);
        calendar.add(Calendar.SECOND, expiredIn);
        Date expiryTime = calendar.getTime();
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SESSION_EXPIRY_TIME, expiryTime.getTime());
        editor.apply();
    }

    public boolean isSessionActive(Context context, Date currentTime){
        Date sessionExpiresAt;
        SharedPreferences preferences = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE);
        if(preferences.getBoolean(REMEMBER_ME, false)){ //if remember me set expireed to tomorrow
            sessionExpiresAt = getTomorrow();
            Log.d("asdf", "does not expire : " + sessionExpiresAt);
        } else {
            sessionExpiresAt = new Date(getExpiryDateFromPreference(context));
            Log.d("asdf", "expires at: " + sessionExpiresAt);
        }

        return !currentTime.after(sessionExpiresAt);
    }

    private Date getTomorrow(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    private long getExpiryDateFromPreference(Context context){
        return context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE)
                .getLong(SESSION_EXPIRY_TIME, 0);
    }

    public void storeUserToken(Context context, String token){
        SharedPreferences.Editor editor =
                context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.putString(SESSION_TOKEN, token);
        editor.apply();
    }

    public String getUserToken(Context context){
        return context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE)
                .getString(SESSION_TOKEN, "");
    }

    public void endUserSession(Context context){
        clearStoredData(context);
    }

    private void clearStoredData(Context context){
        SharedPreferences.Editor editor =
                context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

}
