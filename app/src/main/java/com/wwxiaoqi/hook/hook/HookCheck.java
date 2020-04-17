package com.wwxiaoqi.hook.hook;

import android.content.Context;
import android.content.pm.PackageManager;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;

public class HookCheck {
  public static Context Mcontext;
  
  public static int checkActivityManagerNative() {
    try {
      Method method = Class.forName("android.app.ActivityManagerNative").getMethod("getDefault", new Class[0]);
      method.setAccessible(true);
      if (method.invoke(null, new Object[0]).getClass().getName().startsWith("$Proxy")) {
        return 256;
      }
      return 0;
    } catch (Exception e) {
      return 256;
    }
  }

  public static int checkPackage(Context context) {
    int i = 0;
    PackageManager packageManager = context.getPackageManager();
    try {
      packageManager.getInstallerPackageName("de.robv.android.xposed.installer");
      i = 1;
    } catch (Exception e) {
    }
    try {
      packageManager.getInstallerPackageName("com.saurik.substrate");
      return i | 2;
    } catch (Exception e2) {
      return i;
    }
  }

  public static int checkStackTraceElement() {
    int i = 0;
    try {
      throw new Exception("detect hook");
    } catch (Exception e) {
      int i2 = 0;
      for (StackTraceElement stackTraceElement : e.getStackTrace()) {
        
        if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") &&
            stackTraceElement.getMethodName().equals("main")) {
          i2 |= 4;
        }
        
        if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") &&
            stackTraceElement.getMethodName().equals("handleHookedMethod")) {
          i2 |= 8;
        }
        
        if (stackTraceElement.getClassName().equals("com.saurik.substrate.MS$2") &&
            stackTraceElement.getMethodName().equals("invoked")) {
          i2 |= 16;
        }
        
        if (stackTraceElement.getClassName().equals("com.android.internal.os.ZygoteInit")) {
          i++;
          if (i == 2) {
            i2 |= 32;
          }
        }
      }
      return i2;
    }
  }
  
  // proc/mypid/maps
  public static int checkMap() throws Throwable {
    UnsupportedEncodingException unsupportedEncodingException;
    Throwable th;
    
    BufferedReader bufferedReader;
    BufferedReader bufferedReader2;
    
    int result = 0;
    
    try {
      HashSet hashSet = new HashSet();
      bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/" + android.os.Process.myPid() + "/maps"), "utf-8"));
      
      while (true) {
        try {
          String readLine = bufferedReader2.readLine();
          if (readLine == null) {
            break;
          } else if (readLine.endsWith(".so") || readLine.endsWith(".jar")) {
            hashSet.add(readLine.substring(readLine.lastIndexOf(" ") + 1));
          }
        } catch (UnsupportedEncodingException e) {
          unsupportedEncodingException = e;
          bufferedReader = bufferedReader2;
          try {
            unsupportedEncodingException.printStackTrace();
            if (bufferedReader != null) {
              
            }
          } catch (Throwable th2) {
            th = th2;
            bufferedReader2 = bufferedReader;
            if (bufferedReader2 != null) {
              
            }
            throw th;
          }
        }
      }

      Iterator it = hashSet.iterator();
      while (it.hasNext()) {
        Object next = it.next();
        if (((String) next).toLowerCase().contains("xposed")) {
          result = result | 64;
        }

        if (((String) next).toLowerCase().contains("com.saurik.substrate")) {
          result = result | 128;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
  
}
