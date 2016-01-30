package com.oceansoft.ghclock.androidui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.oceansoft.ghclock.R;


/**
 * Created by Administrator on 2015/12/3.
 */
public class AlterManagerActivity  extends AppCompatActivity {

	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_altermanager);
		initWidgets();
	}

	private void initWidgets() {
		lv = (ListView) findViewById(R.id.lv_alter_notifies);
	}
}
