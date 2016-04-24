package android.nla.org.gotway.ble.profile;

import android.nla.org.gotway.data.Data0x00;

public abstract interface BleManagerCallbacks
{
  public abstract void onDeviceConnected();
  
  public abstract void onDeviceDisconnected();
  
  public abstract void onDeviceNotSupported();
  
  public abstract void onLinklossOccur();
  
  public abstract void onNotifyEnable();
  
  public abstract void onReciveCurrentData(Data0x00 paramData0x00);
  
  public abstract void onReviceTotalData(float paramFloat);
  
  public abstract void onServicesDiscovered();
  
  public abstract void onWriteSuccess(byte[] paramArrayOfByte);
}