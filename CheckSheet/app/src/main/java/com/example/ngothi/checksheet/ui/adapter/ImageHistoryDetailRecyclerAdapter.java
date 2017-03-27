package com.example.ngothi.checksheet.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.model.CategoryHistory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kienht on 3/16/17.
 */

public class ImageHistoryDetailRecyclerAdapter extends RecyclerView.Adapter<ImageHistoryDetailRecyclerAdapter.ViewHolder> {

    private Activity activity;
    private List<CategoryHistory.ImageError> imageErrorList;

    public ImageHistoryDetailRecyclerAdapter(Activity activity, List<CategoryHistory.ImageError> imageErrorList) {
        this.activity = activity;
        this.imageErrorList = imageErrorList;
    }

    @Override
    public ImageHistoryDetailRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_history_detail_list, parent, false);
        return new ImageHistoryDetailRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageHistoryDetailRecyclerAdapter.ViewHolder holder, int position) {
        holder.bindData(imageErrorList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageErrorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageview_error)
        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(CategoryHistory.ImageError imageErrorList) {

            Glide.with(activity).load(imageErrorList.getmUri()).fitCenter().into(mImageView);
        }
    }
}
