package vn.com.toyota.checkdetail.feature;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

/**
 * Created by kienht on 3/14/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();

    public Context mContext;
    public KProgressHUD loadingHUD;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResourceLayout());
        ButterKnife.bind(this);

        mContext = this;

        loadingHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        afterSetcontenview();
    }

    public abstract int getResourceLayout();
    public abstract void afterSetcontenview();

    public void showToastSuccess(String text) {
        Toasty.success(mContext, text, Toast.LENGTH_SHORT, true).show();
    }

    public void showToastError(String text) {
        Toasty.error(mContext, text, Toast.LENGTH_SHORT, true).show();
    }

    public void showToastInfo(String text) {
        Toasty.info(mContext, text, Toast.LENGTH_SHORT, true).show();
    }

    public void showToastSuccess(int resourceId) {
        Toasty.success(mContext, getString(resourceId), Toast.LENGTH_SHORT, true).show();
    }

    public void showToastError(int resourceId) {
        Toasty.error(mContext, getString(resourceId), Toast.LENGTH_SHORT, true).show();
    }

    public void showToastInfo(int resourceId) {
        Toasty.info(mContext, getString(resourceId), Toast.LENGTH_SHORT, true).show();
    }

    public void showToastWarning(String text) {
        Toasty.warning(mContext, text, Toast.LENGTH_SHORT, true).show();
    }

    public void showToastWarning(int resourceId) {
        Toasty.warning(mContext, getString(resourceId), Toast.LENGTH_SHORT, true).show();
    }

    public void showHUD() {
        loadingHUD.setLabel(null);
        if (!loadingHUD.isShowing()) loadingHUD.show();
    }

    public void showHUD(String label) {
        loadingHUD.setLabel(label);
        if (!loadingHUD.isShowing()) loadingHUD.show();
    }

    public void dismissHUD() {
        if (loadingHUD.isShowing()) loadingHUD.dismiss();
    }
}
