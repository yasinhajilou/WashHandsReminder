package com.yasinhajiloo.washhandsreminder.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

import com.yasinhajiloo.washhandsreminder.BootReceiver;

public class BootHandler {
    private ComponentName mComponentName;
    private PackageManager mPackageManager;

    public BootHandler(Context context) {
        mComponentName = new ComponentName(context, BootReceiver.class);
        mPackageManager = context.getPackageManager();
    }

    public void disableReceiver() {
        mPackageManager.setComponentEnabledSetting(mComponentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void enableReceiver() {
        mPackageManager.setComponentEnabledSetting(mComponentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
}
