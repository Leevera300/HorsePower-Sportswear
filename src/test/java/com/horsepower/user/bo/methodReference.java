package com.horsepower.user.bo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class methodReference {
	
	@ToString
	@AllArgsConstructor
	public class Person {
		private String name;
        private int age;
        
        public void printInfo() {
        	log.info("#### {}", this);
        }
	}
	
	@Test
	void methodReferenceTest() {
		List<Person> people = new ArrayList<>();
		people.add(new Person("Alice", 23));
		people.add(new Person("Bob", 25));
		
		// 객체 안에 있는 메소드 호출
		people.stream()
		.forEach(Person::printInfo);
		
		
		//people.forEach(p -> p.printInfo());
		people.forEach(Person::printInfo);
		
		// 객체를 println로 출력
		people.forEach(System.out::println);
		people.forEach(p -> System.out.println(p));
	}
}
