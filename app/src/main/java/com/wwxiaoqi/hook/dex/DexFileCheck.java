package com.wwxiaoqi.hook.dex;

import android.util.Log;
import dalvik.system.DexFile;
import java.lang.reflect.Field;

public class DexFileCheck {
  private static String TAG = "CheckInfo";

  private static Field getDeclaredField(Object obj, String str) {
    Class cls = obj.getClass();
    while (cls != Object.class) {
      try {
        return cls.getDeclaredField(str);
      } catch (Exception e) {
        cls = cls.getSuperclass();
      }
    }
    return null;
  }

  private static Object getDeclaredFieldValue(Object obj, String fieldName) {
    try {
      Field f = getDeclaredField(obj, fieldName);
      f.setAccessible(true);
      return f.get(obj);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  // 查询所有加载的 dex jar apk 文件
  // 看一下是否有其他异己的模块加载
  public static void allDex() {
    Object pathList = getDeclaredFieldValue(DexFileCheck.class.getClassLoader(), "pathList");
    Object [] dexElements = (Object []) getDeclaredFieldValue(pathList, "dexElements");

    for (Object dex:dexElements) {
      DexFile dexFile = (DexFile) getDeclaredFieldValue(dex, "dexFile");

      if (dexFile == null) {
        continue;
      }

      Log.d(TAG, "allDex: found dexfile " + dexFile.getName());
    }
  }
  
}
