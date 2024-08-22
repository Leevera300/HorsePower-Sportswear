package com.horsepower.user.bo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LamdaTest {

	@Test
	void LamdaTest1() {
		List<String> fruits = Arrays.asList("apple", "banana", "cherry", "orange", "lemon");
		fruits.stream()
		.filter(element -> element.startsWith("b"))
		.forEach(element -> log.info(element));
	}
	
	@Test
	void LamdaTest2() {
		List<String> fruits = Arrays.asList("apple", "banana", "cherry", "orange", "lemon");
        
		fruits = fruits.stream()
        .map(element -> element.toUpperCase())
        .collect(Collectors.toList()); // stream to list
		
		log.info("$$$$ {}", fruits);
    
	}
}
