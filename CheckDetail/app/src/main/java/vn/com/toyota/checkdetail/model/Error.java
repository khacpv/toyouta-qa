package vn.com.toyota.checkdetail.model;

/**
 * Created by Do Hoang on 4/11/2017.
 */
public class Error {
    private String code;
    private boolean selected;

    public Error(String code) {
        this.code = code;
        this.selected = false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
