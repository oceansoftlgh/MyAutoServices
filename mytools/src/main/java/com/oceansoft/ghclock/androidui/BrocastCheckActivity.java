package com.oceansoft.ghclock.androidui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2015/12/4.
 */
public class BrocastCheckActivity extends Activity {

	private String bc_adress = 	"android.intent.action.SCANRESULT",
							
	
	bc_key = "value";
	private EditText et_result, et_address, et_key;
	private BroadcastReceiver recevier;
	;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initWidgets();


		registerResultRecever();
	}

	private void registerResultRecever() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(bc_adress);
		recevier =  new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String scanResult = "";

				if (null != intent.getStringExtra(bc_key)) {
					et_result.setText(intent.getStringExtra("value"));
				}
			}
		};
		registerReceiver(recevier, filter);
	}

	private void initWidgets() {
		LinearLayout ln = new LinearLayout(this);
		LinearLayout.LayoutParams lnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		ln.setLayoutParams(lnParams);
		ln.setOrientation(LinearLayout.VERTICAL);
		

		LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		childParams.setMargins(5, 10, 5, 10);
		

		et_address = new EditText(this);
		et_address.setTextSize(16f);
		et_address.setSingleLine(true);
		et_address.setText(bc_adress);


		et_key = new EditText(this);
		et_address.setTextSize(16f);
		et_key.setText(bc_key);

		Button btn_save = new Button(this);
		btn_save.setText("确定");
		btn_save.setTextSize(16f);
		btn_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bc_adress = et_address.getText().toString();
				bc_key = et_address.getText().toString();
				saveConfig();
				if (recevier != null) {
				unregisterReceiver(recevier);
					
				}
				registerResultRecever();
			}
		});

		et_result = new EditText(this);
		et_result.setTextSize(16f);
		et_result.setSingleLine(true);

		ln.addView(et_address, childParams);
		ln.addView(et_key, childParams);
		ln.addView(et_result, childParams);
		ln.addView(btn_save, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		setContentView(ln);


	}

	private void saveConfig() {
		// TODO: 2015/12/4  使用 perfrence保存配置 

	}
}
