package com.example.ngothi.checksheet.ui.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.model.HistoryDetailObj;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kienht on 3/16/17.
 */

public class HistoryDetailRecyclerAdapter extends RecyclerView.Adapter<HistoryDetailRecyclerAdapter.ViewHolder> {

    private Activity activity;
    private List<HistoryDetailObj> historyDetailObjList;

    public HistoryDetailRecyclerAdapter(Activity activity, List<HistoryDetailObj> historyDetailObjList) {
        this.activity = activity;
        this.historyDetailObjList = historyDetailObjList;
    }

    @Override
    public HistoryDetailRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_detail_list, parent, false);
        return new HistoryDetailRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryDetailRecyclerAdapter.ViewHolder holder, int position) {
        holder.bindData(historyDetailObjList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return historyDetailObjList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_number_step)
        TextView mTextViewNumberStep;

        @BindView(R.id.textview_number_error)
        TextView mTextViewNumberError;

        @BindView(R.id.list)
        RecyclerView mRecyclerView;

        ImageHistoryDetailRecyclerAdapter imageHistoryDetailRecyclerAdapter;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mRecyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
        }

        public void bindData(HistoryDetailObj historyDetailObj, int position) {

            mTextViewNumberStep.setText(activity.getResources().getString(R.string.number_step, position + 1, historyDetailObj.getmNumberStep()));

            if (historyDetailObj.getImageErrorsList().size() == 0) {
                mTextViewNumberError.setTextColor(Color.BLACK);
            }
            mTextViewNumberError.setText(activity.getResources().getString(R.string.number_error, historyDetailObj.getImageErrorsList().size()));

            imageHistoryDetailRecyclerAdapter = new ImageHistoryDetailRecyclerAdapter(activity, historyDetailObj.getImageErrorsList());
            mRecyclerView.setAdapter(imageHistoryDetailRecyclerAdapter);

        }
    }
}
