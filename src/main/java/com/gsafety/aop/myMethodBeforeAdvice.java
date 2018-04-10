package com.gsafety.aop;

import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 
 * @Title: 方法级别拦截器实现 主要拦截com.gsafety 下包的所有方法
 * @Description:
 * @Author:Administrator
 * @Since:2018年3月19日
 * @Version:1.1.0
 */
//@Aspect
//@Component
public class myMethodBeforeAdvice {

	// http://blog.csdn.net/zhengchao1991/article/details/53391244 aop语法讲解

	private static Logger LOGGER = LogManager.getLogger(myMethodBeforeAdvice.class);
	private static SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Pointcut("execution(* com.gsafety..*.*(..))")
	private void aspectJMethod() {
	};

	@Before("aspectJMethod()")
	public void doBefore(JoinPoint joinPoint) {

		LOGGER.info(joinPoint.getSignature().getDeclaringTypeName() + "类的方法为:"
				+ joinPoint.getSignature().getName() + "被调用");
		LOGGER.debug("目标方法所属类的简单类名:"
				+ joinPoint.getSignature().getDeclaringType().getSimpleName());
		LOGGER.debug("目标方法声明类型:"
				+ Modifier.toString(joinPoint.getSignature().getModifiers()));

		// 获取传入目标方法的参数
		Object[] args = joinPoint.getArgs();
		for (int i = 0; i < args.length; i++) {
			System.out.println("第" + (i + 1) + "个参数为:" + args[i].toString());
		}

		LOGGER.debug("开始执行时间" + df.format(System.currentTimeMillis()));

		// System.out.println("被代理的对象:" + joinPoint.getTarget());
		// System.out.println("代理对象自己:" + joinPoint.getThis());

	}

	// @Around("aspectJMethod()")
	// public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
	//
	// System.out.println("----doAround()开始----");
	//
	// //核心逻辑
	// Object retval=pjp.proceed();
	//
	//
	// return retval;
	// }
	@After(value = "aspectJMethod()")
	public void doAfter(JoinPoint joinPoint) throws SQLException {
		LOGGER.debug("结束执行时间" + df.format(System.currentTimeMillis()));

	}

	// @AfterReturning(value="aspectJMethod()")
	// public void doReturn(JoinPoint joinPoint){
	// System.out.println("AfterReturning()开始");
	//
	//
	// }
	//
	// @AfterThrowing(value="aspectJMethod()", throwing="e")
	// public void doThrowing(JoinPoint joinPoint,Exception e){
	// System.out.println("-----doThrowing()开始-----");
	//
	// }

}
