package com.example.studentserver.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SchoolTest {
	private static ObjectMapper objectMapper;
	@BeforeAll
	static void setUp() {
		objectMapper = new ObjectMapper();
	}
	@Test
	void testJackson() {
		try {
			School school = objectMapper
					.readValue(getClass()
							           .getClassLoader()
							           .getResource("school.json"),
					           School.class);
			System.out.println(school);
			assertEquals("HTL Kaindorf", school.getName());
			assertEquals(new Student(1, "Hans", LocalDateTime.of(2005, 2, 15, 0, 0), 2),
			             school.getClasses().get(0).getStudents().get(0));
			// assertEquals nicht einziges assert
			assertTrue(school.getName().equals("HTL Kaindorf"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}