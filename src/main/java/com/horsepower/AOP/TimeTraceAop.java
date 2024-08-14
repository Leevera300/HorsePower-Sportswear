package com.horsepower.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect // 부가 기능 정의 (advice) + 어디에 적용할지 정의 (pointcut)
@Component
public class TimeTraceAop {

	//@Around("execution(* com.horsepower..*(..)") // 패기지 범위 => 어디에 적용?(pointcut)
	@Around("@annotation(TimeTrace)") // 어노테이션으로 적용 => 어디에 적용?(pointcut")
	public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
		// 시간 측정
		StopWatch sw = new StopWatch();
		sw.start();
		
		Object proceedObj = joinPoint.proceed(); // 본래 할 일
		
		sw.stop();
		
		log.info("$$$$$$$$$$$$$$$ time: " + sw.getTotalTimeMillis());
		log.info(sw.prettyPrint());
		
		return proceedObj;
	}
}
