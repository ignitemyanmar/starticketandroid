package com.ignite.mdm.ticketing.agent.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by user on 1/18/17.
 */

public class PrefManager {
  public static final String USERNAME = "USERNAME";
  public static final String PASSWORD = "PASSWORD";

  public static void putUserName(Context context, String userName) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(USERNAME, userName).apply();
  }

  public static String getUserName(Context context) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getString(USERNAME, null);
  }

  public static String getPassword(Context context) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getString(PASSWORD, null);
  }

  public static void putPassword(Context context, String password) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(PASSWORD, password).apply();
  }

  public static void clearPreferences(Context context) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.clear().apply();
  }
}
