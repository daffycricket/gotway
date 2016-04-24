package android.nla.org.gotway.ble.profile;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.nla.org.gotway.ble.DataPraser;
import android.nla.org.gotway.ble.Util;
import android.nla.org.gotway.util.DebugLogger;

import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

public class BleCore {
    public static final UUID CHARACTER_UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
    public static final UUID CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    private static final String ERROR_CONNECTION_STATE_CHANGE = "Error on connection state change";
    private static final String ERROR_DISCOVERY_SERVICE = "Error on discovering services";
    private static final String ERROR_WRITE_DESCRIPTOR = "Error on writing descriptor";
    public static final UUID SERVICE_UUID = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
    private static final String TAG = "BleManager";
    private String address;
    private BluetoothGatt mBluetoothGatt;
    private BleManagerCallbacks mCallbacks;
    private BluetoothGattCharacteristic mCharacteristic;
    private Context mContext;
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        public void onCharacteristicChanged(BluetoothGatt paramAnonymousBluetoothGatt, BluetoothGattCharacteristic paramAnonymousBluetoothGattCharacteristic) {
            DebugLogger.d("BleManager", "onCharacteristicChanged---->");
            byte[] value = paramAnonymousBluetoothGattCharacteristic.getValue();
            DataPraser.praser(BleCore.this.mCallbacks, value);
        }

        public void onCharacteristicRead(BluetoothGatt paramAnonymousBluetoothGatt, BluetoothGattCharacteristic paramAnonymousBluetoothGattCharacteristic, int paramAnonymousInt) {
            DebugLogger.e("BleManager", "onCharacteristicRead---->");
        }

        public void onCharacteristicWrite(BluetoothGatt paramAnonymousBluetoothGatt, BluetoothGattCharacteristic paramAnonymousBluetoothGattCharacteristic, int paramAnonymousInt) {

            // TODO à quoi il sert ?
            //StringBuilder stringBuilder = new StringBuilder("onCharacteristicWrite---->").append(paramAnonymousBluetoothGattCharacteristic.getUuid().equals(BleCore.CHARACTER_UUID)).append("******");
            if (BleCore.this.mCallbacks != null) {
            }

            for (boolean bool = true; ; bool = false) {
                DebugLogger.d("BleManager", bool + "*****" + paramAnonymousBluetoothGattCharacteristic.getUuid().equals(BleCore.CHARACTER_UUID));
                if (paramAnonymousInt == 0) {
                    byte[] value = paramAnonymousBluetoothGattCharacteristic.getValue();
                    String str = Util.bytes2HexStr(value);
                    DebugLogger.e("BleManager", "write:" + str);
                    if ((paramAnonymousBluetoothGattCharacteristic.getUuid().equals(BleCore.CHARACTER_UUID)) && (Arrays.equals(value, BleCore.this.mLastWriteData)) && (BleCore.this.mCallbacks != null)) {
                        DebugLogger.e("BleManager", "write:" + str);
                        BleCore.this.mCallbacks.onWriteSuccess(BleCore.this.mLastWriteData);
                    }
                }
                return;
            }
        }

        public void onConnectionStateChange(BluetoothGatt paramAnonymousBluetoothGatt, int paramAnonymousInt1, int paramAnonymousInt2) {
            if (paramAnonymousInt1 == 0) {
                if (paramAnonymousInt2 == 2) {
                    DebugLogger.i("BleManager", "Device connected");
                    BleCore.this.mBluetoothGatt.discoverServices();
                    BleCore.this.mCallbacks.onDeviceConnected();
                }
                while (paramAnonymousInt2 != 0) {
                    return;
                }
                DebugLogger.i("BleManager", "Device disconnected");
                if (BleCore.this.mUserDisConnect) {
                    BleCore.this.mCallbacks.onDeviceDisconnected();
                }
//        BleCore.this.closeBluetoothGatt();
//        return;
//        BleCore.this.mCallbacks.onLinklossOccur();
//        BleCore.this.mUserDisConnect = false;
            }
            BleCore.this.onError("Error on connection state change", paramAnonymousInt1);
        }

        public void onDescriptorWrite(BluetoothGatt paramAnonymousBluetoothGatt, BluetoothGattDescriptor paramAnonymousBluetoothGattDescriptor, int paramAnonymousInt) {
            DebugLogger.e("BleManager", "onDescriptorWrite---->" + Arrays.toString(paramAnonymousBluetoothGattDescriptor.getValue()));
            if (paramAnonymousInt == 0) {
                if ((paramAnonymousBluetoothGattDescriptor.getUuid().equals(BleCore.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID)) && (paramAnonymousBluetoothGattDescriptor.getValue()[0] == BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE[0])) {
                    BleCore.this.mCallbacks.onNotifyEnable();
                    BleCore.this.mIsConnected = true;
                }
                return;
            }
            BleCore.this.onError("Error on writing descriptor", paramAnonymousInt);
        }

        public void onServicesDiscovered(BluetoothGatt paramAnonymousBluetoothGatt, int paramAnonymousInt) {
            if (paramAnonymousInt == 0) {
                Iterator localIterator = paramAnonymousBluetoothGatt.getServices().iterator();
                for (; ; ) {
                    if (!localIterator.hasNext()) {
                        if (BleCore.this.mCharacteristic != null) {
                            break;
                        }
                        BleCore.this.mCallbacks.onDeviceNotSupported();
                        paramAnonymousBluetoothGatt.disconnect();
                        return;
                    }
                    BluetoothGattService localBluetoothGattService = (BluetoothGattService) localIterator.next();
                    if (localBluetoothGattService.getUuid().equals(BleCore.SERVICE_UUID)) {
                        BleCore.this.mCharacteristic = localBluetoothGattService.getCharacteristic(BleCore.CHARACTER_UUID);
                        DebugLogger.i("BleManager", "service is found------" + BleCore.this.mCharacteristic);
                    }
                }
                BleCore.this.mCallbacks.onServicesDiscovered();
                BleCore.this.enableNotification(paramAnonymousBluetoothGatt);
                return;
            }
            BleCore.this.onError("Error on discovering services", paramAnonymousInt);
        }
    };
    private boolean mIsConnected;
    private byte[] mLastWriteData;
    private boolean mUserDisConnect;

    private void disConnect(boolean paramBoolean) {
        this.mUserDisConnect = paramBoolean;
        if (this.mBluetoothGatt != null) {
            this.mBluetoothGatt.disconnect();
        }
    }

    private void enableNotification(BluetoothGatt paramBluetoothGatt) {
        paramBluetoothGatt.setCharacteristicNotification(this.mCharacteristic, true);
        BluetoothGattDescriptor localBluetoothGattDescriptor = this.mCharacteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
        localBluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        paramBluetoothGatt.writeDescriptor(localBluetoothGattDescriptor);
    }

    public void closeBluetoothGatt() {
        if (this.mBluetoothGatt != null) {
            this.mBluetoothGatt.close();
            this.mBluetoothGatt = null;
        }
        this.mIsConnected = false;
    }

    public boolean connect(Context paramContext, BluetoothDevice paramBluetoothDevice) {
        DebugLogger.i("BleManager", "connect--" + paramBluetoothDevice.getAddress());
        this.mContext = paramContext;
        if (isConnected()) {
            if (paramBluetoothDevice.getAddress().equals(this.address)) {
                DebugLogger.d("BleManager", "是同一个设备，忽略掉");
                return true;
            }
            DebugLogger.d("BleManager", "不同设备，先断开");
            disConnect(false);
            return true;
        }
        this.address = paramBluetoothDevice.getAddress();
        if (this.mBluetoothGatt == null) {
            this.mBluetoothGatt = paramBluetoothDevice.connectGatt(this.mContext, false, this.mGattCallback);
            return true;
        }
        return this.mBluetoothGatt.connect();
    }

    public boolean connect(Context paramContext, String paramString) {
        if (BluetoothAdapter.checkBluetoothAddress(paramString)) {
            BluetoothAdapter localBluetoothAdapter = ((BluetoothManager) paramContext.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
            if (localBluetoothAdapter != null) {
                BluetoothDevice bluetoothDevice = localBluetoothAdapter.getRemoteDevice(paramString);
                if (bluetoothDevice != null) {
                    return connect(paramContext, bluetoothDevice);
                }
            }
        }
        return false;
    }

    public void disconnect() {
        disConnect(true);
    }

    public boolean isConnected() {
        return this.mIsConnected;
    }

    protected void onError(String paramString, int paramInt) {
        disConnect(false);
    }

    public void setGattCallbacks(BleManagerCallbacks paramBleManagerCallbacks) {
        this.mCallbacks = paramBleManagerCallbacks;
    }

    public boolean write(byte[] paramArrayOfByte) {
        if (this.mCharacteristic != null) {
            this.mCharacteristic.setValue(paramArrayOfByte);
            if (this.mBluetoothGatt.writeCharacteristic(this.mCharacteristic)) {
                this.mLastWriteData = paramArrayOfByte;
                return true;
            }
        }
        return false;
    }
}