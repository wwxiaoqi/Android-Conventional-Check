package com.wwxiaoqi.hook.debug;

import com.wwxiaoqi.hook.utils.AdbShell;
import java.io.BufferedReader;
import java.io.FileReader;

public class DebugCheck {

  // 检测是否拥有调试属性
  public static String HaveDebugProp() {
    // ro.debuggable 表示调试权限
    // 默认为 0
    // 1 表示可以调试
    StringBuilder builder = new StringBuilder();
    builder.append("ro.debuggable");
    builder.append(AdbShell.getprop("ro.debuggable"));
    return builder.toString();

  }

  // 如果进程被调试 TracerPid 不为 0
  public static String getTracerPid() {
    BufferedReader bufferedReader;
    String readLine = "";
    try {
      bufferedReader = new BufferedReader(new FileReader("/proc/self/status"));
      do {
        readLine = bufferedReader.readLine();
        if (readLine == null) {
          break;
        }

      } while (!readLine.startsWith("TracerPid:"));
      readLine = readLine.substring(10).trim();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return readLine;
  }


}

