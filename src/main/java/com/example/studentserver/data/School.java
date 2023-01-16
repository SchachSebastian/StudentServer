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
	// you can use a constructor, but you have to specify the name of the fields in the JSON file
	/*public School(@JsonProperty("schoolname") String name,
	              @JsonProperty("classes")List<SchoolClass> classes) {
		this.name = name;
		this.classes = classes;
	}*/
	// you can use @JsonAnySetter, to add any field to the object with unknown value names
}
