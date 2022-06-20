package me.hajoo.aop._1_before.advisor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAdvisor {

    /* Pointcut */
    @Pointcut("execution(* me.hajoo.aop._1_before..controller.TestController.hello())")
    private void commonPointcut() {};

    /* Around */
    //    @Around("@annotation(me.hajoo.aop._1_before.annotation.RecordLog)")
    //    @Around("bean(testController)")
    //    @Around("execution(* me.hajoo.aop._1_before.controller.TestController.hello(..))")
    //    @Around("execution(* me.hajoo.aop._1_before.controller.TestController.*(*))")
    //    @Around("execution(* me.hajoo.aop._1_before.controller.TestController.*(*, *))")
    //    @Around("execution(* me.hajoo.aop._1_before.controller.TestController.*(*, Integer))")
    //    @Around("execution(Integer me.hajoo.aop._1_before.controller.TestController.*(*, *))")
    @Around("within(me.hajoo.aop._1_before.controller.TestController)")
    public Object recordLog(ProceedingJoinPoint pjp) throws Throwable {
        long before = System.currentTimeMillis();
        log.info("전처리");
        Object ret = pjp.proceed();
        log.info(System.currentTimeMillis() - before + "ms");
        return ret;
    }

    @Before("commonPointcut()")
    public void recordLogBefore(JoinPoint jp){
        log.info("@Before 실행");
    }

    @After("commonPointcut()")
    public void recordLogAfter(JoinPoint jp){
        log.info("@After 실행");
    }

    @AfterReturning("commonPointcut()")
    public void recordLogAfterReturning(JoinPoint jp){
        log.info("@AfterReturning 실행");
    }

    @AfterThrowing("execution(* me.hajoo.aop._1_before.service.TestService.doSomething(..))")
    public void recordLogAfterThrowing(JoinPoint jp){
        log.info("@AfterThrowing 실행");
    }
}
