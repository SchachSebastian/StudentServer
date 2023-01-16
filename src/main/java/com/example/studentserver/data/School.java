package com.example.studentserver.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class School {
	@JsonProperty("schoolname")
	private String name;
	private List<SchoolClass> classes;
	/*public School(@JsonProperty("schoolname") String name,
	              @JsonProperty("classes")List<SchoolClass> classes) {
		this.name = name;
		this.classes = classes;
	}*/
}
