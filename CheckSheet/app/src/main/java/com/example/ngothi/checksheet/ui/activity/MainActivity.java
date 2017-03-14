package com.example.ngothi.checksheet.ui.activity;

import android.content.Intent;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.ngothi.checksheet.R;
<<<<<<< HEAD
import com.example.ngothi.checksheet.ui.activity.history.KHistoryActivity;
=======
import com.example.ngothi.checksheet.ui.utils.FileUtils;
>>>>>>> a09cfe2ce70db6e42cb47541428009a38dc42217

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public int getResourceLayout() {
        return R.layout.activity_main;
=======
        setContentView(R.layout.activity_main);
        Log.e("file directory", FileUtils.getDirectoryImageCapturePath());
>>>>>>> a09cfe2ce70db6e42cb47541428009a38dc42217
    }

    @OnClick(R.id.scan)
    void moveScanActivity() {
        moveIntent(ScanActivity.class);
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

