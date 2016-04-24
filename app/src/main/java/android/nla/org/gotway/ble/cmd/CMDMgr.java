package android.nla.org.gotway.ble.cmd;

import java.util.ArrayList;
import java.util.Arrays;

public class CMDMgr
{
  public static final byte[] ALARM_FIRST;
  public static final byte[] ALARM_OPEN;
  public static final byte[] ALARM_SECOND;
  public static final byte[] CALL = { 98 };
  public static final byte[] CORRECT_END = { 121 };
  public static final byte[] CORRECT_START;
  public static final byte[] MODE_COMFORTABLE;
  public static final byte[] MODE_EXPLORE = { 104 };
  public static final byte[] MODE_SOFT;
  private static ArrayList<byte[]> NO_NEED_ALERT = new ArrayList();
  public static final byte[] PADDLE_A;
  public static final byte[] PADDLE_CANCEL;
  public static final byte[] PADDLE_CLOSE;
  public static final byte[] PADDLE_D;
  public static final byte[] PADDLE_F;
  public static final byte[] PADDLE_G;
  public static final byte[] PADDLE_H;
  public static final byte[] PADDLE_J;
  public static final byte[] PADDLE_K;
  public static final byte[] PADDLE_L;
  public static final byte[] PADDLE_OPEN;
  public static final byte[] PADDLE_S;
  public static final byte[] PADDLE_Z;
  
  static
  {
    MODE_COMFORTABLE = new byte[] { 102 };
    MODE_SOFT = new byte[] { 115 };
    ALARM_FIRST = new byte[] { 117 };
    ALARM_SECOND = new byte[] { 105 };
    ALARM_OPEN = new byte[] { 111 };
    PADDLE_CLOSE = new byte[] { 73 };
    PADDLE_OPEN = new byte[] { 79 };
    PADDLE_A = new byte[] { 65 };
    PADDLE_S = new byte[] { 83 };
    PADDLE_D = new byte[] { 68 };
    PADDLE_F = new byte[] { 70 };
    PADDLE_G = new byte[] { 71 };
    PADDLE_H = new byte[] { 72 };
    PADDLE_J = new byte[] { 74 };
    PADDLE_K = new byte[] { 75 };
    PADDLE_L = new byte[] { 76 };
    PADDLE_Z = new byte[] { 59 };
    PADDLE_CANCEL = new byte[] { 34 };
    CORRECT_START = new byte[] { 99 };
  }
  
  public CMDMgr()
  {
    NO_NEED_ALERT.add(CALL);
    NO_NEED_ALERT.add(CORRECT_START);
    NO_NEED_ALERT.add(CORRECT_END);
  }
  
  public static boolean isNeedAlert(byte[] paramArrayOfByte)
  {
    return (!Arrays.equals(paramArrayOfByte, CALL)) && (!Arrays.equals(paramArrayOfByte, CORRECT_START)) && (!Arrays.equals(paramArrayOfByte, CORRECT_END));
  }
}