package com.gsafety.dataMapper;

/**
 * 
 * @Title: 动态设置数据库基础类
 * @Description:
 * @Author:Administrator
 * @Since:2018年3月19日
 * @Version:1.1.0
 */
public class CustomContextHolder {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	
	public static final String DATA_SOURCE_FROM_13 = "DataSource13";// 对应动态数据源配置中的key
	public static final String DATA_SOURCE_15 = "DataSource15"; // 对应动态数据源配置中的key
	public static final String DATA_SOURCE_HBase = "DataSourceHbase"; // 对应动态数据源配置中的key
	public static final String DATA_SOURCE_Hive = "DataSourceHive"; // 对应动态数据源配置中的key

	public static void setCustomerType(String customerType) {
		contextHolder.set(customerType);
	}

	public static String getCustomerType() {
		return contextHolder.get();
	}

	public static void clearCustomerType() {
		contextHolder.remove();
	}
}
