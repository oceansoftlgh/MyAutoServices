package com.inpes.commoui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.inpes.R;


/**
 * Created by Administrator on 2015/11/25.
 */
public class ExitActivity extends Activity implements View.OnClickListener{

	public static final double DOUBLE = 11.;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit_dialog);
		Button btn_0 = (Button) findViewById(R.id.exitBtn0);
		Button btn_1 = (Button) findViewById(R.id.exitBtn1);
		btn_0.setOnClickListener(this);
		btn_1.setOnClickListener(this);
		
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
			
			case R.id.exitBtn0:
				exitApplication();
				break;
			case R.id.exitBtn1:
				this.finish();
				break;
			default:
				break;
		}
		
		
	}

	private void exitApplication() {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(startMain);
			System.exit(0);
		} else {
			// android2.1  
			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			am.restartPackage(getPackageName());
		}

	}

	private void cancelExit() {
		Toast.makeText(ExitActivity.this, "确认取消", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		this.finish();
		
		return true;
	}
}
