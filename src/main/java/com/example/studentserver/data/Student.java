package com.example.studentserver.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
	@JsonProperty("student_id") // This is the name of the field in the JSON file
	private int id;
	private String name;
	@JsonProperty("birthday")
	@JsonDeserialize(using = BirthDateDeserializer.class) // extra deserializer for birthday, to
	// convert format dd.MM.yyyy to LocalDateTime
	private LocalDateTime birthDate;
	private int mark;
}
