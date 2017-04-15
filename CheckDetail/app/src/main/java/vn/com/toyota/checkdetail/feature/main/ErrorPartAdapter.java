package vn.com.toyota.checkdetail.feature.main;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.toyota.checkdetail.R;
import vn.com.toyota.checkdetail.model.ErrorPart;
import vn.com.toyota.checkdetail.utils.Utils;

public class ErrorPartAdapter extends RecyclerView.Adapter<ErrorPartAdapter.ViewHolder> {

    public interface ErrorPartListener {
        void errorPartItemOnClick(View view, ErrorPart errorPart);
    }

    private Context mContext;
    private List<ErrorPart> mList;
    private ErrorPartListener mErrorPartListener;

    public ErrorPartAdapter(Context context, List<ErrorPart> list, ErrorPartListener errorPartListener) {
        this.mContext = context;
        this.mList = list;
        this.mErrorPartListener = errorPartListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_error_part, parent, false);
        return new ErrorPartAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ErrorPart item = getItem(position);
        holder.tvErrorPartName.setText(item.getName());
        holder.tvErrorPartName.setSelected(item.isSelected());
        if (item.isSelected()) {
            Utils.setTypefaceText(holder.tvErrorPartName, Typeface.BOLD);
        } else {
            Utils.setTypefaceText(holder.tvErrorPartName, Typeface.NORMAL);
        }
        holder.tvErrorPartName.setEnabled(!item.getImgUrl().isEmpty());
        holder.tvErrorPartName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mErrorPartListener.errorPartItemOnClick(v, item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<ErrorPart> getList() {
        return this.mList;
    }

    public ErrorPart getItem(int position) {
        return mList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_error_part_name)
        TextView tvErrorPartName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
