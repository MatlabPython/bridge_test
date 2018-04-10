package com.gsafety.test;

import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.gsafety.SensorBean.SensorData;
import com.gsafety.jdbcDao.SensorService;
/**
 * 执行该程序会自动进行对13和15数据库中的传感器信息对象进行对比测试找出不同点
 * 
 * @author xj
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class TestBridgeEQUIPMENT {
	
	@Autowired
	private SensorService SensorService;
	
	@Test
	public void test()
	{

		List<SensorData> allSensordata15 = SensorService.getAllSensordata15();

		List<SensorData> allSensorInfo = SensorService.getAllSensorInfo();
		int i = 0;
		int flag = 0;
		for (SensorData s15 : allSensordata15) {

			for (SensorData s13 : allSensorInfo) {

				if (s15.getEquipmentname().equals(s13.getEquipmentname())
						&& s15.getModularnum().equals(s13.getModularnum())
						&& s15.getGatewaynum().equals(s13.getGatewaynum()) && s15.getPathnum().equals(s13.getPathnum()))

				{
					i = i + 1;
					flag = 0;
					// System.out.println(s.toString());
					// System.out.println(i);
					break;
				}

				flag = 1;
			}

			if (flag == 1) {

				System.out.println(s15.toString());
				flag = 0;
			}

		}
		System.out.println(i);
		System.out.println(allSensorInfo.size());
	}

}
