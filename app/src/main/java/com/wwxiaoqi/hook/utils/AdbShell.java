package com.wwxiaoqi.hook.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdbShell {
  private static Map<String, String> getpropMap = null;

  public static String getprop(String str) {
    if (str == null || str.trim().equals("")) {
      return "";
    }
    
    if (getpropMap == null) {
      getpropMap = new HashMap<>();
      ArrayList<String> getpropResult = execCmd("getprop");
      if (getpropResult != null && getpropResult.size() > 0) {
        Pattern compile = Pattern.compile("\\[(.+)\\]: \\[(.*)\\]");
        for (String matcher : getpropResult) {
          Matcher matcher2 = compile.matcher(matcher);
          if (matcher2.find()) {
            getpropMap.put(matcher2.group(1), matcher2.group(2));
          }
        }
      }
    }
    
    if (getpropMap.containsKey(str)) {
      return getpropMap.get(str);
    }
    
    return "fail";
  }

  private static ArrayList<String> execCmd(String str) {
    ArrayList<String> arrayList = new ArrayList<>();
    BufferedReader bufferedReader;
    
    String cmd = "/system/bin/sh";
    try {
      File file = new File(cmd);
      if (!(file.exists() && file.canExecute())) {
        cmd = "sh";
      }
      
      ArrayList arrayList2 = new ArrayList(Arrays.asList(new String[]{cmd, "-c"}));
      arrayList2.add(str);
      Process exec = Runtime.getRuntime().exec((String[]) arrayList2.toArray(new String[3]));
      bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
      
      while (true) {
        try {
          String readLine = bufferedReader.readLine();
          if (readLine == null) {
            break;
          }
          arrayList.add(readLine);
        } catch (Throwable th) {
          th.printStackTrace();
        }
      }
      
      bufferedReader = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
      while (true) {
        try {
          String readLine = bufferedReader.readLine();
          if (readLine != null) {
            arrayList.add(readLine);
          } else {
            break;
          }

        } catch (Throwable th) {
          th.printStackTrace();
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return arrayList;
  }

}

