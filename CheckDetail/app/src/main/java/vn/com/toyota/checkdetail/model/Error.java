package vn.com.toyota.checkdetail.model;

import com.activeandroid.Model;

/**
 * Created by Do Hoang on 4/11/2017.
 */
public class Error {
    private String code;
    private String imgGuideUrl;

    public Error(String code, String imgGuideUrl) {
        this.code = code;
        this.imgGuideUrl = imgGuideUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImgGuideUrl() {
        return imgGuideUrl;
    }

    public void setImgGuideUrl(String imgGuideUrl) {
        this.imgGuideUrl = imgGuideUrl;
    }
}
