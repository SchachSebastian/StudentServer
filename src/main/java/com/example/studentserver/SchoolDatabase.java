package com.example.studentserver;

import com.example.studentserver.data.School;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class SchoolDatabase {
	private static SchoolDatabase INSTANCE;
	private School school;
	private SchoolDatabase() {
		try {
			school = new ObjectMapper()
					.readValue(getClass()
							           .getClassLoader()
							           .getResource("school.json"),
					           School.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public static SchoolDatabase getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SchoolDatabase();
		}
		return INSTANCE;
	}
	public School getSchool() {
		return school;
	}
}
