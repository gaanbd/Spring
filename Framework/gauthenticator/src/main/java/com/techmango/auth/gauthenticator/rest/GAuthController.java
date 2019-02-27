package com.techmango.auth.gauthenticator.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techmango.auth.gauthenticator.service.GAuthService;

@RestController
@RequestMapping("api/auth")
public class GAuthController {

    private GAuthService gAuthService;
    
    public GAuthController(GAuthService gAuthService) {
		this.gAuthService = gAuthService;
	}

    @PostMapping
    @RequestMapping(value = "/generate")
    public ResponseEntity<Object> generateTOTP(@RequestParam("userName") String userName)
    {
    	Map<String, String> response = new HashMap<>();
        boolean isGenerated = gAuthService.generateTOTP(userName);
        if (!isGenerated)
        {
            response.put("status", "error");
            response.put("message", "Error while generating OTP!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("status", "success");
        response.put("message", "OTP successfully generated. Please check your e-mail!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/validate")
    public ResponseEntity<Object> validateOTP(@RequestParam("userName") String userName, @RequestParam("otp") int otp)
    {
        Map<String, String> response = new HashMap<>(2);
        Boolean isValid = gAuthService.validateOTP(userName, otp);
        if (!isValid)
        {
            response.put("status", "error");
            response.put("message", "OTP is not valid!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("status", "success");
        response.put("message", "Entered OTP is valid!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping(value = "generateQRCode")
    public ResponseEntity<Map<String,String>> generateQRCode(@RequestParam("userName") String userName)
    {
        Map<String, String> response = new HashMap<>();
        String url = gAuthService.generateQRCodeUrl(userName);
        if (StringUtils.isEmpty(url))
        {
            response.put("status", "error");
            response.put("message", "Error while generating QRCode!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        response.put("status", "success");
        response.put("url", url);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
