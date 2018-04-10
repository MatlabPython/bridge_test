package com.gsafety.DataAnalysis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gsafety.Controller.DataController;
import com.gsafety.SensorBean.SensorData;
import com.gsafety.SensorBean.BridgeSensorTable;
import com.gsafety.databean.databaen;
import com.gsafety.databean.databiud;
import com.gsafety.io.FileOperation;
import com.gsafety.jdbcDao.SensorService;
import com.zeone.lifeline.collector.util.DateUtil;
import com.gsafety.mail.MailUtil;

@Component
public class HbaseDataAnalysis {
	
	
	@Autowired
	private MailUtil mail;
	private static Double avg = 0.0;

	static int lishishujutiaoshu;
	/**
	 * @see 未来数据条数
	 */
	static int weilaishujutiaoshu;
	/**
	 * @see totalLines 文件行数
	 */
	static int totalLines;
	/**
	 * @see chongfushuju 重复数据个数
	 */
	static int chongfushuju = 0;
	/**
	 * @see zhongduanshujugeshu 中断数据个数
	 */
	static int zhongduanshujugeshu = 0;
	/**
	 * @see cuowu 错误频率数据个数
	 */
	static int cuowu;
	/**
	 * @see chaochuliangchenggeshu 超出量程数据
	 */
	static int chaochuliangchenggeshu;//总计超出量程数据
	static int leijizhongduanshijian;//总计中断时间
	private static ArrayList<String> as = new ArrayList<String>();//缓存一秒数据
	private static Double num = 0.0;//数据计算总数 比如第一个值是10 第二个值200 最后会计算平均值
	private static Double Test;//错误数据
	private static Double Test1;//理论数据
	@Autowired
	private BridgeSensorTable BridgeSensorTable;
	@Autowired
	private SensorService SensorService;
	
	@Autowired
	private DataController DataController;
	public static ArrayList<databiud> datatest = new ArrayList<databiud>();


	private static TreeMap<String, ArrayList<String>> tmp = new TreeMap<String, ArrayList<String>>();

	@SuppressWarnings("static-access")
	public  databiud HbaseDataAnalysisCheck(SensorData s, ArrayList<databaen> datab) throws Exception {

		StringBuffer sb2 = new StringBuffer();
		sb2.append("桥梁名称" + "\t");
		sb2.append("传感器名称" + "\t");
		sb2.append("模块号" + "\t");
		sb2.append("通道号" + "\t");
		sb2.append("数据时间" + "\t");
		sb2.append("单位时间最大值" + "\t");
		sb2.append("单位时间最小值" + "\t");
		sb2.append("单位时间平均值" + "\t");
		sb2.append("实际采集（频率）" + "\t");
		sb2.append("最大量程" + "\t");
		sb2.append("最小量程" + "\t");
		sb2.append("标准频率" + "\n");

		FileOperation.writeTxFile(sb2.toString(), s.getEquipmentname(), "分析数据");
		sb2 = null;

		for (int i = 0; i < datab.size(); i++) {

			databaen date = datab.get(i);
			String awm=date.getVALUE();
			num = Double.valueOf(awm) + num;

			Double max = BridgeSensorTable.max_vlaue(s.getLeixing());
			Double min = BridgeSensorTable.min_vlaue(s.getLeixing());
			as.add(date.getVALUE());
			Double value = Double.valueOf(date.getVALUE());
			if (value > max || value < min) {// 超出量程范围统计
				chaochuliangchenggeshu = chaochuliangchenggeshu + 1;
				StringBuffer sbchao = new StringBuffer();
				sbchao.append(s.getBridgename()).append("\t");
				sbchao.append(s.getEquipmentname()).append("\t");
				sbchao.append(s.getModularnum()).append("\t");
				sbchao.append(s.getPathnum()).append("\t");
				sbchao.append(date.getDatetime()).append("\t");
				sbchao.append(date.getVALUE()).append("\n");
				FileOperation.writeTxFile(sbchao.toString(),
						s.getEquipmentname(), "超出量程数据明细");

			}
			Date hdatetime = DateUtil.parse(date.getDatetime(),
					"yyyy-MM-dd HH:mm:ss");
			Date hsystime = DateUtil.parse(date.getSystime(),
					"yyyy-MM-dd HH:mm:ss");
			
			if ((hsystime.getTime() - hdatetime.getTime()) > 1000 * 120) 
			{
				lishishujutiaoshu = lishishujutiaoshu + 1;
				StringBuffer sbchao = new StringBuffer();
				sbchao.append(s.getBridgename()).append("\t");
				sbchao.append(s.getEquipmentname()).append("\t");
				sbchao.append(s.getModularnum()).append("\t");
				sbchao.append(s.getPathnum()).append("\t");
				sbchao.append(date.getSystime()).append("\t");
				sbchao.append(date.getDatetime()).append("\t");
				sbchao.append(date.getVALUE()).append("\n");
				FileOperation.writeTxFile(sbchao.toString(),
						s.getEquipmentname(), "历史数据明细");

			}// 系时间大于数据时间超过30秒以上预警数据==历史预警数据条数
			if ((hdatetime.getTime() - hsystime.getTime()) > 1000 * 120) 
			{

				weilaishujutiaoshu = weilaishujutiaoshu + 1;
				StringBuffer sbchao = new StringBuffer();
				sbchao.append(s.getBridgename()).append("\t");
				sbchao.append(s.getEquipmentname()).append("\t");
				sbchao.append(s.getModularnum()).append("\t");
				sbchao.append(s.getPathnum()).append("\t");
				sbchao.append(date.getSystime()).append("\t");
				sbchao.append(date.getDatetime()).append("\t");
				sbchao.append(date.getVALUE()).append("\n");
				FileOperation.writeTxFile(sbchao.toString(),
						s.getEquipmentname(), "未来数据明细");

			}// 系时间小雨数据时间超过30秒以上预警数据==未来预警数据条数
				//

			/**
			 *
			 */

			if (i == datab.size() - 1) {
		
				ArrayList<String> als = tmp.get(date.getDatetime());

				if (als != null) {

					StringBuffer sb61 = new StringBuffer();
					sb61.append(s.getBridgename()).append("\t");
					sb61.append(s.getEquipmentname()).append("\t");
					sb61.append(s.getModularnum()).append("\t");
					sb61.append(s.getPathnum()).append("\t");
					sb61.append(date.getDatetime()).append("\n");

					FileOperation.writeTxFile(sb61.toString(),
							s.getEquipmentname(), "重复数据的明细");
					chongfushuju = chongfushuju + 1;
					als.addAll(as);

				} else {// 确认这个时间戳是否已经在map中有重复的KEY 如果没有就把值的集合存起来
					als = as;
				}

				tmp.put(date.getDatetime(), als);// 如果没有就把值的集合存起来
				Date a1 = DateUtil.parse(datab.get(i - 1).getDatetime(),
						"yyyy-MM-dd HH:mm:ss");
				Date a2 = DateUtil.parse(date.getDatetime(),
						"yyyy-MM-dd HH:mm:ss");

				if (a2.getTime() - a1.getTime() > BridgeSensorTable.longtime(s) && a2.getTime() - a1.getTime() != 0) {
					zhongduanshujugeshu = zhongduanshujugeshu + 1;
					long Longtime = a2.getTime() - a1.getTime();
					Longtime = Longtime - (10 * 1000 * 60);

					leijizhongduanshijian = (int) (leijizhongduanshijian + Longtime);
					String b=	BridgeSensorTable.getLongtime(Longtime);
					StringBuffer sb5 = new StringBuffer();
					sb5.append(s.getBridgename()).append("\t");
					sb5.append(s.getEquipmentname()).append("\t");
					sb5.append(s.getModularnum()).append("\t");
					sb5.append(s.getPathnum()).append("\t");
					sb5.append(date.getDatetime()).append("上一条数据时间\t");
					sb5.append(datab.get(i).getDatetime()).append("当前时间\t");
					sb5.append(b + "\n");
					FileOperation.writeTxFile(sb5.toString(),
							s.getEquipmentname(), "中断数据明细");

					// System.out.println(b);
				}

				if (als.size() != BridgeSensorTable.max_frequency(s.getLeixing(),s)) {// 如果这个频率不等于需要的频率
																				// 说明这个值的集合频率存在问题
																				// 是错误频率的数据

					cuowu = cuowu + 1;

					StringBuffer nb = new StringBuffer();
					nb.append(s.getBridgename()).append("\t");
					nb.append(s.getEquipmentname()).append("\t");
					nb.append(s.getModularnum()).append("\t");
					nb.append(s.getPathnum()).append("\t");
					nb.append(date.getDatetime()).append("\t");
					nb.append(als.size()).append("\n");

					FileOperation.writeTxFile(nb.toString(),
							s.getEquipmentname(), "错误频率数据明细表");

				}

				StringBuffer sb1 = new StringBuffer();
				sb1.append(s.getBridgename()).append("\t");
				sb1.append(s.getEquipmentname()).append("\t");
				sb1.append(s.getModularnum()).append("\t");
				sb1.append(s.getPathnum()).append("\t");
				sb1.append(date.getDatetime()).append("\t");
				sb1.append(Maths.germax(als)).append("\t");
				sb1.append(Maths.getmin(als)).append("\t");
				sb1.append(Maths.getavg(als)).append("\t");
				sb1.append(Maths.getsize(als)).append("\t");
				sb1.append(BridgeSensorTable.max_vlaue(s.getLeixing())).append("\t");
				sb1.append(BridgeSensorTable.min_vlaue(s.getLeixing())).append("\t");
				sb1.append(BridgeSensorTable.max_frequency(s.getLeixing(),s)).append(
						"\n");

				FileOperation.writeTxFile(sb1.toString(), s.getEquipmentname(),
						"分析数据");

				sb1 = null;
			
				as = new ArrayList<String>(50000);

				break;

			}

			/**
			 * 
			 * 判断date.getdatatime与下个时间戳是否相同 此项存在的意义是 因为一秒超过一条数据的话 就不进行重复数据的统计了
			 * 继续累加值的集合
			 */

			if (!date.getDatetime().toString().equals(datab.get(i + 1).getDatetime().toString())) 
			{

				ArrayList<String> als = tmp.get(date.getDatetime());
				if (als != null) {// 确认这个时间戳是否已经在map中有重复的KEY 如果有
									// 说明有重复的数据时间戳来了
									// 要记录下来
					StringBuffer sb61 = new StringBuffer();
					sb61.append(s.getBridgename()).append("\t");
					sb61.append(s.getEquipmentname()).append("\t");
					sb61.append(s.getModularnum()).append("\t");
					sb61.append(s.getPathnum()).append("\t");
					sb61.append(date.getDatetime()).append("\n");

					FileOperation.writeTxFile(sb61.toString(),
							s.getEquipmentname(), "重复数据的明细");

					chongfushuju = chongfushuju + 1;
					als.addAll(as);
				} else {// 确认这个时间戳是否已经在map中有重复的KEY 如果没有就把值的集合存起来
					als = as;
				}

				tmp.put(date.getDatetime(), als);// 如果没有就把值的集合存起来

				if (als.size() != BridgeSensorTable.max_frequency(s.getLeixing(),s)) {// 如果这个频率不等于需要的频率
																				// 说明这个值的集合频率存在问题
																				// 是错误频率的数据
					cuowu = cuowu + 1;
					StringBuffer nb = new StringBuffer();
					nb.append(s.getBridgename()).append("\t");
					nb.append(s.getEquipmentname()).append("\t");
					nb.append(s.getModularnum()).append("\t");
					nb.append(s.getPathnum()).append("\t");
					nb.append(date.getDatetime()).append("\t");
					nb.append(als.size()).append("\n");
					FileOperation.writeTxFile(nb.toString(),s.getEquipmentname(), "错误频率数据明细表");

				}

				Date a1 = DateUtil.parse(date.getDatetime(),"yyyy-MM-dd HH:mm:ss");
				Date a2 = DateUtil.parse(datab.get(i + 1).getDatetime(),
						"yyyy-MM-dd HH:mm:ss");

				/**
				 * 判断下一秒的数据时间戳与当前的时间 如果是不等于理论间隔
				 * 
				 */

				if (a2.getTime() - a1.getTime() != BridgeSensorTable.longtime(s) && a2.getTime() - a1.getTime() != 0) {
					long a = a2.getTime() - a1.getTime()- BridgeSensorTable.longtime(s);

					
						leijizhongduanshijian = (int) (leijizhongduanshijian + a);
						String b = BridgeSensorTable.getLongtime(a);
						StringBuffer sb5 = new StringBuffer();
						sb5.append(s.getBridgename()).append("\t");
						sb5.append(s.getEquipmentname()).append("\t");
						sb5.append(s.getModularnum()).append("\t");
						sb5.append(s.getPathnum()).append("\t");
						sb5.append(date.getDatetime()).append("上一条数据时间\t");
						sb5.append(datab.get(i + 1).getDatetime()).append(
								"当前数据时间\t");
						sb5.append(b + "\n");
						FileOperation.writeTxFile(sb5.toString(),s.getEquipmentname(), "中断数据明细");
						
						zhongduanshujugeshu = zhongduanshujugeshu + 1;

				}
				StringBuffer sb1 = new StringBuffer();
				sb1.append(s.getBridgename()).append("\t");
				sb1.append(s.getEquipmentname()).append("\t");
				sb1.append(s.getModularnum()).append("\t");
				sb1.append(s.getPathnum()).append("\t");
				sb1.append(date.getDatetime()).append("\t");
				sb1.append(Maths.germax(als)).append("\t");
				sb1.append(Maths.getmin(als)).append("\t");
				sb1.append(Maths.getavg(als)).append("\t");
				sb1.append(Maths.getsize(als)).append("\t");
				sb1.append(BridgeSensorTable.max_vlaue(s.getLeixing())).append("\t");
				sb1.append(BridgeSensorTable.min_vlaue(s.getLeixing())).append("\t");
				sb1.append(BridgeSensorTable.max_frequency(s.getLeixing(),s)).append(
						"\n");

				FileOperation.writeTxFile(sb1.toString(), s.getEquipmentname(),
						"分析数据");

				sb1 = null;
				as=new ArrayList<String>();
			

			}

		}
		// 加入数据统计功能
		avg = num / datab.size();
		StringBuffer sb = new StringBuffer();

		sb.append(datab.size()).append("\n");
		sb.append(avg).append("\n");

		FileOperation.writeTxFile(sb.toString(), s.getEquipmentname(), "分析数据");
		sb = null;

		databiud da = new databiud();
		da.setBridgename(s.getBridgename());
		da.setEquipmentname(s.getEquipmentname());
		da.setModularnum(s.getModularnum());
		da.setPathnum(s.getPathnum());
		da.setFilerow(datab.size());
		da.setPl(cuowu);
		da.setLilunpl(BridgeSensorTable.max_frequency(s.getLeixing(),s));
		da.setTimesize(tmp.size());
		Date n1 = DateUtil.parse(datab.get(0).getDatetime(),"yyyy-MM-dd HH:mm:ss");
		Date n2 = DateUtil.parse(datab.get(datab.size() - 1).getDatetime(),"yyyy-MM-dd HH:mm:ss");
		long s1 = n2.getTime() - n1.getTime();

		if (s1 < BridgeSensorTable.nd) {
			da.setLilunsize((int) ((BridgeSensorTable.nd / BridgeSensorTable.longtime(s))));

		} else {
			da.setLilunsize((int) ((s1 / BridgeSensorTable.longtime(s))));
		}

		if (s1 == 0) {
			da.setLilunsize(1);
		}
		da.setLilunfilerow(da.getLilunsize()* BridgeSensorTable.max_frequency(s.getLeixing(),s));//设置理论值
	
		String zhongduanshijian =BridgeSensorTable.getLongtime(leijizhongduanshijian);//计算累计中断了多久
		da.setZhongduanshuju(zhongduanshujugeshu);
		da.setChongfushujugeshu(chongfushuju);
		da.setChaochuliangchenggeshu(chaochuliangchenggeshu);
		da.setLishishujutiaoshu(lishishujutiaoshu);
		da.setWeilaishujutiaoshu(weilaishujutiaoshu);
		da.setShujujieshoushijianyichangzongshu(lishishujutiaoshu
				+ weilaishujutiaoshu);
		da.setZhongduanshijian(zhongduanshijian);
		da.setLeijizhongduanshijian(leijizhongduanshijian);
		da.setLeixing(s.getLeixing());
		int a = da.getPl() * da.getLilunpl();//错误个数*理论频率=错误数据个数
		int b = (int) da.getFilerow();//理论数据个数
		Test = (double) a;
		Test1 = (double) b;
		da.setCuowuPLzhanbi((Test / Test1) * 100);//设置错误数据的占比
		long a1 = da.getLeijizhongduanshijian() / 1000;
		long b1 = 86400;
		Test = (double) a1;
		Test1 = (double) b1;
		da.setLeijizhongduanshijianzhanbi((Test / Test1) * 100);
		int aaa = da.getChongfushujugeshu() * da.getLilunpl();
		int bba = (int) da.getFilerow();
		Test = (double) aaa;
		Test1 = (double) bba;
		da.setChongfushujzhanbi((Test / Test1) * 100);
		int aa = da.getChaochuliangchenggeshu();
		int ba = (int) da.getFilerow();
		Test = (double) aa;
		Test1 = (double) ba;
		da.setChaochuliangchengfanweizhanbi((Test / Test1) * 100);
		int aaaa = da.getShujujieshoushijianyichangzongshu();
		int bbba = (int) da.getFilerow();
		Test = (double) aaaa;
		Test1 = (double) bbba;

		da.setJieshouyichangzongshuzhanbi((Test / Test1) * 100);

		num = 0.0;
		avg = 0.0;
		cuowu = 0;
		zhongduanshujugeshu = 0;
		chongfushuju = 0;
		chaochuliangchenggeshu = 0;
		lishishujutiaoshu = 0;
		weilaishujutiaoshu = 0;
		leijizhongduanshijian = 0;
		tmp = new TreeMap<String, ArrayList<String>>();
		return da;
		

	}
	

public void AnalysisCheckeData (ArrayList<databiud> databiudList)
{

	Date date = new Date();
	String datadate = DateUtil.format(date, "yyyyMMdd");
	StringBuffer sb2 = new StringBuffer();
	sb2.append("桥梁名称" + "\t");
	sb2.append("传感器名称" + "\t");
	sb2.append("传感器类型" + "\t");
	sb2.append("模块号" + "\t");
	sb2.append("通道号" + "\t");
	sb2.append("实际数据行数" + "\t");
	sb2.append("理论数据行数" + "\t");
	sb2.append("理论频率" + "\t");
	sb2.append("错误频率数" + "\t");
	sb2.append("错误频率占比" + "\t");
	sb2.append("中断数据次数" + "\t");
	sb2.append("累计中断时间" + "\t");
	sb2.append("累计中断时间占比" + "\t");
	sb2.append("重复数据个数" + "\t");
	sb2.append("重复数据占比" + "\t");
	sb2.append("超出量程范围数据个数" + "\t");
	sb2.append("超出量程范围数据占比" + "\t");
	sb2.append("数据接收异常数据总数" + "\t");
	sb2.append("数据接收异常数据总数占比" + "\n");
	FileOperation.writeTxFile(sb2.toString(), datadate, "实时值分析数据汇总");
	if (databiudList.size() > 0) {

		for (databiud databiud : databiudList) {
			StringBuffer sb = new StringBuffer();
			sb.append(databiud.getBridgename()).append("\t");
			sb.append(databiud.getEquipmentname()).append("\t");
			sb.append(databiud.getLeixing()).append("\t");
			sb.append(databiud.getModularnum()).append("\t");
			sb.append(databiud.getPathnum()).append("\t");
			sb.append(databiud.getFilerow()).append("\t");
			sb.append(databiud.getLilunfilerow()).append("\t");
			sb.append(databiud.getLilunpl()).append("\t");
			sb.append(databiud.getPl()).append("\t");
			sb.append(databiud.getCuowuPLzhanbi()).append("\t");
			sb.append(databiud.getZhongduanshuju()).append("\t");
			sb.append(databiud.getZhongduanshijian()).append("\t");
			sb.append(databiud.getLeijizhongduanshijianzhanbi()).append(
					"\t");
			sb.append(databiud.getChongfushujugeshu()).append("\t");
			sb.append(databiud.getChongfushujzhanbi()).append("\t");
			sb.append(databiud.getChaochuliangchenggeshu()).append("\t");
			sb.append(databiud.getChaochuliangchengfanweizhanbi()).append(
					"\t");
			sb.append(databiud.getShujujieshoushijianyichangzongshu())
					.append("\t");
			sb.append(databiud.getJieshouyichangzongshuzhanbi()).append(
					"\n");
			FileOperation.writeTxFile(sb.toString(), datadate, "实时值分析数据汇总");

		}
		

		
		

	}
	
	System.out.println("统计完毕等待中！！！！");


	}
	
public  ArrayList<databiud> devHBASEjunit(String bridgename ,String modularnum,String pathnum,String starTime, String endTime) throws Exception
{
	  ArrayList<databiud> datatest = new ArrayList<databiud>();

	  List<SensorData> allSensorInfo = SensorService.getAllSensorInfo(bridgename, modularnum, pathnum);
	  for (SensorData sensorData : allSensorInfo) {
		System.out.println(sensorData.toString());
		ArrayList<databaen> data = DataController.getData(sensorData, starTime, endTime,"PV","rtime","time","values");
		data.remove(data.size()-1);
		databiud hbaseDataAnalysisCheck = HbaseDataAnalysisCheck(sensorData, data);
		datatest.add(hbaseDataAnalysisCheck);
	}
	return datatest;
}

public  ArrayList<databiud> devHivejunit(String bridgename ,String modularnum,String pathnum,String starTime, String endTime) throws Exception
{
	  ArrayList<databiud> datatest = new ArrayList<databiud>();

	  List<SensorData> allSensorInfo = SensorService.getAllSensorInfo(bridgename, modularnum, pathnum);
	  for (SensorData sensorData : allSensorInfo) {
		System.out.println(sensorData.toString());
		ArrayList<databaen> data = DataController.getHiveData(sensorData, starTime, endTime,"PV","rtime","time","values");
		databiud hbaseDataAnalysisCheck = HbaseDataAnalysisCheck(sensorData, data);
		datatest.add(hbaseDataAnalysisCheck);
	}
	return datatest;
}


public void sendmail(ArrayList<databiud> datatest ) throws MessagingException

{
	Date date = new Date();
	String datadate = DateUtil.format(date, "yyyy-MM-dd");
	String subject= "关于"+datadate+"桥梁历史数据检测分析结果"+"----"+DateUtil.format(new Date(),	"yyyy-MM-dd HH:mm:ss")+"检测完成";
	String recipient="xujian_anhui@gsafety.com";
	String  content =mail.getContentx(datatest).toString();
	mail.sendhtml(recipient, subject, content);

}


}
