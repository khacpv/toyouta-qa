package vn.com.toyota.checkdetail.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;

import vn.com.toyota.checkdetail.model.ErrorPosition;

public class ErrorPositionStorage {
    private static ErrorPositionStorage instance;
    private SharedPreferences mSharedPreferences;

    public static synchronized ErrorPositionStorage getInstance() {
        if (instance == null) {
            instance = new ErrorPositionStorage();
        }
        return instance;
    }

    private ErrorPositionStorage() {
    }

    public void init(Context context) {
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void addErrorPosition(ErrorPosition errorPosition) {
        setString("error_position", new Gson().toJson(errorPosition));
    }

    public ErrorPosition getErrorPosition() {
        if (this.mSharedPreferences == null) {
            return null;
        }
        String json = this.mSharedPreferences.getString("error_position", null);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return new Gson().fromJson(json, ErrorPosition.class);
    }

    public void clearErrorPosition() {
        if (this.mSharedPreferences != null) {
            SharedPreferences.Editor editor = this.mSharedPreferences.edit();
            editor.remove("error_position");
            editor.apply();
        }
    }

    public String getString(String key) {
        if (this.mSharedPreferences == null) {
            return null;
        }
        return this.mSharedPreferences.getString(key, null);
    }

    public void setString(String key, String value) {
        if (this.mSharedPreferences != null) {
            this.mSharedPreferences.edit().putString(key, value).apply();
        }
    }
}