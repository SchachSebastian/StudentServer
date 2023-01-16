package com.example.studentserver;

import com.example.studentserver.data.School;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class SchoolDatabase {
	// school database implemented as a singleton
	// reads the school.json file and stores the data in the school object
	private static SchoolDatabase INSTANCE;
	private School school;
	private SchoolDatabase() {
		try {
			// readValue - reads from file
			// file is specified via getClass().getClassLoader().getResource("school.json") and
			// has to be in the resources folder
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
