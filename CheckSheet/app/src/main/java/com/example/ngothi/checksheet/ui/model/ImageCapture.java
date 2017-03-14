package com.example.ngothi.checksheet.ui.model;

import android.graphics.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FRAMGIA\hoang.van.cuong on 14/03/2017.
 */

public class ImageCapture {
    private int mResourceId;
    private String mFilepath;
    private boolean isEditted;
    private String mFileEdittedpath;
    private boolean isFromFile;
    private List<Path> mPaths;

    public void setResourceId(int resourceId) {
        mResourceId = resourceId;
    }

    public void setFilepath(String filepath) {
        mFilepath = filepath;
    }

    public void setEditted(boolean editted) {
        isEditted = editted;
    }

    public void setFileEdittedpath(String fileEdittedpath) {
        mFileEdittedpath = fileEdittedpath;
    }

    public void setFromFile(boolean fromFile) {
        isFromFile = fromFile;
    }

    public List<Path> getPaths() {
        return mPaths;
    }

    public void setPaths(List<Path> paths) {
        if (mPaths == null) {
            mPaths = new ArrayList<>();
        }
        mPaths.clear();
        mPaths.addAll(paths);
    }

    public int getResourceId() {
        return mResourceId;
    }

    public String getFilepath() {
        return mFilepath;
    }

    public boolean isEditted() {
        return isEditted;
    }

    public String getFileEdittedpath() {
        return mFileEdittedpath;
    }

    public boolean isFromFile() {
        return isFromFile;
    }

    public static class Builder {

        private int mResourceId;
        private String mFilepath;
        private boolean isEditted;
        private String mFileEdittedpath;
        private boolean isFromFile;

        public Builder setResourceId(int resourceId) {
            mResourceId = resourceId;
            return this;
        }

        public Builder setFilepath(String filepath) {
            mFilepath = filepath;
            return this;
        }

        public Builder setEditted(boolean editted) {
            isEditted = editted;
            return this;
        }

        public Builder setFileEdittedpath(String fileEdittedpath) {
            mFileEdittedpath = fileEdittedpath;
            return this;
        }

        public Builder setFromFile(boolean fromFile) {
            isFromFile = fromFile;
            return this;
        }

        public ImageCapture build() {
            ImageCapture imageCapture = new ImageCapture();
            imageCapture.mResourceId = mResourceId;
            imageCapture.mFilepath = mFilepath;
            imageCapture.isEditted = isEditted;
            imageCapture.mFileEdittedpath = mFileEdittedpath;
            imageCapture.isFromFile = isFromFile;
            return imageCapture;
        }
    }
}
