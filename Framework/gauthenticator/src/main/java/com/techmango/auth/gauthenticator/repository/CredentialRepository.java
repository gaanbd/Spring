package com.techmango.auth.gauthenticator.repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.techmango.auth.gauthenticator.service.GAuthService;
import com.techmango.googleauth.ICredentialRepository;


public class CredentialRepository implements ICredentialRepository
{
    
    @Override
    public String getSecretKey(String userName)
    {
        String key = null;
		try {
			key = GAuthService.getKey(userName);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
        return key;
    	 
    }

    @Override
    public void saveUserCredentials(
            String userName,
            String secretKey,
            int validationCode,
            List<Integer> scratchCodes)
    {
        System.out.println("saveUserCredentials invoked with user name " + userName);
    }
}
