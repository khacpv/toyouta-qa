package vn.com.toyota.checkdetail.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FRAMGIA\hoang.van.cuong on 14/03/2017.
 */

public class ImageCapture {
    @Expose
    private int mResourceId;
    @Expose
    private String mFilepath;
    @Expose
    private boolean isEditted;
    @Expose
    private String mFileEdittedpath;
    @Expose
    private boolean isFromFile;
    @Expose
    private Size mViewSize;
    @Expose
    private List<DrawEntityPath> mDrawEntityPaths;

    @Expose
    private String thumbPath;

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

    public Size getViewSize() {
        return mViewSize;
    }

    public void setViewSize(Size viewSize) {
        mViewSize = viewSize;
    }

    public void setDrawEntityPaths(List<DrawEntityPath> paths) {
        if (mDrawEntityPaths == null) {
            mDrawEntityPaths = new ArrayList<>();
        }
        mDrawEntityPaths.clear();
        mDrawEntityPaths.addAll(paths);
    }

    public List<DrawEntityPath> getDrawEntityPaths() {
        return mDrawEntityPaths;
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

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
        if (thumbPath != null) {
            if (!isFromFile()) {
                String outputFile = thumbPath.replace("_thumb.jpg", ".jpg");
                setFilepath(outputFile);
            }
        }
    }

    public static class Builder {

        private int mResourceId;
        private String mFilepath;
        private boolean isEditted;
        private String mFileEdittedpath;
        private boolean isFromFile;
        private Size mViewSize;
        private String thumbPath;

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

        public Builder setFromFile(Size viewSize) {
            mViewSize = viewSize;
            return this;
        }

        public Builder setThumbPath(String thumbPath) {
            thumbPath = thumbPath;
            return this;
        }

        public ImageCapture build() {
            ImageCapture imageCapture = new ImageCapture();
            imageCapture.mResourceId = mResourceId;
            imageCapture.mFilepath = mFilepath;
            imageCapture.isEditted = isEditted;
            imageCapture.mFileEdittedpath = mFileEdittedpath;
            imageCapture.isFromFile = isFromFile;
            imageCapture.mViewSize = mViewSize;
            imageCapture.thumbPath = thumbPath;
            return imageCapture;
        }
    }
}
