package com.oceansoft.ghclock.datamodel;


/**
 * 数据集
 */
public class DataSet {

	/**
	 * 数据集名称
	 */
	public String DataSetName="Set";
	/**
	 * 数据集中的表集合
	 */
	public DataTableCollection Tables;

	/**
	 * 构造器定义
	 */
	public DataSet() {
		Tables = new DataTableCollection(this);
	}

	/**
	 * 构造器定义
	 * 
	 * @param _DataSetName
	 */
	public DataSet(String _DataSetName) {
		this();
		DataSetName = _DataSetName;
	}

	/**
	 * 获取数据集名称
	 * 
	 * @return 数据集名称
	 */
	public String getDataSetName() {
		return DataSetName;
	}

	/**
	 * 设置数据集名称
	 * 
	 * @param _DataSetName
	 *            数据集名称
	 */
	public void setDataSetName(String _DataSetName) {
		DataSetName = _DataSetName;
	}

	/**
	 * 获取数据集中的所有数据表
	 * 
	 * @return 数据表
	 */
	public DataTableCollection getTables() {
		return Tables;
	}
}
