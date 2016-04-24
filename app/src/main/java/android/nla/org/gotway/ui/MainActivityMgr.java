package android.nla.org.gotway.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.nla.org.gotway.data.Data0x00;
import android.nla.org.gotway.ui.fragment.AboutFragment;
import android.nla.org.gotway.ui.fragment.MainFragment;
import android.nla.org.gotway.ui.fragment.SettingFragment2;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivityMgr
        implements OnCheckedChangeListener {
    private Activity activity;
    private FragmentManager fm;
    private Fragment mAboutFragment;
    private Fragment mLastFragment;
    private MainFragment mMainFragment;
    private Fragment mSettingFragment2;
    private ViewStub mViewStub;

    public MainActivityMgr(Activity paramActivity) {
        this.activity = paramActivity;
        this.mViewStub = ((ViewStub) paramActivity.findViewById(2131361792));
        this.mViewStub.inflate();
        paramActivity = paramActivity.findViewById(2131361809);
        Animation localAnimation = AnimationUtils.loadAnimation(this.activity, 2130968576);
        localAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation paramAnonymousAnimation) {
                MainActivityMgr.this.mViewStub.setVisibility(8);
                MainActivityMgr.this.mViewStub = ((ViewStub) MainActivityMgr.this.activity.findViewById(2131361793));
                MainActivityMgr.this.mViewStub.inflate();
                MainActivityMgr.this.mViewStub = null;
                MainActivityMgr.this.fm = MainActivityMgr.this.activity.getFragmentManager();
                paramAnonymousAnimation = MainActivityMgr.this;
                MainActivityMgr localMainActivityMgr = MainActivityMgr.this;
                MainFragment localMainFragment = new MainFragment();
                localMainActivityMgr.mMainFragment = localMainFragment;
                paramAnonymousAnimation.mLastFragment = localMainFragment;
                MainActivityMgr.this.fm.beginTransaction().add(2131361810, MainActivityMgr.this.mMainFragment, null).commit();
                ((RadioGroup) MainActivityMgr.this.activity.findViewById(2131361811)).setOnCheckedChangeListener(MainActivityMgr.this);
            }

            public void onAnimationRepeat(Animation paramAnonymousAnimation) {
            }

            public void onAnimationStart(Animation paramAnonymousAnimation) {
            }
        });
        paramActivity.startAnimation(localAnimation);
    }

    private void changeFragment(Fragment paramFragment) {
        if ((paramFragment == null) || (paramFragment == this.mLastFragment)) {
            return;
        }
        FragmentTransaction localFragmentTransaction = this.activity.getFragmentManager().beginTransaction();
        if ((this.mLastFragment != null) && (this.mLastFragment != paramFragment)) {
            localFragmentTransaction.detach(this.mLastFragment);
        }
        if (!paramFragment.isAdded()) {
            localFragmentTransaction.add(2131361810, paramFragment);
        }
        if (paramFragment.isDetached()) {
            localFragmentTransaction.attach(paramFragment);
        }
        this.mLastFragment = paramFragment;
        localFragmentTransaction.commitAllowingStateLoss();
    }

    public void onCheckedChanged(RadioGroup paramRadioGroup, int paramInt) {
        switch (paramInt) {
            default:
                return;
            case 2131361812:
                if (this.mMainFragment == null) {
                    this.mMainFragment = new MainFragment();
                }
                changeFragment(this.mMainFragment);
                return;
            case 2131361813:
                if (this.mSettingFragment2 == null) {
                    this.mSettingFragment2 = new SettingFragment2();
                }
                changeFragment(this.mSettingFragment2);
                return;
        }
        if (this.mAboutFragment == null) {
            this.mAboutFragment = new AboutFragment();
        }
        changeFragment(this.mAboutFragment);
    }

    public void setData(final Data0x00 paramData0x00) {
        if (this.mLastFragment == this.mMainFragment) {
            this.activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (MainActivityMgr.this.mMainFragment != null) {
                        MainActivityMgr.this.mMainFragment.setData(paramData0x00);
                    }
                }
            });
        }
    }
}