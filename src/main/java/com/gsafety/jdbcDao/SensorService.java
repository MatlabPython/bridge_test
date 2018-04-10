package com.gsafety.jdbcDao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.gsafety.SensorBean.SensorData;
import com.gsafety.annotation.ChooseDataSource;
import com.gsafety.dataMapper.SensorData13;
import com.gsafety.dataMapper.SensorData15;

/**
 * 数据库相关操作
 */
@Component
public class SensorService {

	@Autowired
	/**
	 * c3p0操作模板
	 */
	JdbcTemplate jdbcTemplate;

	/**
	 * 
	 * @return 返回设备列表
	 * @Description:
	 */
	@ChooseDataSource(dataSourceName = "13")
	public List<SensorData> getAllSensorInfo() {
		String sql = "select bridge.bridgename, a.equipmentid, a.equipmentname, a.device_position, b.gatewaynum, b.modularnum, b.pathnum, mon.monproject from bas_equipment a left join MAM_IOTDNSCFG b on a.equipmentid = b.equipmentid and a.monprojectid = b.monprojectid left join bas_bridge bridge on a.bridgeid = bridge.bridgeid left join mon_monproject mon on a.monprojectid = mon.monprojectid where b.gatewaynum is not null and mon.monproject !='地磅' order by bridge.bridgename, a.monprojectid";
		List<SensorData> SensorData = jdbcTemplate.query(sql, new SensorData13());
		return SensorData;

	}

	
	

	/**
	 * 
	 * @return 返回设备列表
	 * @Description:
	 */
	@ChooseDataSource(dataSourceName = "13")
	public List<SensorData> getAllSensorInfo(String bridgename, String modularnum, String pathnum) {
		
		StringBuffer sb = new StringBuffer();

		String sql = "select bridge.bridgename, a.equipmentid, a.equipmentname, a.device_position, b.gatewaynum, b.modularnum, b.pathnum, mon.monproject from bas_equipment a left join MAM_IOTDNSCFG b on a.equipmentid = b.equipmentid and a.monprojectid = b.monprojectid left join bas_bridge bridge on a.bridgeid = bridge.bridgeid left join mon_monproject mon on a.monprojectid = mon.monprojectid where b.gatewaynum is not null and mon.monproject !='地磅'";
		sb.append(sql);
		if (bridgename != null) {
			sb.append(" and  bridge.bridgename =" + "\'" + bridgename + "\'");
		}
		if (modularnum != null) {
			sb.append(" and  b.modularnum =" + "\'" + modularnum + "\'");
		}
		if (pathnum != null) {
			sb.append(" and  b.pathnum =" + "\'" + pathnum + "\'");
		}
		
		sb.append(" order by bridge.bridgename, a.monprojectid");
		List<SensorData> SensorData = jdbcTemplate.query(sb.toString(), new SensorData13());
		return SensorData;

	}
	/**
	 * 
	 * @return 返回设备列表
	 * @Description:
	 */
	@ChooseDataSource(dataSourceName = "15")
	public List<SensorData> getAllSensordata15() {
		String sql = "select a.equipmentname,a.parent_id,b.tid,b.monitor_id,c.bridgename,d.tname,b.sensor_code from  PLAT_BAS_EQUIPMENT a,PLAT_BAS_EQUIPCONFIG b,PLAT_BAS_BRIDGE c,plat_bas_dictionary d where a.equipmentid=b.equip_id and c.bridgeid=b.build_id and d.tid=b.monitor_id and b.sensor_code is not null and d.tname!='地磅' order by c.bridgename, d.tname ";
		List<SensorData> SensorData = jdbcTemplate.query(sql, new SensorData15());
		return SensorData;

	}

	@ChooseDataSource(dataSourceName = "15")
	public List<SensorData> getAllSensordata15(String bridgename, String modularnum, String pathnum) {

		StringBuffer sb = new StringBuffer();
		String sql = "select a.equipmentname,a.parent_id,b.tid,b.monitor_id,c.bridgename,d.tname,b.sensor_code from  PLAT_BAS_EQUIPMENT a,PLAT_BAS_EQUIPCONFIG b,PLAT_BAS_BRIDGE c,plat_bas_dictionary d where a.equipmentid=b.equip_id and c.bridgeid=b.build_id and d.tid=b.monitor_id and b.sensor_code is not null and d.tname!='地磅' ";
		sb.append(sql);

		if (bridgename != null) {
			sb.append(" and  c.bridgename =" + "\'" + bridgename + "\'");
		}

		if (modularnum != null&&pathnum != null) {
			String sensor_code= modularnum+"_"+pathnum;
			
			sb.append(" and  b.sensor_code =" + "\'" +sensor_code+ "\'");
		}
		sb.append(" order by c.bridgename, d.tname");

		List<SensorData> SensorData = jdbcTemplate.query(sb.toString(), new SensorData15());
		return SensorData;

	}
	
	@ChooseDataSource(dataSourceName = "15")
	public List<Map<String, Object>> getgatewaynumList()
	{
		String sql = "select a.parent_id from  PLAT_BAS_EQUIPMENT a,PLAT_BAS_EQUIPCONFIG b,PLAT_BAS_BRIDGE c,plat_bas_dictionary d where a.equipmentid=b.equip_id and c.bridgeid=b.build_id and d.tid=b.monitor_id and b.sensor_code is not null and d.tname!='地磅' group by  a.parent_id ";
		List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql);
		return queryForList;
	
	}

}
