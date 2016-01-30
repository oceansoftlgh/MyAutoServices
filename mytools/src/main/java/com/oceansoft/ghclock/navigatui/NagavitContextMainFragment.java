package com.oceansoft.ghclock.navigatui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oceansoft.ghclock.R;


/**
 * Created by Administrator on 2015/12/4.
 */
public class NagavitContextMainFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_ngcontext_main, container, false);
		return rootView;
	}
}
