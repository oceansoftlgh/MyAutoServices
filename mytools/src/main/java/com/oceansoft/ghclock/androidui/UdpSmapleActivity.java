package com.oceansoft.ghclock.androidui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/11/26.
 */
public class UdpSmapleActivity  extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initWidgets();
	}

	private void initWidgets() {
		TextView tv = new TextView(this);
		tv.setText("暂未开发");
		tv.setTextSize(16);
		
		setContentView(tv);
	}
}
