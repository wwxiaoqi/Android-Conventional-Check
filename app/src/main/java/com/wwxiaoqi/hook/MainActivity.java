package com.wwxiaoqi.hook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.wwxiaoqi.hook.debug.DebugCheck;
import com.wwxiaoqi.hook.dex.DexFileCheck;
import com.wwxiaoqi.hook.hook.HookCheck;
import com.wwxiaoqi.hook.root.RootCheck;
import com.wwxiaoqi.hook.signature.SignCheck;
import com.wwxiaoqi.hook.virtual.VirtualCheck;

public class MainActivity extends Activity {
  TextView result;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    result = findViewById(R.id.CheckResult);
    HookCheck.Mcontext = this;
  }

  public void HookCheck(View view) throws Throwable {
    StringBuilder mHookResult = new StringBuilder();
    mHookResult.append("HookCheck: checkActivityManagerNative: " + HookCheck.checkActivityManagerNative());
    mHookResult.append("\n");
    mHookResult.append("HookCheck: checkPackage: " + HookCheck.checkPackage(this));
    mHookResult.append("\n");
    mHookResult.append("HookCheck: checkMap: " + HookCheck.checkMap());
    mHookResult.append("\n");
    mHookResult.append("HookCheck: checkStackTraceElement: " + HookCheck.checkStackTraceElement());
    result.setText(mHookResult.toString());
  }

  public void VirtualCheck(View view) {
    String mVirtualResult = "VirtualCheck: " + VirtualCheck.getVMDesc();
    result.setText(mVirtualResult);
  }

  public void DebugCheck(View view) {
    String mDebugResult = 
      "DebugCheck: getTracerPid: " + DebugCheck.getTracerPid() + 
      "\n" +
      "DebugCheck: HaveDebugProp: " + DebugCheck.HaveDebugProp();
    result.setText(mDebugResult);
  }

  public void DexFileCheck(View view) {
    DexFileCheck.allDex();
    result.setText("请观看 LogCat -> Tag::CheckInfo");
  }

  public void RootCheck(View view) {
    String mRootResult = 
      "RootCheck: haveSu: " + RootCheck.haveSu() + 
      "\n" +
      "RootCheck: " + RootCheck.RootCheckProp();
    result.setText(mRootResult);
  }

  public void SignCheck(View view) {
    SignCheck signCheck = new SignCheck(this, "AC:B4:27:A9:FA:CB:AA:7C:9A:BA:35:37:45:65:9E:E7:D8:37:25:83");
    if (signCheck.check()) {
      result.setText("Signature: Yes");
      return;
    }
    result.setText("Signature: No");
  }

}
