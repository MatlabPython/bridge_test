package com.gsafety.dataMapper;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 
 * @Title: 继承AbstractRoutingDataSource实现动态切换
 * @Description:
 * @Author:Administrator
 * @Since:2018年3月19日
 * @Version:1.1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		String sourceType = CustomContextHolder.getCustomerType();
		return sourceType;
	}

}
