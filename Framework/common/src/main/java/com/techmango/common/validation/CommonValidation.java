package com.techmango.common.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonValidation {

	/* Allow only alphanumeric values */
	String alphaNumericRegex = "^[a-zA-Z0-9]+$";

	/*
	 * Visa : 13 or 16 digits, starting with 4. MasterCard : 16 digits, starting
	 * with 51 through 55. Discover : 16 digits, starting with 6011 or 65. American
	 * Express : 15 digits, starting with 34 or 37. Diners Club : 14 digits,
	 * starting with 300 through 305, 36, or 38. JCB : 15 digits, starting with 2131
	 * or 1800, or 16 digits starting with 35.
	 */
	String cardRegex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" + "(?<mastercard>5[1-5][0-9]{14})|"
			+ "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" + "(?<amex>3[47][0-9]{13})|"
			+ "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" + "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";

	/* Check only char's are present */
	String stringRegex = "^[a-zA-Z]+$";

	/* Check for the valid email format */
	// String emailRegex = "\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";
	String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/*
	 * Find if this special char's (&,%,$,#,@,!,~) are present or not in the given
	 * string
	 */
	String specialCharRegex = "[^&%$#@!~]*";

	/*
	 * ^ # start-of-string (?=.*[0-9]) # a digit must occur at least once
	 * (?=.*[a-z]) # a lower case letter must occur at least once (?=.*[A-Z]) # an
	 * upper case letter must occur at least once (?=.*[@#$%^&+=]) # a special
	 * character must occur at least once (?=\S+$) # no whitespace allowed in the
	 * entire string .{8,} # anything, at least eight places though $ #
	 * end-of-string
	 */
	// String passwordRegex =
	// "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

	/*
	 * (?=.*[a-z]) : This matches the presence of at least one lowercase letter.
	 * (?=.*d) : This matches the presence of at least one digit i.e. 0-9.
	 * (?=.*[@#$%]) : This matches the presence of at least one special character.
	 * ((?=.*[A-Z]) : This matches the presence of at least one capital letter.
	 * {6,16} : This limits the length of password from minimum 6 letters to maximum
	 * 16 letters.
	 */
	String passwordRegex = "((?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{6,16})";

	/*
	 * Begins with 0, +91 or 0091 Followed by a 7-9 Followed by exactly 9 numbers No
	 * capture groups Must match entire input
	 */
	String mobileRegex = "^(?:0091|\\\\+91|0)[7-9][0-9]{9}$";

	/* Validate the text contains only numbers */
	String numberRegex = "[0-9]+";

	/* Max and Min length validation */
	String maxMinRegex = "^[A-Z]{1,10}$";
	
	/* date format dd/mm/yyyy */
	String dateRegex1 = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
	
	/* date format dd-mm-yyyy */
	String dateRegex2 = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$"; 
	
	
	String dateRegex3 = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$"; 	
	
	/* date format mm/dd/yyyy */
	String dateRegex4 = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
	
	/* date format mm-dd-yyyy */
	String dateRegex6 = "^(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])-[0-9]{4}$"; 
	
	
	public boolean containsAlphaNumericChar(String input) {
		Pattern pattern = Pattern.compile(alphaNumericRegex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validstring(String str) {
		if (str.matches(stringRegex)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validEamilFormat(String email) {
		if (email.matches(emailRegex)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean containsSpecialChar(String input) {
		Pattern pattern = Pattern.compile(specialCharRegex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validPassword(String password) {
		if (password.matches(passwordRegex)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validMobileNumber(String mobileno) {
		if (mobileno.matches(mobileRegex)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validNumber(String inputnumber) {
		if (inputnumber.matches(numberRegex)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean cardValidation(String input) {
		input = input.replaceAll("-", "");
		Pattern pattern = Pattern.compile(cardRegex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean maxMinValidation(String input) {
		Pattern pattern = Pattern.compile(maxMinRegex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean dateFormatValidation(String inputdate, String dateformat)
	{
		
		if(dateformat.equals("dd/mm/yyyy") || dateformat.equals("dd-mm-yyyy"))
		{
			Pattern pattern1 = Pattern.compile(dateRegex1);
			Matcher matcher1 = pattern1.matcher(inputdate);
			
			Pattern pattern2 = Pattern.compile(dateRegex2);
			Matcher matcher2 = pattern2.matcher(inputdate);
			
			if (matcher1.matches() || matcher2.matches()) {
				return true;
			} else {
				return false;
			}
			
		}
		if(dateformat.equals("mm/dd/yyyy") || dateformat.equals("mm-dd-yyyy"))
		{
			Pattern pattern1 = Pattern.compile(dateRegex3);
			Matcher matcher1 = pattern1.matcher(inputdate);
			
			Pattern pattern2 = Pattern.compile(dateRegex4);
			Matcher matcher2 = pattern2.matcher(inputdate);
			
			if (matcher1.matches() || matcher2.matches()) {
				return true;
			} else {
				return false;
			}
		}
		else {return false;}
	}
}
