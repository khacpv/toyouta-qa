package com.example.ngothi.checksheet.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kienht on 3/14/17.
 */

public class HistoryObj implements Serializable {

    private String mNameCar;
    private boolean mStatus;
    private String mBarcodeNumber;
    private String mTimeScan;
    private String mDetail;
    private List<HistoryDetailObj> historyDetailObjList;

    public HistoryObj(String mNameCar, boolean mStatus, String mBarcodeNumber, String mTimeScan, String mDetail, List<HistoryDetailObj> historyDetailObjList) {
        this.mNameCar = mNameCar;
        this.mStatus = mStatus;
        this.mBarcodeNumber = mBarcodeNumber;
        this.mTimeScan = mTimeScan;
        this.mDetail = mDetail;
        this.historyDetailObjList = historyDetailObjList;
    }

    public String getmNameCar() {
        return mNameCar;
    }

    public void setmNameCar(String mNameCar) {
        this.mNameCar = mNameCar;
    }

    public List<HistoryDetailObj> getHistoryDetailObjList() {
        return historyDetailObjList;
    }

    public void setHistoryDetailObjList(List<HistoryDetailObj> historyDetailObjList) {
        this.historyDetailObjList = historyDetailObjList;
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
