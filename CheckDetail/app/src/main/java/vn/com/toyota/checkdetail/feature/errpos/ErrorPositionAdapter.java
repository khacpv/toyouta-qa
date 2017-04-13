package vn.com.toyota.checkdetail.feature.errpos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.toyota.checkdetail.R;
import vn.com.toyota.checkdetail.config.AppConfig;
import vn.com.toyota.checkdetail.model.ErrorPosition;
import vn.com.toyota.checkdetail.utils.Utils;

public class ErrorPositionAdapter extends RecyclerView.Adapter<ErrorPositionAdapter.ViewHolder> {

    private Context mContext;
    private List<ErrorPosition> mList;

    public ErrorPositionAdapter(Context context, List<ErrorPosition> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_error_position, parent, false);
        return new ErrorPositionAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ErrorPosition item = getItem(position);
        String code = item.getCode();
        holder.btnErrorPosition.setText(code);
        holder.btnErrorPosition.setClickable(!TextUtils.isEmpty(code));
        holder.btnErrorPosition.setLongClickable(!TextUtils.isEmpty(code));

        int height = Utils.getScreenHeight(mContext);
        holder.btnErrorPosition.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                height / AppConfig.ERROR_POSITION_ROW));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public ErrorPosition getItem(int position) {
        return mList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btn_error_position)
        Button btnErrorPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
