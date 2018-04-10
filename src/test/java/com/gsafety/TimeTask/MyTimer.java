package com.gsafety.TimeTask;  

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 执行该程序会自动进行Redis数据的抽样检测工作
 * @author xj
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"}) 
public class MyTimer {
	@Autowired
	private TimeTask ts;
	@Test
	public void run()
	{
		ts.run();
	}

	
}
