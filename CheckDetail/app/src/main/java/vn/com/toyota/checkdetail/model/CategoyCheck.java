package vn.com.toyota.checkdetail.model;

import vn.com.toyota.checkdetail.utils.GsonUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eo_cuong on 3/15/17.
 */

public class CategoyCheck {

    @SerializedName("no_stt")
    @Expose
    private int mNoStt;

    @SerializedName("name")
    @Expose
    private String mName;

    @SerializedName("specificities")
    @Expose
    private String mSpecificities;

    @SerializedName("image_default")
    @Expose
    private String imageDefaul;

    @SerializedName("position")
    @Expose
    private String position;

    public int getNoStt() {
        return mNoStt;
    }

    public void setNoStt(int noStt) {
        mNoStt = noStt;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<String> getSpecificities() {
        return GsonUtils.String2ListObject(mSpecificities, String[].class);
    }

    public void setSpecificities(List<String> specificities) {
        mSpecificities = GsonUtils.Object2String(specificities);
    }

    public String getImageDefaul() {
        return imageDefaul;
    }

    public void setImageDefaul(String imageDefaul) {
        this.imageDefaul = imageDefaul;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
