package vn.com.toyota.checkdetail.feature.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import vn.com.toyota.checkdetail.R;

public class MainV2Activity extends AppCompatActivity {
    private final String TAG = MainV2Activity.class.getName();

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainV2Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);

        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {

    }
}