package android.nla.org.gotway.ui.adapter;

import android.content.Context;
import android.nla.org.gotway.R;
import android.nla.org.gotway.ble.cmd.CMDMgr;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class SettingListAdapter
        extends BaseExpandableListAdapter {
    private static final int[][] CHILD_STR_ID;
    private static final byte[][][] CMDS;
    private static final int[] GROUP_STR_ID = {R.string.setMode, R.string.setWarning, R.string.setPaddleSpeed, R.string.setCorrect};
    private LayoutInflater inflater;

    static {
        int[] localObject = {
                R.string.setPaddleSpeedA,
                R.string.setPaddleSpeedS,
                R.string.setPaddleSpeedD,
                R.string.setPaddleSpeedF,
                R.string.setPaddleSpeedG,
                R.string.setPaddleSpeedH,
                R.string.setPaddleSpeedJ,
                R.string.setPaddleSpeedK,
                R.string.setPaddleSpeedL,
                R.string.setPaddleSpeedCancel};

        CHILD_STR_ID = new int[][]{
                {R.string.setModeExplode, R.string.setModeComfortable, R.string.setModeSoft},
                {R.string.setWarningFirst, R.string.setWarningSecond, R.string.setWarningOpenAll},
                localObject,
                {R.string.setCorrectStart}
        };
        byte[][] arrayOfByte13 = {CMDMgr.MODE_EXPLORE, CMDMgr.MODE_COMFORTABLE, CMDMgr.MODE_SOFT};
        byte[] arrayOfByte0 = CMDMgr.ALARM_FIRST;
        byte[] arrayOfByte1 = CMDMgr.ALARM_SECOND;
        byte[] arrayOfByte2 = CMDMgr.ALARM_OPEN;
        byte[] arrayOfByte3 = CMDMgr.PADDLE_A;
        byte[] arrayOfByte4 = CMDMgr.PADDLE_S;
        byte[] arrayOfByte5 = CMDMgr.PADDLE_D;
        byte[] arrayOfByte6 = CMDMgr.PADDLE_F;
        byte[] arrayOfByte7 = CMDMgr.PADDLE_G;
        byte[] arrayOfByte8 = CMDMgr.PADDLE_H;
        byte[] arrayOfByte9 = CMDMgr.PADDLE_J;
        byte[] arrayOfByte10 = CMDMgr.PADDLE_K;
        byte[] arrayOfByte11 = CMDMgr.PADDLE_L;
        byte[] arrayOfByte12 = CMDMgr.PADDLE_CANCEL;
        byte[][] arrayOfByte14 = {CMDMgr.CORRECT_START};
        CMDS = new byte[][][]{arrayOfByte13, {arrayOfByte0, arrayOfByte1, arrayOfByte2}, {arrayOfByte3, arrayOfByte4, arrayOfByte5, arrayOfByte6, arrayOfByte7, arrayOfByte8, arrayOfByte9, arrayOfByte10, arrayOfByte11, arrayOfByte12}, arrayOfByte14};
    }

    public SettingListAdapter(Context paramContext) {
        this.inflater = LayoutInflater.from(paramContext);
    }

    public static byte[] getCMDByPosition(int paramInt1, int paramInt2) {
        return CMDS[paramInt1][paramInt2];
    }

    public Object getChild(int paramInt1, int paramInt2) {
        return Integer.valueOf(CHILD_STR_ID[paramInt1][paramInt2]);
    }

    public long getChildId(int paramInt1, int paramInt2) {
        return paramInt2;
    }

    public View getChildView(int paramInt1, int paramInt2, boolean paramBoolean, View paramView, ViewGroup paramViewGroup) {
        View localView = paramView;
        if (localView == null) {
            localView = this.inflater.inflate(R.layout.setting_item, null);
        }
        TextView textView = (TextView) localView;
        textView.setLayoutParams(new AbsListView.LayoutParams(-1, (int) paramViewGroup.getContext().getResources().getDimension(R.dimen.item_height)));
        textView.setText(CHILD_STR_ID[paramInt1][paramInt2]);
        return localView;
    }

    public int getChildrenCount(int paramInt) {
        return CHILD_STR_ID[paramInt].length;
    }

    public Object getGroup(int paramInt) {
        return Integer.valueOf(GROUP_STR_ID[paramInt]);
    }

    public int getGroupCount() {
        return GROUP_STR_ID.length;
    }

    public long getGroupId(int paramInt) {
        return paramInt;
    }

    public View getGroupView(int paramInt, boolean paramBoolean, View paramView, ViewGroup paramViewGroup) {
        View localView = paramView;
        if (localView == null) {
            localView = this.inflater.inflate(R.layout.setting_group, null);
        }
        TextView textView = (TextView) localView;
        textView.setLayoutParams(new AbsListView.LayoutParams(-1, (int) paramViewGroup.getContext().getResources().getDimension(R.dimen.settingItemTittleHeight)));
        textView.setPadding(paramView.getPaddingLeft(), paramView.getPaddingTop(), paramView.getPaddingRight(), (int) paramViewGroup.getContext().getResources().getDimension(R.dimen.settingItemTittlePaddingBottom));
        textView.setText(GROUP_STR_ID[paramInt]);
        return localView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int paramInt1, int paramInt2) {
        return true;
    }
}