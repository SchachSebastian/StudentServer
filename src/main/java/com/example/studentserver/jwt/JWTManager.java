package com.example.studentserver.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;

public class JWTManager {
	private static final Algorithm algorithm = Algorithm.HMAC256("student-server is a nice " +
			                                                             "example");
	public static Token createJWT() {
		return new Token(JWT.create().withIssuedAt(new Date()).withIssuer("Student-Server")
		                    .sign(algorithm));
	}
	public static boolean verifyToken(String token) {
		try {
			JWTVerifier verifier = JWT.require(algorithm).withIssuer("Student-Server").build();
			verifier.verify(token.replaceFirst("Bearer ", ""));
			return true;
		} catch (JWTVerificationException ex) {
			return false;
		}
	}
}
