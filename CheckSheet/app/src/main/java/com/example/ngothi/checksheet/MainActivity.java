package com.example.ngothi.checksheet;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void Scan13(View v)
    {
        Intent intent = new Intent(MainActivity.this, ScanActivity.class);
        startActivity(intent);
        }
    public void history(View v) {

        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }
    public void setting(View v){
        Intent intent = new Intent(MainActivity.this, CheckActivity.class);
        startActivity(intent);
    }
}

