package com.example.ngothi.checksheet.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ngothi.checksheet.R;
import com.google.zxing.Result;

import butterknife.OnClick;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends BaseActivity implements ZXingScannerView.ResultHandler {
    private static final String FLASH_STATE = "FLASH_STATE";
    private static final int CAMERA_REQUEST_CODE = 1;

    private ZXingScannerView mScannerView;

    private boolean mFlash;
    private String mSeq;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.setFlash(mFlash);
        mScannerView.setAutoFocus(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] { Manifest.permission.CAMERA },
                        CAMERA_REQUEST_CODE);
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScannerView.startCamera();
                }
            }, 200);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*finish();
                moveSheetActivity();*/
            }
        }, 500);
    }

    @OnClick(R.id.button_back)
    public void back() {
        finish();
    }

    @Override
    public int getResourceLayout() {
        return R.layout.activity_scan;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
        mSeq = result.getText();
        Toast.makeText(this,
                "Contents = " + result.getText() + ", Format = " + result.getBarcodeFormat()
                        .toString(), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                moveSheetActivity();
            }
        }, 2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScannerView.startCamera();
                    }
                }, 200);
            } else {

            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FLASH_STATE, mFlash);
    }

    public void moveSheetActivity() {
        Intent intent = new Intent(ScanActivity.this, SheetActivity.class);
        Bundle ten_image = new Bundle();
        ten_image.putString("Seq", mSeq);
        intent.putExtra("GoiTen1", ten_image);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //    public void toggleFlash(View v) {
    //        mFlash = !mFlash;
    //        mScannerView.setFlash(mFlash);
    //    }
}
