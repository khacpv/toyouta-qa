package vn.com.toyota.checkdetail.model;

import com.activeandroid.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Do Hoang on 4/11/2017.
 */
public class ErrorPosition {
    private String code;

    public List<ErrorPart> errorParts;

    public ErrorPosition(String code, List<ErrorPart> errorParts) {
        this.code = code;
        this.errorParts = errorParts;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ErrorPart> getErrorParts() {
        return errorParts;
    }

    public void setErrorParts(List<ErrorPart> errorParts) {
        this.errorParts = errorParts;
    }
}
