package com.oceansoft.ghclock.androidui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.oceansoft.ghclock.R;
import com.oceansoft.ghclock.adapter.ToolsChoiceAdapter;
import com.oceansoft.ghclock.recevier.InstallBroadcast;
import com.oceansoft.ghclock.service.ServicesManager;
import com.oceansoft.ghclock.service.SystemAlterClockService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/11/26.
 */
public class ToolsSmaples extends Activity{
	private ListView listView;
	private List<String> items;
	private List<String> pkgs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_allsmaples);

		if(!ServicesManager.isServiceRunning(this, "com.oceansoft.ghclock.service.SystemAlterClockService")) {
			Intent service = new Intent(this, SystemAlterClockService.class);
			this.startService(service);
		}
		
		listView = (ListView) findViewById(R.id.lv_smaples);
		items = Arrays.asList(getResources().getStringArray(R.array.toolss_items));
		pkgs = Arrays.asList(getResources().getStringArray(R.array.tools_pkgs));

		ToolsChoiceAdapter tcAdapter = new ToolsChoiceAdapter(ToolsSmaples.this,
				items,pkgs);
		listView.setAdapter(tcAdapter);

		InstallBroadcast.registerReceiver(getApplicationContext());
	}

	
}
