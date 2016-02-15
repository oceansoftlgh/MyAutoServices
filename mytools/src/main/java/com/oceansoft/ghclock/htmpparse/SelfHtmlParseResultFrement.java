package com.oceansoft.ghclock.htmpparse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.oceansoft.ghclock.R;

/**
 * Created by Administrator on 2016/2/15.
 */
public class SelfHtmlParseResultFrement extends Fragment {
	private ListView lv_result;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_htmlparse_result,container,false);
		initWidgets(view);
		return view;
		
	}

	private void initWidgets(View view) {
		lv_result = (ListView) view.findViewById(R.id.lv_alter_notifies);
	}
	
	
}
