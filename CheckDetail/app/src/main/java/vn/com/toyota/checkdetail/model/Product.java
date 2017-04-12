package vn.com.toyota.checkdetail.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Do Hoang on 4/11/2017.
 */

public class Product {
    private String sequence;
    private String grade;
    private List<ErrorPosition> errorPositions;

    public Product(String sequence, String grade, List<ErrorPosition> errorPositions) {
        this.sequence = sequence;
        this.grade = grade;
        this.errorPositions = errorPositions;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<ErrorPosition> getErrorPositions() {
        return errorPositions;
    }

    public void setErrorPositions(List<ErrorPosition> errorPositions) {
        this.errorPositions = errorPositions;
    }
}