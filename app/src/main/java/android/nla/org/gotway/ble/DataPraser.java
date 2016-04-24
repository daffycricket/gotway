package android.nla.org.gotway.ble;

import android.nla.org.gotway.ble.profile.BleManagerCallbacks;
import android.nla.org.gotway.data.Data0x00;
import android.nla.org.gotway.util.DebugLogger;

import java.util.Arrays;

public class DataPraser
{
  private static final byte[] DATA;
  private static final byte[] END;
  private static final int END_OFFSET = 20;
  private static final byte[] HADER = { 85, -86 };
  private static final int HEADER_OFFSET = 2;
  private static final String TAG = "DataPraser";
  private static byte[] mData = new byte[24];
  private static int mDataIndex;
  private static int mTestIndex = 0;
  private static int start = 7;
  
  static
  {
    END = new byte[] { 90, 90, 90, 90 };
    byte[] arrayOfByte = new byte[24];
    arrayOfByte[0] = 85;
    arrayOfByte[1] = -86;
    arrayOfByte[2] = 22;
    arrayOfByte[3] = 111;
    arrayOfByte[4] = -4;
    arrayOfByte[5] = 24;
    arrayOfByte[8] = 18;
    arrayOfByte[10] = -1;
    arrayOfByte[11] = -17;
    arrayOfByte[12] = -82;
    arrayOfByte[13] = 93;
    arrayOfByte[15] = 1;
    arrayOfByte[16] = -1;
    arrayOfByte[17] = -8;
    arrayOfByte[19] = 24;
    arrayOfByte[20] = 90;
    arrayOfByte[21] = 90;
    arrayOfByte[22] = 90;
    arrayOfByte[23] = 90;
    DATA = arrayOfByte;
  }
  
  private static boolean arrayContains(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    int i = 0;
    for (;;)
    {
      if (i >= paramArrayOfByte1.length) {
        return false;
      }
      int j = paramArrayOfByte1[i];
      j = paramArrayOfByte2[0];
      i += 1;
    }
  }
  
  private static boolean checkEnd(byte[] paramArrayOfByte)
  {
    return Arrays.equals(Arrays.copyOfRange(paramArrayOfByte, 20, paramArrayOfByte.length), END);
  }
  
  private static boolean checkHead(byte[] paramArrayOfByte)
  {
    return Arrays.equals(Arrays.copyOfRange(paramArrayOfByte, 0, 2), HADER);
  }
  
  private static short[] convertToShort(byte[] paramArrayOfByte)
  {
    short[] arrayOfShort = new short[paramArrayOfByte.length];
    int i = 0;
    for (;;)
    {
      if (i >= arrayOfShort.length) {
        return arrayOfShort;
      }
      arrayOfShort[i] = ((short)(paramArrayOfByte[i] & 0xFF));
      i += 1;
    }
  }
  
  private static byte[] createData()
  {
    byte[] arrayOfByte = new byte[20];
    int i = start;
    for (;;)
    {
      if (i >= arrayOfByte.length)
      {
        DebugLogger.i("DataPraser", "recive:" + Util.bytes2HexStr(arrayOfByte));
        return arrayOfByte;
      }
      arrayOfByte[i] = DATA[(mTestIndex % 24)];
      mTestIndex += 1;
      start += 1;
      start %= 20;
      i += 1;
    }
  }
  
  private static int getEnergeByVoltage(int paramInt)
  {
    if (paramInt < 5300) {
      return 0;
    }
    if (paramInt < 5410) {
      return 10;
    }
    if (paramInt < 5620) {
      return 20;
    }
    if (paramInt < 5770) {
      return 30;
    }
    if (paramInt < 5910) {
      return 40;
    }
    if (paramInt < 6050) {
      return 50;
    }
    if (paramInt < 6170) {
      return 60;
    }
    if (paramInt < 6280) {
      return 70;
    }
    if (paramInt < 6390) {
      return 80;
    }
    if (paramInt < 6500) {
      return 90;
    }
    return 100;
  }
  
  private static float getSpeed(float paramFloat)
  {
    return 3.6F * (paramFloat / 100.0F);
  }
  
  private static int getTrueTemper(int paramInt)
  {
    return (int)((paramInt - 521) / 340.0F + 35.0F);
  }
  
  public static void praser(BleManagerCallbacks paramBleManagerCallbacks, byte[] paramArrayOfByte)
  {
    int i = 0;
    for (;;)
    {
      try
      {
        j = paramArrayOfByte.length;
        if (i >= j) {
          return;
        }
        if (mDataIndex == 0)
        {
          if (paramArrayOfByte[i] == 85)
          {
            arrayOfByte = mData;
            j = mDataIndex;
            mDataIndex = j + 1;
            arrayOfByte[j] = paramArrayOfByte[i];
          }
        }
        else if (mDataIndex == 1) {
          if (paramArrayOfByte[i] == -86)
          {
            arrayOfByte = mData;
            j = mDataIndex;
            mDataIndex = j + 1;
            arrayOfByte[j] = paramArrayOfByte[i];
          }
        }
      }
      finally {}
      mDataIndex = 0;
      break label228;
      byte[] arrayOfByte = mData;
      int j = mDataIndex;
      mDataIndex = j + 1;
      arrayOfByte[j] = paramArrayOfByte[i];
      if (mDataIndex > 20) {
        if (paramArrayOfByte[i] == 90)
        {
          if (mDataIndex == 23)
          {
            DebugLogger.e("DataPraser", "收到完整包:" + Util.bytes2HexStr(mData));
            if (mData[18] == 0) {
              praser0x00(paramBleManagerCallbacks, mData);
            }
            for (;;)
            {
              mDataIndex = 0;
              break;
              if (mData[18] == 4) {
                praser0x04(paramBleManagerCallbacks, mData);
              }
            }
          }
        }
        else {
          mDataIndex = 0;
        }
      }
      label228:
      i += 1;
    }
  }
  
  private static void praser0x00(BleManagerCallbacks paramBleManagerCallbacks, byte[] paramArrayOfByte)
  {
    paramArrayOfByte = convertToShort(paramArrayOfByte);
    Data0x00 localData0x00 = new Data0x00();
    int i = paramArrayOfByte[2];
    int j = 2 + 1;
    localData0x00.energe = getEnergeByVoltage((i << 8) + paramArrayOfByte[j]);
    j += 1;
    i = paramArrayOfByte[j];
    j += 1;
    localData0x00.speed = Math.abs(getSpeed((short)(i << 8 | paramArrayOfByte[j])));
    j = j + 2 + 1;
    i = paramArrayOfByte[j];
    j += 1;
    localData0x00.distance = ((i << 8) + paramArrayOfByte[j]);
    i = j + 2 + 1;
    localData0x00.temperature = getTrueTemper((short)(paramArrayOfByte[i] << 8 | paramArrayOfByte[(i + 1)]));
    DebugLogger.i("DataPraser", "speed = " + localData0x00.speed + "*******temper = " + localData0x00.temperature + "*****distance = " + localData0x00.distance + "********energe = " + localData0x00.energe);
    paramBleManagerCallbacks.onReciveCurrentData(localData0x00);
  }
  
  private static void praser0x04(BleManagerCallbacks paramBleManagerCallbacks, byte[] paramArrayOfByte)
  {
    paramArrayOfByte = convertToShort(paramArrayOfByte);
    int i = paramArrayOfByte[2];
    int k = 2 + 1;
    int j = paramArrayOfByte[k];
    k += 1;
    paramBleManagerCallbacks.onReviceTotalData(((i << 24) + (j << 16) + (paramArrayOfByte[k] << 8) + paramArrayOfByte[(k + 1)]) / 1000.0F);
  }
}