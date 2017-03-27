package com.example.ngothi.checksheet.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Path;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.Common;
import com.example.ngothi.checksheet.ui.activity.view.ImageDrawing;
import com.example.ngothi.checksheet.ui.adapter.GridSpacingItemDecoration;
import com.example.ngothi.checksheet.ui.adapter.StepImageAdapter;
import com.example.ngothi.checksheet.ui.event.OnItemListener;
import com.example.ngothi.checksheet.ui.model.CategoyCheck;
import com.example.ngothi.checksheet.ui.model.DrawEntityPath;
import com.example.ngothi.checksheet.ui.model.ImageCapture;
import com.example.ngothi.checksheet.ui.model.SettingModel;
import com.example.ngothi.checksheet.ui.model.Size;
import com.example.ngothi.checksheet.ui.model.Step;
import com.example.ngothi.checksheet.ui.utils.CanvasUtils;
import com.example.ngothi.checksheet.ui.utils.DrawableUtils;
import com.example.ngothi.checksheet.ui.utils.FileUtils;
import com.example.ngothi.checksheet.ui.utils.GsonUtils;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SheetActivity extends BaseActivity implements OnItemListener<ImageCapture> {

    public static final int REQUEST_CODE_EDIT = 1234;
    public static final int MAX = Integer.MAX_VALUE;

    @BindView(R.id.rcvImage)
    RecyclerView rcvImage;

    @BindView(R.id.tvCategory)
    TextView tvCategory;

    @BindView(R.id.tvStepCount)
    TextView tvStepCount;

    @BindView(R.id.inputNote)
    EditText inputNote;

    int REQUEST_ID_IMAGE_CAPTURE = 1000;
    StepImageAdapter mStepImageAdapter;
    List<ImageCapture> mImageCaptures = new ArrayList<>();

    private String mSequence = "1H00543";
    private String mGrade = "2403";
    private SettingModel mSettingModel;
    private int currentStep = 1;
    private int maxStep;
    private CategoyCheck currenCategory;

    private List<Step> mSteps = new ArrayList<>();
    private Step mStep;

    @Override
    public int getResourceLayout() {
        return R.layout.activity_sheetctivity;
    }

    @Override
    public void afterSetcontenview() {
        mStepImageAdapter = new StepImageAdapter(getApplicationContext(), mImageCaptures, this);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);

        rcvImage.setHasFixedSize(true);
        rcvImage.setLayoutManager(layoutManager);
        rcvImage.setAdapter(mStepImageAdapter);
        rcvImage.addItemDecoration(new GridSpacingItemDecoration(3, 20, true));

        initData();
    }

    private void initData() {
        fakeData();

        if (mSettingModel.getCategoyChecks().size() == 0) {
            return;
        }
        currentStep = 1;
        currenCategory = mSettingModel.getCategoyChecks().get(currentStep - 1);
        maxStep = mSettingModel.getCategoyChecks().size();
        mStep = new Step();
        mStep.setNoNumber(currentStep);
        mStep.setCategoyCheck(currenCategory);
        refreshStepView();
        refreshCurrentCategory();
    }

    private void refreshCurrentCategory() {
        inputNote.setText("");
        tvCategory.setText(currenCategory.getName());
        if (currenCategory.getImageDefaul() != null) {
            mStepImageAdapter.clear();
            mStepImageAdapter.addImage(new ImageCapture.Builder().setFromFile(false)
                    .setResourceId(DrawableUtils.getResourceIdFromName(getApplicationContext(),
                            currenCategory.getImageDefaul()))
                    .build());
        }
    }

    private void refreshStepView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String step =
                        String.format(getResources().getString(R.string.step_by_step), currentStep,
                                maxStep);
                tvStepCount.setText(step);
            }
        });
    }

    private void fakeData() {

        CategoyCheck categoyCheck1 = new CategoyCheck();
        categoyCheck1.setNoStt(0);
        categoyCheck1.setName("Kiem tra lop ");
        categoyCheck1.setSpecificities(Arrays.asList("xanh", "dep"));
        categoyCheck1.setImageDefaul("lopoto");

        CategoyCheck categoyCheck2 = new CategoyCheck();
        categoyCheck2.setNoStt(1);
        categoyCheck2.setName("Kiem tra canh cua");
        categoyCheck2.setSpecificities(Arrays.asList("to", "vuong"));
        categoyCheck2.setImageDefaul("canhcua");

        CategoyCheck categoyCheck3 = new CategoyCheck();
        categoyCheck3.setNoStt(2);
        categoyCheck3.setName("Kiem tra den pha");
        categoyCheck3.setSpecificities(Arrays.asList("sang ", "trang"));
        categoyCheck3.setImageDefaul("denpha");

        mSettingModel = new SettingModel();
        mSettingModel.setCarModel("2403");
        mSettingModel.setCarModelName("INNOVA HA");

        List<CategoyCheck> categoyChecks = new ArrayList<>();
        categoyChecks.add(categoyCheck1);
        categoyChecks.add(categoyCheck2);
        categoyChecks.add(categoyCheck3);
        mSettingModel.setCategoyChecks(categoyChecks);
    }

    private void onStepCheckDone(boolean isGood) {

        if (mStep != null) {
            mStep.setGood(isGood);
            mStep.setNoteReality(inputNote.getText().toString());
            mStep.setImageList(mImageCaptures);
            mSteps.add(mStep);
        }

        if (currentStep == maxStep) {
            Intent intent = new Intent(SheetActivity.this, ResultSheetActivity.class);
            intent.putExtra(Common.BundleConstant.LIST_STEP, GsonUtils.Object2String(mSteps));
            intent.putExtra(Common.BundleConstant.GRADE, mGrade);
            intent.putExtra(Common.BundleConstant.SEQUENCE, mSequence);
            startActivity(intent);
            finish();
            return;
        }

        currentStep++;
        currenCategory = mSettingModel.getCategoyChecks().get(currentStep - 1);
        mStep = new Step();
        mStep.setNoNumber(currentStep);
        mStep.setCategoyCheck(currenCategory);
        refreshStepView();
        refreshCurrentCategory();
    }

    public void okClick(View v) {
        onStepCheckDone(true);
    }

    public void notGoodClick(View v) {
        onStepCheckDone(false);
    }

    public void captureClick(View v) {
        Intent intent = new Intent(SheetActivity.this, EditImageActivity.class);
        intent.putExtra(Common.BundleConstant.IMAGE_CAPTURE, "");
        intent.putExtra(Common.BundleConstant.POSITION, MAX);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
                true);
    }

    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT) {
            if (resultCode == RESULT_OK) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {

                        Bundle bundle = data.getExtras();

                        if (bundle == null) {
                            return;
                        }

                        ImageCapture imageCapture = GsonUtils.String2Object(
                                bundle.getString(Common.BundleConstant.IMAGE_CAPTURE),
                                ImageCapture.class);
                        int position = bundle.getInt(Common.BundleConstant.POSITION);
                        if (position == MAX) {
                            mStepImageAdapter.addImage(imageCapture);
                        } else {
                            mImageCaptures.set(position, imageCapture);
                            rcvImage.scrollToPosition(position);
                        }

                        mStepImageAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override
    public void onItemClick(ImageCapture item, int position) {
        Intent intent = new Intent(SheetActivity.this, EditImageActivity.class);
        intent.putExtra(Common.BundleConstant.IMAGE_CAPTURE, GsonUtils.Object2String(item));
        intent.putExtra(Common.BundleConstant.POSITION, position);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }
}