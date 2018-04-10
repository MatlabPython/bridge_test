package com.gsafety.SensorBean;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gsafety.io.FileOperation;
import com.zeone.lifeline.collector.util.DateUtil;

@Component
public class BridgeSensorTable {


	public final static String DynamicDeflection = "动态挠度";// 动态挠度
	public final static String strain = "应变";// 应变
	public final static String temperature = "温度";// 温度
	public final static String windSpeed = "风速";// 风速
	public final static String windDirection = "风向";// 风向
	public final static String StaticDeflection = "静态挠度";// 静态挠度
	public final static String SuspenderForce = "吊杆力";// 吊杆力
	public final static String displacement = "位移";// 位移
	public final static String DipAngle = "倾角";// 倾角
	public final static String Jsd = "加速度";// 加速度
	public final static long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
	public final static long nh = 1000 * 60 * 60;// 一小时的毫秒数
	public final static long nm = 1000 * 60;// 一分钟的毫秒数
	public final static long ns = 1000;// 一秒钟的毫秒数long
	
	
	
	
	public  void Dowirte(SensorData s,Map<String, String> value, String time)
	{
		long history = 0L;
		if(value.get("time") == null){
			history = 0;
		} else {
			history = Long.parseLong(value.get("time"));
		}
		StringBuffer sb = new StringBuffer();
		sb.append(s.getBridgename()).append("\t");
		sb.append(s.getEquipmentname()).append("\t");
		sb.append(s.getModularnum()).append("\t");
		sb.append(s.getPathnum()).append("\t");
		sb.append(DateUtil.format(new Date(history), "yyyy-MM-dd HH:mm:ss")).append("\t");
		sb.append(value.get("value")).append("\n");


		FileOperation.writeTxFile(sb.toString(), time,"_超标数据");

	}

	/**
	 * 
	 * @param Sensortype
	 * @return 返回桥梁传感器的最小量程
	 */
	public   Double min_vlaue(String Sensortype) {

		if (Sensortype.equals(DynamicDeflection)) {

			return (double) -750;
		}

		if (Sensortype.equals(strain)) {

			return (double) -3000;
		}
		if (Sensortype.equals(temperature)) {

			return (double) -50;
		}
		if (Sensortype.equals(windSpeed)) {

			return (double) 0;
		}
		if (Sensortype.equals(windDirection)) {

			return (double) 0;
		}

		if (Sensortype.equals(StaticDeflection)) {

			return (double) -150;
		}
		if (Sensortype.equals(SuspenderForce)) {

			return (double) -3000;
		}
		if (Sensortype.equals(displacement)) {

			return (double) -375;
		}
		if (Sensortype.equals(DipAngle)) {

			return (double) -15;
		}
		if (Sensortype.equals(Jsd)) {

			return (double) -2000;
		}

		return (double) -99999;

	}
	
	/**
	 * 
	 * @param Sensortype
	 * @return 返回桥梁传感器的最大量程
	 */
	public   Double max_vlaue(String Sensortype) {
		if (Sensortype.equals(DynamicDeflection)) {

			return (double) 750;
		}

		if (Sensortype.equals(strain)) {

			return (double) 3000;
		}
		if (Sensortype.equals(temperature)) {

			return (double) 100;
		}
		if (Sensortype.equals(windSpeed)) {

			return (double) 50;
		}
		if (Sensortype.equals(windDirection)) {

			return (double) 360;
		}

		if (Sensortype.equals(StaticDeflection)) {

			return (double) 150;
		}
		if (Sensortype.equals(SuspenderForce)) {

			return (double) 3000;
		}
		if (Sensortype.equals(displacement)) {

			return (double) 375;
		}
		if (Sensortype.equals(DipAngle)) {

			return (double) 15;
		}
		if (Sensortype.equals(Jsd)) {

			return (double) 2000;
		}

		return (double) 99999;

	}
	/**
	 * 
	 * @param Sensortype
	 * @return 返回桥梁传感器的频率值
	 */
	public   Integer max_frequency(String Sensortype ,SensorData s) {
		if (Sensortype.equals(DynamicDeflection)) {

			return  8;
		}

		if (Sensortype.equals(strain)) {

			return  10;
		}
		if (Sensortype.equals(temperature)) {

			return  1;
		}
		if (Sensortype.equals(windSpeed)) {

			return  1;
		}
		if (Sensortype.equals(windDirection)) {

			return 1;
		}

		if (Sensortype.equals(StaticDeflection)) {

			return  1;
		}
		if (Sensortype.equals(SuspenderForce)) {
			if(s.getBridgename().equals("环巢湖路南淝河大桥"))
			{
				return 1;
			}
			else{
				return 1;
			}
		
		}
		if (Sensortype.equals(displacement)) {

			return  10;
		}
		if (Sensortype.equals(DipAngle)) {

			return  1;
		}
		if (Sensortype.equals(Jsd)) {

			return  20;
		}

		return  99999;

	}
	
	public   int getlilunfilerow(SensorData s) {
		if (s.getLeixing().equals(DynamicDeflection)) {

			return  24*60*60*8;
		}

		if (s.getLeixing().equals(strain)) {

			return  24*60*60*10;
		}
		if (s.getLeixing().equals(temperature)) {

			return  6*24;
		}
		if (s.getLeixing().equals(windSpeed)) {

			return  24*60*60*10;
		}
		if (s.getLeixing().equals(windDirection)) {

			return 24*60*60*10;
		}

		if (s.getLeixing().equals(StaticDeflection)) {

			return  6*24;
		}
		if (s.getLeixing().equals(SuspenderForce)) {
			if(s.getBridgename().equals("环巢湖路南淝河大桥"))
			{
				return 24*60*60*10;
			}
			else{
				return 6*24;
			}

		}
		if (s.getLeixing().equals(displacement)) {

			return  24*60*60*10;
		}
		if (s.getLeixing().equals(DipAngle)) {

			return  6*24;
		}
		if (s.getLeixing().equals(Jsd)) {

			return  24*60*60*20;
		}

		return  99999;

	}
	/**
	 * 
	 * @param Sensortype
	 * @return 返回桥梁传感器的传输间隔时间
	 */
	public   long longtime(SensorData s) {
		if (s.getLeixing().equals(DynamicDeflection)) {

			return  1000;
		}

		if (s.getLeixing().equals(strain)) {

			return  1000;
		}
		if (s.getLeixing().equals(temperature)) {

			return  1000*60*10;
		}
		if (s.getLeixing().equals(windSpeed)) {

			return  1000;
		}
		if (s.getLeixing().equals(windDirection)) {

			return 1000;
		}

		if (s.getLeixing().equals(StaticDeflection)) {

			return  1000*60*10;
		}
		if (s.getLeixing().equals(SuspenderForce)) {
			
			if(s.getBridgename().equals("环巢湖路南淝河大桥"))
			{
				return 1000;
			}
			else{
				return 1000*60*10;
			}

		}
		if (s.getLeixing().equals(displacement)) {

			return  1000;
		}
		if (s.getLeixing().equals(DipAngle)) {

			return  1000*60*10;
		}
		if (s.getLeixing().equals(Jsd)) {

			return  1000;
		}

		return  99999;

	}
	
	
	  /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String DateToTimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
	
	
    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     * @param timestampString 时间戳 如："1473048265";
     * @param formats 要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     *
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStampTODate(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString);
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }
	
	
    
    
    public static void main(String[] args) {
    	
    	String timeStampTODate = BridgeSensorTable.TimeStampTODate("1522210211300", "yyyy-MM-dd HH:mm:ss SSS");
    	String timeStampTODate1 = BridgeSensorTable.DateToTimeStamp(timeStampTODate, "yyyy-MM-dd HH:mm:ss");
    	System.out.println(timeStampTODate);
    	System.out.println(timeStampTODate1);
	}
    

	public String getLongtime( long  time )
	
	{
		long day = time / nd;// 计算差多少天
		long hour = time % nd / nh;// 计算差多少小时
		long min = time % nd % nh / nm;// 计算差多少分钟
		long sec = time % nd % nh % nm / ns;// 计算差多少秒//输出结果
		String t = day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
		
		
		return t;
		
	}
	
}
