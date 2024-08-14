package com.horsepower.AOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) // 어디에 적용할 것인가
@Retention(RetentionPolicy.RUNTIME) // 언제까지 적용할 것인가
public @interface TimeTrace {
	
	

}
