package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class ProjectApplicationTests {
	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public ProjectApplicationTests(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}



	@Test
	void contextLoads() {
		String encoded = passwordEncoder.encode("111");
		System.out.println(encoded);
	}

}
