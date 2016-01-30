package com.oceansoft.ghclock.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/12/29.
 */
public class InstallBroadcast extends BroadcastReceiver {
	private static InstallBroadcast mReceiver = new InstallBroadcast();

	private static IntentFilter mIntentFilter;

	public static void registerReceiver(Context context) {
		mIntentFilter = new IntentFilter();
		mIntentFilter.addDataScheme("package");
		mIntentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
		mIntentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		mIntentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
		context.registerReceiver(mReceiver, mIntentFilter);
	}

	public static void unregisterReceiver(Context context) {
		context.unregisterReceiver(mReceiver);
	}

	@Override
	 public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		String packageName = intent.getData().getSchemeSpecificPart();
		if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
			Toast.makeText(context, "添加了新的应用", Toast.LENGTH_LONG).show();
			PackageManager pm = context.getPackageManager();
			Intent newIntent = new Intent();
			newIntent = pm.getLaunchIntentForPackage(packageName);
			newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(newIntent);
		} else if (Intent.ACTION_PACKAGE_REMOVED.equals(action)) {
			Toast.makeText(context, "有应用被删除", Toast.LENGTH_LONG).show();
		} else if (Intent.ACTION_PACKAGE_REPLACED.equals(action)) {
			Toast.makeText(context, "有应用被替换", Toast.LENGTH_LONG).show();
		}
	}



}
