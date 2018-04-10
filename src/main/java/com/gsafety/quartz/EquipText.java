package com.gsafety.quartz;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gsafety.SensorBean.MialBean;
import com.gsafety.SensorBean.SensorData;
import com.gsafety.SensorBean.BridgeSensorTable;
import com.gsafety.io.FileOperation;
import com.gsafety.jdbcDao.SensorService;
import com.gsafety.redis.RadisData;
import com.zeone.lifeline.collector.util.DateUtil;
import com.gsafety.mail.MailUtil;

@Component
public class EquipText {

	@Autowired
	private SensorService SensorService;

	@Autowired
	private RadisData radis;

	@Autowired
	private MailUtil MailUtil;

	@Autowired
	private BridgeSensorTable tablemaxmin;
	/** 保存设备信息 */

	/** 判断数据是否正常的时间依据（11分钟） */
	private final static int INTEVAL = 1000 * 60 * 11;// 变更5分钟
	/** 检查时间间隔（1小时） */
	private final static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy年MM月dd日");// 时间为样式的文件名称
	String time = sdf.format(new Date());
	// 获得两个时间的毫秒时间差异
	static String gettime;

	/**
	 * @param args
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */

	public int init() throws UnsupportedEncodingException, Exception {

		System.out.println("开始检查桥梁中断数据" + sdf.format(new Date()));
		ArrayList<MialBean> write = write(radis);
		System.out.println("开始检查桥梁错误数据" + sdf.format(new Date()));
		errorwrite(radis);
		System.out.println("开始检查桥梁超频数据" + sdf.format(new Date()));
		test(radis);
		if (write.size() > 0) {
			return SendMail(write);
		}
		return 0;
	}

	public int SendMail(ArrayList<MialBean> mail) throws Exception {
		int stratus = 0;
		int flag = 0;
		int oneflag = 0;
		// 遍历获取中断超过5小时的个数
		for (MialBean mialBean : mail) {
			if (mialBean.getPass() > 1000 * 60 * 60 * 5)// 大于5小时的发送给哪些人
			{
				flag++;

			}

			if (mialBean.getPass() > 1000 * 60 * 60)// 大于一个小时的发给哪些人
			{
				oneflag++;

			}

		}
		
		

		if (flag > 10) // 大于5小时 的设备超过10个发送给哪些人
		{
			MailUtil.sendhtml("xujian_anhui@gsafety.com", "桥梁自动监测系统数据中断提示邮件"
					+ DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
					getContent(mail).toString());
			stratus = 1;
			return 1;

		}
		if (oneflag > 20 && stratus == 0) {
			MailUtil.sendhtml("xujian_anhui@gsafety.com", "桥梁自动监测系统数据中断提示邮件"
					+ DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
					getContent(mail).toString());

			stratus = 0;
			return 1;
		}
		return 0;

	}

	public void test(RadisData Radis) {
		List<SensorData> data = SensorService.getAllSensorInfo();

		String time = sdf.format(new Date());
		String prompt = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss")
				+ "超频数据检查结果：\n";
		String prop = "桥梁名称" + "\t" + "传感器名称" + "\t" + "模块号" + "\t" + "通道号"
				+ "\t" + "数据采集时间" + "\t" + "实际采集频率" + "\t" + "标准频率" + "\n";
		FileOperation.writeTxFile(prompt, time, "_超频数据");
		FileOperation.writeTxFile(prop, time, "_超频数据");
		data = SensorService.getAllSensorInfo();
		for (int i = 0; i < data.size(); i++) {
			SensorData s = data.get(i);
			HashMap<String, Integer> map = Radis.getEquipData1000_Ext(
					s.getGatewaynum(), s.getModularnum(), s.getPathnum());
			List<Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(
					map.entrySet());
			Collections.sort(list, new Comparator<Entry<String, Integer>>() {
				public int compare(Entry<String, Integer> o1,
						Entry<String, Integer> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
			});

			for (Entry<String, Integer> entry : list) {
				Object key = entry.getKey();
				Integer value = entry.getValue();
				Integer abc = tablemaxmin.max_frequency(s.getLeixing(), s);
				if (value > abc || abc > value) {
					StringBuffer sb = new StringBuffer();
					sb.append(s.getBridgename()).append("\t");
					sb.append(s.getEquipmentname()).append("\t");
					sb.append(s.getModularnum()).append("\t");
					sb.append(s.getPathnum()).append("\t");
					sb.append(key).append("\t");
					sb.append(value).append("\t");
					sb.append(abc).append("\n");
					FileOperation.writeTxFile(sb.toString(), time, "_超频数据");

				}

			}

		}
		System.out.println("结束检查桥梁超频数据" + sdf.format(new Date()));

	}

	public ArrayList<MialBean> write(RadisData radis) {
		List<SensorData> data = SensorService.getAllSensorInfo();

		ArrayList<MialBean> arrlyMial = new ArrayList<MialBean>();

		String time = sdf.format(new Date());
		String prompt = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss")
				+ "中断数据检查结果：\n";
		String prop = "桥梁名称" + "\t" + "传感器名称" + "\t" + "模块号" + "\t" + "通道号"
				+ "\t" + "数据采集时间" + "\t" + "采集值" + "\t" + "累计中断时间" + "\n";
		FileOperation.writeTxFile(prompt, time, "_中断数据");
		FileOperation.writeTxFile(prop, time, "_中断数据");
		for (int i = 0; i < data.size(); i++) {
			
			SensorData s = data.get(i);

			Map<String, String> value = radis.getEquipData(s.getGatewaynum(),
					s.getModularnum(), s.getPathnum());
			Long history = 0L;
			Long pass = 0L;
			if (value.get("time") == null) {
				StringBuffer sb = new StringBuffer();
				sb.append(s.getBridgename()).append("\t");
				sb.append(s.getEquipmentname()).append("\t");
				sb.append(s.getModularnum()).append("\t");
				sb.append(s.getPathnum()).append("\t");
				sb.append(
						DateUtil.format(new Date(history),
								"yyyy-MM-dd HH:mm:ss")).append("\t");
				sb.append(value.get("value")).append("\t");
				sb.append(time).append("\n");
				FileOperation.writeTxFile(sb.toString(), time, "_没有接收到数据");
			}

			if (value.get("time") != null) {
				history = Long.parseLong(value.get("time"));
				pass = new Date().getTime() - history;
				gettime = tablemaxmin.getLongtime(pass);
			}
			if (pass > INTEVAL) {

				StringBuffer sb = new StringBuffer();
				sb.append(s.getBridgename()).append("\t");
				sb.append(s.getEquipmentname()).append("\t");
				sb.append(s.getModularnum()).append("\t");
				sb.append(s.getPathnum()).append("\t");
				sb.append(
						DateUtil.format(new Date(history),
								"yyyy-MM-dd HH:mm:ss")).append("\t");
				sb.append(value.get("value")).append("\t");
				sb.append(gettime).append("\n");
				FileOperation.writeTxFile(sb.toString(), time, "_中断数据");

				MialBean mail = new MialBean();
				mail.setSensorData(s);
				mail.setPass(pass);
				mail.setHistory(history);
				mail.setVaule(value.get("value"));
				mail.setPassString(time);
				arrlyMial.add(mail);
			}

		}
		System.out.println("结束检查桥梁中断数据" + sdf.format(new Date()));
		return arrlyMial;
	}

	public void errorwrite(RadisData radis) {
		List<SensorData> data = SensorService.getAllSensorInfo();

		String time = sdf.format(new Date());
		String prompt = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss")
				+ "超标数据检查结果：\n";
		String prop = "桥梁名称" + "\t" + "传感器名称" + "\t" + "模块号" + "\t" + "通道号"
				+ "\t" + "数据采集时间" + "\t" + "采集值" + "\n";

		FileOperation.writeTxFile(prompt, time, "_超标数据");
		FileOperation.writeTxFile(prop, time, "_超标数据");

		for (int i = 0; i < data.size(); i++) {
			SensorData s = data.get(i);
			Map<String, String> value = radis.getEquipData(s.getGatewaynum(),
					s.getModularnum(), s.getPathnum());

			find(s, value, time);

		}
		FileOperation.writeTxFile("\n\n\n\n", time, "_超标数据");
		System.out.println("结束检查桥梁错误数据" + sdf.format(new Date()));

	}

	public void find(SensorData s, Map<String, String> value, String time) {

		if (value.get("time") == null) {
			System.out.println(s.getBridgename() + "\t" + value.get("value")
					+ "\t" + s.getEquipmentname() + "\t" + s.getModularnum()
					+ "\t" + s.getPathnum() + "\t"
					+ tablemaxmin.max_vlaue(s.getLeixing()) + "\t"
					+ tablemaxmin.min_vlaue(s.getLeixing()));

		}

		if (value.get("time") != null) {

			Double a = Double.valueOf(value.get("value"));

			if (a > tablemaxmin.max_vlaue(s.getLeixing())
					|| a < tablemaxmin.min_vlaue(s.getLeixing())) {
				tablemaxmin.Dowirte(s, value, time);

			}
		}

	}

	public StringBuffer getContent(ArrayList<MialBean> ls) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<body>");

		sb.append("下表是来自于自动监测系统监测的实时数据 请尽快处理中断情况");

		sb.append("<table border=" + "1" + ">");

		sb.append("<tr>");
		sb.append("<th border=" + "1" + ">");
		sb.append("桥梁名称");
		sb.append("</th border=" + "1" + ">");
		sb.append("<th border=" + "1" + ">");
		sb.append("传感器名称");
		sb.append("</th border=" + "1" + ">");
		sb.append("<th border=" + "1" + ">");
		sb.append("模块号");
		sb.append("</th border=" + "1" + ">");
		sb.append("<th border=" + "1" + ">");
		sb.append("通道号");
		sb.append("</th border=" + "1" + ">");
		sb.append("<th border=" + "1" + ">");
		sb.append("最后一条数据时间");
		sb.append("</th border=" + "1" + ">");
		sb.append("<th border=" + "1" + ">");
		sb.append("最后一条数据值");
		sb.append("</th border=" + "1" + ">");
		sb.append("<th border=" + "1" + ">");
		sb.append("累计中断时间");
		sb.append("</th border=" + "1" + ">");
		sb.append("</tr>");
		for (MialBean mialBean : ls) {

			sb.append("<tr>");
			sb.append("<td border=" + "1" + ">");
			sb.append(mialBean.getSensorData().getBridgename());
			sb.append("</td border=" + "1" + ">");
			sb.append("<td border=" + "1" + ">");
			sb.append(mialBean.getSensorData().getEquipmentname());
			sb.append("</td border=" + "1" + ">");
			sb.append("<td border=" + "1" + ">");
			sb.append(mialBean.getSensorData().getModularnum());
			sb.append("</td border=" + "1" + ">");
			sb.append("<td border=" + "1" + ">");
			sb.append(mialBean.getSensorData().getPathnum());
			sb.append("</td border=" + "1" + ">");
			sb.append("<td border=" + "1" + ">");
			sb.append(DateUtil.format(new Date(mialBean.getHistory()),
					"yyyy-MM-dd HH:mm:ss"));
			sb.append("</td border=" + "1" + ">");
			sb.append("<td border=" + "1" + ">");
			sb.append(mialBean.getVaule());
			sb.append("</td border=" + "1" + ">");
			sb.append("<td border=" + "1" + ">");
			sb.append(mialBean.getPassString());
			sb.append("</td border=" + "1" + ">");
			sb.append("</tr>");
		}

		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");

		return sb;

	}

}
