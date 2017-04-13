package vn.com.toyota.checkdetail.model;

/**
 * Created by Dichung on 4/13/2017.
 */

public class ErrorPixel {
    private float x;
    private float y;
    private String imageUrl;

    public ErrorPixel(float x, float y) {
        this.x = x;
        this.y = y;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
