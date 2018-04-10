package com.gsafety.testDemo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gsafety.mail.MailUtil;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class mailtest {

	private static Logger LOGGER = LogManager.getLogger(mailtest.class);

	@Autowired
	private MailUtil MailUtil;
	
	@Test
	public void sendmail ()
	{
		MailUtil.send("xujian_anhui@gsafety.com", "assdad", "sadsad");

	}
}
