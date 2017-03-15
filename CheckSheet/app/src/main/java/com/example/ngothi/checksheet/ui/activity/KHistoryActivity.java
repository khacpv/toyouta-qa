package com.example.ngothi.checksheet.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.adapter.HistoryRecyclerAdapter;
import com.example.ngothi.checksheet.ui.model.HistoryDetailObj;
import com.example.ngothi.checksheet.ui.model.HistoryObj;
import com.example.ngothi.checksheet.ui.event.RecyclerItemClickListener;
import com.example.ngothi.checksheet.ui.utils.Constants;

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

        final List<HistoryDetailObj> historyDetailObjList = new ArrayList<>();
        List<HistoryDetailObj.ImageError> imageErrorArrayList = new ArrayList<>();

        //Fake data
        imageErrorArrayList.add(new HistoryDetailObj().new ImageError(R.drawable.lopoto));
        imageErrorArrayList.add(new HistoryDetailObj().new ImageError(R.drawable.lopoto));
        imageErrorArrayList.add(new HistoryDetailObj().new ImageError(R.drawable.lopoto));
        imageErrorArrayList.add(new HistoryDetailObj().new ImageError(R.drawable.lopoto));
        imageErrorArrayList.add(new HistoryDetailObj().new ImageError(R.drawable.lopoto));

        historyDetailObjList.add(new HistoryDetailObj("Bánh xe", imageErrorArrayList));
        historyDetailObjList.add(new HistoryDetailObj("Lốp xe", imageErrorArrayList));

        historyObjList.add(new HistoryObj("Ferrari", true, "#23598", "17:06 12/3/2017"
                , "12 bước: bánh xe, thân xe, vô lăng, lốp, khung, sường, mui", historyDetailObjList));

        historyObjList.add(new HistoryObj("Lamborghini", false, "#23598", "17:06 12/3/2017"
                , "12 bước: bánh xe, thân xe, vô lăng, lốp, khung, sường, mui", historyDetailObjList));
        historyRecyclerAdapter = new HistoryRecyclerAdapter(this, historyObjList);

        mRecyclerHistory.setAdapter(historyRecyclerAdapter);

        mRecyclerHistory.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                moveHistoryDetail(historyObjList.get(position));
            }
        }));
    }

    private void moveHistoryDetail(HistoryObj historyObj) {
        Intent intent = new Intent(KHistoryActivity.this, KHistoryDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.HISTORY_OBJ, historyObj);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
