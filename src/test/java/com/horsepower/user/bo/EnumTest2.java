package com.horsepower.user.bo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

public class EnumTest2 {
	
	public enum CalcType {
		// 열거형 정의
		CALC_A(value -> value),
		CALC_B(value -> value * 10),
		CALC_C(value -> value * 3),
		CALC_ETC(value -> 0);
		
		// enum에 정의된 항목 필드
		private Function<Integer, Integer> expression;
		
		// 생성자
		CalcType(Function<Integer, Integer> expression) {
			this.expression = expression;
		}
		
		// 메소드
		public int calculate(int value) {
			return expression.apply(value);
		}
	}
	
	@Test
	void CalcTest() {
		// given - 준비
		CalcType ctype = CalcType.CALC_C;
		CalcType btype = CalcType.CALC_B;
		
		// when - 실행
		int result = ctype.calculate(500);
		int result2 = btype.calculate(150);
		
		// then - 검증
		assertEquals(1500, result);
		assertEquals(1500, result2);
	}

}
