package com.gsafety.dataMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gsafety.SensorBean.SensorData;
import com.gsafety.loadometer.LoadoMeterBean;

public class LoadoMeterMapper implements RowMapper<LoadoMeterBean> {

	public LoadoMeterBean mapRow(ResultSet rs, int rowNum) throws SQLException {

		LoadoMeterBean s = new LoadoMeterBean();
		s.setVEHICLEID(rs.getString(1));
		s.setDEVICEKEY(rs.getString(2));
		s.setINDEXCODE(rs.getString(3));
		s.setVEHICLEPOINT(rs.getString(4));
		s.setPLATENO(rs.getString(5));
		s.setPLATETYPE(rs.getString(6));
		s.setVEHICLEWEIGHT(rs.getString(7));
		s.setISOVERWEIGHT(rs.getString(8));
		s.setOVERWEIGHT(rs.getString(9));
		s.setLIMITWEIGHT(rs.getString(10));
		s.setAXLENUM(rs.getString(11));
		s.setAXLEWEIGHT(rs.getString(12));
		s.setAXLEDISTANCE(rs.getString(13));
		s.setAXLEGROUPNUM(rs.getString(14));
		s.setAXLEGROUPWEIGHT(rs.getString(15));
		s.setSPEED(rs.getString(16));
		s.setACCELERATION(rs.getString(17));
		s.setLANENO(rs.getString(18));
		s.setVEHICLETIME(rs.getString(19));
		s.setCONFIDENCELEVEL(rs.getString(20));
		s.setHEADIMAGEURL(rs.getString(21));
		s.setBODYIMAGEURL(rs.getString(22));
		s.setTAILIMAGEURL(rs.getString(23));
		s.setUPLOADTIME(rs.getString(24));
		s.setEQUIPMENTID(rs.getString(25));
		s.setBRIDGEID(rs.getString(26));
		return s;
	}

}
