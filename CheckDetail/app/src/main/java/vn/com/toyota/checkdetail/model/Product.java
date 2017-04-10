package vn.com.toyota.checkdetail.model;

/**
 * Created by Do Hoang on 4/11/2017.
 */

public class Product {
    private String sequence;
    private String grade;

    public Product(String sequence, String grade) {
        this.sequence = sequence;
        this.grade = grade;
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
}
