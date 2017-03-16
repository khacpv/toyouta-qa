package com.example.ngothi.checksheet.ui.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import java.util.List;

/**
 * Created by eo_cuong on 3/15/17.
 */

public class CategoyCheck {

    @Expose
    private int mNoStt;

    @Expose
    private String mName;

    @Expose
    private List<String> mSpecificities;

    @Expose
    private String imageDefaul;

    @Expose
    private String position;

    public int getNoStt() {
        return mNoStt;
    }

    public void setNoStt(int noStt) {
        mNoStt = noStt;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<String> getSpecificities() {
        return mSpecificities;
    }

    public void setSpecificities(List<String> specificities) {
        mSpecificities = specificities;
    }

    public String getImageDefaul() {
        return imageDefaul;
    }

    public void setImageDefaul(String imageDefaul) {
        this.imageDefaul = imageDefaul;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
