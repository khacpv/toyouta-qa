package vn.com.toyota.checkdetail.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int mVerticalSpaceHeight;
    private final int mVerticalSpaceWidth;

    public SpaceItemDecoration(int verticalSpaceHeight, int verticalSpaceWidth) {
        this.mVerticalSpaceHeight = verticalSpaceHeight;
        this.mVerticalSpaceWidth = verticalSpaceWidth;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = mVerticalSpaceHeight;
            outRect.top = mVerticalSpaceHeight;
            outRect.right = mVerticalSpaceWidth;
            outRect.left = mVerticalSpaceWidth;
        }
    }
}
