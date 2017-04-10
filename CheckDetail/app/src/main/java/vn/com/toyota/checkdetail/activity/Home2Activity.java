package vn.com.toyota.checkdetail.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.toyota.checkdetail.R;

public class Home2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        ButterKnife.bind(this);

        showImage();
    }

    @BindView(R.id.iv_car_part)
    SubsamplingScaleImageView ivCarPart;

    private void showImage() {
        ivCarPart.setImage(ImageSource.resource(R.drawable.toyota_innova));
    }
}
