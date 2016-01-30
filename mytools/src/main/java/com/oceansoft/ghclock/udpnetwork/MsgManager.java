package com.oceansoft.ghclock.udpnetwork;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;



/*
 * Udp消息管理类
 * 解析消息，并作出响应
 */
public class MsgManager {
	
	private static Handler uiHandler;
	
	private static Thread udpThread;
	private static boolean udpFlag;
	private static UdpSocket udpListenner;		
	private static final int Test = 0x001;
	
	public MsgManager(){
		this.createHandler();
	}
	
	public static void StartUdp(){	
		//客户端UDP监听
		udpListenner = new UdpSocket(LocalVariable.ClientPort);
		//开启线程监听
		if(udpThread==null){
			udpFlag = true;		
			udpThread=new Thread(_udpListenner);
		}
		udpThread.start();		
	}	
	
	public static void StopUdp(){
		udpFlag = false;
	}	
	
	private static Runnable _udpListenner = new Runnable() {
		@Override
		public void run() {
			try {	
				String msg="";
				while(udpFlag){			
					msg = udpListenner.receive();
					//处理消息
					MsgOperation mo = new MsgOperation(msg);
					mo.MessageHandle();
					if (mo.IsTest)
	                {
						Message s = new Message();
						s.what = Test;
						uiHandler.sendMessage(s);
						
	                    //服务器端口号为 客户端口号+1
						UdpSocket client = new UdpSocket(LocalVariable.ClientPort + 1);                   
	                    client.send(LocalVariable.ServerIP, CLIENT.Test, LocalVariable.GetKey());
	                }
					if (LocalVariable.Ticks > 0)
		            {
		                //大于50秒，心跳包未30秒一次
		                long ticks = new DateTimeConvert().getNowDate().getTime() - LocalVariable.Ticks - 10000000 * 50;
		                if (ticks > 0)
		                {
		                    LocalVariable.ServerLinked = false;
		                }
		            }
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
			}
		}
	};
	
	
	private void createHandler() {
		uiHandler = new Handler() {
			@Override
			@SuppressLint({ "HandlerLeak", "ResourceAsColor" })
			public void handleMessage(Message msg) {
				switch (msg.what) {				
				case Test:
					Toast.makeText(LocalVariable.currentContext, "未连接上ftp",
							Toast.LENGTH_SHORT).show();
					break;				
				}
			};
		};
	}
	
	
	/**
	 * 服务器连接
	 */
	private Runnable _serverLink = new Runnable() {
		@Override
		public void run() {
			try {		
				while(true){			
					if(LocalVariable.ServerLinked){
						if(!LocalVariable.LinkTest(true)){
							
						}
					} else {
						LocalVariable.LinkTest(false);
					}					
					Thread.sleep(10000);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
			}			
		}
	};
	
}