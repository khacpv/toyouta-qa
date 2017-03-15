package com.example.ngothi.checksheet.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;
import butterknife.OnClick;
import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.utils.FileUtils;
import java.io.IOException;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public int getResourceLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void afterSetcontenview() {

        requestPermissionWrite();
    }

    private void createDirectory() {
        String directory = null;
        try {
            directory = FileUtils.getDirectory();
            Toast.makeText(getApplicationContext(),
                    "create directory " + directory + " successfully", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "can not create directory .Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        if (directory != null) {
            Log.e("DIRECTTORY", directory);
        }
    }

    private void requestPermissionWrite() {

        if (Build.VERSION.SDK_INT < 23) {
            createDirectory();
            return;
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(MainActivity.this,
                        "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.",
                        Toast.LENGTH_LONG).show();
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, 1);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            createDirectory();
        }
    }

    @OnClick(R.id.scan)
    void moveScanActivity() {
        moveIntent(ScanActivity.class);
        // moveIntent(SheetActivity.class);
    }

    @OnClick(R.id.history)
    void moveHistoryActivity() {
        moveIntent(KHistoryActivity.class);
    }

    @OnClick(R.id.setting)
    void moveSettingActivity() {
        moveIntent(CheckActivity.class);
    }

    private void moveIntent(Class c) {
        Intent intent = new Intent(MainActivity.this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}

