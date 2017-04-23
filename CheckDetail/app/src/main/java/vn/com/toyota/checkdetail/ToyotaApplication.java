package vn.com.toyota.checkdetail;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import vn.com.toyota.checkdetail.storage.ProductStorage;

/**
 * Created by eo_cuong on 3/15/17.
 */

public class ToyotaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        ActiveAndroid.initialize(this);
    }
}
