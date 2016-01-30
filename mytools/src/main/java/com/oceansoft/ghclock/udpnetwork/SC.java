package com.oceansoft.ghclock.udpnetwork;

public class SC {
	public static final String GetServerPara = "0000001";
	
	/*
	 * 服务器连接检测
	 * 参数：Key、WardID
	 */
    public static final String LinkTest = "0000002"; 
	
	public static final String CheckUser = "0004001";
	public static final String GetWardsByDeptID = "0003002";
	
	public static String GetPatientByWardID = "0006001";
	/*
	 * 获取病区的病人和病床
	 * 参数：病区ID
	 */
	public static String GetBedAndPatientByWardID = "0006007";
	public static String GetBedsByWardID = "0005001";
	
	public static String GetAllFrequency = "0008001";
	public static String GetAllAdministration = "0007004";
	public static String GetDeptAll = "0003001";
	
	public static String GetMenusByRoleID = "0002002";
	
	/*
	 * 保存用户登录记录
	 */
	public static String AddLoginRecord = "0004008";

	/*
	 * 获取服务器时间
	 */
    public static String GetServerTime="0000000"; 

	/*
	 * 更据科室ID获取用户 参数：科室ID
	 */
	public static String GetUserByDeptID = "0004004";

	/*
	 * 获取某个病人的所有医嘱 参数：病人ID、住院次数、遗嘱类型(空代表全部)
	 */
	public static String GetPatientOrders = "0101002";
	
	

	/*
	 * 获取单个病人化验类医嘱
	 * 参数：病人ID、住院次数
	 */
    public static String GetTestOrders = "0101007";

	/*
	 * 获取病区病人某段时间医嘱任务
	 * 参数：病区ID、开始时间、结束时间、医嘱类别（可选）
	 */
    public static String GetOrdersTasksByWard = "0102001";

    /*
     * 获取病人某段时间医嘱任务
     *  参数：病人ID、住院次数、开始时间、结束时间
     */
    public static String GetPatientOrdersTasksByTimeSpan = "0102002";

	/*
	 * 根据医嘱ID获取任务 参数：任务ID
	 */
	public static String GetOrdersTaskByID = "0102009";
	
	
	/*
	 * 根据医嘱化验ID（InspectID）获取任务 参数：化验ID：InspectID
	 */
	public static String GetOrdersTaskByInspectID = "0102014";

	/*
	 * 修改医嘱任务状态
	 * 参数：任务ID，状态
	 */
    public static String UpdateOrdersTaskStatus = "0102011";
    
    /*
     * 添加执行记录
     * 参数：DataTable
     */
    public static String AddRecord = "0103005";
    
	/*
	 * 根据遗嘱任务ID获取当前医嘱所有执行结果（升序）
	 * 参数：遗嘱任务ID
	 */
    public static String GetExcuteById = "0103001";

	/*
	 * 获取给药分类 参数：无
	 */
	public static String GetAdministrationClass = "0007001";

	/*
	 * 修改医嘱任务 参数：医嘱任务表、执行明细表、医嘱表
	 */
	public static String UpdateOrdersTask = "0102007";

	/*
	 * 获取病区病人的所有医嘱
	 */
	public static String GetOrders = "0101001";
	/*
	 * 获取病区所有科室的护理记录缓存
	 */
	public static String GetNursingRecordCache = "0206007";

	/*
	 * 根据模版获取病人某段时间的护理记录
	 * 参数：模版ID、病人ID、住院次数、开始日期、结束日期、在院标识
	 */
    public static String GetNursingRecordByTemplateAndTime = "0206002";

	/*
	 * 保存护理记录 参数：DataTable
	 */
	public static String SaveNursingRecord = "0206003";

	/*
	 * 删除护理记录
	 * 参数：记录ID
	 */
    public static String DeleteNursingRecord = "0206004";

	/*
	 * 获取病区所有科室的生命体征记录缓存
	 */
	public static String GetVitalSignCache = "0405006";
	
	/*
	 * 根据模版获取病人某段时间的生命特征记录
	 * 参数：模版ID、病人ID、住院次数、开始日期、结束日期
	 */
    public static String GetVitalSignByTemplateAndTime = "0405002";

	/*
	 * 获取事件
	 */
	public static String GetEventsByWard = "0011001";
	
	/*
	 * 获取病人事件
	 * 参数：病人ID，住院次数，病区ID
	 */
    public static String GetEnvetRecordsByPatient = "0012002";
    
	/*
	 * 保存生命特征记录 参数：DataTable
	 */
	public static String SaveVitalSign = "0405003";

	/*
	 * 保存生命特征记录
	 * 参数：记录ID
	 */
    public static String DeleteVitalSign = "0405004";
	
	/*
	 * 保存病人事件 参数：DataTable
	 */
	public static String SaveEventRecords = "0012004";
	
	/*
	 * 根据病区获得病人事件记录
	 */
	public static String GetEventRecordsByWard = "0012006";

	/*
	 * 获取观察措施 参数：病区ID
	 */
	public static String GetWatchMeasure = "0205001";

	/*
	 * 添加观察措施 参数：DataTable
	 */
	public static String SaveWatchMeasure = "0205002";

	/*
	 * 根据模版健康教育记录
	 * 参数：模版ID、病人ID、住院次数
	 */
    public static String GetHealthEduByTemplate = "0507001"; 
	
	/*
	 * 保存健康教育记录
	 * 参数：DataTable
	 */
    public static String SaveHealthEduRecord = "0507002"; 

    /*
     * 删除健康教育记录
     * 参数：记录ID
     */
    public static String DeleteHealthEduRecord = "0507003";
    
	/*
	 * 获取病区所有科室的记录缓存
	 */
	public static String GetHealthEduCache = "0507004";
	
	/*
	 * 根据ID获取检查报告
	 */
    public static String GetExamByID = "0901001";

    /*
     *  获取病人全部的检查报告
     */
    public static String GetPatientExam = "0901002";

    /*
     * 获取病人某段时间的检查报告
     */
    public static String GetPatientExamByTime = "0901003";

    /*
     * 根据ID获取化验结果
     */
    public static String GetTestByID = "0902001";
    
    /*
     *  获取病人全部的化验结果
     *  参数：病人ID，住院次数ID
     */
    public static String GetPatientTest = "0902002";

    /*
     * 获取病人某段时间的化验结果
     */
    public static String GetPatientTestByTime = "0902003";
    
    /*
     * 获取体征提醒信息
     * 参数：病区ID
     */
    public static String GetReminderByWard = "0406001";

    /*
     * 添加修改查房记录
     */
    public static String SaveWRRecord = "0307002";

    /*
     * 获取病人全部的护理路径
     * 参数：病人ID、住院次数、病区ID
     */
    public static String GetPatientNursingPath = "0903001";

    /*
     * 修改护理路径
     */
    public static String UpdateNursingPath = "0903002";
    
    /*
     * 获取护理路径分类
     */
    public static String GetNursingPathClass = "0304001";
    
    /*
     * 获取病区全部护理路径
     * 参数：病区ID
     */
    public static String GetNursingPathByWard = "0305001";

	/*
	 * 根据模版获取入院评估记录
	 *参数：模版ID、病人ID、住院次数
	 */
	public static String GetAARecordByTemplate = "0604001";

	/*
	 * 保存入院评估记录
	 * 参数：DataSet原记录表、新生成的记录表 
	 */
	public static String SaveAARecord = "0604002";
	
	/*
	 * 删除模版某个项目所有记录
	 * 参数：明细ID
	 */
	public static String DelAARecordByDetaiID = "0604004";
	
	/*
     * 获取病区所有科室的入院评估记录缓存
     */
    public static String GetAssessmentCache = "0604005";
    
    /*
     * 获取当前病人是否有输液任务未结束
     */
    public static String GudgeTransfusion = "0102013";
}
