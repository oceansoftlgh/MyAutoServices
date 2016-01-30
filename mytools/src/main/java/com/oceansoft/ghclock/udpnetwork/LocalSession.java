package com.oceansoft.ghclock.udpnetwork;

public class LocalSession {
	public static String UserId = "";
	public static String UserName = "";
	public static String DeptId = "";
	public static String DeptName = "";
	public static String WardId = "";
	public static String WardName = "";
	public static String RoleId = "";
	public static String HeadNurseId = "";
	public static String TaskId = "";
	public static String PatientId="";	
	public static String orderDate = "2014-01-01";
	public static int dayDiff = 0;
	public static long secondsDiff = 0;
	public static String TestNo = "";
	
	
	/*
	 * 获取执行者
	 */
	public static String getPerformer() {
		return UserId;
	}

	/*
	 * 清空LocalSession内容
	 */
	public static void clear() {
		UserId = "";
		UserName = "";
		DeptId = "";
		DeptName = "";
		WardId = "";
		WardName = "";
		RoleId = "";
		HeadNurseId = "";
		TaskId = "";
		PatientId="";
	}
}
