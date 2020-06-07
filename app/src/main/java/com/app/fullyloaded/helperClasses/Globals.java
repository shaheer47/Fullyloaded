package com.app.fullyloaded.helperClasses;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.app.fullyloaded.sharedPreference.SharedPreferencesEditor;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Globals {

    private static Globals instance = null;

    public static Globals getUsage() {
        return instance;
    }

    public static Globals getInstance(Activity activity) {
        if (instance == null) {
            instance = new Globals(activity);
        }
        return instance;
    }

    public Locale mLocale;
    public Activity mActivity;
    public Context mContext;
    public String mPackageName;
    public int mScreenWidth;
    public int mScreenHeight;
    public SharedPreferencesEditor sharedPreferencesEditor;
    public static Typeface typeface = null;


    private Globals(Activity activity) {
        mLocale = Locale.ENGLISH;
        mActivity = activity;
        mContext = activity;
        mPackageName = mContext.getPackageName();
        sharedPreferencesEditor = new SharedPreferencesEditor(mContext);

        Point size = new Point();
        mActivity.getWindowManager().getDefaultDisplay().getSize(size);
        mScreenWidth = size.x;
        mScreenHeight = size.y;
        setUrduFont();

    }

    public static void setUrduFont() {
        try {
//			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
//				typeface = Typeface.createFromAsset(Globals.getUsage().mContext.getAssets(), "fonts/jameel_noori_nastaleeq.ttf");
//				typeface = Typeface.createFromAsset(Globals.getUsage().mContext.getAssets(), "fonts/alvi_nastaleeq_regular.ttf");
//				typeface = Typeface.createFromAsset(Globals.getUsage().mContext.getAssets(), "font/urdu_regular.ttf");
//			} else {
//				typeface = Typeface.createFromAsset(Globals.getUsage().mContext.getAssets(), "fonts/nastaleeq_like.ttf");
//			}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getAppVersion() {
        PackageInfo pInfo = null;
        try {
            pInfo = Globals.getUsage().mContext.getPackageManager().getPackageInfo(Globals.getUsage().mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo.versionCode;
    }

    public static boolean isEmailValid(String email) {
        String expression = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String getAppVersionName() {
        PackageInfo pInfo = null;
        try {
            pInfo = Globals.getUsage().mContext.getPackageManager().getPackageInfo(Globals.getUsage().mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo.versionName;
    }

    public static int getAppVersionCode() {
        PackageInfo pInfo = null;
        try {
            pInfo = Globals.getUsage().mContext.getPackageManager().getPackageInfo(Globals.getUsage().mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo.versionCode;
    }

}