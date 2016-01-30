package com.oceansoft.ghclock.androidui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass.Device.Major;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.oceansoft.ghclock.R;
import com.oceansoft.ghclock.bluetoothwork.BluetoothChatService;
import com.oceansoft.ghclock.bluetoothwork.DeviceListActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/11/27.
 */
public class BluetoothSmapleActivity  extends Activity implements View.OnClickListener{

	
	private String btName;
	

	private Map<String,BluetoothDevice> mNewDevicesAdapter;
	private Map<String,BluetoothDevice> mBondedDevicesAdapter;

	

	BluetoothAdapter btAdaptor;
	BroadcastReceiver mReceiver ;
	//objects
	private BluetoothDevice mOptionDevice;
	private BluetoothChatService mChatService;
	private StringBuffer mOutStringBuffer;
	private BluetoothAdapter mBluetoothAdapter;
	//widgets
	private TextView tv_bt_message,tv_device_state;
	private EditText et_SendMessage;
	private Button btn_senMsg, btn_linkBlueTooth;



	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	public static final int DEVICE_CHOICE = 6 ;

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";
	
	//varils
	private final String TAG = "Bluetooth";
	private static final boolean D = true;
	private boolean secure = true;
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	private String mPairedDeviceName;
	private static final int BT_DECEIVE_CHOICE = 103;
	private static final int BT_REQUEST_ENABLE = 102;
	private String bt_pwd = "1234";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		//初始化插件
		initWidgets();
		//注册广播
		//registBrodcast();
		//初始化蓝牙
		//initBluetooth();
		
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
			if(D) Log.e(TAG,"+mBluetoothAdapter为空");
			return;
		}
		
	}


	@Override
	protected void onStart() {
		
		super.onStart();
		initBluetooth();
		
	}

	private void initWidgets() {
		setContentView(R.layout.avticity_bluetoothsmaple);
		
		btn_senMsg = (Button) findViewById(R.id.btn_sendtobluetooth);
		btn_linkBlueTooth = (Button) findViewById(R.id.btn_linkbluetooth);
		tv_bt_message = (TextView) findViewById(R.id.tv_bt_message);
		tv_device_state = (TextView) findViewById(R.id.tv_device_state);
		et_SendMessage = (EditText) findViewById(R.id.et_sendtobluetooth);
		

		btn_senMsg.setOnClickListener(this);
		btn_linkBlueTooth.setOnClickListener(this);
		tv_device_state.setOnClickListener(this);
		
		tv_device_state.setText(getResources().getString(R.string.device_noconnect));
	}

	/**
	 * 注册蓝牙检索广播
	 */
	private void registBrodcast() {
		mNewDevicesAdapter = new HashMap<String,BluetoothDevice>();
		mBondedDevicesAdapter = new HashMap<String,BluetoothDevice>();
		mReceiver = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();

				//找到设备
				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					
					BluetoothDevice device = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					
					
					
					if (device.getBondState() != BluetoothDevice.BOND_BONDED) {

						Log.v(TAG, "find device:" + device.getName()

								+ device.getAddress());

						if(!mNewDevicesAdapter.keySet().contains(device.getAddress())){
							mNewDevicesAdapter.put(device.getAddress(),device);
						}
					}else{
						mBondedDevicesAdapter.put(device.getAddress(),device);
					}
				

			}
			//搜索完成

			else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED

					.equals(action))

			{


				//setTitle("搜索完成");
				if (mNewDevicesAdapter.size() == 0) {


					Log.v(TAG, "find over");

				}
			}
		}

	};
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED
		);
		intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
		registerReceiver(mReceiver, intentFilter);
		
	}

	

	/**
	 * 初始化蓝牙设备
	 */
	private void initBluetooth() {

		

		if (!mBluetoothAdapter.isEnabled()) {
			if(false) {
				Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableIntent, BT_REQUEST_ENABLE);
			}else{
				mBluetoothAdapter.enable();
			}
		}{

			
		}


		// Get a set of currently paired devices
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		if(pairedDevices.size() <= 0){
			if(D) Log.e(TAG,"+无配对蓝牙");
			Toast.makeText(this,"无配对蓝牙，请先选择蓝牙进行配对.",Toast.LENGTH_SHORT).show();
			return;
		}
		if(D) {
			String devicesSet = "";
			for (BluetoothDevice device : pairedDevices) {
				//types.add(device.getBluetoothClass().getDeviceClass());
				switch (device.getBluetoothClass().getMajorDeviceClass()){
					
					case   Major.COMPUTER:
						devicesSet += "++ 电脑" +device.getName();
						break;
					case  Major.PHONE:
						devicesSet += "++ 手机" +device.getName();
						break;
					default:
						devicesSet += "++ 未支持设备+" +device.getName();
				}

			}
			
			
			Log.e(TAG,devicesSet);
		}
		
		for(BluetoothDevice device:pairedDevices){
			if(!mBluetoothAdapter.checkBluetoothAddress(device.getAddress())){

				if(D) Log.e(TAG,"++AddressErrOR++");
				continue;
			}
			
			mOptionDevice = device;

			
			mPairedDeviceName = device.getName();
			tv_device_state.setText(getResources().getString(R.string.device_paired)+ mPairedDeviceName);
			break;
		}
	}

	private void ensureDiscoverable() {
		if (mBluetoothAdapter.getScanMode() !=
				BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
		if (mChatService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't started already
			if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
				// Start the Bluetooth chat services
				mChatService.start();

			}
		}else{
			setupChat();
			mChatService.start();
		}

		
	}
	
	class conectTimer extends AsyncTask<Void,Void,Boolean>{


		@Override
		protected Boolean doInBackground(Void... params) {
			while (true) {
	
				
				
				if (1 < 0) {
					break;
				}
			}
			return true;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mChatService != null) mChatService.stop();
	}

	/**
	 * Sends a message.
	 * @param message  A string of text to send.
	 */
	private void sendMessage(String message) {
		// Check that we're actually connected before trying anything
		if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
			return;
		}

		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothChatService to write
			byte[] send = message.getBytes();
			mChatService.write(send);

			// Reset out string buffer to zero and clear the edit text field
			mOutStringBuffer.setLength(0);
			et_SendMessage.setText(mOutStringBuffer);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == BT_REQUEST_ENABLE) {
			btAdaptor.enable();
		}
		
		if (null != data) {
			mChatService.stop();
			mChatService.start();
			
			if (requestCode == BT_DECEIVE_CHOICE) {
				connectDevice(data, secure);
			}
		}	
		
		
	}

	

	@Override
	public void onClick(View v) {
		
		switch (v.getId()){
			case R.id.btn_sendtobluetooth:
				sendMessage(et_SendMessage.getText().toString());
				break;
			case R.id.tv_device_state:
				showDevices();
				break;
			case R.id.btn_linkbluetooth:
				linkBluetooth();
				break;
			default:
				break;
		}
		
	}

	/**
	 * 选择蓝牙进行配对或连接
	 */
	private void showDevices() {
		Intent intent = new Intent(BluetoothSmapleActivity.this, DeviceListActivity.class);
		startActivityForResult(intent,BT_DECEIVE_CHOICE);
	}

	private void setupChat() {
		// Initialize the array adapter for the conversation thread
		tv_bt_message.setText("");
		// Initialize the BluetoothChatService to perform bluetooth connections
		mChatService = new BluetoothChatService(this, mHandler);
		// Initialize the buffer for outgoing messages
		mOutStringBuffer = new StringBuffer("");
		
	}
	
	private  void connectDevice(Intent data, boolean secure) {
		// Get the device MAC address
		String address = data.getExtras()
				.getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
		// Get the BluetoothDevice object
		tv_device_state.setText( "配对中");
		BluetoothDevice device = BluetoothChatService.pair(address,bt_pwd);
		// Attempt to connect to the device
		if (null == device) {
			tv_device_state.setText( "未连接");
		}else {
			
			mOptionDevice = device;
			tv_device_state.setText("已配对：" + mOptionDevice.getName());
			mChatService.connect(device, secure);
			
		}
	}


	private void linkBluetooth() {

		if (null == mOptionDevice) {
			Toast.makeText(this,"未找到匹配蓝牙设备，请手动选择",Toast.LENGTH_SHORT).show();
			return;
		}
		String address = mOptionDevice.getAddress();
		// Create the result Intent and include the MAC address
		Intent intent = new Intent();
		intent.putExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS, address);
		connectDevice(intent, secure);
		
	}


	// The Handler that gets information back from the BluetoothChatService
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MESSAGE_STATE_CHANGE:
					switch (msg.arg1) {
						case BluetoothChatService.STATE_CONNECTED:
							tv_bt_message.setText("");
							break;
						case BluetoothChatService.STATE_CONNECTING:
							break;
						case BluetoothChatService.STATE_LISTEN:
						case BluetoothChatService.STATE_NONE:
							break;
					}
					break;
				case MESSAGE_WRITE:
					byte[] writeBuf = (byte[]) msg.obj;
					// construct a string from the buffer
					String writeMessage = new String(writeBuf);
					tv_bt_message.setText("Me:  " + writeMessage + "\n" + tv_bt_message.getText().toString());
					break;
				case MESSAGE_READ:
					byte[] readBuf = (byte[]) msg.obj;
					// construct a string from the valid bytes in the buffer
					String readMessage = new String(readBuf, 0, msg.arg1);
					tv_bt_message.setText(mPairedDeviceName + ":  " + readMessage + "\n" + tv_bt_message.getText().toString());
					break;
				case MESSAGE_DEVICE_NAME:
					// save the connected device's name
					mPairedDeviceName = msg.getData().getString(DEVICE_NAME);
					Toast.makeText(getApplicationContext(), "Connected to "
							+ mPairedDeviceName, Toast.LENGTH_SHORT).show();

					tv_device_state.setText("已配对：" + mPairedDeviceName);
					break;
				case MESSAGE_TOAST:
					Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
							Toast.LENGTH_SHORT).show();
					break;
					
			}
		}
	};
	
}