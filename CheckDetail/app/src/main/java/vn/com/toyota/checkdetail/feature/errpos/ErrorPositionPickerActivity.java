package vn.com.toyota.checkdetail.feature.errpos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.toyota.checkdetail.R;
import vn.com.toyota.checkdetail.config.AppConfig;
import vn.com.toyota.checkdetail.listener.RecyclerTouchListener;
import vn.com.toyota.checkdetail.model.ErrorPosition;
import vn.com.toyota.checkdetail.utils.DataUtils;
import vn.com.toyota.checkdetail.view.SpaceItemDecoration;

public class ErrorPositionPickerActivity extends AppCompatActivity
        implements RecyclerTouchListener.ClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_position_picker);

        ButterKnife.bind(this);
        initViews();
        showErrorPositionList();
    }

    @BindView(R.id.rv_error_position)
    RecyclerView mRecyclerView;

    private void initViews() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, AppConfig.ERROR_POSITION_COL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, mRecyclerView, this));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(0, 0));
    }

    @Override
    public void onClick(View view, int position) {
        if (mAdapter == null) {
            return;
        }
        ErrorPosition item = mAdapter.getItem(position);
        if(!TextUtils.isEmpty(item.getCode())) {
            Toast.makeText(this, item.getCode(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    private ErrorPositionAdapter mAdapter;

    private void showErrorPositionList() {
        List<ErrorPosition> list = DataUtils.getErrorPositionList();
        mAdapter = new ErrorPositionAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);
    }
}
