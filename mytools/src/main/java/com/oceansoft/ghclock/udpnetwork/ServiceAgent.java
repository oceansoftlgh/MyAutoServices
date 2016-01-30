package com.oceansoft.ghclock.udpnetwork;


import com.oceansoft.ghclock.datamodel.DataOperationResult;

public class ServiceAgent {
	public static DataOperationResult deal(String operCode)
	{
		return deal(operCode,"");
	}
	
	public static DataOperationResult deal(
			String operCode, String para) {

		DataOperationResult dataOperationResult = new DataOperationResult();
		try {
			if(para==null||para.trim().equals(""))
			{
				para="NULLPARA";
			}

			//编码
			SocketClient client = new SocketClient("127.0.0.1",8001);

			if (client.connect()) {
				LocalVariable.ServerLinked=true;
				ProtocolTranslator protocolTranslator = new ProtocolTranslator();
				MessageProtocol messageProtocol = new MessageProtocol(
						LocalSession.UserId, LocalSession.UserName,
						LocalVariable.Mac,operCode);
				// 发送命令
				client.send(protocolTranslator.serilizeCommand(messageProtocol));

				// 发送数据
				client.send(para);
				
				//接收数据
				String str = client.receive();

				String[] str_ReceiveMessage = MessageParaHelper.parse(str);

				dataOperationResult.ReturnCode = Integer
						.parseInt(str_ReceiveMessage[0]);
				if(str_ReceiveMessage.length>1){
					dataOperationResult.ReturnMessage = str_ReceiveMessage[1];
				}
				if(str_ReceiveMessage.length>2){
					dataOperationResult.ResultDataSet = new XmlDataSetParser()
					.xmlStringToDataSet(str_ReceiveMessage[2]);
				}
				
				client.disconnect();
			} else {
				dataOperationResult.ResultDataSet = null;
				dataOperationResult.ReturnCode = -2;
				dataOperationResult.ReturnMessage = "连接不到服务器,请检查网络连接";
				LocalVariable.ServerLinked=false;
			}

		} catch (Exception e) {
			dataOperationResult.ResultDataSet = null;
			dataOperationResult.ReturnCode = -3;
			dataOperationResult.ReturnMessage = "连接服务器出错:" + e.getMessage();
			LocalVariable.ServerLinked=false;
		}
		return dataOperationResult;
	}
}
