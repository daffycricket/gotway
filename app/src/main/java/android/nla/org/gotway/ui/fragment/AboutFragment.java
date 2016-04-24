package android.nla.org.gotway.ui.fragment;

import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.nla.org.gotway.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment
        extends Fragment {
    private View mRootView;

    private void initView() {
        TextView localTextView = (TextView) this.mRootView.findViewById(R.id.version);
        try {
            PackageInfo localPackageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), PackageManager.GET_CONFIGURATIONS);
            localTextView.setText(getActivity().getString(R.string.about_version, new Object[]{localPackageInfo.versionName}));
            return;
        } catch (NameNotFoundException localNameNotFoundException) {
            localNameNotFoundException.printStackTrace();
        }
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.mRootView = paramLayoutInflater.inflate(R.layout.fragment_about, paramViewGroup, false);
        initView();
        if (this.mRootView != null) {
            ViewGroup viewGroup = (ViewGroup) this.mRootView.getParent();
            if (paramLayoutInflater != null) {
                viewGroup.removeView(this.mRootView);
            }
        }
        return this.mRootView;
    }
}