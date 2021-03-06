package android.nla.org.gotway.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nla.org.gotway.R;
import android.nla.org.gotway.ble.profile.BleProfileActivity;
import android.nla.org.gotway.ble.profile.BleService;
import android.nla.org.gotway.data.Data0x00;
import android.nla.org.gotway.share.SharePeference;
import android.nla.org.gotway.ui.MainActivityMgr;
import android.nla.org.gotway.ui.fragment.ExitDialog;
import android.nla.org.gotway.util.DebugLogger;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;


public class MainActivity
        extends BleProfileActivity {
    public Data0x00 mData;
    private long mLastBackTime;
    private MainActivityMgr mgr;

    private void judgeExit() {
        int i = mShare.getInt(SharePeference.EXIT_MODE, 0);
        DebugLogger.i("judgeExit", i + "");
        if ((i & 0x1) == 0) {
            new ExitDialog().show(getFragmentManager(), null);
            return;
        }
        exit(i);
    }

    public void exit(int paramInt) {
        DebugLogger.i("exit", paramInt + "");
        if ((paramInt >> 1 & 0x1) == 1) {
            DebugLogger.i("exit", "exit 1: 保持链接");
            finish();
            return;
        }
        DebugLogger.i("exit", "exit 2: 完全退出");
        stopService(new Intent(getApplication(), BleService.class));
        finish();
    }

    @SuppressLint({"InlinedApi"})
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_main);
        this.mgr = new MainActivityMgr(this);
    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        if (paramInt == 4) {
            long l = System.currentTimeMillis();
            if (l - this.mLastBackTime < 2000L) {
                judgeExit();
            }

            // TODO Check behavior
            this.mLastBackTime = l;
            toast(R.string.exit_by_muilt_click);
            return true;
        }
        return super.onKeyDown(paramInt, paramKeyEvent);
    }

    public void onReciveCurrentData(Data0x00 paramData0x00) {
        super.onReciveCurrentData(paramData0x00);
        if (paramData0x00 != null) {
            if (this.mData != null) {
                paramData0x00.totalDistance = this.mData.totalDistance;
            }
        }
        for (this.mData = paramData0x00; ; this.mData = new Data0x00()) {
            this.mgr.setData(this.mData);
            return;
        }
    }

    public void onReviceTotalData(float paramFloat) {
        super.onReviceTotalData(paramFloat);
        if (this.mData == null) {
            this.mData = new Data0x00();
        }
        this.mData.totalDistance = paramFloat;
        this.mgr.setData(this.mData);
    }
}