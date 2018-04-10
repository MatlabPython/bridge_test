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
@ContextConfiguration({"classpath:applicationContext.xml"}) 
public class HBaseSelectTest {

	@Autowired
	 private com.gsafety.jdbcDao.HBaseSelectService HBaseSelectService;
	@Autowired
	private SensorService SensorService;
	@Test
	public void test() throws UnsupportedEncodingException, ParseException
	{
		List<SensorData> allSensorInfo = SensorService.getAllSensorInfo("繁华大道跨南淝河大桥", null, null);

		  List<Map<String, Object>> select1 = HBaseSelectService.select();
		  Map<String, Object> map2 = select1.get(0);
			System.out.println(map2.get("time"));

		
			 
	}
}
