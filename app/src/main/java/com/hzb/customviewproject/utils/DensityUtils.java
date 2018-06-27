package com.hzb.customviewproject.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by huangzhibo on 2018/5/1/001.
 * mail:1043202454@qq.com
 */
public class DensityUtils {
    /**
     * dp转px
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

}
