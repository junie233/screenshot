package com.junie.screenshot.util;

import android.content.res.Resources;

/**
 * Created by niejun on 2018/1/18.
 */

public class ScreenUtils {

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
