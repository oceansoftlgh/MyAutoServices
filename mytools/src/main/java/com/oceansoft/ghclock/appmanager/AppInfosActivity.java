package com.oceansoft.ghclock.appmanager;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.oceansoft.ghclock.adapter.IconToNameAdapter;
import com.oceansoft.ghclock.service.SystemAlterClockService;

import java.util.List;

/**
 * Created by Administrator on 2015/12/3.
 */
public class AppInfosActivity extends Activity{

	private List<PackageInfo> appInfos;
//	private 
	
	
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initWidgers();	
		loadAppInfos();
	}

	private void loadAppInfos() {
		appInfos=SystemAlterClockService.getAllApps(this);

		if (null == appInfos || appInfos.size() <= 0) {
			return;
		}

		
		
		
		IconToNameAdapter adapter = new IconToNameAdapter(AppInfosActivity.this,appInfos);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
			}
		});
	}
	
	

	private void initWidgers() {
		
		
		LinearLayout ln = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
		ln.setLayoutParams(params);
		
		lv = new ListView(this);
		
		ln.addView(lv, params);
		setContentView(ln);
		
		
	}
	

}
