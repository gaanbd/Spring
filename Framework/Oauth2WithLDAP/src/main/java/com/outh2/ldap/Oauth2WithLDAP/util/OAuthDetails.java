package com.outh2.ldap.Oauth2WithLDAP.util;

import java.text.ParseException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
public class OAuthDetails {

	public static String getAccessToken(){
		if(RequestContextHolder.getRequestAttributes() == null){
			return null;
		}
		return (String) RequestContextHolder.getRequestAttributes().getAttribute("token", RequestAttributes.SCOPE_REQUEST);
	}
	
	public static String getUserLanguage(){
		String userLanguage = null;
		String accessToken = getAccessToken();
		if(!StringUtils.isEmpty(accessToken)){
			JWTClaimsSet claim;
			try {
				claim = JWTParser.parse(accessToken).getJWTClaimsSet();
				userLanguage = (String) claim.getClaim("userlocale");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return userLanguage;
	}
}
