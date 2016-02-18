package com.oceansoft.ghclock.htmpparse;

import android.os.Handler;
import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2016/2/16.
 */
public class BasicFragment extends Fragment {
	ViewPargerInsideJumpInterface viewPargerInsideJumpInterface;
	Handler mHandler;

	public void setOnViewParViewPargerInsideJumpInterface(ViewPargerInsideJumpInterface viewPargerInsideJumpInterface) {
		if (viewPargerInsideJumpInterface != null)
			this.viewPargerInsideJumpInterface = viewPargerInsideJumpInterface;
	}

	public void setHandler(Handler handler) {
		if (handler != null)
			this.mHandler = handler;
	}

	public void changToFragment(int fragMentid) {
		if (viewPargerInsideJumpInterface != null) {
			viewPargerInsideJumpInterface.changFragment(fragMentid);
		}
	}

}
