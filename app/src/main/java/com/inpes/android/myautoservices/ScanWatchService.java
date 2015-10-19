package com.inpes.android.myautoservices;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

public class ScanWatchService extends Service {
    private AudioManager audioManager;
    private MyVolumeReceiver mVolumeReceiver;
    private int count =0 ;
    @Override
    public IBinder onBind(Intent arg0) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void onCreate() {
    // TODO Auto-generated method stub
	super.onCreate();
	
	audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE) ;
       

	
	  mVolumeReceiver = new MyVolumeReceiver() ;
	  IntentFilter filter = new IntentFilter() ;
	  filter.addAction("android.media.VOLUME_CHANGED_ACTION") ;
	  registerReceiver(mVolumeReceiver, filter) ;

    }

    
    /**
     * 处理音量变化时的界面显示
     * @author long
     */
    private class MyVolumeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //如果音量发生变化则更改seekbar的位置
            if(intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")){
                //ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                //ComponentName cn = am.getRunningTasks(2).get(1).topActivity;
                
                count++;


                startApp();
                
                

                //Intent u = new Intent(context, CaptureActivity.class);
                //startActivity(u);


                // Intent newIntent = context.getPackageManager().getLaunchIntentForPackage("com.dtr.zbar.scan.CaptureActivity"); 
     	   	    //context.startActivity(newIntent);   
            }
            
            
        }


    }

    private void startApp() {
        String appPkg = "com.dtr.zbar.scan";
        
        if(isPkgInstalled(appPkg)) {
            Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(appPkg);

            startActivity(LaunchIntent);
        }
    }

    /**获得手机内应用的包名，返回一个List集合**/
    public List<PackageInfo> getAllApps() {
        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        PackageManager packageManager = this.getPackageManager();
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

    public boolean isPkgInstalled(String appPkg) {

        List<PackageInfo> appPkgs = getAllApps();
        for (int i = 0;i<appPkgs.size();i++){
            if(appPkgs.get(i).packageName.contains(appPkg)){
                return true;
            }
        }
        return false;
    }

}
