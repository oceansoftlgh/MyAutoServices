package com.oceansoft.ghclock.androidui;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.EditText;

import com.oceansoft.ghclock.R;

/**
 * Created by Administrator on 2015/12/12.
 */
public class EquipmentDetailsActivity extends Activity{
	public static final double DOUBLE = 42.;
	EditText et_px,et_dp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_equipment_detaild_main);
		
		et_px = (EditText) findViewById(R.id.et_equipment_px);
		et_dp= (EditText) findViewById(R.id.et_equipment_dp);


		// 方法1 Android获得屏幕的宽和高    
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenWidth = screenWidth = display.getWidth();
		int screenHeight = screenHeight = display.getHeight();
		
		// 方法2   
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		float width=dm.widthPixels*dm.density;
		float height=dm.heightPixels*dm.density;
		
		et_px.setText("First method:"+dm.toString()+"\n"+"Second method:"+"Y="+screenWidth+";X="+screenHeight);

	}}
