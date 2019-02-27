package com.techmango.common.mail;

public class MailSender {

	// Send simple text mail
	public static boolean sendTextMail(String from, String pwd, String[] to, String host, String subject, String mailbody,
			String[] cc, String[] bcc, String defaultTo) {
		return MailUtility.sendSimpleMail(from, pwd, to, host, subject, mailbody, cc, bcc, false, "",defaultTo); 
	}

	// Send simple HTML mail
	public static boolean sendHtmlMail(String from, String pwd, String[] to, String host, String subject, String mailbody,
			String[] cc, String[] bcc, String defaultTo) {
		return MailUtility.sendSimpleMail(from, pwd, to, host, subject, mailbody, cc, bcc, true, "",defaultTo);
	}

	// Send simple text mail with Attachment
	public static boolean sendTextMailWithAttachment(String from, String pwd, String[] to, String host, String subject,
			String mailbody, String[] cc, String[] bcc, String filePath, String defaultTo) {
		return MailUtility.sendSimpleMail(from, pwd, to, host, subject, mailbody, cc, bcc, false, filePath,defaultTo);
	}

	// Send simple HTML mail with Attachment
	public static boolean sendHtmlMailWithAttachment(String from, String pwd, String[] to, String host, String subject,
			String mailbody, String[] cc, String[] bcc, String filePath, String defaultTo) {
		return MailUtility.sendSimpleMail(from, pwd, to, host, subject, mailbody, cc, bcc, true, filePath,defaultTo);
	}
}
