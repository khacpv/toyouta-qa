package com.example.ngothi.checksheet.ui.model;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FRAMGIA\hoang.van.cuong on 14/03/2017.
 */

public class Step {

    @Expose
    private int noNumber;
    @Expose
    private CategoyCheck mCategoyCheck;
    @Expose
    private boolean isGood;
    @Expose
    List<ImageCapture> mImageList;
    @Expose
    private String mNoteReality;

    public Step() {
        mImageList=new ArrayList<>();
    }



    public int getNoNumber() {
        return noNumber;
    }

    public void setNoNumber(int noNumber) {
        this.noNumber = noNumber;
    }

    public CategoyCheck getCategoyCheck() {
        return mCategoyCheck;
    }

    public void setCategoyCheck(CategoyCheck categoyCheck) {
        mCategoyCheck = categoyCheck;
    }

    public List<ImageCapture> getImageList() {
        return mImageList;
    }

    public void setImageList(List<ImageCapture> imageList) {
        if(mImageList!=null){
            mImageList.addAll(imageList);
        }
    }

    public String getNoteReality() {
        return mNoteReality;
    }

    public void setNoteReality(String noteReality) {
        mNoteReality = noteReality;
    }

    public boolean isGood() {
        return isGood;
    }

    public void setGood(boolean good) {
        isGood = good;
    }
}
