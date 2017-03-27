package com.example.ngothi.checksheet.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ngothi.checksheet.R;
import com.example.ngothi.checksheet.ui.event.OnItemListener;
import com.example.ngothi.checksheet.ui.model.ImageCapture;
import java.io.File;
import java.util.List;

/**
 * Created by FRAMGIA\hoang.van.cuong on 14/03/2017.
 */

public class StepImageAdapter extends RecyclerView.Adapter<StepImageAdapter.StepImageViewHolder> {

    Context mContext;
    List<ImageCapture> mImageCaptures;
    OnItemListener<ImageCapture> mOnItemListener;

    private int selectedPos = 0;
    private int screenWidth;

    public StepImageAdapter(Context context, List<ImageCapture> imageCaptures,
            OnItemListener<ImageCapture> onItemListener) {
        mImageCaptures = imageCaptures;
        mContext = context;
        mOnItemListener = onItemListener;

        Display display = ((WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay();
        screenWidth = display.getWidth();
    }

    public void addImage(ImageCapture imageCapture) {
        mImageCaptures.add(imageCapture);
        selectedPos = mImageCaptures.size() - 1;
        notifyDataSetChanged();
    }

    public void clear() {
        mImageCaptures.clear();
        notifyDataSetChanged();
    }

    @Override
    public StepImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StepImageViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_image_square, null));
    }

    @Override
    public void onBindViewHolder(StepImageViewHolder holder, int position) {
        ImageCapture imageCapture = mImageCaptures.get(position);

        holder.mImageView.getLayoutParams().height = screenWidth / 3 - 20;
        holder.mImageView.requestLayout();

        if (imageCapture.getThumbPath() != null) {
            Glide.with(mContext)
                    .load(new File(imageCapture.getThumbPath()))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.mImageView);
        } else {
            Glide.with(mContext)
                    .load(imageCapture.isFromFile() ? new File(imageCapture.getFilepath())
                            : imageCapture.getResourceId())
                    .into(holder.mImageView);
            holder.mImageView.setSelected(selectedPos == position);
        }
    }

    public void setOnItemListener(OnItemListener<ImageCapture> onItemListener) {
        mOnItemListener = onItemListener;
    }

    @Override
    public int getItemCount() {
        return mImageCaptures.size();
    }

    class StepImageViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        public StepImageViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imagePhoto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemListener != null) {
                        mOnItemListener.onItemClick(mImageCaptures.get(getAdapterPosition()),
                                getAdapterPosition());
                        notifyItemChanged(selectedPos);
                        selectedPos = getAdapterPosition();
                        notifyItemChanged(selectedPos);
                    }
                }
            });
        }
    }
}
