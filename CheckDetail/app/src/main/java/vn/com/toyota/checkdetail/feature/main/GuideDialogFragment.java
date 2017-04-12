package vn.com.toyota.checkdetail.feature.main;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.toyota.checkdetail.R;
import vn.com.toyota.checkdetail.model.Error;
import vn.com.toyota.checkdetail.utils.DialogUtils;

public class GuideDialogFragment extends DialogFragment {

    public static final String TAG = GuideDialogFragment.class.getName();

    public GuideDialogFragment() {
    }

    public void onCreate(Bundle state) {
        super.onCreate(state);
        setRetainInstance(true);
    }

    private Error mError;

    public static GuideDialogFragment newInstance(Error error) {
        GuideDialogFragment fragment = new GuideDialogFragment();
        fragment.mError = error;
        return fragment;
    }

    private Dialog mDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog = DialogUtils.createDialog(getContext(), R.layout.dialog_guide);
        setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        ButterKnife.bind(this, mDialog);
        initViews();
        return mDialog;
    }

    @BindView(R.id.iv_guide)
    ImageView ivGuide;

    public void initViews() {
        Glide.with(this)
                .load(mError.getImgGuideUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(ivGuide);
    }

}
