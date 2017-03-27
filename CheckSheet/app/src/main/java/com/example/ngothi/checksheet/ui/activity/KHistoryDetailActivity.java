package com.example.ngothi.checksheet.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.adapter.HistoryDetailRecyclerAdapter;
import com.example.ngothi.checksheet.ui.model.CategoryHistory;
import com.example.ngothi.checksheet.ui.model.History;
import com.example.ngothi.checksheet.ui.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by kienht on 3/14/17.
 */

public class KHistoryDetailActivity extends BaseActivity {

    @BindView(R.id.list)
    RecyclerView mRecyclerHistoryDetail;

    private HistoryDetailRecyclerAdapter historyDetailRecyclerAdapter;
    private List<CategoryHistory> historyDetailObjList = new ArrayList<>();

    private History historyObj;

    @Override
    public int getResourceLayout() {
        return R.layout.activity_history_detail;
    }

    @Override
    public void afterSetcontenview() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        historyObj = (History) intent.getSerializableExtra(Constants.HISTORY_OBJ);
        historyDetailObjList = historyObj.getCategoryHistories();
        setTitle(historyObj.getmNameCar());
        initRecycler();
    }

    private void initRecycler() {
        mRecyclerHistoryDetail.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerHistoryDetail.setHasFixedSize(true);

        historyDetailRecyclerAdapter = new HistoryDetailRecyclerAdapter(this, historyDetailObjList);
        mRecyclerHistoryDetail.setAdapter(historyDetailRecyclerAdapter);
    }
}
