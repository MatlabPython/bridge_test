package com.gsafety.testDemo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsafety.SensorBean.SensorData;
import com.gsafety.jdbcDao.SensorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class BridgeHabseDataTest {

	@Autowired
	private SensorService SensorService;

	@Autowired
	private com.gsafety.DataAnalysis.HbaseTable HbaseTable;

	@Autowired
	private com.gsafety.jdbcDao.HBaseSelectService HBaseSelectService;

	@Test
	public void test() throws UnsupportedEncodingException, ParseException {
		List<SensorData> allSensorInfo = SensorService.getAllSensorInfo("繁华大道跨南淝河大桥", "18", "7");

		for (SensorData sensorData : allSensorInfo) {

			List<Map<String, Object>> select = HBaseSelectService.select(sensorData, "2018-03-26 00:00:13",	"2018-03-26 13:12:13", HbaseTable.getTablename(sensorData,HbaseTable.Features ), null,null);

			for (Map<String, Object> map : select) {
				Object object = map.get("values");
				System.out.println(map.get("time")+object.toString());
				
			}

		}

	}
}
