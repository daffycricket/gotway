package android.nla.org.gotway.ble.scanner;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class DeviceListAdapter
  extends BaseAdapter
{
  private static final int TYPE_EMPTY = 2;
  private static final int TYPE_ITEM = 1;
  private static final int TYPE_TITLE = 0;
  private final ExtendedBluetoothDevice.AddressComparator comparator = new ExtendedBluetoothDevice.AddressComparator();
  private final Context mContext;
  private final ArrayList<ExtendedBluetoothDevice> mListBondedValues = new ArrayList();
  private final ArrayList<ExtendedBluetoothDevice> mListValues = new ArrayList();
  
  public DeviceListAdapter(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public void addBondedDevice(ExtendedBluetoothDevice paramExtendedBluetoothDevice)
  {
    this.mListBondedValues.add(paramExtendedBluetoothDevice);
    notifyDataSetChanged();
  }
  
  public void addOrUpdateDevice(ExtendedBluetoothDevice paramExtendedBluetoothDevice)
  {
    if (this.mListBondedValues.contains(paramExtendedBluetoothDevice)) {
      return;
    }
    int i = this.mListValues.indexOf(paramExtendedBluetoothDevice);
    if (i >= 0)
    {
      ((ExtendedBluetoothDevice)this.mListValues.get(i)).rssi = paramExtendedBluetoothDevice.rssi;
      notifyDataSetChanged();
      return;
    }
    this.mListValues.add(paramExtendedBluetoothDevice);
    notifyDataSetChanged();
  }
  
  public boolean areAllItemsEnabled()
  {
    return false;
  }
  
  public void clearDevices()
  {
    this.mListValues.clear();
    notifyDataSetChanged();
  }
  
  public int getCount()
  {
    int j = this.mListBondedValues.size() + 1;
    if (this.mListValues.isEmpty()) {}
    for (int i = 2; j == 1; i = this.mListValues.size() + 1) {
      return i;
    }
    return i + j;
  }
  
  public Object getItem(int paramInt)
  {
    this.mListBondedValues.size();
    if (this.mListBondedValues.isEmpty())
    {
      if (paramInt == 0) {
        return Integer.valueOf(2131165220);
      }
      return this.mListValues.get(paramInt - 1);
    }
    return "";
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public int getItemViewType(int paramInt)
  {
    if (paramInt == 0) {}
    while ((!this.mListBondedValues.isEmpty()) && (paramInt == this.mListBondedValues.size() + 1)) {
      return 0;
    }
    if ((paramInt == getCount() - 1) && (this.mListValues.isEmpty())) {
      return 2;
    }
    return 1;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    Object localObject = LayoutInflater.from(this.mContext);
    int i = getItemViewType(paramInt);
    View localView;
    TextView localTextView;
    switch (i)
    {
    case 1: 
    default: 
      localView = paramView;
      if (paramView == null)
      {
        localView = ((LayoutInflater)localObject).inflate(2130903042, paramViewGroup, false);
        paramView = new ViewHolder(null);
        paramView.name = ((TextView)localView.findViewById(2131361795));
        paramView.address = ((TextView)localView.findViewById(2131361796));
        paramView.rssi = ((ImageView)localView.findViewById(2131361794));
        localView.setTag(paramView);
      }
      paramViewGroup = (ExtendedBluetoothDevice)getItem(paramInt);
      localObject = (ViewHolder)localView.getTag();
      paramView = paramViewGroup.name;
      localTextView = ((ViewHolder)localObject).name;
      if (paramView == null) {
        break;
      }
    }
    for (;;)
    {
      localTextView.setText(paramView);
      ((ViewHolder)localObject).address.setText(paramViewGroup.device.getAddress());
      if ((paramViewGroup.isBonded) && (paramViewGroup.rssi == 64536)) {
        break;
      }
      paramInt = (int)(100.0F * (127.0F + paramViewGroup.rssi) / 147.0F);
      ((ViewHolder)localObject).rssi.setImageLevel(paramInt);
      ((ViewHolder)localObject).rssi.setVisibility(0);
      do
      {
        return localView;
        localView = paramView;
      } while (paramView != null);
      return ((LayoutInflater)localObject).inflate(2130903041, paramViewGroup, false);
      localView = paramView;
      if (paramView == null) {
        localView = ((LayoutInflater)localObject).inflate(2130903043, paramViewGroup, false);
      }
      ((TextView)localView).setText(((Integer)getItem(paramInt)).intValue());
      return localView;
      paramView = this.mContext.getString(2131165222);
    }
    ((ViewHolder)localObject).rssi.setVisibility(8);
    return localView;
  }
  
  public int getViewTypeCount()
  {
    return 3;
  }
  
  public boolean isEnabled(int paramInt)
  {
    return getItemViewType(paramInt) == 1;
  }
  
  public void updateRssiOfBondedDevice(String paramString, int paramInt)
  {
    this.comparator.address = paramString;
    int i = this.mListBondedValues.indexOf(this.comparator);
    if (i >= 0)
    {
      ((ExtendedBluetoothDevice)this.mListBondedValues.get(i)).rssi = paramInt;
      notifyDataSetChanged();
    }
  }
  
  private class ViewHolder
  {
    private TextView address;
    private TextView name;
    private ImageView rssi;
    
    private ViewHolder() {}
  }
}


/* Location:              C:\tmp\dex2jar-2.0\classes-dex2jar.jar!\com\reb\hola\ble\scanner\DeviceListAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */