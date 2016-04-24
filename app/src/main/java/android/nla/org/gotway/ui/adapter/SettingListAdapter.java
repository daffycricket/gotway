package android.nla.org.gotway.ui.adapter;

import android.content.Context;
import android.nla.org.gotway.ble.cmd.CMDMgr;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class SettingListAdapter
  extends BaseExpandableListAdapter
{
  private static final int[][] CHILD_STR_ID;
  private static final byte[][][] CMDS;
  private static final int[] GROUP_STR_ID = { 2131165195, 2131165199, 2131165203, 2131165215 };
  private LayoutInflater inflater;
  
  static
  {
    Object localObject = { 2131165204, 2131165205, 2131165206, 2131165207, 2131165208, 2131165209, 2131165210, 2131165211, 2131165212, 2131165214 };
    CHILD_STR_ID = new int[][] { { 2131165196, 2131165197, 2131165198 }, { 2131165200, 2131165201, 2131165202 }, localObject, { 2131165216 } };
    byte[][] arrayOfByte13 = { CMDMgr.MODE_EXPLORE, CMDMgr.MODE_COMFORTABLE, CMDMgr.MODE_SOFT };
    localObject = CMDMgr.ALARM_FIRST;
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
    byte[][] arrayOfByte14 = { CMDMgr.CORRECT_START };
    CMDS = new byte[][][] { arrayOfByte13, { localObject, arrayOfByte1, arrayOfByte2 }, { arrayOfByte3, arrayOfByte4, arrayOfByte5, arrayOfByte6, arrayOfByte7, arrayOfByte8, arrayOfByte9, arrayOfByte10, arrayOfByte11, arrayOfByte12 }, arrayOfByte14 };
  }
  
  public SettingListAdapter(Context paramContext)
  {
    this.inflater = LayoutInflater.from(paramContext);
  }
  
  public static byte[] getCMDByPosition(int paramInt1, int paramInt2)
  {
    return CMDS[paramInt1][paramInt2];
  }
  
  public Object getChild(int paramInt1, int paramInt2)
  {
    return Integer.valueOf(CHILD_STR_ID[paramInt1][paramInt2]);
  }
  
  public long getChildId(int paramInt1, int paramInt2)
  {
    return paramInt2;
  }
  
  public View getChildView(int paramInt1, int paramInt2, boolean paramBoolean, View paramView, ViewGroup paramViewGroup)
  {
    View localView = paramView;
    if (paramView == null) {
      localView = this.inflater.inflate(2130903051, null);
    }
    paramView = (TextView)localView;
    paramView.setLayoutParams(new AbsListView.LayoutParams(-1, (int)paramViewGroup.getContext().getResources().getDimension(2131099650)));
    paramView.setText(CHILD_STR_ID[paramInt1][paramInt2]);
    return localView;
  }
  
  public int getChildrenCount(int paramInt)
  {
    return CHILD_STR_ID[paramInt].length;
  }
  
  public Object getGroup(int paramInt)
  {
    return Integer.valueOf(GROUP_STR_ID[paramInt]);
  }
  
  public int getGroupCount()
  {
    return GROUP_STR_ID.length;
  }
  
  public long getGroupId(int paramInt)
  {
    return paramInt;
  }
  
  public View getGroupView(int paramInt, boolean paramBoolean, View paramView, ViewGroup paramViewGroup)
  {
    View localView = paramView;
    if (paramView == null) {
      localView = this.inflater.inflate(2130903050, null);
    }
    paramView = (TextView)localView;
    paramView.setLayoutParams(new AbsListView.LayoutParams(-1, (int)paramViewGroup.getContext().getResources().getDimension(2131099652)));
    paramView.setPadding(paramView.getPaddingLeft(), paramView.getPaddingTop(), paramView.getPaddingRight(), (int)paramViewGroup.getContext().getResources().getDimension(2131099651));
    paramView.setText(GROUP_STR_ID[paramInt]);
    return localView;
  }
  
  public boolean hasStableIds()
  {
    return true;
  }
  
  public boolean isChildSelectable(int paramInt1, int paramInt2)
  {
    return true;
  }
}