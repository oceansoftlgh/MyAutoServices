package com.oceansoft.ghclock.udpnetwork;

/**
 * 消息协议
 * 
 * @author Administrator
 * 
 */
public class MessageProtocol {

	public String UserId;
	public String UserName;
	public String ClientMac;
	public String OperationCode;

	public MessageProtocol(String userId, String userName,
			String clientMac, String operationCode) {
		this.UserId = userId;
		this.UserName = userName;
		this.ClientMac = clientMac;
		this.OperationCode = operationCode;
	}
	
	public MessageProtocol(int _parameterLength) {
		this.UserId = "";
		this.UserName = "";
		this.ClientMac = "";
		this.OperationCode = "";
	}

}
