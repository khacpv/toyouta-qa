package com.example.ngothi.checksheet.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kienht on 3/15/17.
 */

public class CategoryHistory implements Serializable {

    private String mNumberStep;
    private List<ImageError> imageErrorsList;

    public CategoryHistory() {
    }

    public CategoryHistory(String mNumberStep, List<ImageError> imageErrorsList) {
        this.mNumberStep = mNumberStep;
        this.imageErrorsList = imageErrorsList;
    }

    public class ImageError implements Serializable {
        int mUri;

        public ImageError(int mUri) {
            this.mUri = mUri;
        }

        public int getmUri() {
            return mUri;
        }

        public void setmUri(int mUri) {
            this.mUri = mUri;
        }
    }

    public String getmNumberStep() {
        return mNumberStep;
    }

    public void setmNumberStep(String mNumberStep) {
        this.mNumberStep = mNumberStep;
    }

    public List<ImageError> getImageErrorsList() {
        return imageErrorsList;
    }

    public void setImageErrorsList(List<ImageError> imageErrorsList) {
        this.imageErrorsList = imageErrorsList;
    }
}
