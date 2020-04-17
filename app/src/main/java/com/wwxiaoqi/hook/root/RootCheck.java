package com.wwxiaoqi.hook.root;

import android.os.Build;
import com.wwxiaoqi.hook.utils.AdbShell;
import java.io.File;

public class RootCheck {
  private static final String[] suFiles = new String[] { 
    "/su", "/su/bin/su",
    "/sbin/su", "/data/local/xbin/su",
    "/data/local/bin/su", "/data/local/su",
    "/system/xbin/su", "/system/bin/su",
    "/system/sd/xbin/su", "/system/bin/failsafe/su",
    "/system/bin/cufsdosck", "/system/xbin/cufsdosck",
    "/system/bin/cufsmgr", "/system/xbin/cufsmgr",
    "/system/bin/cufaevdd", "/system/xbin/cufaevdd",
    "/system/bin/conbb", "/system/xbin/conbb"
  };

  public static boolean haveSu() {
    boolean z = false;
    boolean z2 = false;
    
    for (String file : suFiles) {
      if (new File(file).exists()) {
        z = true;
        break;
      }
    }

    if (Build.TAGS == null || !Build.TAGS.contains("test-keys")) {
      z2 = false;
    } else {
      z2 = true;
    }
    return z2 || z;
  }

  public static String RootCheckProp() {
    // ro.secure 表示 root 权限
    // 如果为 0 则表示启用 root 权限，1 则相反
    // 这个只能检测 ROM 被刷入时的默认属性
    StringBuilder builder = new StringBuilder();
    builder.append("ro.secure:");
    builder.append(AdbShell.getprop("ro.secure"));
    builder.append("\n");
    builder.append("ro.adb.secure:");
    builder.append(AdbShell.getprop("ro.adb.secure"));
    builder.append("\n");
    return builder.toString();
  }

}
