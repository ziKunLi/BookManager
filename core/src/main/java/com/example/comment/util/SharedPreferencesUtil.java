package com.example.comment.util;

import android.content.SharedPreferences;

import com.example.comment.app.ThisApp;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtil {

    public static SharedPreferences PREFERENCES = ThisApp.getApplicationContext().getSharedPreferences("shareFile", MODE_PRIVATE);

    public static SharedPreferences.Editor EDITOR;

    static {
        EDITOR = PREFERENCES.edit();
    }

    public static void putBoolean(String key, boolean input) {
        EDITOR.putBoolean(key, input);
        EDITOR.commit();
    }

    public static boolean getBoolean(String key) {
        return PREFERENCES.getBoolean(key, false);
    }
}
