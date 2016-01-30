package com.oceansoft.ghclock.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * AutoServiceStartupReceiver
 *
 * @author liuguanghui
 * @date
 */
public class AutoServiceStartupReceiver extends BroadcastReceiver {
	private boolean D = true;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(D) Log.e("Auto_Receiver","onReceive:"+intent.getAction().toString());
		// TODO Auto-generated method stub
		if(!ServicesManager.isServiceRunning(context, "com.oceansoft.ghclock.service.SystemAlterClockService")) {
			Intent service = new Intent(context, SystemAlterClockService.class);
			context.startService(service);
		}
	}

}
