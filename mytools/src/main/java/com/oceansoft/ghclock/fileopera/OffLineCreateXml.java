package com.oceansoft.ghclock.fileopera;

import android.annotation.SuppressLint;

import com.oceansoft.ghclock.datamodel.DataColumn;
import com.oceansoft.ghclock.datamodel.DataRow;
import com.oceansoft.ghclock.datamodel.DataSet;
import com.oceansoft.ghclock.datamodel.DataTable;
import com.oceansoft.ghclock.udpnetwork.LocalVariable;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;



public class OffLineCreateXml {
	private String errorMessage;
	private String str_Path;

	public OffLineCreateXml(String XmlName) {
		this.str_Path = LocalVariable.OffLineSdPath+"/test/"
				 + XmlName;
		File f=new File(LocalVariable.OffLineSdPath+"/test");
		if(!f.exists()){
			f.mkdir();
		}
		File file = new File(this.str_Path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressLint("DefaultLocale")
	private Boolean checkFileSuffix() {
		String[] strArray = this.str_Path.split("\\.");
		if (strArray.length <= 0) {
			this.errorMessage = "该文件无后缀名";
			return false;
		}
		if ((strArray[strArray.length - 1].toUpperCase().equals("XML"))
				|| (strArray[strArray.length - 1].toUpperCase().equals("CFG"))
				|| (strArray[strArray.length - 1].toUpperCase().equals("TBL"))) {
			return true;
		}
		this.errorMessage = "系统不支持该文件格式,请确保文件后缀名为[.CFG] 或者[.TBL],[.XML]";
		return false;
	}

	public Boolean dataSetToXML(DataSet _dataSet) {
		Boolean flag;
		if ((_dataSet == null) || (_dataSet.Tables.getCount() <= 0)) {
			this.errorMessage = "数据集为空或者无数据";
			return false;
		}

		try {
			File file = new File(this.str_Path);
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (Exception e) {

		}

		XmlCreater writer = new XmlCreater(this.str_Path);

		try {
			Element root = writer.createRootElement("DS");
			writer.createAttribute(root, "Name", _dataSet.DataSetName);
			writer.createAttribute(root, "Num",
					String.valueOf(_dataSet.Tables.getCount()));
			for (DataTable table : _dataSet.Tables) {
				Element dt = writer.createElement(root, "DT");
				writer.createAttribute(dt, "Name", table.TableName);
				writer.createAttribute(dt, "Num",
						String.valueOf(table.Rows.getCount()));
				if (table.Rows.getCount() == 0) {
					Element dr = writer.createElement(dt, "DR");
					writer.createAttribute(dr, "Name", "1");
					writer.createAttribute(dr, "Num", "-1");
					for (DataColumn column : table.Columns) {
						Element dc = writer.createElement(dr, "DC");
						writer.createAttribute(dc, "Name", column.ColumnName);
						writer.createAttribute(dc, "Num", "0");
					}
				} else {
					for (DataRow row : table.Rows) {
						Element dr = writer.createElement(dt, "DR");
						writer.createAttribute(dr, "Name", "1");
						writer.createAttribute(dr, "Num", "1");
						for (DataColumn column : table.Columns) {
							Element dc = writer.createElement(
									dr,
									"DC",
									row.get(column.ColumnName).toString()
											.replace("<", "(!lt!)")
											.replace(">", "(!gt!)"));
							writer.createAttribute(dc, "Name",
									column.ColumnName);
							writer.createAttribute(dc, "Num", "0");
						}
					}
				}
			}

			writer.buildXmlFile();
			flag = true;
		} catch (Exception exception) {
			this.errorMessage = exception.getMessage();
			flag = false;
		}
		return flag;
	}

	public String dataSetToXMLString(DataSet _dataSet) {
		if (_dataSet == null) {
			this.errorMessage = "数据集为空";
			return null;
		}
		if (!this.dataSetToXML(_dataSet)) {
			return null;
		}
		try {
			File file = new File(this.str_Path);
			FileInputStream reader = new FileInputStream(file);
			int fileLen = (int) file.length();
			byte[] chars = new byte[fileLen];
			reader.read(chars);
			String txt = new String(chars, "UTF-8");
			txt = txt.replace("\r", " ").replace("\n", " ");
			reader.close();
			return txt;
		} catch (Exception exception) {
			this.errorMessage = exception.getMessage();
			return null;
		}
	}

	public DataSet xmlStringToDataSet(String _xmlString) {

		try {
			FileOutputStream writer = new FileOutputStream(this.str_Path);
			writer.write(_xmlString.getBytes("UTF-8"));
			writer.close();
		} catch (Exception exception) {
			this.errorMessage = exception.getMessage();
			return null;
		}
		DataSet set = this.xmlToDataSet();

		return set;
	}

	public DataSet xmlToDataSet() {
		DataSet set2;
		if (!this.checkFileSuffix()) {
			return null;
		}
		DataSet set = null;
		DataTable table = null;
		DataRow row = null;
		XmlBuilder reader = null;
		try {
			reader = new XmlBuilder(this.str_Path);
			Element root = reader.getRoot();
			set = new DataSet();
			NodeList dts = root.getElementsByTagName("DT");
			// datatable
			for (int i = 0; i < dts.getLength(); i++) {
				Element dt = (Element) dts.item(i);
				table = new DataTable();
				table.TableName = dt.getAttribute("Name");

				NodeList drs = dt.getElementsByTagName("DR");
				for (int r = 0; r < drs.getLength(); r++) {
					// datarow
					row = table.NewRow();
					Element dr = (Element) drs.item(r);
					String drNum = dr.getAttribute("Num");

					// datacolumn
					NodeList dcs = dr.getElementsByTagName("DC");
					for (int c = 0; c < dcs.getLength(); c++) {
						Element dc = (Element) dcs.item(c);
						String columnName = dc.getAttribute("Name");
						String columnValue = dc.getTextContent()
								.replace("(!lt!)", "<").replace("(!gt!)", ">");
						if (!table.Columns.Contains(columnName)) {
							table.Columns.Add(columnName);
						}

						row.set(columnName, columnValue);
					}

					// 如果是空行则不读取
					if (!drNum.trim().equals("-1")) {
						table.Rows.Add(row);
					}
				}

				set.Tables.Add(table);
			}

			set2 = set;
		} catch (Exception exception) {
			this.errorMessage = exception.getMessage();
			set2 = null;
		}
		return set2;
	}

	public DataTable xmlToDataTable(String _tableName) {
		DataTable table2 = null;
		if (!this.checkFileSuffix()) {
			return null;
		}
		DataSet ds = this.xmlToDataSet();
		if (ds != null) {
			table2 = ds.Tables.get(_tableName);
		}
		return table2;
	}

	public String getErrorMsg() {
		return this.errorMessage;
	}
}
