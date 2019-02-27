package com.techmango.auth.gauthenticator.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.techmango.googleauth.GoogleAuthenticator;
import com.techmango.googleauth.GoogleAuthenticatorKey;
import com.techmango.googleauth.GoogleAuthenticatorQRGenerator;

@Service
public class GAuthService {

	private static final Integer EXPIRE_MINS = 5;

	public static LoadingCache<String, String> otpCache;
	
	@Value("${mail.sender.username}")
	private String senderMail;
	
	@Value("${mail.sender.password}")
	private String senderPassword;
	
	@Value("${mail.host}")
	private String mailHost;
	
	public GAuthService() {
		super();
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
				.build(new CacheLoader<String, String>() {
					@Override
					public String load(final String s) throws Exception {
						return getKey(s);
					}
				});
	}

	public static String getKey(String key) throws ExecutionException {
		return otpCache.get(key);
	}

	public boolean generateTOTP(String userName) {
		GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
		final GoogleAuthenticatorKey gkey = googleAuthenticator.createCredentials(userName);
		String secretKey = gkey.getKey();
		otpCache.put(userName, secretKey);
		int totpPass = googleAuthenticator.getTotpPassword(secretKey);
		/*String[] recipients = { "sankarimusiri@gmail.com" };
		MailUtility.sendSimpleMail(senderMail, senderPassword, recipients, mailHost, "OTP",
				"Your generated OTP is " + totpPass, new String[0], new String[0], false, "", "");*/

		/* String msg = "Your OTP code is " + totpPass +
		 * " . Please use the code within 30 seconds. - Demo Message.";
		 * SmsOtpService smsOtpService = new SmsOtpService();
		 * //smsOtpService.SMSSend(userId, password, mobile, totpPass, msg, senderId);
		 * //smsOtpService.SMSSendTxtLocal("g7+ww+DpkMM-CnVoTl6SH3ULBStLYIejCCIzoYqGVn",
		 * msg, "TXTLCL", "918438102037");*/
		return true;
	}

	public Boolean validateOTP(String userName, Integer verificationCode) {
		GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
		boolean isCodeValid = googleAuthenticator.authorizeUser(userName, verificationCode);
		if (isCodeValid) {
			return true;
		}
		return false;
	}
	
	public String generateQRCodeUrl(String userName) {
		GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
		final GoogleAuthenticatorKey gkey = googleAuthenticator.createCredentials(userName);
		String secretKey = gkey.getKey();
		otpCache.put(userName, secretKey);
		return GoogleAuthenticatorQRGenerator.getOtpAuthURL("test", "test@example.com", gkey);
	}

}
