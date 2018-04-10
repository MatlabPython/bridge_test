package com.gsafety.testDemo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsafety.SensorBean.SensorData;
import com.gsafety.jdbcDao.SensorService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class selecttest {

	@Autowired
	private SensorService SensorService;
	
	@Autowired
	private com.gsafety.DataAnalysis.HbaseTable HbaseTable;
	
	@Test
	public void test()
	{
		List<SensorData> allSensorInfo = SensorService.getAllSensorInfo("206立交桥",null,null);
		System.out.println(allSensorInfo.size());
		
		
		List<SensorData> a = SensorService.getAllSensordata15();
	
		for (SensorData sensorData : a) {
			String tablename = HbaseTable.getTablename(sensorData ,null);
			System.out.println(tablename);

		}
		
	}
}
