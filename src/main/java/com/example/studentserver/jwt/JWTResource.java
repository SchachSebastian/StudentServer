package com.example.studentserver.jwt;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/jwt")
public class JWTResource {
	@POST
	@Consumes("application/json")
	public Response getJWT(String username) {
		if (username.equals("Schachner")) {
			Token token = JWTManager.createJWT();
			return Response.ok().entity(token).build();
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}
}
