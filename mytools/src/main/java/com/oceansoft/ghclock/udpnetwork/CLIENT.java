package com.oceansoft.ghclock.udpnetwork;

public class CLIENT {
	
	/// <summary>
    /// 心跳报
    /// 参数：无
    /// </summary>
    public static String Test = "5000001";
    
    
	
	 /// <summary>
    /// 更新医嘱缓存
    /// </summary>
    public static String UpdateOrders = "5001001";
    /// <summary>
    /// 更新病人缓存
    /// </summary>
    public static String UpdatePatient = "5001002";
    /// <summary>
    /// 更新病人和医嘱缓存
    /// </summary>
    public static String UpdatePatientAndOrders = "5001003";
    /// <summary>
    /// 更新病床缓存
    /// </summary>
    public static String UpdateBed = "5001004";
    /// <summary>
    /// 更新用户缓存
    /// </summary>
    public static String UpdateUser = "5001005";
    /// <summary>
    /// 更新事件缓存
    /// </summary>
    public static String UpdateEvent = "5001006";
    /// <summary>
    /// 更新医嘱给药途径分类缓存
    /// </summary>
    public static String UpdateAdministrationClass = "5001007";
    /// <summary>
    /// 更新医嘱给药途径缓存
    /// </summary>
    public static String UpdateAdministration = "5001008";
    /// <summary>
    /// 更新护理任务缓存
    /// </summary>
    public static String UpdateNursingTask = "5001009";
    /// <summary>
    /// 更新护理记录单模板缓存
    /// </summary>
    public static String UpdateNursingRecord = "5001010";
    /// <summary>
    /// 更新观察措施缓存
    /// </summary>
    public static String UpdateWatchAndMeasure = "5001011";
    /// <summary>
    /// 更新健康教育缓存
    /// </summary>
    public static String UpdateHealthEdu = "5001012";
    /// <summary>
    /// 更新生命体征
    /// </summary>
    public static String UpdateVitalSign = "5001013";
    /// <summary>
    /// 更新入院评估缓存
    /// </summary>
    public static String UpdateAssessment = "5001014";
    /// <summary>
    /// 更新工作班次缓存
    /// </summary>
    public static String UpdateWorkOrder = "5001015";
    
    
    /// <summary>
    /// 在线
    /// 参数：病区ID、用户名、IP、终端类型
    /// </summary>
    public static String Online = "5002001";
    /// <summary>
    /// 离线
    /// 参数：病区ID、用户名、IP、终端类型
    /// </summary>
    public static String Offline = "5002002";
    
    
    /// <summary>
    /// 发送文字消息
    /// 参数：Ip、Name、Text
    /// </summary>
    public static String TextMsg = "5004001"; 
}
