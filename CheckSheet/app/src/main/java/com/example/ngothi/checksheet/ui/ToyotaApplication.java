package com.example.ngothi.checksheet.ui;

import android.app.Application;
import com.activeandroid.ActiveAndroid;

/**
 * Created by eo_cuong on 3/15/17.
 */

public class ToyotaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
