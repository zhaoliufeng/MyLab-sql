package com.ws.mesh.mylab.frame;

import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.List;

/**
 * Log 日志
 */
public class ZLog {

    private static final String TAG = "ZLog";

    private static boolean DEBUG = true;

    public static void info(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static String printList(List<?> objects) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : objects) {
            sb.append("index -> ")
                    .append(objects.indexOf(obj))
                    .append(" Name -> ")
                    .append(obj.getClass().getSimpleName())
                    .append("\n");
        }
        return sb.toString();
    }
}
