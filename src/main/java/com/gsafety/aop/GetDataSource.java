package com.gsafety.aop;

import com.gsafety.annotation.ChooseDataSource;
import com.gsafety.dataMapper.CustomContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class GetDataSource {

	/**
	 * 切面设置要选择的数据库
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.gsafety.annotation.ChooseDataSource)")
	public Object permission(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		Method method = getMethod(joinPoint, args);// 获取指定的方法
		// 获取数据库名称参数
		ChooseDataSource chooseDataSource = method
				.getAnnotation(ChooseDataSource.class);
		if (chooseDataSource != null) {
			String dataSourceName = chooseDataSource.dataSourceName();
			
			if (CustomContextHolder.getCustomerType()==null)
			{
				CustomContextHolder.setCustomerType(CustomContextHolder.DATA_SOURCE_FROM_13);
			}
			// 坚持名称是否合法 如果不合法 就用默认数据库
			if (dataSourceName.equals("15")) {
				{
					if (CustomContextHolder.getCustomerType().equals(
							CustomContextHolder.DATA_SOURCE_15)) {
						return joinPoint.proceed();
					} else {
						CustomContextHolder.setCustomerType(CustomContextHolder.DATA_SOURCE_15);
					}

				}
			} else if (dataSourceName.equals("HBase")) {
				if (CustomContextHolder.getCustomerType().equals(
						CustomContextHolder.DATA_SOURCE_HBase)) {
					return joinPoint.proceed();
				} else {
					CustomContextHolder
							.setCustomerType(CustomContextHolder.DATA_SOURCE_HBase);
				}

			} else if (dataSourceName.equals("Hive")) {
				if (CustomContextHolder.getCustomerType().equals(
						CustomContextHolder.DATA_SOURCE_Hive)) {
					return joinPoint.proceed();
				} else {
					CustomContextHolder
							.setCustomerType(CustomContextHolder.DATA_SOURCE_Hive);
				}

			} else {
				CustomContextHolder
						.setCustomerType(CustomContextHolder.DATA_SOURCE_FROM_13);
				return joinPoint.proceed();

			}

		}
		String customerType = CustomContextHolder.getCustomerType();
		System.out.println("切换到数据库" + customerType);
		return joinPoint.proceed();
	}

	private Method getMethod(ProceedingJoinPoint joinPoint, Object[] args) {
		String methodName = joinPoint.getSignature().getName();
		Class<? extends Object> clazz = joinPoint.getTarget().getClass();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (methodName.equals(method.getName())) {
				return method;
			}
		}
		return null;
	}

}
