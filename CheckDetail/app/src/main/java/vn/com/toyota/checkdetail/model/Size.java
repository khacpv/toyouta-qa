package vn.com.toyota.checkdetail.model;

import com.google.gson.annotations.Expose;

/**
 * Created by eo_cuong on 3/16/17.
 */

public class Size {
    @Expose
    private int width;
    @Expose
    private int height;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }
}

