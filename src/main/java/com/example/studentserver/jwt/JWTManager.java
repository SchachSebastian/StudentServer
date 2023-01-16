package com.example.studentserver.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;

public class JWTManager {
	private static final Algorithm algorithm = Algorithm.HMAC256(
			"student-server is a nice " + "example"); // alogrithm
	// to sign the token, secret can be any string, but preferably a long one
	public static Token createJWT() {
		return new Token(JWT.create().withIssuedAt(new Date()).withIssuer("Student-Server")
		                    .sign(algorithm)); // create a token with the issuer Student-Server
		// and the issue date
	}
	public static boolean verifyToken(String token) {
		try {
			JWTVerifier verifier = JWT.require(algorithm).withIssuer("Student-Server").build();
			// JWTVerifier tests if the token is valid, based on secret and the added extra
			// information, like issuer
			verifier.verify(token.replaceFirst("Bearer ", "")); // remove Bearer before
			// verifying, as it is not part of the token but stands for the type of the token
			return true;
		} catch (JWTVerificationException ex) { // exception is thrown if the token is not valid
			return false;
		}
	}
}
