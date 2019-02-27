package com.techmango.common.event;

public class SMS {

	String fromNumber;
	String toNumber;
	String provideName;
	int otp;
	public String getFromNumber() {
		return fromNumber;
	}
	public void setFromNumber(String fromNumber) {
		this.fromNumber = fromNumber;
	}
	public String getToNumber() {
		return toNumber;
	}
	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}
	public String getProvideName() {
		return provideName;
	}
	public void setProvideName(String provideName) {
		this.provideName = provideName;
	}
	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}
	
	
	
}
