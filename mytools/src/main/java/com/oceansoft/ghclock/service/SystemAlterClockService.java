package com.oceansoft.ghclock.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.oceansoft.ghclock.R;
import com.oceansoft.ghclock.baidupush.Utils;
import com.oceansoft.ghclock.tts.TextToSpeak;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class SystemAlterClockService extends Service {
	private static final  String TAG = "Service_DeBug";
	
	private int lastAMPostDayDay = 0;
	private int lastPMPostDayDay = 0;
	
	private String appPkg_DingDing = "com.alibaba.android.rimet";
	private NotificationManager notificationManager;
	private final int NOTIFY_TAG = 999;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private boolean D = true;
	private Handler handler = null;

	public SystemAlterClockService() {
		
	}

	@Override
	public void onCreate() {
		super.onCreate();
		//initService();
		
		
		//加载BaiduPush
		loadBaiduPush();

		createHandler();
		
		loadDingDingAlterTask();
		
	}

	/**
	 * 启动DingDingt提醒的轮训任务
	 */
	private void loadDingDingAlterTask() {
		
		
		//启动DingDingt提醒的轮训任务
		Timer timer = new Timer(true);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				
				Calendar c = Calendar.getInstance();
				
			
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int minute = c.get(Calendar.MINUTE);

				if(hour == 8 && Math.abs(minute -40) <= 10)
						{
					int nowDay  = c.get(Calendar.DAY_OF_MONTH);
					if(nowDay != lastAMPostDayDay){
						lastAMPostDayDay = nowDay;
						handler.sendEmptyMessage(1);
						
					}
				}else if(hour == 17 && Math.abs(minute -40) <= 10){

					int nowDay  = c.get(Calendar.DAY_OF_MONTH);
					if(nowDay != lastPMPostDayDay){
						lastPMPostDayDay = nowDay;
						handler.sendEmptyMessage(1);
					}
				}

				
			}
		};
		timer.schedule(task,new Date(),1000*60*10);
	}

	/**
	 * 加载BaiduPush
	 */
	private void loadBaiduPush() {

		Resources resource = this.getResources();
		String pkgName = this.getPackageName();
		
		// Push: 以apikey的方式登录，一般放在主Activity的onCreate中。
		// 这里把apikey存放于manifest文件中，只是一种存放方式，
		// 您可以用自定义常量等其它方式实现，来替换参数中的Utils.getMetaValue(PushDemoActivity.this,
		// "api_key")
		// ！！ 请将AndroidManifest.xml 128 api_key 字段值修改为自己的 api_key 方可使用 ！！
		//！！ ATTENTION：You need to modify the value of api_key to your own at row 128 in AndroidManifest.xml to use this Demo !!
		PushManager.startWork(getApplicationContext(),

				PushConstants.LOGIN_TYPE_API_KEY,
				Utils.getMetaValue(SystemAlterClockService.this, "api_key"));
		// Push: 如果想基于地理位置推送，可以打开支持地理位置的推送的开关
		// PushManager.enableLbs(getApplicationContext());

		// Push: 设置自定义的通知样式，具体API介绍见用户手册，如果想使用系统默认的可以不加这段代码
		// 请在通知推送界面中，高级设置->通知栏样式->自定义样式，选中并且填写值：1，
		// 与下方代码中 PushManager.setNotificationBuilder(this, 1, cBuilder)中的第二个参数对应
		CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
				resource.getIdentifier(
						"notification_baidupush_builder", "layout", pkgName),
				resource.getIdentifier("notification_icon", "id", pkgName),
				resource.getIdentifier("notification_title", "id", pkgName),
				resource.getIdentifier("notification_text", "id", pkgName));
		cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
		cBuilder.setNotificationDefaults(Notification.DEFAULT_VIBRATE);
		cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
		cBuilder.setLayoutDrawable(resource.getIdentifier(
				"simple_notification_icon", "drawable", pkgName));
		cBuilder.setNotificationSound(Uri.withAppendedPath(
				MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "6").toString());
		// 推送高级设置，通知栏样式设置为下面的ID
		PushManager.setNotificationBuilder(this, 1, cBuilder);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);

		// TODO: 2015/12/3  startTask
	}
	
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void setAppAlertConfig(String appPkgName){
		PackageInfo pkgInfo = getAppInfoByPkgName(this, appPkgName) ;
		
		if(null == pkgInfo){
			if(D) Log.e(TAG, "未找到相应安装程序");
			return ;
		}

		Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(appPkgName);


		

		showDinDingAlert(LaunchIntent);
	}
	
	
	public  void showDinDingAlert(Intent launchIntent){
		if(launchIntent == null)
			return;
	
		PendingIntent pendingIntent = PendingIntent.getActivity(SystemAlterClockService.this, 0, launchIntent, 0);

		if(null == notificationManager) {
			notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		}

		NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
		bigTextStyle.setBigContentTitle("签到的时候到了~")
				.setSummaryText("Summary 签到了")
				.bigText("主人 我来喊您签到了~");

		TextToSpeak tts = new TextToSpeak(this," 赶紧签到撒 ，不签就晚喽~");

		Notification notification = new NotificationCompat.Builder(SystemAlterClockService.this)
				.setSmallIcon(R.drawable.btn_press)
				.setTicker("钉钉签到啦~")
				.setContentInfo(dateFormat.format(new Date()))
				.setContentTitle("钉钉提醒")
				.setContentText("及时打卡，准时下班")

				.setStyle(bigTextStyle)

				.setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_ALL)
				.setContentIntent(pendingIntent)
				.build();

		notificationManager.notify(NOTIFY_TAG, notification);
	}



	/**获得手机内应用的包名，返回一个List集合**/
	public static List<PackageInfo> getAllApps(Context context) {
		List<PackageInfo> apps = new ArrayList<PackageInfo>();
		PackageManager packageManager = context.getPackageManager();
		//获取手机内所有应用     
		List<PackageInfo> paklist = packageManager.getInstalledPackages(0);
		for (int i = 0; i < paklist.size(); i++) {
			PackageInfo pak = (PackageInfo) paklist.get(i);
			//判断是否为非系统预装的应用  (大于0为系统预装应用，小于等于0为非系统应用)   
			if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
				apps.add(pak);
			}
		}
		return apps;
	}

	public PackageInfo getAppInfoByPkgName(Context context,String appPkgName) {

		List<PackageInfo> appPkgs = getAllApps(context);
		for (int i = 0;i<appPkgs.size();i++){
			if(appPkgs.get(i).packageName.contains(appPkgName)){
				return appPkgs.get(i);
			}
		}
		return null;
	}
	
	public void createHandler(){

		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {

				switch (msg.what){
					//钉钉打卡提醒
					case 1 :
						setAppAlertConfig(appPkg_DingDing);
				}
			}
		};
	}
}
