package com.matching.tech.bio.utilities;

import android.util.Log;

import com.matching.tech.bio.BuildConfig;
public class LogUtils {
    public static void debug(final String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }
}
