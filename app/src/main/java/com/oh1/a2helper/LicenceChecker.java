package com.oh1.a2helper;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import java.util.List;

public class LicenceChecker {

    private static LicenceChecker instance;
    private final PackageManager packageManager;
    private final int tabColor;

    public static String URL = "https://play.google.com/store/apps/details?id=com.oh1.unlocker";

    public static LicenceChecker getInstance(Context context) {

        if (instance == null) instance = new LicenceChecker(context);

        return instance;

    }

    private LicenceChecker(Context context) {

        packageManager = context.getPackageManager();
        tabColor = ContextCompat.getColor(context,  R.color.colorPrimary);

    }

    public boolean isKeyInstalled() {

        String keyPackage = "com.oh1.unlocker";
        String installer = "com.android.vending";

        List<ApplicationInfo> packages = packageManager.getInstalledApplications(0);

        for (ApplicationInfo packageInfo : packages) {

            Log.i("KEYINST", packageInfo.packageName);

            if (packageInfo.packageName.equals(keyPackage)) {

                return true;
             //   String installerName = packageManager.getInstallerPackageName(packageInfo.packageName);


              //  if (installerName != null && installerName.equals(installer))
            }

        }

        return false;
    }

    public void gotoPlayStore(Context context) {



        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(tabColor);
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(URL));
    }
}
