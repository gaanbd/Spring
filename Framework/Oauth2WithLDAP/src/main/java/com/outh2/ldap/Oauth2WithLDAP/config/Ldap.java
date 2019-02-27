package com.outh2.ldap.Oauth2WithLDAP.config;

import java.util.Properties;

import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.springframework.security.core.AuthenticationException;

public class Ldap {
	
	public static Attributes doLookup(String url,String dn,String uid,String password) {
		
		String fulDomainName = "uid="+uid+","+dn;
		Attributes attrs = null;
		Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		properties.put(Context.PROVIDER_URL, url);
		properties.put(Context.SECURITY_AUTHENTICATION, "simple");
		properties.put(Context.SECURITY_PRINCIPAL, fulDomainName);
		properties.put(Context.SECURITY_CREDENTIALS, password);
		try {
			DirContext context = new InitialDirContext(properties);
			attrs = context.getAttributes(fulDomainName);	
			context.close();
		} catch (AuthenticationNotSupportedException ex) {
		    System.out.println("The authentication is not supported by the server");
		} catch (AuthenticationException ex) {
		    System.out.println("incorrect password or username");
		} catch (NamingException e) {
			System.out.println("error when trying to create the context");
		}
				
		return attrs;
	}

}

