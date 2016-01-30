package com.oceansoft.ghclock.udpnetwork;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;

import com.oceansoft.ghclock.datamodel.DataOperationResult;
import com.oceansoft.ghclock.datamodel.DataTable;
import com.oceansoft.ghclock.fileopera.ConfigManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



public class LocalVariable {
	public static List<String> deleteRecords=new ArrayList<String>();//初始化删除护理记录信息静态变量
	public static List<String> deleteVitalSigns=new ArrayList<String>();//初始化删除体征采集静态变量
	public static List<String> deleteHealthEdus=new ArrayList<String>();//初始化删除健康教育静态变量
	public static List<String> deleteAARecords=new ArrayList<String>();//初始化删除入院评估记录静态变量
	
	public static String NursingRecordTemplateID;//护理记录模板ID
	public static String VitalSignTemplateID;//生命体征模板ID
	
	public static int SocketTimeout = 7000; // Socket连接超时
	public static String ApplicationPath = ""; // 当前系统路径
	public static String OffLineSdPath=Environment.getExternalStorageDirectory()
			.getPath();;//获取当前android机sd路径
	public static String ConfigFilePath = ""; // 配置文件路径
	public static int AutoLockTime = 0; // 单位：秒
	public static String Company = ""; // 从服务器中获取
	public static String HospitalName = ""; // 从服务器中获取
	public static Boolean IsDemo = false; // 从服务器中获取
	public static String ReleaseVersion = "0.0.0.0"; // 从服务器中获取
	public static String LocalVersion = "0.0.0.0";	
	public static int MinMoney = 200;
	
	public static String FtpAddress;//从本地xml文件中获取
	public static String FtpName;//从本地xml文件中获取
	public static String FtpPassword;//从本地xml文件中获取
	public static String FtpPath;//从本地xml文件中获取
	public static String UpdateVersion;//从本地xml文件中获取
	
	public static String ServerIP = "";
	public static String UserName = "";
	public static int ServerPort = 0;
	public static String Mac = "00:01:02:1E";
	public static String IP = "127.0.0.1";
	public static int ClientPort = 6000;
	public static Boolean IsStartLocalListening = false;
	public static String LocalDeptId; // 本地配置科室Id
	public static String LocalWardId; // 本地配置病区Id
	public static Boolean ServerLinked; // 服务器连接状况
	public static int Color_Critical_NCC = Color.rgb(255, 0, 0); // 特级护理颜色
	public static int Color_First_NCC = Color.rgb(255, 128, 0); // 一级护理颜色
	public static int Color_Second_NCC = Color.rgb(255, 20, 147); // 二级护理颜色
	public static int Color_Third_NCC = Color.rgb(0, 128, 0); // 三级护理颜色
	/*
	 * 扫描地址
	 */
	public static String BroadAddress = "com.android.server.scannerservice.broadcast";
    public static String BroadKey = "scannerdata";
    public static boolean BroadTest = false;
    
    
    public static String broadAddress_sl= "SYSTEM_BAR_READ";
    public static String broadKey_sl= "BAR_VALUE";
    
    public static boolean isSacnMust = false; 
    public static long Ticks = 0;
    /// <summary>
    /// 连接服务器时间
    /// </summary>
    public static int SpanMilliseconds;
    
    /*
     * 当前窗体
     */
    public static Context currentContext; 
	
	// / <summary>
	// / 获取颜色
	// / </summary>
	// / <param name="rgb">r,g,b</param>
	// / <returns></returns>
	private static int getColor(String rgb) {
		String[] splits = rgb.split(",");
		if (splits.length < 3) {
			return Color.BLACK;
		}

		return Color.rgb(Integer.parseInt(splits[0]),
				Integer.parseInt(splits[1]), Integer.parseInt(splits[2]));
	}

	// / <summary>
	// / 获取本地配置参数
	// / </summary>
	// / <param name="paraName"></param>
	// / <returns></returns>
	private static Object getLocalParameter(String paraName) {
		try {
			DataTable dtDesktopConfig = ConfigManager.getConfig().Tables
					.get("LocalConfig");
			if (dtDesktopConfig.Columns.Contains(paraName)) {
				return dtDesktopConfig.Rows.get(0).get(paraName);
			} else {
				return "0";
			}
		} catch (Exception e) {
			// 无该参数配置则返回空
			return "0";
		}
	}

	/*
	 * 获取Mac地址
	 */
	@SuppressLint("NewApi")
	private static String[] getIPAndMAC(Context context) {
		String[] address = new String[2];
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);

		WifiInfo info = wifi.getConnectionInfo();
		String macAdress = info.getMacAddress(); // 获取mac地址
		int ipAddress = info.getIpAddress(); // 获取ip地址
		String ip = intToIp(ipAddress);
		address[0] = ip;
		address[1] = macAdress;
		return address;
	}

	/*public static int getPatientColor(NursingClass nc){
		int color = Color.BLACK;
		if ("Critical".equals(String.valueOf(nc))) {			
			color = LocalVariable.Color_Critical_NCC;
		} else if ("First".equals(String.valueOf(nc))) {
			color = LocalVariable.Color_First_NCC;
		} else if ("Second".equals(String.valueOf(nc))) {
			color = LocalVariable.Color_Second_NCC;
		} else if ("Third".equals(String.valueOf(nc))) {
			color = LocalVariable.Color_Third_NCC;
		}
		return color;
	}*/
	
	public static String intToIp(int i) {	
		return (i & 0xFF)+ "." +((i >> 8) & 0xFF) +"."+ ((i >> 16) & 0xFF)+ "." + ((i >> 24) & 0xFF);
	}

	/*
	 * 初始化本地参数
	 */
	public static void initLocalPara(Context context) {
		AutoLockTime = Integer.parseInt(getLocalParameter("AutoLockTime")
				.toString());
		LocalVersion = getLocalParameter("LocalVersion").toString();		
		ServerIP = getLocalParameter("ServerIP").toString();		
		
		//获取ftp配置信息
		FtpAddress=getLocalParameter("Ftpaddress").toString();
		FtpName=getLocalParameter("FtpName").toString();
		FtpPassword=getLocalParameter("FtpPassword").toString();
		FtpPath=getLocalParameter("McisApk").toString();
		UpdateVersion=getLocalParameter("FtpVersion").toString();
		
		UserName = getLocalParameter("UserName").toString();
		ServerPort = Integer.parseInt(getLocalParameter("ServerPort").toString());
		Color_Critical_NCC = getColor(getLocalParameter("Critical_NCC").toString());
		Color_First_NCC = getColor(getLocalParameter("First_NCC").toString());
		Color_Second_NCC = getColor(getLocalParameter("Second_NCC").toString());
		Color_Third_NCC = getColor(getLocalParameter("Third_NCC").toString());

		String[] ipAndMac = getIPAndMAC(context);

		Mac = ipAndMac[1];
		IP = ipAndMac[0];
		ClientPort = Integer.parseInt(getLocalParameter("LocalListenPort").toString());
		IsStartLocalListening = getLocalParameter("IsLocalListening")
				.toString() == "1" ? true : false;
		LocalDeptId = getLocalParameter("LocalDeptId").toString();
		LocalWardId = getLocalParameter("LocalWardId").toString();
		
		//广播地址
		BroadAddress = getLocalParameter("BroadAddress").toString();
		BroadKey = getLocalParameter("BroadKey").toString();
	}	
	
	/*
	 * 服务器连接测试
	 * 
	 */
     public static boolean LinkTest(boolean flag){
        DataOperationResult dor;
        if (flag) {
            String paras = MessageParaHelper.compose(GetKey(), LocalSession.WardId);
            dor = ServiceAgent.deal(SC.LinkTest, paras);
        } else {
            dor = ServiceAgent.deal(SC.LinkTest);
        } if (MessageParaHelper.isSuccess(dor)){
            LocalVariable.ServerLinked = true;           
        } else {
            LocalVariable.ServerLinked = false;
        }
        return LocalVariable.ServerLinked;
    }
    
    /*
     * 获取服务器交互内容
     */
    public static String GetKey()
    {
        String split = "_";
        String key = LocalSession.UserName;
        key += split;
        key += LocalVariable.ServerIP;
        key += split;
        key += LocalVariable.Mac;
        key += split;
        key += String.valueOf(ClientType.Android);            
        return key;
    }
	
	/**
	 * 修改系统时间
	 * @param time
	 */
	@SuppressLint("SimpleDateFormat")
	public static void setSysTime(String time){
		System.out.println("获取服务器时间：" + time);
		try {
			new DateTimeConvert().getDiffSecondsAddDay(time);
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			System.out.println("当前系统修改后时间："
					+ format.format(new DateTimeConvert().getNowDate()));
		} catch(Exception e){
			
		}
	}	
	
}
