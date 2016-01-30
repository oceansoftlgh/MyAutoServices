package com.oceansoft.ghclock.fileopera;

import com.oceansoft.ghclock.datamodel.DataRow;
import com.oceansoft.ghclock.datamodel.DataSet;
import com.oceansoft.ghclock.datamodel.DataTable;
import com.oceansoft.ghclock.udpnetwork.LocalVariable;
import com.oceansoft.ghclock.udpnetwork.XmlDataSetParser;


public class ConfigManager {
    private static DataSet _anddroidConfigSet;
    private static DataSet _offLineConfingSet;
    private static DataTable _offLineConfingDT;
    private static String _tableName;
    
    public static DataSet getOffLineConfig(String fileName){
    	if(_offLineConfingSet==null){
    		_offLineConfingSet=new OffLineCreateXml(fileName).xmlToDataSet();
    	}
    	return _offLineConfingSet;
    }
    public static DataTable getOffLineConfigDT(String _tableName,String fileName){
//    	if(_offLineConfingDT==null){
    		_offLineConfingDT=new OffLineCreateXml(fileName).xmlToDataTable(_tableName);
//    	}
    	return _offLineConfingDT;
    }
    
    public static DataSet getConfig(){
    	if (_anddroidConfigSet == null)
        {
    		_anddroidConfigSet = new XmlDataSetParser(LocalVariable.ConfigFilePath).xmlToDataSet();
        }
        return _anddroidConfigSet;
    }
    public static Boolean offLineSaveConfig(DataSet saveDataSet,String fileName){
    	if (saveDataSet == null)
    	{
    		return false;
    	}
    	return new OffLineCreateXml(fileName).dataSetToXML(saveDataSet);
    }
    public static Boolean saveConfig()
    {
    	if (_anddroidConfigSet == null)
    	{
    		return false;
    	}
    	
    	DataRow dr=_anddroidConfigSet.Tables.get(0).Rows.get(0);
    	dr.set("ServerIP", LocalVariable.ServerIP);
    	dr.set("ServerPort", LocalVariable.ServerPort);
    	dr.set("BroadAddress", LocalVariable.BroadAddress);
    	dr.set("BroadKey", LocalVariable.BroadKey);
    	dr.set("FtpAddress", LocalVariable.FtpAddress);
    	dr.set("FtpName", LocalVariable.FtpName);
    	dr.set("FtpPassword", LocalVariable.FtpPassword);
    	dr.set("McisApk", LocalVariable.FtpPath);
    	dr.set("FtpVersion",LocalVariable.UpdateVersion);
    	
    	return new XmlDataSetParser(LocalVariable.ConfigFilePath).dataSetToXML(_anddroidConfigSet);
    }
    
    public static Boolean saveUserName(){
    	if (_anddroidConfigSet == null)
    	{
    		return false;
    	}
    	
    	DataRow dr=_anddroidConfigSet.Tables.get(0).Rows.get(0);
    	dr.set("UserName", LocalVariable.UserName);
    	
    	return new XmlDataSetParser(LocalVariable.ConfigFilePath).dataSetToXML(_anddroidConfigSet);
    }
}
