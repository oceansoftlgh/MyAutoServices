package com.oceansoft.ghclock.datamodel;

import android.annotation.SuppressLint;

/**
 * 数据列
 */
@SuppressLint("DefaultLocale")
public class DataColumn
{
	/**
	 * 列名
	 */
	public String ColumnName;
	/**
	 * 数据类型
	 */
	public Class<?> DataType;
	/**
	 * 默认值
	 */
	public Object DefaultValue;
	/**
	 * 数据列描述
	 */
	public String Description;
	/**
	 * 数据表
	 */
	private DataTable table;
	/**
	 * 数据列标题
	 */
	private String Caption;

	/**
	 * 构造器定义 +2重载
	 */
	public DataColumn()
	{
	}
	/**
	 * 构造器定义
	 * 
	 * @param _ColumnName
	 *            列名
	 */
	public DataColumn(String _ColumnName)
	{
		ColumnName = _ColumnName;
	}
	/**
	 * 构造器定义
	 * 
	 * @param _ColumnName
	 *            列名
	 * @param _DataType
	 *            类型
	 */
	public DataColumn(String _ColumnName, Class<?> _DataType)
	{
		this(_ColumnName);
		DataType = _DataType;
	}
	/**
	 * 属性定义:ColumnName
	 * 
	 * @return 列名
	 */
	public String getColumnName()
	{
		return ColumnName;
	}
	/**
	 * 设置列名
	 * 
	 * @param _ColumnName
	 *            列名
	 */
	public void setColumnName(String _ColumnName)
	{
		ColumnName = _ColumnName;
	}
	/**
	 * 属性定义:DataType
	 * 
	 * @return 数据类型
	 */
	public Class<?> getDataType()
	{
		return DataType;
	}
	/**
	 * 设置数据类型
	 * 
	 * @param _DataType
	 *            数据类型
	 */
	public void setDataType(Class<?> _DataType)
	{
		DataType = _DataType;
	}
	/**
	 * 属性定义:DefaultValue
	 * 
	 * @return 默认值
	 */
	public Object getDefaultValue()
	{
		return DefaultValue;
	}
	/**
	 * 设置 默认值
	 * 
	 * @param _DefaultValue
	 *            默认值
	 */
	public void setDefaultValue(Object _DefaultValue)
	{
		DefaultValue = _DefaultValue;
	}
	/**
	 * 属性定义:Description
	 * 
	 * @return 描述
	 */
	public String getDescription()
	{
		return Description;
	}
	/**
	 * 设置 描述
	 * 
	 * @param _Description
	 *            描述
	 */
	public void setDescription(String _Description)
	{
		Description = _Description;
	}
	/**
	 * 属性定义:DataTable
	 * 
	 * @return DataTable
	 */
	public DataTable getTable()
	{
		return table;
	}
	/**
	 * 设置 DataTable
	 * 
	 * @param _Table
	 *            DataTable
	 */
	public void setTable(DataTable _Table)
	{
		this.table = _Table;
	}
	/**
	 * 属性定义:Ordinal
	 * 
	 * @return Ordinal
	 */
	public int getOrdinal()
	{
		if (this.table == null) return -1;
		return this.table.getColumns().IndexOf(this);
	}
	/**
	 * 设置Caption
	 * 
	 * @param _Caption
	 */
	public void setCaption(String _Caption)
	{
		Caption = _Caption;
	}
	/**
	 * 属性定义:Caption
	 * 
	 * @return Caption
	 */
	public String getCaption()
	{
		return Caption;
	}
}
