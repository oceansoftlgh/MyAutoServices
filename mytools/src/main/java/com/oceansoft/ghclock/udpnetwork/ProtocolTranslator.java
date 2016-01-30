package com.oceansoft.ghclock.udpnetwork;


/**
 * 消息协议转换
 * @author Administrator
 *
 */
public class ProtocolTranslator {
	// / <summary>
	// / 分隔符
	// / </summary>
	private final static String separator = ";";

	/**
	 * 获取客户端发送命令
	 * @param messageProtocol
	 * @return
	 */
	public String serilizeCommand(MessageProtocol messageProtocol) {

		String ret=messageProtocol.UserId + separator +
				messageProtocol.UserName+ separator + 
				messageProtocol.ClientMac + separator
				+ messageProtocol.OperationCode + separator;
		return ret;
	}
	
	public MessageProtocol deserilizeCommand(String _receivedMessage)
    {
        String[] temp = _receivedMessage.split(separator);
        MessageProtocol messageProtocol = new MessageProtocol(temp[0], temp[1], temp[2], temp[3]);
        return messageProtocol;
    }

}
