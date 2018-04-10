package com.gsafety.TimeTask;  

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsafety.quartz.EquipText;


@Component  
public class TimeTask  extends TimerTask{
	
	
	@Autowired
	private EquipText ex;
	int init;
	@Override
	
	public void run() {
		
		// TODO Auto-generated method stub  
		System.out.println(new Date()+"开始时间");
		try {
			 init = ex.init();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}
		System.out.println(new Date()+"结束时间");
		
		if(init==1)
		{
			
			this.cancel();
			Config.exectime=1000*6*60*60;
			TimerTask ts =new TimeTask();
			AutozhixingMethood(ts,Config.exectime,Config.exectime);
			System.out.println(Config.exectime);
		}
		else{
			this.cancel();
			Config.exectime=1000*60*60;
			TimerTask ts =new TimeTask();
			AutozhixingMethood(ts,Config.exectime,Config.exectime);
			System.out.println(Config.exectime);
		}
		
	}

		 void AutozhixingMethood(TimerTask task ,long whenBeginLongtime ,long longtime) {
			Timer time= new Timer();
			time.schedule(task, whenBeginLongtime,longtime);
			
		}
		/**
		 * 在延时delay毫秒后执行task
		 * @param task
		 * @param whenBeginLongtime
		 * @param longtime  
		 * @Description:
		 */
		private static void zhidingTimeMethood(TimerTask task ,long longtime) {
			Timer time= new Timer();
			time.schedule(task, longtime);
		}
	

}
