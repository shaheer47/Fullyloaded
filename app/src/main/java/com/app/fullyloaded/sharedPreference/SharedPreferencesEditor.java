package com.app.fullyloaded.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesEditor {

    /* SHARED PREFERENCES CONSTANTS */
    private final String PREF_SYNC_REQUIRED = "syncRequired";
    private final String PREF_USER_ID = "user_id";
    /* APPLICATION CONSTANTS */
    private final String PREF_FILE_APP = "ApplicationDetails";

    private final String PREF_LOGGED_IN = "loggedIn";
    private final String PREF_EMAIL = "mobileNo";
    private final String PREF_SERVER_KEY = "serverKey";
    private final String PREF_PASS = "password";
    private final String PREF_TO_BE_VERIFIED = "verified";
    private final String PREF_FIRST_TIME_LOGIN = "firstTimeLogin";

    private Context mContext = null;

    static final String PREF_LOGGEDIN_CNIC = "logged_in_cnic";
    static final String PREF_USER_LOGGEDIN_STATUS = "logged_in_status";

    public static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setLoggedInCnic(Context ctx, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LOGGEDIN_CNIC, email);
        editor.commit();
    }

    public static String getLoggedInCnic(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_LOGGEDIN_CNIC, "");
    }

    public static void setUserLoggedInStatus(Context ctx, boolean status) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_USER_LOGGEDIN_STATUS, status);
        editor.commit();
    }

    public static boolean getUserLoggedInStatus(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(PREF_USER_LOGGEDIN_STATUS, false);
    }

    public static void clearLoggedInEmailAddress(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_LOGGEDIN_CNIC);
        editor.remove(PREF_USER_LOGGEDIN_STATUS);
        editor.commit();
    }


    public SharedPreferencesEditor(Context context) {
        this.mContext = context;
    }

    public void clearPreferences() {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE).edit();
        editor.clear().commit();

    }

    public boolean getSyncRequired() {
        try {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE);
            boolean value = sharedPreferences.getBoolean(PREF_SYNC_REQUIRED, true);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean setSyncRequired(boolean syncRequired) {
        try {
            SharedPreferences.Editor editor = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE).edit();
            editor.putBoolean(PREF_SYNC_REQUIRED, syncRequired);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUserID() {
        try {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE);
            String value = sharedPreferences.getString(PREF_USER_ID, "");
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean setUserID(String value) {
        try {
            SharedPreferences.Editor editor = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE).edit();
            editor.putString(PREF_USER_ID, value);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean IsLoggedIn() {
        try {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE);
            boolean value = sharedPreferences.getBoolean(PREF_LOGGED_IN, false);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean setLoggedIn(boolean syncRequired) {
        try {
            SharedPreferences.Editor editor = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE).edit();
            editor.putBoolean(PREF_LOGGED_IN, syncRequired);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getEmail() {
        try {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE);
            String value = sharedPreferences.getString(PREF_EMAIL, "");
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean setEmail(String value) {
        try {
            SharedPreferences.Editor editor = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE).edit();
            editor.putString(PREF_EMAIL, value);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getServerKey() {
        try {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE);
            String value = sharedPreferences.getString(PREF_SERVER_KEY, "");
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean setServerKey(String value) {
        try {
            SharedPreferences.Editor editor = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE).edit();
            editor.putString(PREF_SERVER_KEY, value);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getPass() {
        try {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE);
            String value = sharedPreferences.getString(PREF_PASS, "");
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean setPass(String value) {
        try {
            SharedPreferences.Editor editor = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE).edit();
            editor.putString(PREF_PASS, value);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean IsToBeVerified() {
        try {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE);
            boolean value = sharedPreferences.getBoolean(PREF_TO_BE_VERIFIED, false);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean setToBeVerified(boolean syncRequired) {
        try {
            SharedPreferences.Editor editor = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE).edit();
            editor.putBoolean(PREF_TO_BE_VERIFIED, syncRequired);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean firstTimeLogin() {
        try {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE);
            boolean value = sharedPreferences.getBoolean(PREF_FIRST_TIME_LOGIN, false);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean setFirstTimeLogin(boolean firstTimeLogin) {
        try {
            SharedPreferences.Editor editor = mContext.getSharedPreferences(PREF_FILE_APP, Context.MODE_PRIVATE).edit();
            editor.putBoolean(PREF_FIRST_TIME_LOGIN, firstTimeLogin);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}