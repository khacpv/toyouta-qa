package com.example.ngothi.checksheet.ui.utils;

import android.content.Context;

/**
 * Created by eo_cuong on 3/15/17.
 */

public class DrawableUtils {

    public static int getResourceIdFromName(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
}
