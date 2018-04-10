package com.gsafety.jdbcDao;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.util.MD5Hash;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.gsafety.SensorBean.SensorData;
import com.gsafety.annotation.ChooseDataSource;
import com.gsafety.DataAnalysis.HbaseTable;
@Component
public class HBaseSelectService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static Logger logger = LogManager.getLogger(HBaseSelectService.class);

	@Autowired
	private HbaseTable HbaseTable;
/**
 * 
 * 
 * @param SensorData 设备实体
 * @param starTime 开始时间
 * @param endTime 结束时间
 * @param table 表名称
 * @param columns 字段名称，字段名称，。。。
 * @return
 * @throws ParseException
 * @throws UnsupportedEncodingException  
 * @Description:
 */

	@ChooseDataSource(dataSourceName = "HBase")
	/**
	 * 
	 * @param SensorData 设备实体
	 * @param starTime 开始时间
	 * @param endTime 结束时间
	 * @param table 表名称
	 * @param datatype 数据类型 PV S10M FFT
	 * @param columns 哪些列PK, dataType, level, location, monitoring, rtime, seconds, sensorType, time, values
	 * @return
	 * @throws ParseException
	 * @throws UnsupportedEncodingException  
	 * @Description:
	 */
	public List<Map<String, Object>> select(SensorData SensorData, String starTime, String endTime,String table,String datatype,String... columns) throws ParseException, UnsupportedEncodingException {
		if(datatype==null)
		{
			datatype="PV";
		}
		
		String terninal = SensorData.getGatewaynum();
		String sensor = HbaseTable.getsensor(SensorData);
		String md5String = sensor + terninal;
		String hashcode = MD5Hash.getMD5AsHex(md5String.getBytes("utf-8"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long start = dateFormat.parse(starTime).getTime();
		long end = dateFormat.parse(endTime).getTime();

		String startrowkey = hashcode.substring(0, 6) + ":" + terninal + ":" + sensor + ":" + (Long.MAX_VALUE - end);
		String endrowkey = hashcode.substring(0, 6) + ":" + terninal + ":" + sensor + ":" + (Long.MAX_VALUE - start);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("select ");
		if (columns != null) {
			for (int i = 0; i < columns.length; i++) {
				if (i == columns.length - 1) {
					String column = HbaseTable.getcolumn(columns[i]);
					if (column != null) {
						stringBuffer.append("\"" + column + "\"");
					}

					break;
				} else {
					String column = HbaseTable.getcolumn(columns[i]);
					if (column != null) {
						stringBuffer.append("\"" + column + "\",");
					}

				}

			}

		}

		else {
			stringBuffer.append(" * ");
		}
		String baseSql = "  from " + table.toUpperCase() + " where \"PK\" between \'" + startrowkey + "\' and \'"
				+ endrowkey + "\' "+ " and "  +"\""+"dataType"+"\""+"="+ "\'"+datatype+"\'" ;
		stringBuffer.append(baseSql);
		stringBuffer.append(" order by "+"\"time\""+" asc");

		return jdbcTemplate.queryForList(stringBuffer.toString());
	}

	@ChooseDataSource(dataSourceName = "HBase")
	public List<Map<String, Object>> select() {
		
		StringBuffer sql = new StringBuffer("select *  from BRIDGE_HF_FHDD_DY000001 limit 10");
		return jdbcTemplate.queryForList(sql.toString());
	}

}
