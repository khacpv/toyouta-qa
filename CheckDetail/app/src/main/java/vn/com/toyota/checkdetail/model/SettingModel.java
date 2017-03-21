package vn.com.toyota.checkdetail.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import vn.com.toyota.checkdetail.utils.GsonUtils;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by eo_cuong on 3/15/17.
 */

@Table(name = "SettingModel")
public class SettingModel extends Model {

    public SettingModel() {
        super();
    }

    @Expose
    @Column(name = "car_model")
    private String mCarModel;

    @Expose
    @Column(name = "car_model_name")
    private String mCarModelName;

    @Expose
    @Column(name = "category_check")
    private String mCategoyChecks;

    public String getCarModel() {
        return mCarModel;
    }

    public void setCarModel(String carModel) {
        mCarModel = carModel;
    }

    public List<CategoyCheck> getCategoyChecks() {
        return GsonUtils.String2ListObject(mCategoyChecks, CategoyCheck[].class);
    }

    public void setCategoyChecks(List<CategoyCheck> categoyChecks) {
        mCategoyChecks = GsonUtils.Object2String(categoyChecks);
    }

    public String getCarModelName() {
        return mCarModelName;
    }

    public void setCarModelName(String carModelName) {
        mCarModelName = carModelName;
    }

    public static SettingModel getSettingByModel(String model) {
        return new Select().from(SettingModel.class).where("car_model = ?", model).executeSingle();
    }
}
