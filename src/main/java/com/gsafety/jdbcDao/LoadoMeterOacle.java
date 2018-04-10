package com.gsafety.jdbcDao;  


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.gsafety.annotation.ChooseDataSource;
import com.gsafety.dataMapper.LoadoMeterMapper;
import com.gsafety.loadometer.LoadoMeterBean;



@Component
public class LoadoMeterOacle {
	
	
	@Autowired
	/**
	 * c3p0操作模板
	 */
	JdbcTemplate jdbcTemplate;

	@ChooseDataSource(dataSourceName = "13")
	public  List<LoadoMeterBean> getAllLoadoMeterBean(String date) {
	

		String sql = "select * from mon_vehicledata where UPLOADTIME >=to_date('"+date+" 00:00:00','yyyy-mm-dd hh24:mi:ss') and UPLOADTIME <= to_date('"+date+" 23:59:59','yyyy-mm-dd hh24:mi:ss')";
		List<LoadoMeterBean> data=jdbcTemplate.query(sql, new LoadoMeterMapper());
		return data;
		
	}



	
}
