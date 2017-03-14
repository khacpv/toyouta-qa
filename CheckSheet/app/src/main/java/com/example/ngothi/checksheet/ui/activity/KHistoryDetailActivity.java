package com.example.ngothi.checksheet.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.ngothi.checksheet.R;


/**
 * Created by kienht on 3/14/17.
 */

public class KHistoryDetailActivity extends BaseActivity {

    RecyclerView mRecyclerBanhXe;
    RecyclerView mRecyclerThanXe;
    RecyclerView mRecyclerVoLang;
    RecyclerView mRecyclerLop;
    RecyclerView mRecyclerKhung;
    RecyclerView mRecyclerSuon;
    RecyclerView mRecyclerMui;

    @Override
    public int getResourceLayout() {
        return R.layout.activity_history_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
//        mRecyclerBanhXe = (RecyclerView) findViewById(R.id.layout_banhxe).findViewById(R.id.list);
//        mRecyclerThanXe = (RecyclerView) findViewById(R.id.layout_thanxe).findViewById(R.id.list);
//        mRecyclerVoLang = (RecyclerView) findViewById(R.id.layout_volang).findViewById(R.id.list);
//        mRecyclerLop = (RecyclerView) findViewById(R.id.layout_lop).findViewById(R.id.list);
//        mRecyclerKhung = (RecyclerView) findViewById(R.id.layout_khung).findViewById(R.id.list);
//        mRecyclerSuon = (RecyclerView) findViewById(R.id.layout_suon).findViewById(R.id.list);
//        mRecyclerMui = (RecyclerView) findViewById(R.id.layout_mui).findViewById(R.id.list);

    }
}
