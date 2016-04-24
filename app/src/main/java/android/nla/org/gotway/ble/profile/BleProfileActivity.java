package android.nla.org.gotway.ble.profile;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.nla.org.gotway.ble.cmd.CMDMgr;
import android.nla.org.gotway.data.Data0x00;
import android.nla.org.gotway.share.SharePeference;
import android.nla.org.gotway.util.DebugLogger;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Arrays;

public class BleProfileActivity
  extends Activity
  implements ScannerFragment.OnDeviceSelectedListener, BleManagerCallbacks
{
  protected static final int REQUEST_ENABLE_BT = 2;
  protected static SharedPreferences mShare;
  private ServiceConnection conn = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      BleProfileActivity.this.mService = ((BleService.LocalBinder)paramAnonymousIBinder).getService();
      BleProfileActivity.this.mService.setBleCallBack(BleProfileActivity.this);
      BleProfileActivity.this.mService.connect(BleProfileActivity.this.getApplicationContext(), BleProfileActivity.mShare.getString(SharePeference.DEVICE_ADDRESS, ""));
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      BleProfileActivity.this.mService = null;
    }
  };
  private Handler handler = new Handler();
  private float mCurrentSpeed;
  protected BleService mService;
  private Toast mToast;
  private Runnable notifyUINullData = new Runnable()
  {
    public void run()
    {
      DebugLogger.i("ACT", "null Data");
      BleProfileActivity.this.mCurrentSpeed = 0.0F;
      BleProfileActivity.this.onReciveCurrentData(null);
    }
  };
  private Runnable sendAlert = new Runnable()
  {
    public void run()
    {
      BleProfileActivity.this.writeData(CMDMgr.CALL);
    }
  };
  private Runnable stopCorrect = new Runnable()
  {
    public void run()
    {
      BleProfileActivity.this.writeData(CMDMgr.CORRECT_END);
    }
  };
  
  private void checkListenerTime()
  {
    DebugLogger.i("ACT", "remove");
    this.handler.removeCallbacks(this.notifyUINullData);
    this.handler.postDelayed(this.notifyUINullData, 3000L);
  }
  
  private void ensureBLESupported()
  {
    if (!getPackageManager().hasSystemFeature("android.hardware.bluetooth_le"))
    {
      Toast.makeText(this, 2131165224, 1).show();
      finish();
    }
  }
  
  protected void checkBle()
  {
    ensureBLESupported();
    if (!isBLEEnabled()) {
      showBLEDialog();
    }
  }
  
  public void disconnect()
  {
    if (isConnected()) {
      this.mService.disconnect();
    }
  }
  
  public boolean isBLEEnabled()
  {
    BluetoothAdapter localBluetoothAdapter = ((BluetoothManager)getSystemService("bluetooth")).getAdapter();
    return (localBluetoothAdapter != null) && ((localBluetoothAdapter.getState() == 12) || (localBluetoothAdapter.getState() == 11));
  }
  
  public boolean isConnected()
  {
    if (this.mService != null) {
      return this.mService.isConnected();
    }
    return false;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    checkBle();
    paramBundle = new Intent(this, BleService.class);
    startService(paramBundle);
    bindService(paramBundle, this.conn, 1);
    mShare = getSharedPreferences(SharePeference.FILE_NMAE, 0);
  }
  
  protected void onDestroy()
  {
    this.handler.removeCallbacksAndMessages(null);
    super.onDestroy();
    if (this.mService != null) {
      unbindService(this.conn);
    }
  }
  
  public void onDeviceConnected()
  {
    checkListenerTime();
  }
  
  public void onDeviceDisconnected()
  {
    toast(2131165226);
    this.handler.removeCallbacks(this.notifyUINullData);
    this.handler.post(this.notifyUINullData);
  }
  
  public void onDeviceNotSupported()
  {
    toast(2131165225);
  }
  
  public void onDeviceSelected(BluetoothDevice paramBluetoothDevice, String paramString)
  {
    if (this.mService != null)
    {
      if ((this.mService.isConnected()) && (!mShare.getString(SharePeference.DEVICE_ADDRESS, "").equals(paramBluetoothDevice.getAddress()))) {
        this.mService.disconnect();
      }
      this.mService.connect(getApplicationContext(), paramBluetoothDevice);
      toast(2131165228);
      mShare.edit().putString(SharePeference.DEVICE_ADDRESS, paramBluetoothDevice.getAddress()).commit();
    }
  }
  
  public void onDialogCanceled() {}
  
  public void onLinklossOccur()
  {
    toast(2131165223);
    this.handler.removeCallbacks(this.notifyUINullData);
    this.handler.post(this.notifyUINullData);
  }
  
  public void onNotifyEnable()
  {
    toast(2131165227);
  }
  
  public void onReciveCurrentData(Data0x00 paramData0x00)
  {
    if (paramData0x00 != null)
    {
      this.mCurrentSpeed = paramData0x00.speed;
      checkListenerTime();
    }
  }
  
  public void onReviceTotalData(float paramFloat)
  {
    checkListenerTime();
  }
  
  public void onServicesDiscovered() {}
  
  public void onWriteSuccess(byte[] paramArrayOfByte)
  {
    DebugLogger.i("ACT", "onWriteSuccess");
    if (CMDMgr.isNeedAlert(paramArrayOfByte)) {
      runOnUiThread(new Runnable()
      {
        public void run()
        {
          BleProfileActivity.this.handler.postDelayed(BleProfileActivity.this.sendAlert, 300L);
        }
      });
    }
    while (!Arrays.equals(paramArrayOfByte, CMDMgr.CORRECT_START)) {
      return;
    }
    runOnUiThread(new Runnable()
    {
      public void run()
      {
        BleProfileActivity.this.handler.postDelayed(BleProfileActivity.this.stopCorrect, 300L);
      }
    });
  }
  
  public void showBLEDialog()
  {
    startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 2);
  }
  
  protected void toast(final int paramInt)
  {
    runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (BleProfileActivity.this.mToast != null) {
          BleProfileActivity.this.mToast.cancel();
        }
        BleProfileActivity.this.mToast = Toast.makeText(BleProfileActivity.this, paramInt, 1);
        BleProfileActivity.this.mToast.show();
      }
    });
  }
  
  public boolean writeData(byte[] paramArrayOfByte)
  {
    if (this.mService != null)
    {
      checkBle();
      if (!this.mService.isConnected()) {
        break label50;
      }
      if (this.mCurrentSpeed <= 0.3F) {
        break label41;
      }
      toast(2131165229);
    }
    for (;;)
    {
      return false;
      label41:
      return this.mService.write(paramArrayOfByte);
      label50:
      toast(2131165226);
    }
  }
}