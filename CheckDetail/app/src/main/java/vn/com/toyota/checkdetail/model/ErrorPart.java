package vn.com.toyota.checkdetail.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Do Hoang on 4/11/2017.
 */
public class ErrorPart {
    private String name;
    private String imgUrl;
    private List<Error> errors;
    private List<ErrorPixel> errorPixels;
    private boolean selected;

    public ErrorPart(String name, String imgUrl, List<Error> errors) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.errors = errors;
        this.errorPixels = new ArrayList<>();
        this.selected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<ErrorPixel> getErrorPixels() {
        return errorPixels;
    }

    public void setErrorPixels(List<ErrorPixel> errorPixels) {
        this.errorPixels = errorPixels;
    }
}
