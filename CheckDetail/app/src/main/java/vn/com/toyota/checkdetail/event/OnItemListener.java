package vn.com.toyota.checkdetail.event;

/**
 * Created by FRAMGIA\hoang.van.cuong on 14/03/2017.
 */

public interface OnItemListener<T> {
    void onItemClick(T item, int position);
}
