package com.oceansoft.ghclock.androidui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.oceansoft.ghclock.service.ServicesManager;
import com.oceansoft.ghclock.service.SystemAlterClockService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/17.
 */
public class ServicesManagerSmapleActivity extends Activity implements View.OnClickListener {

	Button btn_start,btn_stop;
	ListView lv;
	
	
	
	private Button btn_showProcess;
	private Button btn_showApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initWidgets();
		
		initService();

		showServiceList();
	}

	private void showServiceList() {


		ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);

		List<ActivityManager.RunningServiceInfo> serviceList
				= activityManager.getRunningServices(100);
		
		if (!(serviceList.size() > 0)) {
			return;
		}

		List<String> servicesClssName = new ArrayList<String>();
		for (int i = 0; i < serviceList.size(); i++) {
			servicesClssName.add(serviceList.get(i).service.getPackageName()+"+++"+serviceList.get(i).service.getClassName());
		}
		
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, servicesClssName));
		
	}


	private void showApppList() {


		ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);

		List<ActivityManager.RunningAppProcessInfo> serviceList
				= activityManager.getRunningAppProcesses();


		List<ActivityManager.RunningTaskInfo> taskList
				= activityManager.getRunningTasks(100);

		if (!(serviceList.size() > 0)) {
			return;
		}
		List<String> appClassName = new ArrayList<String>();
		for (int i = 0; i < serviceList.size(); i++) {
			appClassName.add(serviceList.get(i).processName);
		}

		lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,appClassName));

	}
	private void initService() {
		if(!ServicesManager.isServiceRunning(this, "com.oceansoft.ghclock.service.SystemAlterClockService")) {
			btn_start.setEnabled(true);
			btn_stop.setEnabled(false);
		}else{

			
			btn_start.setEnabled(false);
			btn_stop.setEnabled(true);
		}
	}

	private void initWidgets() {
		LinearLayout ln = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		ln.setLayoutParams(params);
		ln.setOrientation(LinearLayout.VERTICAL);
		
		lv = new ListView(this);
		
		
		
		btn_start = new Button(this);
		btn_start.setTag("Start");
		btn_start.setTextSize(16);
		btn_start.setText("启动服务");
		
		btn_stop = new Button(this);
		btn_stop.setTextSize(16);
		btn_stop.setTag("Stop");
		btn_stop.setText("停止服务");

		btn_showProcess = new Button(this);
		btn_showProcess.setTextSize(16);
		btn_showProcess.setTag("ShowProcess");
		btn_showProcess.setText("显示Service");

		btn_showApp = new Button(this);
		btn_showApp.setTextSize(16);
		btn_showApp.setTag("ShowApp");
		btn_showApp.setText("显示App");
		
		
		btn_start.setOnClickListener(this);
		btn_stop.setOnClickListener(this);
		btn_showApp.setOnClickListener(this);
		btn_showProcess.setOnClickListener(this);
		
		LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
		childParams.gravity = Gravity.CENTER_HORIZONTAL;
		
		ln.addView(btn_start,childParams);
		ln.addView(btn_stop,childParams);
		ln.addView(btn_showApp,childParams);
		ln.addView(btn_showProcess,childParams);
		ln.addView(lv);
		setContentView(ln);
		
	}

	@Override
	public void onClick(View v) {
		try {
			if (v.getTag().toString().equalsIgnoreCase("Start")) {

				Intent service = new Intent(this, SystemAlterClockService.class);
				this.startService(service);

				btn_start.setEnabled(false);
				btn_stop.setEnabled(true);


			} else if (v.getTag().toString().equalsIgnoreCase("Stop")) {

				Intent service = new Intent(this, SystemAlterClockService.class);
				this.stopService(service);

				btn_start.setEnabled(true);
				btn_stop.setEnabled(false);
			} else if (v.getTag().toString().equalsIgnoreCase("ShowProcess"))
			{
				showServiceList();
			}else if(v.getTag().toString().equalsIgnoreCase("ShowApp")){
			
				showApppList();
			}
		
		}catch(Exception e){
			
			
		}
	}
}
