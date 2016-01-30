package com.oceansoft.ghclock.udpnetwork;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpSocket {
	
	DatagramSocket  udpSocket;
	int port;
	
	public UdpSocket(int port){
		this.port = port;
		try {
			this.udpSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public String createMsg(String command, String message)
    {           
        return (command + "|" + message);           
    }
	
	/*
	 * 发送数据
	 */
	public boolean send(String ip,String command,String message) throws IOException{

		if(this.udpSocket==null){
			return false;
		}
		message = this.createMsg(command,message);
		
		byte []data =message.getBytes("UTF8");
		int dataLen = data.length;
		byte []lenBytes = FormatTransfer.toLH(dataLen);
		byte []sendData = new byte[dataLen + lenBytes.length];
		
		System.arraycopy(lenBytes, 0, sendData, 0, 4);
		System.arraycopy(data, 4, sendData, 4, data.length);		

		InetAddress addr = InetAddress.getByName(ip);
		DatagramPacket sendPacket = 
				new DatagramPacket(sendData , sendData.length , addr , this.port);        
		//发送数据
		this.udpSocket.send(sendPacket);
		return true;	
	}
	
	/*
	 * 接收数据
	 */
	public String receive() throws IOException{

		if(this.udpSocket==null){
			return "";
		}		
		byte[] data = new byte[100];
		DatagramPacket packet  = new DatagramPacket(data,data.length);			  
        while (true)
        {
        	this.udpSocket.receive(packet);
        	byte[] recvData=packet.getData();
        	if(recvData.length<4){
        		return "";
        	}
        	byte[] realData=new byte[recvData.length-4];
        	
        	System.arraycopy(recvData, 4, realData, 0, recvData.length);        	
        	
        	return new String(realData,"UTF8");
        }	
	}
}
