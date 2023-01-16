package com.example.studentserver;

import com.example.studentserver.jwt.JWTManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/school")
public class SchoolResource {
	@GET
	@Produces("application/json")
	public Response getSchool(@HeaderParam("Authorization") String token) {
		if (JWTManager.verifyToken(token)) {
			return Response.ok().entity(SchoolDatabase.getInstance().getSchool()).build();
		} else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
}