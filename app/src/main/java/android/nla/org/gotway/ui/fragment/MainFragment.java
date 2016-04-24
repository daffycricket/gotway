package android.nla.org.gotway.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.nla.org.gotway.R;
import android.nla.org.gotway.ble.cmd.CMDMgr;
import android.nla.org.gotway.ble.profile.BleCore;
import android.nla.org.gotway.ble.profile.BleProfileActivity;
import android.nla.org.gotway.ble.scanner.ScannerFragment;
import android.nla.org.gotway.data.Data0x00;
import android.nla.org.gotway.ui.activity.MainActivity;
import android.nla.org.gotway.ui.view.BatterView;
import android.nla.org.gotway.ui.view.DashBoardView;
import android.nla.org.gotway.ui.view.TemperatureView;
import android.nla.org.gotway.util.DebugLogger;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class MainFragment
        extends Fragment
        implements OnClickListener {
    private BleProfileActivity act;
    private BatterView batterView;
    private DashBoardView dashBoardView;
    private long lastAnimTime;
    private View mRootView;
    private TemperatureView temperView;

    private void initView() {
        this.temperView = ((TemperatureView) this.mRootView.findViewById(R.id.temper));
        this.batterView = ((BatterView) this.mRootView.findViewById(R.id.batter));
        this.dashBoardView = ((DashBoardView) this.mRootView.findViewById(R.id.dashBoard));
        this.mRootView.findViewById(R.id.scan).setOnClickListener(this);
    }

    public void onAttach(Activity paramActivity) {
        super.onAttach(paramActivity);
        this.act = ((BleProfileActivity) paramActivity);
    }

    public void onClick(View paramView) {
        this.act.writeData(CMDMgr.CALL);
        switch (paramView.getId()) {
            case R.id.scan:
                if (this.act.isConnected()) {
                    new DisconnDialog().show(getFragmentManager(), null);
                    return;
                }
                if (((MainActivity) getActivity()).isBLEEnabled()) {
                    ScannerFragment.getInstance(getActivity(), BleCore.SERVICE_UUID, true).show(getFragmentManager(), null);
                    return;
                }
                ((MainActivity) getActivity()).showBLEDialog();
                return;
            default:
                return;
        }
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.mRootView = paramLayoutInflater.inflate(R.layout.fragment_main, paramViewGroup, false);
        initView();

        if (this.mRootView != null) {
            ViewGroup viewGroup = (ViewGroup) this.mRootView.getParent();
            if (paramLayoutInflater != null) {
                viewGroup.removeView(this.mRootView);
            }
        }

        return this.mRootView;
    }

    public void onResume() {
        super.onResume();
        setData(((MainActivity) getActivity()).mData);
    }

    public void setData(Data0x00 paramData0x00) {
        try {
            long l1 = System.currentTimeMillis();
            long l2 = Math.min(1000L, l1 - this.lastAnimTime);
            this.lastAnimTime = l1;
            this.dashBoardView.setData(paramData0x00, l2);
            this.batterView.startAnim(paramData0x00.energe, l2);
            this.temperView.startAnim((int) paramData0x00.temperature, l2);
            return;
        } catch (NullPointerException npe) {
            DebugLogger.e("MainFragment", "setData() 时fragment的view还未初始化或者收到的数据为null");
            npe.printStackTrace();
        }
    }
}