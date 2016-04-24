package android.nla.org.gotway.ui.fragment;

import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment
  extends Fragment
{
  private View mRootView;
  
  private void initView()
  {
    TextView localTextView = (TextView)this.mRootView.findViewById(2131361797);
    try
    {
      PackageInfo localPackageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 16384);
      localTextView.setText(getActivity().getString(2131165234, new Object[] { localPackageInfo.versionName }));
      return;
    }
    catch (NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
    }
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
      this.mRootView = paramLayoutInflater.inflate(2130903044, paramViewGroup, false);
      initView();
    }
  }
}


/* Location:              C:\tmp\dex2jar-2.0\classes-dex2jar.jar!\com\reb\hola\ui\fragment\AboutFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */