package com.gsafety.test;



import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsafety.databean.databiud;


/**
 * 执行该程序会对指定设备进行数据分析 数据来源来自Hbase 数据检测结果会保存C:\bridge_equipment
 * @author xj
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class HbaseDataAnalysisJunit {

	@Autowired
	private com.gsafety.DataAnalysis.HbaseDataAnalysis HbaseDataAnalysis;
	@Test
	public void test() throws Exception
	{
		ArrayList<databiud> devjunit = HbaseDataAnalysis.devHBASEjunit("206立交桥", null, null, "2018-03-30 13:00:00", "2018-03-30 15:20:00");
		HbaseDataAnalysis.AnalysisCheckeData(devjunit);
//		HbaseDataAnalysis.sendmail(devjunit);
		  
	}
	
	

}
