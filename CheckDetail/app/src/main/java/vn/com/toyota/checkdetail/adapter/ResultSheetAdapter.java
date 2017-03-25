package vn.com.toyota.checkdetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.com.toyota.checkdetail.R;
import vn.com.toyota.checkdetail.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by eo_cuong on 3/16/17.
 */

public class ResultSheetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<Step> mSteps;

    public ResultSheetAdapter(Context context, List<Step> steps) {
        mContext = context;
        mSteps = steps;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == 0) {
            return new ResultHeaderViewHolder(
                    inflater.inflate(R.layout.item_header_result_sheet, parent, false));
        }

        return new ResultSheetViewHolder(
                inflater.inflate(R.layout.item_result_sheet, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ResultSheetViewHolder) {
            ((ResultSheetViewHolder) holder).bindData(mSteps.get(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        return mSteps.size() + 1;
    }

    public class ResultHeaderViewHolder extends RecyclerView.ViewHolder {

        public ResultHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ResultSheetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textCategory)
        TextView textCategory;

        @BindView(R.id.textResult)
        TextView textResult;

        public ResultSheetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(Step step) {
            textCategory.setText(step.getCategoyCheck().getName());
            if (step.isGood()) {
                textResult.setText("OK");
                textResult.setTextColor(mContext.getResources().getColor(R.color.green));
            } else {
                textResult.setText("NG");
                textResult.setTextColor(mContext.getResources().getColor(R.color.red));
            }
        }
    }
}
