package com.gsafety.dataMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.gsafety.SensorBean.SensorData;

public class SensorData13 implements RowMapper<SensorData> {

	public SensorData mapRow(ResultSet rs, int rowNum) throws SQLException {

		SensorData s = new SensorData();
		s.setBridgename(rs.getString("bridgename"));
		s.setEquipmentid(rs.getString("equipmentid"));
		s.setEquipmentname(rs.getString("equipmentname"));
		s.setDevicePosition(rs.getString("device_position"));
		s.setGatewaynum(rs.getString("gatewaynum"));
		s.setModularnum(rs.getString("modularnum"));
		s.setPathnum(rs.getString("pathnum"));
		s.setLeixing(rs.getString("monproject"));
		return s;
	}

}
