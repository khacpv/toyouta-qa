package com.example.ngothi.checksheet.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.utils.FileUtils;

import butterknife.OnClick;

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
        String directory = FileUtils.getDirectory();
        if (directory != null) {
            Log.e("DIRECTTORY", directory);
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

