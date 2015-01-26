package com.getnerdify.android.notifier.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.getnerdify.android.notifier.R;

public class UIUtils {

    private static final int[] RES_IDS_ACTION_BAR_SIZE = {
        R.attr.actionBarSize
    };

    /** Calculates the Action Bar height in pixels. */
    public static int calculateActionBarSize(Context context) {
        if (context == null) {
            return 0;
        }

        Resources.Theme curTheme = context.getTheme();
        if (curTheme == null) {
            return 0;
        }

        TypedArray att = curTheme.obtainStyledAttributes(RES_IDS_ACTION_BAR_SIZE);
        if (att == null) {
            return 0;
        }

        float size = att.getDimension(0, 0);
        att.recycle();

        return (int) size;
    }

}
