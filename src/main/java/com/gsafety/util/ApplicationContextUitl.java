package com.gsafety.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 单例模式的ApplicationContext
 * 
 */
final public class ApplicationContextUitl {
	private static Logger LOGGER = LogManager
			.getLogger(ApplicationContextUitl.class);

	private static ApplicationContext ApplicationContext = null;

	private ApplicationContextUitl() {
		System.out.println("构造函数被调用2");
	}

	/**
	 * 静态方法方式单例模式 没有实现懒加载
	 * 
	 * @return
	 * @Description:
	 */
	public static ApplicationContext getApplicationContext() {
		return ApplicationContext;
	}

	/**
	 * 实现懒加载
	 */
	public static ApplicationContext getApplicationContextlzy() {
		if (ApplicationContext == null) {

			ApplicationContext = new ClassPathXmlApplicationContext(
					"applicationContext.xml");

		}

		return ApplicationContext;
	}

}
