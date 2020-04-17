package com.wwxiaoqi.hook.virtual;

import com.wwxiaoqi.hook.utils.AdbShell;

public class VirtualCheck {

  public static String getVMDesc() {
    StringBuilder stringBuilder = new StringBuilder();
    
    // 判断 GenyMotion 模拟器
    String VM = AdbShell.getprop("ro.genymotion.version");
    if (VM != null) {
      stringBuilder.append("ro.genymotion.version");
      stringBuilder.append("|");
      stringBuilder.append(VM);
      stringBuilder.append("\n");
    }
    
    // 判断使用了 vbox 的模拟器
    // 目前很多市面上的安卓模拟器都是基于 vbox 的
    VM = AdbShell.getprop("androVM.vbox_dpi");
    if (VM != null) {
      stringBuilder.append("androVM.vbox_dpi");
      stringBuilder.append("|");
      stringBuilder.append(VM);
      stringBuilder.append("\n");
    }
    
    // 检测安卓自身的模拟器
    VM = AdbShell.getprop("qemu.sf.fake_camera");
    if (VM != null) {
      stringBuilder.append("qemu.sf.fake_camera");
      stringBuilder.append("|");
      stringBuilder.append(VM);
    }
    return stringBuilder.toString();
  }


}

