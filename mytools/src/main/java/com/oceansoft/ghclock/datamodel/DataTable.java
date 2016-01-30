package com.oceansoft.ghclock.datamodel;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据表<br>
 * 数据表是一个二维表,是数据行和数据列的集合
 */
public class DataTable {
	/**
	 * 数据集
	 */
	private DataSet dataSet;
	/**
	 * 数据表名称
	 */
	public String TableName = "Table";
	/**
	 * 数据行集合
	 */
	public DataRowCollection Rows;
	/**
	 * 数据列集合
	 */
	public DataColumnCollection Columns;

	/**
	 * 构造器定义
	 */
	public DataTable() {
		Rows = new DataRowCollection(this);
		Columns = new DataColumnCollection(this);
	}

	/**
	 * 构造器定义
	 * 
	 * @param _TableName
	 */
	public DataTable(String _TableName) {
		this();
		TableName = _TableName;
	}

	/**
	 * 获取数据集
	 * 
	 * @return 数据集
	 */
	public DataSet getDataSet() {
		return dataSet;
	}

	/**
	 * 设置数据集
	 * 
	 * @param _DataSet
	 *            数据集
	 */
	public void setDataSet(DataSet _DataSet) {
		dataSet = _DataSet;
	}

	/**
	 * 获取数据表名称
	 * 
	 * @return 表名
	 */
	public String getTableName() {
		return TableName;
	}

	/**
	 * 设置数据表名称
	 * 
	 * @param _TableName
	 *            表名
	 */
	public void setTableName(String _TableName) {
		TableName = _TableName;
	}

	/**
	 * 获取数据表中的数据行集合
	 * 
	 * @return 数据行集合
	 */
	public DataRowCollection getRows() {
		return Rows;
	}
	/**
	 * 获取数据表中的数据行集合
	 * 
	 * @return 数据行集合
	 */
	public DataRow getRow(String columnName,Object value)
	{
		for(int i=0;i<this.Rows.getCount();i++)
		{
			if(this.Rows.get(i).get(columnName).equals(value))
			{
				return this.Rows.get(i);
			}
		}
		return null;
	}
	
	public DataRow[] getRows(String columnName,Object value)
	{
		List<DataRow> al=new ArrayList<DataRow>();
		for(int i=0;i<this.Rows.getCount();i++)
		{
			if(this.Rows.get(i).get(columnName).equals(value))
			{
				al.add(this.Rows.get(i));
			}
		}
		return al.toArray(new DataRow [al.size()]);
	}
	public List<DataRow> getRowsList(String columnName,Object value){
		List<DataRow> al=new ArrayList<DataRow>();
		for(int i=0;i<this.Rows.getCount();i++)
		{
			if(this.Rows.get(i).get(columnName).equals(value))
			{
				al.add(this.Rows.get(i));
			}
		}
		return al;
	}

	/**
	 * 获取数据表中的数据列集合
	 * 
	 * @return 数据列集合
	 */
	public DataColumnCollection getColumns() {
		return Columns;
	}

	/**
	 * 清除数据行中的数据
	 */
	public void Clear() {
		Rows.Clear();
	}

	/**
	 * 创建新的数据行
	 * 
	 * @return 新的数据行
	 */
	public DataRow NewRow() {
		DataRow dr = new DataRow();
		dr.setDataTable(this);
		return dr;
	}

	/**
	 * 根据指定的字段对datatable进行排序
	 * 
	 * @param key
	 *            排序字段
	 * @param Asc
	 *            是否为升序
	 */
	@SuppressLint("DefaultLocale")
	public void sort(String key, boolean Asc) {
		if (key == null || "".equals(key)) {
			return;
		}
		if (Columns.get(key.toUpperCase()) == null) {
			return;
		}
		boolean flag = false;
		DataRow temp = null;
		int count = Rows.getCount();
		for (int i = 0; i < count - 1; i++) {
			flag = false;
			for (int j = 0; j < count - i - 1; j++) {
				if (Asc ? Rows.get(j).get(key).toString()
						.compareTo(Rows.get(j + 1).get(key).toString()) > 0
						: Rows.get(j).get(key).toString()
								.compareTo(Rows.get(j + 1).get(key).toString()) < 0) {
					temp = Rows.get(j);
					Rows.dataRow.set(j, Rows.get(j + 1));
					Rows.dataRow.set(j + 1, temp);
					flag = true;
				}
			}
			if (!flag)
				break;
		}
	}

	/**
	 * 根据指定的字段对datatable进行升序排序 <br>
	 * 相当执行 order by <code>key</code> asc
	 * 
	 * @param key
	 *            排序字段
	 */
	public void sort(String key) {
		sort(key, true);
	}
}
