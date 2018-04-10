package com.gsafety.DataAnalysis;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gsafety.SensorBean.SensorData;
import com.gsafety.jdbcDao.SensorService;

@Component
public class HbaseTable {

	@Autowired
	private SensorService SensorService;
	public 	final static String Features = "1";
	public final static String PK = "PK";
	public final static String dataType = "dataType";
	public final static String level = "level";
	public final static String location = "location";
	public final static String monitoring = "monitoring";
	public final static String rtime = "rtime";
	public final static String seconds = "seconds";
	public final static String sensorType = "sensorType";
	public final static String time = "time";
	public final static String values = "values";

	
	/**
	 * 已有网关列表
	 */
	List<Map<String, Object>> getgatewaynumList;

	public String getTablename(SensorData SensorData, String dataype) {

		String Tablenamebegin = "BRIDGE_";
		String TablenameEnd = "_1S";
		String TableName = null;
		if (getgatewaynumList == null) {
			getgatewaynumList = SensorService.getgatewaynumList();
		}

		if (dataype != null && dataype.equals(Features)) {// 如果查询的是特征值 返回特征值的表
			for (Map<String, Object> map : getgatewaynumList) {
				for (Entry<String, Object> entry : map.entrySet()) {
					String value = entry.getValue().toString();

					if (SensorData.getGatewaynum() != null && SensorData.getGatewaynum().equals(value)) {
						TableName = Tablenamebegin + SensorData.getGatewaynum() + TablenameEnd;
						return TableName;
					}
				}

			}

		}
		if (SensorData != null) {
			for (Map<String, Object> map : getgatewaynumList) {
				for (Entry<String, Object> entry : map.entrySet()) {
					String value = entry.getValue().toString();

					if (SensorData.getGatewaynum() != null && SensorData.getGatewaynum().equals(value)) {
						TableName = Tablenamebegin + SensorData.getGatewaynum();
						return TableName;
					}
				}

			}
		}

		return TableName;

	}
	public String getsensor(SensorData SensorData) {
		
		return SensorData.getModularnum()+"_"+SensorData.getPathnum();
	}
	
	
	public String getcolumn(String column)
	{
		if(column.equals(PK))
		{
			return PK;
		}
		if(column.equals(dataType))
		{
			return dataType;
		}
		
		if(column.equals(level))
		{
			return level;
		}
		
		if(column.equals(location))
		{
			return location;
		}
		
		if(column.equals(monitoring))
		{
			return monitoring;
		}
		
		if(column.equals(rtime))
		{
			return rtime;
		}
		
		if(column.equals(seconds))
		{
			return seconds;
		}
		
		if(column.equals(sensorType))
		{
			return sensorType;
		}
		
		if(column.equals(time))
		{
			return time;
		}
		
		if(column.equals(values))
		{
			return values;
		}
		
		
		
		return null;
		
	}

}
