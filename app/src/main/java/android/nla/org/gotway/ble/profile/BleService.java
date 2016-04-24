package android.nla.org.gotway.ble.profile;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nla.org.gotway.data.Data0x00;
import android.nla.org.gotway.share.SharePeference;
import android.nla.org.gotway.util.DebugLogger;
import android.os.Binder;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

public class BleService
  extends Service
  implements BleManagerCallbacks, BleManager
{
  private static final long AUTO_CONN_PORID = 180000L;
  private String mAddress;
  private Timer mAutoConn;
  private BleCore mBleCore;
  private BleManagerCallbacks mCallbacks;
  private BroadcastReceiver toothOpenReciver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      int i;
      if (paramAnonymousIntent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED"))
      {
        i = paramAnonymousIntent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
        if (i != 12) {
          break label52;
        }
        BleService.this.mBleCore.connect(BleService.this.getApplicationContext(), BleService.this.mAddress);
      }
      label52:
      while (i != 10) {
        return;
      }
      BleService.this.mBleCore.closeBluetoothGatt();
    }
  };
  
  private void autoConn()
  {
    this.mAutoConn = new Timer();
    this.mAutoConn.schedule(new TimerTask()
    {
      public void run()
      {
        if (BleService.this.mBleCore.isConnected())
        {
          if (BleService.this.mAutoConn != null)
          {
            BleService.this.mAutoConn.cancel();
            BleService.this.mAutoConn = null;
          }
          return;
        }
        BleService.this.mBleCore.disconnect();
        BleService.this.mBleCore.connect(BleService.this.getApplicationContext(), BleService.this.mAddress);
      }
    }, 0L, 180000L);
  }
  
  public void closeBluetoothGatt()
  {
    this.mBleCore.closeBluetoothGatt();
  }
  
  public boolean connect(Context paramContext, BluetoothDevice paramBluetoothDevice)
  {
    this.mAddress = paramBluetoothDevice.getAddress();
    return this.mBleCore.connect(paramContext, paramBluetoothDevice);
  }
  
  public boolean connect(Context paramContext, String paramString)
  {
    this.mAddress = paramString;
    return this.mBleCore.connect(paramContext, paramString);
  }
  
  public void disconnect()
  {
    this.mAddress = null;
    if (this.mAutoConn != null)
    {
      this.mAutoConn.cancel();
      this.mAutoConn = null;
    }
    this.mBleCore.disconnect();
  }
  
  protected LocalBinder getBinder()
  {
    return new LocalBinder();
  }
  
  public boolean isConnected()
  {
    return this.mBleCore.isConnected();
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return getBinder();
  }
  
  public void onCreate()
  {
    super.onCreate();
    this.mBleCore = new BleCore();
    this.mBleCore.setGattCallbacks(this);
    IntentFilter localIntentFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
    registerReceiver(this.toothOpenReciver, localIntentFilter);
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    DebugLogger.e("BleService", "service destory");
    if (isConnected()) {
      this.mBleCore.disconnect();
    }
    unregisterReceiver(this.toothOpenReciver);
  }
  
  public void onDeviceConnected()
  {
    if (this.mCallbacks != null) {
      this.mCallbacks.onDeviceConnected();
    }
    if (this.mAutoConn != null)
    {
      this.mAutoConn.cancel();
      this.mAutoConn = null;
    }
  }
  
  public void onDeviceDisconnected()
  {
    if (this.mCallbacks != null) {
      this.mCallbacks.onDeviceDisconnected();
    }
  }
  
  public void onDeviceNotSupported()
  {
    if (this.mCallbacks != null) {
      this.mCallbacks.onDeviceNotSupported();
    }
  }
  
  public void onLinklossOccur()
  {
    if (this.mCallbacks != null) {
      this.mCallbacks.onLinklossOccur();
    }
    autoConn();
  }
  
  public void onNotifyEnable()
  {
    if (this.mCallbacks != null) {
      this.mCallbacks.onNotifyEnable();
    }
  }
  
  public void onReciveCurrentData(Data0x00 paramData0x00)
  {
    if (this.mCallbacks != null) {
      this.mCallbacks.onReciveCurrentData(paramData0x00);
    }
  }
  
  public void onReviceTotalData(float paramFloat)
  {
    if (this.mCallbacks != null) {
      this.mCallbacks.onReviceTotalData(paramFloat);
    }
    getSharedPreferences(SharePeference.FILE_NMAE, 0).edit().putFloat(SharePeference.HISTORY_DISTANCE, paramFloat).commit();
  }
  
  public void onServicesDiscovered()
  {
    if (this.mCallbacks != null) {
      this.mCallbacks.onServicesDiscovered();
    }
  }
  
  public boolean onUnbind(Intent paramIntent)
  {
    this.mCallbacks = null;
    return true;
  }
  
  public void onWriteSuccess(byte[] paramArrayOfByte)
  {
    if (this.mCallbacks != null) {
      this.mCallbacks.onWriteSuccess(paramArrayOfByte);
    }
  }
  
  public void setBleCallBack(BleManagerCallbacks paramBleManagerCallbacks)
  {
    this.mCallbacks = paramBleManagerCallbacks;
  }
  
  public void setGattCallbacks(BleManagerCallbacks paramBleManagerCallbacks) {}
  
  public boolean write(byte[] paramArrayOfByte)
  {
    return this.mBleCore.write(paramArrayOfByte);
  }
  
  public class LocalBinder
    extends Binder
  {
    public LocalBinder() {}
    
    public BleService getService()
    {
      return BleService.this;
    }
  }
}