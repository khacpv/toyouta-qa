package com.example.ngothi.checksheet.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.ngothi.checksheet.R;

public class paperSheetActivity extends AppCompatActivity {

    // TODO demo only. shoudl remove it
    public static boolean hasError = false;

    // TODO demo only. shoudl remove it
    public static boolean stepIsOk[] = {true, true, true};

    @BindView(R.id.lyInfo)
    LinearLayout layoutInfo;

    @BindView(R.id.p_check1)
    TextView pCheck1;

    @BindView(R.id.p_check2)
    TextView pCheck2;

    @BindView(R.id.p_check3)
    TextView pCheck3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_sheet);
        ButterKnife.bind(this);

        if (hasError) {
            layoutInfo.setBackgroundColor(Color.RED);
        } else {
            layoutInfo.setBackgroundColor(Color.YELLOW);
        }

        pCheck1.setText(stepIsOk[0] ? "OK" : "NG");
        pCheck2.setText(stepIsOk[1] ? "OK" : "NG");
        pCheck3.setText(stepIsOk[2] ? "OK" : "NG");

        pCheck1.setTextColor(stepIsOk[0] ? getResources().getColor(R.color.blue) : getResources().getColor(R.color.red));
        pCheck2.setTextColor(stepIsOk[1] ? getResources().getColor(R.color.blue) : getResources().getColor(R.color.red));
        pCheck3.setTextColor(stepIsOk[2] ? getResources().getColor(R.color.blue) : getResources().getColor(R.color.red));
    }

    @OnClick(R.id.xong1)
    public void xong(View v) {
        finish();
    }
}
