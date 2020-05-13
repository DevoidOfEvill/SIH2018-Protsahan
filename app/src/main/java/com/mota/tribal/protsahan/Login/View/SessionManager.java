package com.mota.tribal.protsahan.Login.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
    // Shared preferences file name
    private static final String PREF_NAME = "E-suvidha";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    // Shared Preferences
    SharedPreferences pref;
    Editor editor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    int check = 0;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        check = 1;
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public int check() {
        return check;
    }
}