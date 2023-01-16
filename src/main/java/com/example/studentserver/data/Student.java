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
	@JsonProperty("student_id")
	private int id;
	private String name;
	@JsonProperty("birthday")
	@JsonDeserialize(using = BirthDateDeserializer.class)
	private LocalDateTime birthDate;
	private int mark;
}
