package com.example.ngothi.checksheet.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.activity.BaseActivity;
import com.example.ngothi.checksheet.ui.adapter.HistoryRecyclerAdapter;
import com.example.ngothi.checksheet.ui.model.HistoryObj;
import com.example.ngothi.checksheet.ui.event.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by kienht on 3/14/17.
 */

public class KHistoryActivity extends BaseActivity {

    @BindView(R.id.list)
    RecyclerView mRecyclerHistory;

    HistoryRecyclerAdapter historyRecyclerAdapter;
    List<HistoryObj> historyObjList = new ArrayList<>();

    @Override
    public int getResourceLayout() {
        return R.layout.activity_khistory;
    }

    @Override
    public void afterSetcontenview() {
        initRecycler();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initRecycler() {
        mRecyclerHistory.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerHistory.setHasFixedSize(true);

        historyObjList.add(new HistoryObj(true, "#23598", "17:06 12/3/2017", "12 bước: bánh xe, thân xe, vô lăng, lốp, khung, sường, mui"));
        historyObjList.add(new HistoryObj(false, "#23598", "17:06 12/3/2017", "12 bước: bánh xe, thân xe, vô lăng, lốp, khung, sường, mui"));
        historyRecyclerAdapter = new HistoryRecyclerAdapter(this, historyObjList);

        mRecyclerHistory.setAdapter(historyRecyclerAdapter);

        mRecyclerHistory.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                showToastInfo("History: " + position);

            }
        }));
    }
}
