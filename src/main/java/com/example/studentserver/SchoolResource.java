package com.example.studentserver;

import com.example.studentserver.data.School;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/school")
public class SchoolResource {
	@GET
	@Produces("application/json")
	public School getSchool() {
		return SchoolDatabase.getInstance().getSchool();
	}
}