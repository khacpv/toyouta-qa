package vn.com.toyota.checkdetail.model;

import com.activeandroid.Model;

/**
 * Created by Do Hoang on 4/11/2017.
 */
public class Error {
    private String code;
    private float x;
    private float y;
    private String imgGuideUrl;

    public Error(String code, float x, float y, String imgGuideUrl) {
        this.code = code;
        this.x = x;
        this.y = y;
        this.imgGuideUrl = imgGuideUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getImgGuideUrl() {
        return imgGuideUrl;
    }

    public void setImgGuideUrl(String imgGuideUrl) {
        this.imgGuideUrl = imgGuideUrl;
    }
}
