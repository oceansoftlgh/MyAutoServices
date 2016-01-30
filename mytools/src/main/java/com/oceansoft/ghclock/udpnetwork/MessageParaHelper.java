package com.oceansoft.ghclock.udpnetwork;


import com.oceansoft.ghclock.datamodel.DataOperationResult;
import com.oceansoft.ghclock.datamodel.DataSet;
import com.oceansoft.ghclock.datamodel.DataTable;

public class MessageParaHelper
{
    private static String seperator = ";";
    private static String seperatorReplace = "$$&&";
    /// <summary>
    /// 封装参数
    /// </summary>
    /// <param name="paras"></param>
    /// <returns></returns> 
    public static String compose(Object... paras)
    {
        String ret = "";
        XmlDataSetParser xmlDataSetConvert = new XmlDataSetParser();
        for (int i = 0; i < paras.length; i++)
        {
            String temp = "";
            if (paras[i] != null)
            {
                temp = paras[i].toString();
            }
            if (paras[i].getClass() == DataSet.class)
            {
                temp = xmlDataSetConvert.dataSetToXMLString((DataSet)paras[i]);
            }
            if (paras[i].getClass()== DataTable.class)
            {
                DataSet ds = new DataSet();
                DataTable dt = (DataTable)paras[i];
                ds.Tables.Add(dt);
                temp = xmlDataSetConvert.dataSetToXMLString(ds);
            }
            if (i == paras.length - 1)
            {
                ret += temp.replace(seperator, seperatorReplace);
            }
            else
            {
                ret += temp.replace(seperator, seperatorReplace) + seperator;
            }
        }

        return ret;
    }
    /// <summary>
    /// 组合数据DataSet
    /// </summary>
    /// <param name="ds"></param>
    /// <returns></returns>
    public static String compose(DataSet ds)
    {
        String ret = "";
        XmlDataSetParser xmlDataSetConvert = new XmlDataSetParser();
        ret = xmlDataSetConvert.dataSetToXMLString(ds);
        ret=ret.replace(seperator, seperatorReplace);
        return ret;
    }
    /// <summary>
    /// 组合数据
    /// </summary>
    /// <param name="dt"></param>
    /// <returns></returns>
    public static String compose(DataTable dt)
    {
        String ret = "";
        DataSet ds = new DataSet();
        ds.Tables.Add(dt);
        XmlDataSetParser xmlDataSetConvert = new XmlDataSetParser();
        ret = xmlDataSetConvert.dataSetToXMLString(ds);
        ret = ret.replace(seperator, seperatorReplace);
        return ret;
    }
    /// <summary>
    /// 解析数据（DataSet）
    /// </summary>
    /// <param name="data"></param>
    /// <returns></returns>
    public static DataSet parseDataSet(String data)
    {
    	XmlDataSetParser xmlDataSetConvert = new XmlDataSetParser();
        return xmlDataSetConvert.xmlStringToDataSet(data);
    }
    /// <summary>
    /// 解析数据（DataTable）
    /// </summary>
    /// <param name="data"></param>
    /// <returns></returns>
    public static DataTable parseDataTable(String data)
    {
    	XmlDataSetParser xmlDataSetConvert = new XmlDataSetParser();
        DataSet ds=xmlDataSetConvert.xmlStringToDataSet(data);
        return ds.Tables.get(0);
    }
    /// <summary>
    /// 解析参数
    /// </summary>
    /// <param name="paras"></param>
    /// <returns></returns>
    public static String[] parse(String paras)
    {
        String []splits=paras.split(seperator);
        String[] ret = new String[splits.length];
        for(int i=0;i<splits.length;i++)
        {
            ret[i] = splits[i].replace(seperatorReplace, seperator);
        }
        return ret;
    }
    /// <summary>
    /// 判断返回结果是否成功
    /// </summary>
    /// <param name="dor"></param>
    /// <returns></returns>
    public static Boolean isSuccess(DataOperationResult dor)
    {
        if (dor.ReturnCode > 0)
        {
            return true;
        }
        else if (dor.ReturnCode == 0 && dor.ResultDataSet != null)
        {
            return true;
        }
        return false;
    }
}