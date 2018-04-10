package com.gsafety.testDemo;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsafety.redis.RadisData;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class redistest {

	@Autowired
	private RadisData RadisData;
	
	@Test
	public void test()
	{
		Map<String, String> equipData = RadisData.getEquipData("HF_FHDD_DY000001",
				"17", "10");
		System.out.println(equipData.get("time"));
		System.out.println(equipData.get("value"));

	
	}


}
