package com.example.ngothi.checksheet.ui.model;

/**
 * Created by kienht on 3/14/17.
 */

public class HistoryObj {

    private boolean mStatus;
    private String mBarcodeNumber;
    private String mTimeScan;
    private String mDetail;

    public HistoryObj(boolean mStatus, String mBarcodeNumber, String mTimeScan, String mDetail) {
        this.mStatus = mStatus;
        this.mBarcodeNumber = mBarcodeNumber;
        this.mTimeScan = mTimeScan;
        this.mDetail = mDetail;
    }

    public boolean ismStatus() {
        return mStatus;
    }

    public void setmStatus(boolean mStatus) {
        this.mStatus = mStatus;
    }

    public String getmBarcodeNumber() {
        return mBarcodeNumber;
    }

    public void setmBarcodeNumber(String mBarcodeNumber) {
        this.mBarcodeNumber = mBarcodeNumber;
    }

    public String getmTimeScan() {
        return mTimeScan;
    }

    public void setmTimeScan(String mTimeScan) {
        this.mTimeScan = mTimeScan;
    }

    public String getmDetail() {
        return mDetail;
    }

    public void setmDetail(String mDetail) {
        this.mDetail = mDetail;
    }
}
