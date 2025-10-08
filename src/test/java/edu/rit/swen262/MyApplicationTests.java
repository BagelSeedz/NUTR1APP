package edu.rit.swen262;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "spring.profiles.active:test")
class MyApplicationTests {

	@Test
	@DisplayName("Validate that the Spring Context starts up okay.")
	void contextLoads() {
		// do nothing
		assertTrue(true, "Spring Context starts up okay.");
	}

}
