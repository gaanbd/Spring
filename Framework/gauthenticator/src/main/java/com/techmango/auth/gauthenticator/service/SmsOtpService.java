package com.techmango.auth.gauthenticator.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class SmsOtpService {

	public String SMSOTPGeneration(String apiKey, String userId, String password, String mobileNumber, String senderId,
			String msg) {
		String response = null;
		// Send Method (generate|verify)
		String sendMethod = "generate";

		// Message type text/unicode
		String msgType = "text";

		// Final OTP Code Expiry
		String codeExpiry = "600";

		// OTP Code Length
		String codeLength = "6";

		// OTP Code Type
		String codeType = "num";

		// Retry After time
		String retryExpiry = "60";

		// set to true if you need to regenerate new OTP while resending OTP
		// code
		String renew = "false";

		// Valid http url to receive response on your server. E.g
		// http://example.com/getOTPResponse.php
		String callback = "";

		// use comma separated for multiple. E.g. sms,email
		String medium = "sms";

		// if medium is used with comma separated for multiple. E.g. sms,email
		// then use valid user's email address
		String emailId = "";

		// response format
		String format = "json";

		// Prepare Url
		URLConnection myURLConnection = null;
		URL myURL = null;
		BufferedReader reader = null;

		// URL encode message
		String urlencodedmsg = URLEncoder.encode(msg);

		// API End Point
		String mainUrl = "http://enterprise.smsgatewaycenter.com/OTPApi/send?";

		// API Paramters
		StringBuilder sendSmsData = new StringBuilder(mainUrl);
		sendSmsData.append("apiKey=" + apiKey);
		sendSmsData.append("&userId=" + userId);
		sendSmsData.append("&password=" + password);
		sendSmsData.append("&sendMethod=" + sendMethod);
		sendSmsData.append("&msgType=" + msgType);
		sendSmsData.append("&mobile=" + mobileNumber);
		sendSmsData.append("&senderId=" + senderId);
		sendSmsData.append("&msg=" + urlencodedmsg);
		sendSmsData.append("&codeExpiry=" + codeExpiry);
		sendSmsData.append("&codeLength=" + codeLength);
		sendSmsData.append("&codeType=" + codeType);
		sendSmsData.append("&retryExpiry=" + retryExpiry);
		sendSmsData.append("&medium=" + medium);
		sendSmsData.append("&emailId=" + emailId);
		sendSmsData.append("&format=" + format);
		// final string
		mainUrl = sendSmsData.toString();
		try {
			// prepare connection
			myURL = new URL(mainUrl);
			myURLConnection = myURL.openConnection();
			myURLConnection.connect();
			reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
			// reading response

			while ((response = reader.readLine()) != null)
				// print response
				System.out.println(response);

			// finally close connection
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;

	}

	// Sample Response
	// {"status":"success","mobile":"919894877999","transactionId":"4015039608466984834","statusCode":"900","reason":"OTP
	// has been Verified Successfully."}

	public String SMSOTPVerification(String apiKey, String userId, String password, String mobile, String otp) {
		// Send Method (generate|verify)
		String sendMethod = "verify";

		// response format
		String format = "json";

		// Prepare Url
		URLConnection myURLConnection = null;
		URL myURL = null;
		BufferedReader reader = null;

		// URL encode message
		String urlencodedotp = URLEncoder.encode(otp);

		// API End Point
		String mainUrl = "http://enterprise.smsgatewaycenter.com/OTPApi/send?";

		// API Paramters
		StringBuilder sendSmsData = new StringBuilder(mainUrl);
		sendSmsData.append("apiKey=" + apiKey);
		sendSmsData.append("&userId=" + userId);
		sendSmsData.append("&password=" + password);
		sendSmsData.append("&sendMethod=" + sendMethod);
		sendSmsData.append("&mobile=" + mobile);
		sendSmsData.append("&otp=" + urlencodedotp);
		sendSmsData.append("&format=" + format);
		// final string
		mainUrl = sendSmsData.toString();
		try {
			// prepare connection
			myURL = new URL(mainUrl);
			myURLConnection = myURL.openConnection();
			myURLConnection.connect();
			reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
			// reading response
			String response;
			while ((response = reader.readLine()) != null)
				// print response
				System.out.println(response);

			// finally close connection
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	public StringBuffer SMSSendTxtLocal(String apiKeys, String messages, String senders, String number) {
		final StringBuffer stringBuffer = new StringBuffer();
		String sResult="";
		try {
			// Construct data
			String apiKey = "apikey=" + apiKeys;
			String message = "&message=" + messages;
			String sender = "&sender=" + senders;
			String numbers = "&numbers=" + number;
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			System.out.println("Error SMS " + stringBuffer);
			
			/*String apiKey = "apikey=" + URLEncoder.encode("g7+ww+DpkMM-CnVoTl6SH3ULBStLYIejCCIzoYqGVn", "UTF-8");
			String message = "&message=" + URLEncoder.encode("This is your message", "UTF-8");
			String sender = "&sender=" + URLEncoder.encode("TXTLCL", "UTF-8");
			String numbers = "&numbers=" + URLEncoder.encode("918438102037", "UTF-8");
			
			// Send data
			String data = "https://api.textlocal.in/send/?" + apiKey + numbers + message + sender;
			URL url = new URL(data);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
			// Process line...
				sResult=sResult+line+" ";
			}
			rd.close();
			System.out.println("Error SMS " + sResult);
			return sResult;*/
		} catch (Exception e) {
			System.out.println("Error SMS " + e);
		}
		return stringBuffer;
	}
	
	public void SMSSend(String userName, String password, String mobileNumber, int otp, String message, String senderId) {
		try{
            Date mydate = new Date(System.currentTimeMillis());
            String data = "";
            data += "sendMethod=simpleMsg";
            data += "&userId="+userName;
            data += "&password="+password;
            data += "&msg="+message;
            data += "&mobile="+mobileNumber;
            data += "&msgType=text";
            data += "&format=json";
            data += "&senderId="+senderId;
            URL url = new URL("http://enterprise.smsgatewaycenter.com/SMSApi/rest/send?" + data);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false); conn.connect();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuffer buffer = new StringBuffer();
            while((line = rd.readLine()) != null){
                buffer.append(line).append("\n");
            }
            System.out.println(buffer.toString());
            rd.close();
            conn.disconnect();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

