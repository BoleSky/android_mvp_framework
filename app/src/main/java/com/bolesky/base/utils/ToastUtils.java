package com.bolesky.base.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.bolesky.base.SportsApplication;

/**
 * Created by xiaoyong.cui
 * on 2016/9/27
 * E-Mail:hellocui@aliyun.com
 */

/**
 * Toast 工具类
 */
public class ToastUtils {
    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (TextUtils.isEmpty(message)) {
            message = "";
        }
        if (isShow)
            Toast.makeText(SportsApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (TextUtils.isEmpty(message)) {
            message = "";
        }
        if (isShow)
            Toast.makeText(SportsApplication.getInstance(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void showCustom(CharSequence message, int duration) {
        if (TextUtils.isEmpty(message)) {
            message = "";
        }
        if (isShow)
            Toast.makeText(SportsApplication.getInstance(), message, duration).show();
    }
}
