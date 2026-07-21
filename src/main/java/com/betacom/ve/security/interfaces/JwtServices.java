package com.betacom.ve.security.interfaces;

import org.springframework.security.core.Authentication;

public interface JwtServices {
	String generateAccessToken(Authentication authentication);
	String generateRefreshToken(Authentication authentication);
	boolean isValidRefreshToken(String token) throws Exception;
	String extractUsername(String token);
}
