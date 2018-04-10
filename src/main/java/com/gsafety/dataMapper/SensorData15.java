package com.gsafety.dataMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.gsafety.SensorBean.SensorData;

public class SensorData15 implements RowMapper<SensorData> {

	public SensorData mapRow(ResultSet rs, int rowNum) throws SQLException {

		SensorData s = new SensorData();

		s.setBridgename(rs.getString("bridgename"));
		s.setEquipmentid(rs.getString("tid"));
		s.setEquipmentname(rs.getString("equipmentname"));
		s.setGatewaynum(rs.getString("parent_id"));
		s.setLeixing(rs.getString("tname"));
		String a = rs.getString("sensor_code");

		if (a.contains("_")) {
			s.setModularnum(getModularnum(a));
			s.setPathnum(getpathnum(a));
		} else {
			s.setModularnum("null");
			s.setPathnum("null");
		}
		return s;
	}

	public static String getModularnum(String s)

	{
		String[] split = s.split("_");

		return split[0];

	}

	public static String getpathnum(String s) {

		String[] split = s.split("_");

		return split[1];

	}

}
