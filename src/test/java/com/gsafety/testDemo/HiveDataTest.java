package com.gsafety.testDemo;

import com.gsafety.jdbcDao.HiveDataSelectService;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"}) 
public class HiveDataTest {

		@Autowired
	 private HiveDataSelectService HiveDataSelectService;

	 @Test
	 public void test()
	 {
		 List<Map<String, Object>> select = HiveDataSelectService.select();
	 }
}
