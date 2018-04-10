package com.gsafety.jdbcDao;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.gsafety.DataAnalysis.HbaseTable;
import com.gsafety.SensorBean.BridgeSensorTable;
import com.gsafety.SensorBean.SensorData;
import com.gsafety.annotation.ChooseDataSource;
@Component
public class HiveDataSelectService {

	@Autowired
	private HbaseTable HbaseTable;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@ChooseDataSource(dataSourceName = "Hive")
	public List<Map<String, Object>> select(){
		
		String sql=" SELECT * FROM ods.bridge_acce  LIMIT 100 ";
		return jdbcTemplate.queryForList(sql);

	}
/**
 * 
 * @param SensorData 设备实体
 * @param starTime
 * @param endTime
 * @param table
 * @param datatype
 * @param columns
 * @return
 */
	@ChooseDataSource(dataSourceName = "Hive")
	public List<Map<String, Object>> select(SensorData SensorData, String starTime, String endTime,String table,String datatype,String... columns ){
		String terminal = SensorData.getGatewaynum();
		String sensor = HbaseTable.getsensor(SensorData);
//		starTime=BridgeSensorTable.DateToTimeStamp(starTime, "yyyy-MM-dd");
		endTime=BridgeSensorTable.DateToTimeStamp(endTime, "yyyy-MM-dd HH:mm:ss");
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("select ");
		if (columns != null) {
			for (int i = 0; i < columns.length; i++) {
				if (i == columns.length - 1) {
					String column = HbaseTable.getcolumn(columns[i]);
					if (column != null) {
						stringBuffer.append( column );
					}

					break;
				} else {
					String column = HbaseTable.getcolumn(columns[i]);
					if (column != null) {
						stringBuffer.append(column +",");
					}

				}

			}

		}
		else {
			stringBuffer.append(" * ");
		}
//		String baseSql = "  from " + table.toUpperCase() + " where time between " + starTime + " and "
//				+ endTime + " and "  +"dataType"+"="+ "\'"+datatype+"\'" ;
		
		
		String baseSql = "  from " + table.toUpperCase() + " where dt="+"'"+starTime+ "'"+" and  dataType"+"="+ "\'"+datatype+"\'";
		String where = " and terminal="+"'"+terminal+"'"+ " and sensor="+"'"+sensor+"'" ;
		stringBuffer.append(baseSql);
		stringBuffer.append(where);
		stringBuffer.append(" order by "+"time"+" asc ");
	
		return jdbcTemplate.queryForList(stringBuffer.toString());

	}
}
