package com.oceansoft.ghclock.androidui;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.oceansoft.ghclock.R;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by Administrator on 2015/12/14.
 */
public class AutoLayoutSmapleActivity extends AutoLayoutActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setImmersionStatus();
		
		setContentView(R.layout.activity_autolayout_smaple_main);
		
	}

	/**
	 * 初始化状态栏
	 */
	private void setImmersionStatus() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}
}
