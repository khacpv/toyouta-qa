package com.example.ngothi.checksheet.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.Common;
import com.example.ngothi.checksheet.ui.adapter.ResultSheetAdapter;
import com.example.ngothi.checksheet.ui.model.Step;
import com.example.ngothi.checksheet.ui.utils.GsonUtils;
import java.util.List;

public class ResultSheetActivity extends BaseActivity {

    @BindView(R.id.tvGrade)
    TextView tvGrade;

    @BindView(R.id.tvSequence)
    TextView tvSequence;

    @BindView(R.id.rcvResult)
    RecyclerView rcvResult;

    private List<Step> mSteps;

    private ResultSheetAdapter mResultSheetAdapter;

    @Override
    public int getResourceLayout() {
        return R.layout.activity_result_sheet;
    }

    @Override
    public void afterSetcontenview() {

        Bundle bundle = getIntent().getExtras();
        String json = bundle.getString(Common.BundleConstant.LIST_STEP, null);
        if (TextUtils.isEmpty(json)) {
            Toast.makeText(getApplicationContext(), "An error occurs", Toast.LENGTH_SHORT).show();
            return;
        }

        tvGrade.setText("GRADE: " + bundle.getString(Common.BundleConstant.GRADE, ""));
        tvSequence.setText("SEQUENCE: " + bundle.getString(Common.BundleConstant.SEQUENCE, ""));

        mSteps = GsonUtils.String2ListObject(json, Step[].class);
        mResultSheetAdapter = new ResultSheetAdapter(getApplicationContext(), mSteps);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rcvResult.setHasFixedSize(true);
        rcvResult.setLayoutManager(layoutManager);
        rcvResult.setAdapter(mResultSheetAdapter);
    }

    @OnClick(R.id.btnDone)
    public void btnDoneClick(View v) {
        finish();
    }
}
