package vn.com.toyota.checkdetail.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class Utils {

    public static void setTypefaceText(TextView view, int typeface) {
        view.setTypeface(null, typeface);
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int resource = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = 0;
        if (resource > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resource);
        }
        return height - statusBarHeight;
    }
}
