package android.nla.org.gotway.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.nla.org.gotway.R;
import android.nla.org.gotway.ble.profile.BleProfileActivity;
import android.nla.org.gotway.ui.adapter.SettingListAdapter;
import android.nla.org.gotway.util.DebugLogger;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class SettingFragment2
        extends Fragment {
    private BleProfileActivity act;
    private SettingListAdapter adapter;
    private View mRootView;

    private void initView() {
        ExpandableListView localExpandableListView = (ExpandableListView) this.mRootView;
        DebugLogger.i(getTag(), "initView");
        this.adapter = new SettingListAdapter(getActivity());
        localExpandableListView.setAdapter(this.adapter);
        localExpandableListView.setOnChildClickListener(new OnChildClickListener() {
            public boolean onChildClick(ExpandableListView paramAnonymousExpandableListView, View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, long paramAnonymousLong) {
                DebugLogger.i(SettingFragment2.this.getTag(), "click : " + SettingFragment2.this.getString(((Integer) SettingFragment2.this.adapter.getChild(paramAnonymousInt1, paramAnonymousInt2)).intValue()));
                SettingFragment2.this.act.writeData(SettingListAdapter.getCMDByPosition(paramAnonymousInt1, paramAnonymousInt2));
                return false;
            }
        });
    }

    public void onAttach(Activity paramActivity) {
        super.onAttach(paramActivity);
        this.act = ((BleProfileActivity) paramActivity);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.mRootView = paramLayoutInflater.inflate(R.layout.fragment_setting2, paramViewGroup, false);
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