package android.nla.org.gotway.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import com.reb.hola.ble.profile.BleProfileActivity;
import com.reb.hola.ui.adapter.SettingListAdapter;
import com.reb.hola.util.DebugLogger;

public class SettingFragment2
  extends Fragment
{
  private BleProfileActivity act;
  private SettingListAdapter adapter;
  private View mRootView;
  
  private void initView()
  {
    ExpandableListView localExpandableListView = (ExpandableListView)this.mRootView;
    DebugLogger.i(getTag(), "initView");
    this.adapter = new SettingListAdapter(getActivity());
    localExpandableListView.setAdapter(this.adapter);
    localExpandableListView.setOnChildClickListener(new OnChildClickListener()
    {
      public boolean onChildClick(ExpandableListView paramAnonymousExpandableListView, View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, long paramAnonymousLong)
      {
        DebugLogger.i(SettingFragment2.this.getTag(), "click : " + SettingFragment2.this.getString(((Integer)SettingFragment2.this.adapter.getChild(paramAnonymousInt1, paramAnonymousInt2)).intValue()));
        SettingFragment2.this.act.writeData(SettingListAdapter.getCMDByPosition(paramAnonymousInt1, paramAnonymousInt2));
        return false;
      }
    });
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    this.act = ((BleProfileActivity)paramActivity);
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
      this.mRootView = paramLayoutInflater.inflate(2130903047, paramViewGroup, false);
      initView();
    }
  }
}


/* Location:              C:\tmp\dex2jar-2.0\classes-dex2jar.jar!\com\reb\hola\ui\fragment\SettingFragment2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */