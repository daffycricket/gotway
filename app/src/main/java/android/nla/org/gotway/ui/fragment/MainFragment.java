package android.nla.org.gotway.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.reb.hola.ble.cmd.CMDMgr;
import com.reb.hola.ble.profile.BleCore;
import com.reb.hola.ble.profile.BleProfileActivity;
import com.reb.hola.ble.scanner.ScannerFragment;
import com.reb.hola.data.Data0x00;
import com.reb.hola.ui.activity.MainActivity;
import com.reb.hola.ui.view.BatterView;
import com.reb.hola.ui.view.DashBoardView;
import com.reb.hola.ui.view.TemperatureView;
import com.reb.hola.util.DebugLogger;

public class MainFragment
  extends Fragment
  implements OnClickListener
{
  private BleProfileActivity act;
  private BatterView batterView;
  private DashBoardView dashBoardView;
  private long lastAnimTime;
  private View mRootView;
  private TemperatureView temperView;
  
  private void initView()
  {
    this.temperView = ((TemperatureView)this.mRootView.findViewById(2131361802));
    this.batterView = ((BatterView)this.mRootView.findViewById(2131361803));
    this.dashBoardView = ((DashBoardView)this.mRootView.findViewById(2131361801));
    this.mRootView.findViewById(2131361799).setOnClickListener(this);
    this.mRootView.findViewById(2131361800).setOnClickListener(this);
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.act = ((BleProfileActivity)paramActivity);
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131361799: 
      if (this.act.isConnected())
      {
        new DisconnDialog().show(getFragmentManager(), null);
        return;
      }
      if (((MainActivity)getActivity()).isBLEEnabled())
      {
        ScannerFragment.getInstance(getActivity(), BleCore.SERVICE_UUID, true).show(getFragmentManager(), null);
        return;
      }
      ((MainActivity)getActivity()).showBLEDialog();
      return;
    }
    this.act.writeData(CMDMgr.CALL);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    if (this.mRootView != null)
    {
      paramLayoutInflater = (ViewGroup)this.mRootView.getParent();
      if (paramLayoutInflater != null) {
        paramLayoutInflater.removeView(this.mRootView);
      }
    }
    for (;;)
    {
      return this.mRootView;
      this.mRootView = paramLayoutInflater.inflate(2130903046, paramViewGroup, false);
      initView();
    }
  }
  
  public void onResume()
  {
    super.onResume();
    setData(((MainActivity)getActivity()).mData);
  }
  
  public void setData(Data0x00 paramData0x00)
  {
    try
    {
      long l1 = System.currentTimeMillis();
      long l2 = Math.min(1000L, l1 - this.lastAnimTime);
      this.lastAnimTime = l1;
      this.dashBoardView.setData(paramData0x00, l2);
      this.batterView.startAnim(paramData0x00.energe, l2);
      this.temperView.startAnim((int)paramData0x00.temperature, l2);
      return;
    }
    catch (NullPointerException paramData0x00)
    {
      DebugLogger.e("MainFragment", "setData() 时fragment的view还未初始化或者收到的数据为null");
      paramData0x00.printStackTrace();
    }
  }
}


/* Location:              C:\tmp\dex2jar-2.0\classes-dex2jar.jar!\com\reb\hola\ui\fragment\MainFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */