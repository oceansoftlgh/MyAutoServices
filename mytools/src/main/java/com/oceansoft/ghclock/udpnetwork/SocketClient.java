package com.oceansoft.ghclock.udpnetwork;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


public class SocketClient extends Socket {
	
	private Socket client;
	private int timeOut = 7000;
	private InetSocketAddress isa;
	private boolean isConnected;

	public SocketClient(String serverIP,int serverPort) {
		isa = new InetSocketAddress(serverIP, serverPort);
		this.timeOut = timeOut;
	}

	public boolean connect() {
		try {
			if(this.isConnected)
			{
				return true;
			}
			client = new Socket();
			client.connect(isa, timeOut);
			this.isConnected=true;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void disconnect() {

		try {
			if (client != null) {
				client.close();
				client = null;
			}
			this.isConnected=false;
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	// 发送数据
	public boolean send(String message) {
		try {
			if(!this.connect())
			{
				return false;
			}
			byte[] data=message.getBytes("UTF8");
			int dataLen=data.length;
			byte[] byteLen=FormatTransfer.toLH(dataLen);
			byte[] sendData=new byte[dataLen+byteLen.length];
			for(int i=0;i<byteLen.length;i++)
			{
				sendData[i]=byteLen[i];
			}
			for(int i=0;i<data.length;i++)
			{
				sendData[i+4]=data[i];
			}
			this.client.getOutputStream().write(sendData);
			this.client.getOutputStream().flush();
			return true;
		} catch (Exception e) {
			System.out.println("sendMessage:" + e.getMessage());
			this.disconnect();
			return false;
		}
	}

	// 接收数据
	public String receive() {
		try {
			if(!this.connect())
			{
				return null;
			}
			byte []dataLen=new byte[4];
			this.client.getInputStream().read(dataLen,0,4);
            int len = FormatTransfer.lBytesToInt(dataLen);
            if(len<=0)
            {
            	return null;
            }
            
            byte []data=new byte[len];
            int alreadyReadedLen = 0;
            while (alreadyReadedLen != len)
            {
                int n = this.client.getInputStream().read(data, alreadyReadedLen, len-alreadyReadedLen);
                alreadyReadedLen += n;
            }
            return new String(data,"UTF8");
		} catch (Exception e) {
			System.out.println("receiverMessage:" + e.getMessage());
			return e.getMessage();
		}
	}

}
