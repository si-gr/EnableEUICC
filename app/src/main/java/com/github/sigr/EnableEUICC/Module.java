package com.github.sigr.EnableEUICC;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedBridge;

public class Module implements IXposedHookLoadPackage {
    private static final String TAG = "xposedModule";
    private final static String DEFAULT_CARRIER_APP_PACKAGE="com.github.sigr.EnableEUICC.uiccservice";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        findAndHookMethod("android.telephony.euicc.EuiccManager", lpparam.classLoader, "isEnabled",
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("Called isEnabled ");
                        // Modify behavior to always return true
                        return true;
                    }
                }
        );
        XposedBridge.log("UICC privileges bypass installed on "+lpparam.packageName);
    }

}
