package android.nla.org.gotway.util;

import android.util.Log;

public class DebugLogger
{
  public static void d(String paramString1, String paramString2)
  {
    Log.d(paramString1, paramString2);
  }
  
  public static void e(String paramString1, String paramString2)
  {
    Log.e(paramString1, paramString2);
  }
  
  public static void i(String paramString1, String paramString2)
  {
    Log.i(paramString1, paramString2);
  }
  
  public static void v(String paramString1, String paramString2)
  {
    Log.v(paramString1, paramString2);
  }
  
  public static void w(String paramString1, String paramString2)
  {
    Log.w(paramString1, paramString2);
  }
  
  public static void wtf(String paramString1, String paramString2)
  {
    Log.wtf(paramString1, paramString2);
  }
}