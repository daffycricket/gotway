package android.nla.org.gotway;

import android.app.Application;
import android.content.Intent;
import android.nla.org.gotway.ble.profile.BleService;
import android.nla.org.gotway.util.DebugLogger;

public class App
  extends Application
{
  public void onCreate()
  {
    super.onCreate();
    DebugLogger.i("APP", "onCreate");
    startService(new Intent(this, BleService.class));
  }
}