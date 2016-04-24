package android.nla.org.gotway.ble.scanner;

import android.bluetooth.BluetoothDevice;

public class ExtendedBluetoothDevice
{
  public BluetoothDevice device;
  public boolean isBonded;
  public String name;
  public int rssi;
  
  public ExtendedBluetoothDevice(BluetoothDevice paramBluetoothDevice, String paramString, int paramInt, boolean paramBoolean)
  {
    this.device = paramBluetoothDevice;
    this.name = paramString;
    this.rssi = paramInt;
    this.isBonded = paramBoolean;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof ExtendedBluetoothDevice))
    {
      paramObject = (ExtendedBluetoothDevice)paramObject;
      return this.device.getAddress().equals(((ExtendedBluetoothDevice)paramObject).device.getAddress());
    }
    return super.equals(paramObject);
  }
  
  public static class AddressComparator
  {
    public String address;
    
    public boolean equals(Object paramObject)
    {
      if ((paramObject instanceof ExtendedBluetoothDevice))
      {
        paramObject = (ExtendedBluetoothDevice)paramObject;
        return this.address.equals(((ExtendedBluetoothDevice)paramObject).device.getAddress());
      }
      return super.equals(paramObject);
    }
  }
}


/* Location:              C:\tmp\dex2jar-2.0\classes-dex2jar.jar!\com\reb\hola\ble\scanner\ExtendedBluetoothDevice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */