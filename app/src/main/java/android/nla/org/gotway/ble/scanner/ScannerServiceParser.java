package android.nla.org.gotway.ble.scanner;

import android.util.Log;
import com.reb.hola.util.DebugLogger;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class ScannerServiceParser
{
  private static final int COMPLETE_LOCAL_NAME = 9;
  private static final int FLAGS_BIT = 1;
  private static final byte LE_GENERAL_DISCOVERABLE_MODE = 2;
  private static final byte LE_LIMITED_DISCOVERABLE_MODE = 1;
  private static final int SERVICES_COMPLETE_LIST_128_BIT = 7;
  private static final int SERVICES_COMPLETE_LIST_16_BIT = 3;
  private static final int SERVICES_COMPLETE_LIST_32_BIT = 5;
  private static final int SERVICES_MORE_AVAILABLE_128_BIT = 6;
  private static final int SERVICES_MORE_AVAILABLE_16_BIT = 2;
  private static final int SERVICES_MORE_AVAILABLE_32_BIT = 4;
  private static final int SHORTENED_LOCAL_NAME = 8;
  private static final String TAG = "ScannerServiceParser";
  
  public static boolean decodeDeviceAdvData(byte[] paramArrayOfByte, UUID paramUUID)
  {
    boolean bool2 = false;
    boolean bool1;
    int k;
    label29:
    int m;
    if (paramUUID != null)
    {
      paramUUID = paramUUID.toString();
      bool1 = bool2;
      if (paramArrayOfByte != null)
      {
        k = 0;
        if (paramUUID != null) {
          break label71;
        }
        i = 1;
        int n = paramArrayOfByte.length;
        m = 0;
        if (m < n) {
          break label76;
        }
        bool1 = bool2;
        if (k != 0)
        {
          bool1 = bool2;
          if (i != 0) {
            bool1 = true;
          }
        }
      }
    }
    label71:
    label76:
    int i1;
    do
    {
      do
      {
        return bool1;
        paramUUID = null;
        break;
        i = 0;
        break label29;
        i1 = paramArrayOfByte[m];
        if (i1 != 0) {
          break label106;
        }
        bool1 = bool2;
      } while (k == 0);
      bool1 = bool2;
    } while (i == 0);
    return true;
    label106:
    int i2 = m + 1;
    int i3 = paramArrayOfByte[i2];
    int j = i;
    if (paramUUID != null)
    {
      if ((i3 != 2) && (i3 != 3)) {
        break label223;
      }
      j = i2 + 1;
      if (j >= i2 + i1 - 1) {
        j = i;
      }
    }
    else
    {
      label154:
      i = k;
      if (i3 == 1) {
        if ((paramArrayOfByte[(i2 + 1)] & 0x3) <= 0) {
          break label355;
        }
      }
    }
    label223:
    label355:
    for (int i = 1;; i = 0)
    {
      m = i2 + (i1 - 1) + 1;
      k = i;
      i = j;
      break;
      if ((i == 0) && (!decodeService16BitUUID(paramUUID, paramArrayOfByte, j, 2))) {}
      for (i = 0;; i = 1)
      {
        j += 2;
        break;
      }
      if ((i3 == 4) || (i3 == 5))
      {
        m = i2 + 1;
        j = i;
        if (m >= i2 + i1 - 1) {
          break label154;
        }
        if ((i == 0) && (!decodeService32BitUUID(paramUUID, paramArrayOfByte, m, 4))) {}
        for (i = 0;; i = 1)
        {
          m += 4;
          break;
        }
      }
      if (i3 != 6)
      {
        j = i;
        if (i3 != 7) {
          break label154;
        }
      }
      m = i2 + 1;
      j = i;
      if (m >= i2 + i1 - 1) {
        break label154;
      }
      if ((i == 0) && (!decodeService128BitUUID(paramUUID, paramArrayOfByte, m, 16))) {}
      for (i = 0;; i = 1)
      {
        m += 16;
        break;
      }
    }
  }
  
  public static String decodeDeviceName(byte[] paramArrayOfByte)
  {
    int j = paramArrayOfByte.length;
    int k;
    for (int i = 0;; i = i + (k - 1) + 1)
    {
      if (i >= j) {}
      do
      {
        return null;
        k = paramArrayOfByte[i];
      } while (k == 0);
      i += 1;
      int m = paramArrayOfByte[i];
      if ((m == 9) || (m == 8)) {
        return decodeLocalName(paramArrayOfByte, i + 1, k - 1);
      }
    }
  }
  
  public static String decodeLocalName(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      String toReturn  = new String(paramArrayOfByte, paramInt1, paramInt2, "UTF-8");
      return toReturn;
    }
    catch (UnsupportedEncodingException uee)
    {
      Log.e("ScannerServiceParser", "Unable to convert the complete local name to UTF-8", uee);
      return null;
    }
    catch (IndexOutOfBoundsException ioobe)
    {
      Log.e("ScannerServiceParser", "Error when reading complete local name", ioobe);
    }
    return null;
  }
  
  private static boolean decodeService128BitUUID(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    paramArrayOfByte = Integer.toHexString(decodeUuid16(paramArrayOfByte, paramInt1 + paramInt2 - 4));
    DebugLogger.d("ScannerServiceParser", paramArrayOfByte);
    return paramArrayOfByte.equals(paramString.substring(4, 8));
  }
  
  private static boolean decodeService16BitUUID(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    paramArrayOfByte = Integer.toHexString(decodeUuid16(paramArrayOfByte, paramInt1));
    DebugLogger.d("ScannerServiceParser", paramArrayOfByte);
    paramString = paramString.substring(4, 8);
    DebugLogger.d("ScannerServiceParser", paramString);
    return paramArrayOfByte.equals(paramString);
  }
  
  private static boolean decodeService32BitUUID(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    paramArrayOfByte = Integer.toHexString(decodeUuid16(paramArrayOfByte, paramInt1 + paramInt2 - 4));
    DebugLogger.d("ScannerServiceParser", paramArrayOfByte);
    return paramArrayOfByte.equals(paramString.substring(4, 8));
  }
  
  private static int decodeUuid16(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramArrayOfByte[paramInt];
    return (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 8 | (i & 0xFF) << 0;
  }
}