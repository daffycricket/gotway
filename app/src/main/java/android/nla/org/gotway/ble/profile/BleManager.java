package android.nla.org.gotway.ble.profile;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

public abstract interface BleManager
{
  public abstract void closeBluetoothGatt();
  
  public abstract boolean connect(Context paramContext, BluetoothDevice paramBluetoothDevice);
  
  public abstract boolean connect(Context paramContext, String paramString);
  
  public abstract void disconnect();
  
  public abstract void setGattCallbacks(BleManagerCallbacks paramBleManagerCallbacks);
  
  public abstract boolean write(byte[] paramArrayOfByte);
}