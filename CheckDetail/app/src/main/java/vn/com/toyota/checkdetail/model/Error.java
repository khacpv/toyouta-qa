package vn.com.toyota.checkdetail.model;

/**
 * Created by Do Hoang on 4/11/2017.
 */
public class Error {
    private String code;
    private String imgGuideUrl;
    private boolean selected;

    public Error(String code, String imgGuideUrl) {
        this.code = code;
        this.imgGuideUrl = imgGuideUrl;
        this.selected = false;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
