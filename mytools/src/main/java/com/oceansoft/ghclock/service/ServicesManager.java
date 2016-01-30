package com.oceansoft.ghclock.service;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2015/12/17.
 */
public class ServicesManager {

	/**
	 * 用来判断服务是否运行.
	 *
	 * @param mContext
	 * @param className 判断的服务名字
	 * @return true 在运行 false 不在运行
	 */

	public static boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);

		List<ActivityManager.RunningServiceInfo> serviceList
				= activityManager.getRunningServices(100);
		if (!(serviceList.size() > 0)) {
			return false;
		}

		for (int i = 0; i < serviceList.size(); i++) {

			if (serviceList.get(i).service.getClassName().equals(className) == true) {

				isRunning = true;
				break;
			}
		}
		return isRunning;
	}
}
