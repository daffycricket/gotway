package android.nla.org.gotway.ble.scanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import com.reb.hola.util.DebugLogger;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class ScannerFragment
  extends DialogFragment
{
  private static final String CUSTOM_UUID = "custom_uuid";
  private static final boolean DEVICE_IS_BONDED = true;
  private static final boolean DEVICE_NOT_BONDED = false;
  static final int NO_RSSI = -1000;
  private static final String PARAM_UUID = "param_uuid";
  private static final long SCAN_DURATION = 5000L;
  private static final String TAG = "ScannerFragment";
  private DeviceListAdapter mAdapter;
  private BluetoothAdapter mBluetoothAdapter;
  private Handler mHandler = new Handler();
  private boolean mIsCustomUUID;
  private boolean mIsScanning = false;
  private LeScanCallback mLEScanCallback = new LeScanCallback()
  {
    public void onLeScan(BluetoothDevice paramAnonymousBluetoothDevice, int paramAnonymousInt, byte[] paramAnonymousArrayOfByte)
    {
      if (paramAnonymousBluetoothDevice != null)
      {
        ScannerFragment.this.updateScannedDevice(paramAnonymousBluetoothDevice, paramAnonymousInt);
        if (!ScannerFragment.this.mIsCustomUUID) {}
      }
      else
      {
        try
        {
          if (ScannerServiceParser.decodeDeviceAdvData(paramAnonymousArrayOfByte, ScannerFragment.this.mUuid)) {
            ScannerFragment.this.addScannedDevice(paramAnonymousBluetoothDevice, ScannerServiceParser.decodeDeviceName(paramAnonymousArrayOfByte), paramAnonymousInt, false);
          }
          return;
        }
        catch (Exception paramAnonymousBluetoothDevice)
        {
          DebugLogger.e("ScannerFragment", "Invalid data in Advertisement packet " + paramAnonymousBluetoothDevice.toString());
          return;
        }
      }
      ScannerFragment.this.addScannedDevice(paramAnonymousBluetoothDevice, ScannerServiceParser.decodeDeviceName(paramAnonymousArrayOfByte), paramAnonymousInt, false);
    }
  };
  private OnDeviceSelectedListener mListener;
  private Button mScanButton;
  private UUID mUuid;
  
  private void addBondedDevices()
  {
    Iterator localIterator = this.mBluetoothAdapter.getBondedDevices().iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return;
      }
      BluetoothDevice localBluetoothDevice = (BluetoothDevice)localIterator.next();
      this.mAdapter.addBondedDevice(new ExtendedBluetoothDevice(localBluetoothDevice, localBluetoothDevice.getName(), 64536, true));
    }
  }
  
  private void addScannedDevice(final BluetoothDevice paramBluetoothDevice, final String paramString, final int paramInt, final boolean paramBoolean)
  {
    getActivity().runOnUiThread(new Runnable()
    {
      public void run()
      {
        ScannerFragment.this.mAdapter.addOrUpdateDevice(new ExtendedBluetoothDevice(paramBluetoothDevice, paramString, paramInt, paramBoolean));
      }
    });
  }
  
  public static ScannerFragment getInstance(Context paramContext, UUID paramUUID, boolean paramBoolean)
  {
    paramContext = new ScannerFragment();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("param_uuid", new ParcelUuid(paramUUID));
    localBundle.putBoolean("custom_uuid", paramBoolean);
    paramContext.setArguments(localBundle);
    return paramContext;
  }
  
  private void startScan()
  {
    this.mAdapter.clearDevices();
    this.mScanButton.setText(2131165219);
    this.mIsCustomUUID = true;
    if (this.mIsCustomUUID) {
      this.mBluetoothAdapter.startLeScan(this.mLEScanCallback);
    }
    for (;;)
    {
      this.mIsScanning = true;
      this.mHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          if (ScannerFragment.this.mIsScanning) {
            ScannerFragment.this.stopScan();
          }
        }
      }, 5000L);
      return;
      UUID localUUID = this.mUuid;
      BluetoothAdapter localBluetoothAdapter = this.mBluetoothAdapter;
      LeScanCallback localLeScanCallback = this.mLEScanCallback;
      localBluetoothAdapter.startLeScan(new UUID[] { localUUID }, localLeScanCallback);
    }
  }
  
  private void stopScan()
  {
    if (this.mIsScanning)
    {
      this.mScanButton.setText(2131165217);
      this.mBluetoothAdapter.stopLeScan(this.mLEScanCallback);
      this.mIsScanning = false;
    }
  }
  
  private void updateScannedDevice(final BluetoothDevice paramBluetoothDevice, final int paramInt)
  {
    getActivity().runOnUiThread(new Runnable()
    {
      public void run()
      {
        ScannerFragment.this.mAdapter.updateRssiOfBondedDevice(paramBluetoothDevice.getAddress(), paramInt);
      }
    });
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    try
    {
      this.mListener = ((OnDeviceSelectedListener)paramActivity);
      return;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new ClassCastException(paramActivity.toString() + " must implement OnDeviceSelectedListener");
    }
  }
  
  public void onCancel(DialogInterface paramDialogInterface)
  {
    super.onCancel(paramDialogInterface);
    this.mListener.onDialogCanceled();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    paramBundle = getArguments();
    if (paramBundle.containsKey("custom_uuid")) {
      this.mUuid = ((ParcelUuid)paramBundle.getParcelable("param_uuid")).getUuid();
    }
    this.mIsCustomUUID = paramBundle.getBoolean("custom_uuid");
    this.mBluetoothAdapter = ((BluetoothManager)getActivity().getSystemService("bluetooth")).getAdapter();
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    final Object localObject = new Builder(getActivity());
    View localView = LayoutInflater.from(getActivity()).inflate(2130903045, null);
    ListView localListView = (ListView)localView.findViewById(16908298);
    localListView.setEmptyView(localView.findViewById(16908292));
    DeviceListAdapter localDeviceListAdapter = new DeviceListAdapter(getActivity());
    this.mAdapter = localDeviceListAdapter;
    localListView.setAdapter(localDeviceListAdapter);
    ((Builder)localObject).setTitle(2131165218);
    localObject = ((Builder)localObject).setView(localView).create();
    localListView.setOnItemClickListener(new OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        ScannerFragment.this.stopScan();
        localObject.dismiss();
        paramAnonymousAdapterView = (ExtendedBluetoothDevice)ScannerFragment.this.mAdapter.getItem(paramAnonymousInt);
        ScannerFragment.this.mListener.onDeviceSelected(paramAnonymousAdapterView.device, paramAnonymousAdapterView.name);
      }
    });
    this.mScanButton = ((Button)localView.findViewById(2131361798));
    this.mScanButton.setOnClickListener(new OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (paramAnonymousView.getId() == 2131361798)
        {
          if (ScannerFragment.this.mIsScanning) {
            localObject.cancel();
          }
        }
        else {
          return;
        }
        ScannerFragment.this.startScan();
      }
    });
    if (paramBundle == null) {
      startScan();
    }
    return (Dialog)localObject;
  }
  
  public void onDestroyView()
  {
    stopScan();
    super.onDestroyView();
  }
  
  public static abstract interface OnDeviceSelectedListener
  {
    public abstract void onDeviceSelected(BluetoothDevice paramBluetoothDevice, String paramString);
    
    public abstract void onDialogCanceled();
  }
}


/* Location:              C:\tmp\dex2jar-2.0\classes-dex2jar.jar!\com\reb\hola\ble\scanner\ScannerFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */