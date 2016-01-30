package com.oceansoft.ghclock.datamodel;

import java.io.Serializable;

/**
 * 数据操作结果
 * 
 * @author Administrator
 * 
 */
public class DataOperationResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 返回的xml数据
	 */
	public DataSet ResultDataSet = new DataSet();
	/**
	 * 返回的操作代码
	 */
	public int ReturnCode = -1;
	/**
	 * 返回的操作消息
	 */
	public String ReturnMessage = "";

}
