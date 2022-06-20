package me.hajoo.aop._2_after.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

	@Pointcut("execution(* me.hajoo.aop._2_after.order..*(..))")
	public void allOrder(){}

	@Pointcut("execution(* *..*Service.*(..))")
	public void allService(){}

	@Pointcut("allOrder() && allService()")
	public void orderAndAllService(){}
}
