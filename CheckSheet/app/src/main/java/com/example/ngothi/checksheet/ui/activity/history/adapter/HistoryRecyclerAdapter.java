package com.example.ngothi.checksheet.ui.activity.history.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.activity.model.HistoryObj;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kienht on 3/14/17.
 */

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> {

    Activity activity;
    List<HistoryObj> mHistoryObjList;

    public HistoryRecyclerAdapter(Activity activity, List<HistoryObj> mHistoryObjList) {
        this.activity = activity;
        this.mHistoryObjList = mHistoryObjList;
    }

    @Override
    public HistoryRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryRecyclerAdapter.ViewHolder holder, int position) {
        holder.bindData(mHistoryObjList.get(position));
    }

    @Override
    public int getItemCount() {
        return mHistoryObjList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageview_status)
        ImageView mImageViewStatus;

        @BindView(R.id.textview_barcode_number)
        TextView mTextViewBarcodeNumber;

        @BindView(R.id.textview_time_scan)
        TextView mTextViewTimeScan;

        @BindView(R.id.textview_detail)
        TextView mTextViewDetail;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(HistoryObj historyObj) {
            Glide.with(activity)
                    .load(historyObj.ismStatus() ? R.drawable.yes : R.drawable.no)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mImageViewStatus);
            mTextViewBarcodeNumber.setText(historyObj.getmBarcodeNumber());
            mTextViewTimeScan.setText(historyObj.getmTimeScan());
            mTextViewDetail.setText(historyObj.getmDetail());

        }
    }
}
