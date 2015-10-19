package com.inpes.android.myautoservices;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 2015/10/10.
 */
public class ServicesMangerActivity  extends Activity implements View.OnClickListener{
	private String  myClassName = "com.inpes.android.myautoservices.ScanWatchService";
	
	private Button btn_startService,btn_stopServoce;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_servicesmanager);

		btn_startService = (Button) findViewById(R.id.button);
		btn_startService.setOnClickListener(this);
		btn_stopServoce = (Button) findViewById(R.id.button2);
		btn_stopServoce.setOnClickListener(this);

		caseServiceStat();
	}

	private void caseServiceStat() {

		ActivityManager mActivityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);

		List<ActivityManager.RunningServiceInfo> mServiceList = mActivityManager.getRunningServices(100);



		boolean b = MusicServiceIsStart(mServiceList, myClassName);

		if(b){

			Toast.makeText(this, "服务已启动。", Toast.LENGTH_SHORT).show();
			btn_startService.setEnabled(false);
			btn_stopServoce.setEnabled(true);
		}else{
			Toast.makeText(this, "服务未启动。", Toast.LENGTH_SHORT).show();
			btn_startService.setEnabled(true);
			btn_stopServoce.setEnabled(false);
		}
	}


	//通过Service的类名来判断是否启动某个服务   
	private boolean MusicServiceIsStart(List<ActivityManager.RunningServiceInfo> mServiceList,String className){

		for(int i = 0; i < mServiceList.size(); i ++)

		{

			if(className.equals(mServiceList.get(i).service.getClassName()))

			{
				return true;
			}

		}

		return false;

	}

	//获取所有启动的服务的类名   
	private String getServiceClassName(List<ActivityManager.RunningServiceInfo> mServiceList){
		String res = "";

		for(int i = 0; i < mServiceList.size(); i ++){
		
			res+=mServiceList.get(i).service.getClassName()+ " \n";

		}

		return res;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.button:
				Intent service = new Intent(ServicesMangerActivity.this,ScanWatchService.class);
				startService(service);
				caseServiceStat();
				break;
			case R.id.button2:
				Intent service2 = new Intent(ServicesMangerActivity.this, ScanWatchService.class);
				stopService(service2);
				caseServiceStat();
				break;
			default:
				break;
		}
	}
}
