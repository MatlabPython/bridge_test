package com.gsafety.test;  
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import com.gsafety.loadometer.CheckLoadoMeterImp;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
/**
 * 执行该程序会进行对地磅历史数据进行分析 分析结果会保存在C:\bridge_equipment目录中
 * @author xj
 *
 */
public class LoadoMeterTest {

	@Autowired
	CheckLoadoMeterImp bean;
	
	@Test
	public void test  () {
		bean.write();
		String[] getdate = getdate(40);
		for (int i = 0; i < getdate.length; i++) {
			
			bean.init(getdate[i]);

		}
	
		
	}
	
	
	public static String[] getdate(int size)
	{

		   String str[] =new String [size];
		
		   for (int i = 0; i < str.length; i++) {
			   
			   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		        Calendar lastDate = Calendar.getInstance();
		        lastDate.setTime(new Date());
		        lastDate.roll(Calendar.DAY_OF_YEAR, -i);//日期回滚7天
		        String a=sdf.format(lastDate.getTime());
		        str[i]=new String(a);
		        System.out.println(a);
		      
			
		}
	       
			return str;


	}


}

