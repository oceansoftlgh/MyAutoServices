package com.oceansoft.ghclock.baidupush;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.oceansoft.ghclock.R;

/**
 * Created by Administrator on 2015/12/17.
 */
public class BaiduMessageShowActivity extends Activity {
	private String title;
	private String description;
	private String customContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		setContentView(R.layout.activity_baidu_message_smaple);


		Bundle bundle = this.getIntent().getBundleExtra("Bundle");
		title = bundle.getString("title");
		description = bundle.getString("description");
		customContent  = bundle.getString("customContent");


		EditText et_title = (EditText) findViewById(R.id.et_baidu_msg_title);
		EditText et_description = (EditText) findViewById(R.id.et_baidu_msg_description);
		EditText et_customContent = (EditText) findViewById(R.id.et_baidu_msg_customContent);
		
		
		et_title.setText(title);
		et_description.setText(description);
		et_customContent.setText(customContent);
		
	}
}
