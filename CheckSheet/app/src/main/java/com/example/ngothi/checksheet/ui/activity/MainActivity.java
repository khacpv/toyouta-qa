package com.example.ngothi.checksheet.ui.activity;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.utils.FileUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("file directory", FileUtils.getDirectory());
    }

    public void scanClick(View v) {
        Intent intent = new Intent(MainActivity.this, ScanActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        String directory = FileUtils.getDirectory();
        Log.e("Directory", directory);
    }

    public void historyClick(View v) {

        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }

    public void settingClick(View v) {
        Intent intent = new Intent(MainActivity.this, CheckActivity.class);
        startActivity(intent);
    }
}

