package com.gsafety.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.gsafety.databean.databiud;
import com.zeone.lifeline.collector.util.DateUtil;

/**
 * 
 * @Title: Spring 集成邮件操作类
 * @Description:
 * @Author:Administrator
 * @Since:2018年3月19日
 * @Version:1.1.0
 */
@Component
public class MailUtil {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private SimpleMailMessage simpleMailMessage;

	public static final String tdBegin="<td border="+"1"+">";
	public static final String tdEnd="</td border="+"1"+">";
	public static final String tRBegin="<tr>";
	public static final String tREnd="</tr>";
	public static final String tHBegin="<th border="+"1"+">";
	public static final String tHEnd="</th border="+"1"+">";
	/**
	 * 单发
	 * 
	 * @param recipient
	 *            收件人
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 */
	public void send(String recipient, String subject, String content) {
		
		simpleMailMessage.setTo(recipient);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(content);
		mailSender.send(simpleMailMessage);
	}

	
	
	
	public void sendhtml(String recipient, String subject, String content) throws MessagingException {
		
		
		
		 	MimeMessage msg = mailSender.createMimeMessage();    
	        MimeMessageHelper message = new MimeMessageHelper(msg, true, "UTF-8");  
	          
	        message.setFrom("379753498@qq.com");    
	        message.setSubject(subject);    
	        message.setTo(recipient);  //这个地方传入数组可以发送给多人  
	        //message.setCc("testmycc@sina.com");  //抄送地址,传入数组也可以抄送多人  
	        message.setText(content, true);  //如果发的不是html内容去掉true参数  
	          
	        // add the image (图片会嵌入到邮件里显示出来)  
//	        FileSystemResource image = new FileSystemResource(new File("H:/我的像册/摇大旗.JPG"));  
//	        message.addInline("shihuantp", image);   //这里的shihuantp一定要跟<img src=\"cid:shihuantp\">中cid后面的值一样  
//	          
	        mailSender.send(msg);  
	          
	        System.out.println("成功发送Html邮件！");  
		
		
	
	}

	
    public  MimeMessage createMimeMessages( ArrayList<databiud> datatest, String date) throws Exception {
        // 1. 创建一封邮件
     	MimeMessage msg = mailSender.createMimeMessage();    
        // 2. From: 发件人
//        message.setFrom(new InternetAddress(myEmailAccount, "徐健", "UTF-8"));
		

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
//        message.setRecipients(MimeMessage.RecipientType.TO, Address);

        // 4. Subject: 邮件主题
//        message.setSubject("关于"+date+"桥梁历史数据检测分析结果"+"----"+DateUtil.format(new Date(),	"yyyy-MM-dd HH:mm:ss")+"检测完成", "UTF-8");
//
//        // 5. Content: 邮件正文（可以使用html标签）
//        message.setContent(getContentx(datatest).toString(), "text/html;charset=UTF-8");
//        // 6. 设置发件时间
//        message.setSentDate(new Date());
//
//        // 7. 保存设置
//        message.saveChanges();
//
     	return msg;
    }
	
	
	
	
	public void setMailSender(JavaMailSender mailSender) {
	
		this.mailSender = mailSender;

	}

	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	/**
	 * 群发
	 * 
	 * @param recipients
	 *            收件人
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 */
	public void send(List<String> recipients, String subject, String content) {
		simpleMailMessage
				.setTo(recipients.toArray(new String[recipients.size()]));
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(content);
		mailSender.send(simpleMailMessage);

	}
	
	
    public  StringBuffer getContentx(ArrayList<databiud> datatest)
    {
    	StringBuffer sb= new StringBuffer();
    	sb.append("<html>");
    	sb.append("<body>");
    	
    	sb.append("下表是来历史分析系统分析结果请查阅、详细资料可向我索要");
    	
    	sb.append("<table border="+"1"+">");
    	
    	
    	sb.append(MailUtil.tRBegin);
    	sb.append(MailUtil.tHBegin);
    	sb.append("桥梁名称");
    	sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("传感器名称");
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("传感器类型");
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("模块号");
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("通道号");
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("实际数据行数");
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("理论数据行数");
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("理论频率");
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("错误频率数" );
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("错误频率占比");
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("中断数据次数");
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("累计中断时间");
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("累计中断时间占比");
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("重复数据个数");
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("重复数据占比" );
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("超出量程范围数据个数");
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("超出量程范围数据占比");
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("数据接收异常数据总数");
		sb.append(MailUtil.tHEnd);
    	sb.append(MailUtil.tHBegin);
		sb.append("数据接收异常数据总数占比");
		sb.append(MailUtil.tHEnd);
		sb.append(MailUtil.tREnd);
    
    	for (databiud databiud : datatest) {
    		sb.append(MailUtil.tRBegin);
    		sb.append(MailUtil.tdBegin);
    		sb.append(databiud.getBridgename());
    		sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getEquipmentname());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getLeixing());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getModularnum());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getPathnum());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getFilerow());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getLilunfilerow());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getLilunpl());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getPl());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getCuowuPLzhanbi());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getZhongduanshuju());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getZhongduanshijian());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getLeijizhongduanshijianzhanbi());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getChongfushujugeshu());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getChongfushujzhanbi());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getChaochuliangchenggeshu());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getChaochuliangchengfanweizhanbi());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getShujujieshoushijianyichangzongshu());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdBegin);
			sb.append(databiud.getJieshouyichangzongshuzhanbi());
			sb.append(MailUtil.tdEnd);
    		sb.append(MailUtil.tdEnd);
		}
    	    	    	    	
    	sb.append("</table>");
    	sb.append("</body>");
    	sb.append("</html>");
    	
		return sb;
    	
    	
    	
    }

}