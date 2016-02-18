package com.oceansoft.ghclock.commonutil;

import android.os.Handler;

/**
 * Created by Administrator on 2016/1/26.
 */
public class MSGProxy {
	public Handler mHandler;
	public MSGProxy(Handler handler){
		if(null != handler){
			this.mHandler = handler;
		}
	}

	public boolean sendMessage(int msgIndex){
		if (null ==  mHandler) {
			return false;
		}
		
		mHandler.sendEmptyMessage(msgIndex);
		return true;
	}
}
