package com.outh2.ldap.Oauth2WithLDAP.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.outh2.ldap.Oauth2WithLDAP.config.Ldap;
import com.outh2.ldap.Oauth2WithLDAP.dao.UserDao;
import com.outh2.ldap.Oauth2WithLDAP.model.User;
import com.outh2.ldap.Oauth2WithLDAP.service.UserService;
/*import com.event.EventPublisher;*/
import com.techmango.common.exception.BusinessException;
import com.techmango.common.mail.MailSender;
import com.techmango.common.mail.MailServers;




@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

	@Autowired
	private UserDao userDao;
		
	@Value("${ldap.url}")
	private static String url = "ldap://localhost:10389/";

	@Value("${ldap.dn}")
	private static String dn = "ou=users,dc=example,dc=com";

	@Value("${default.mail.sending.flag}")
	private static boolean mailFlag = true;

	@Value("${default.mail.to}")
	private static String defaultMailTo = "sofia@techmango.net";

	@Value("${otp.length}")
	private static int otpLength;

	String activationcode = "43543643";


	@Override
	public UserDetails loadUserByUsername(String userId) {

		User user = new User();
		// getting input password
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			String password = request.getParameter("password");

			Attributes attrs = Ldap.doLookup(url, dn, userId, password);
			if (attrs != null) {
				try {
					user.setUsername(attrs.get("cn").get().toString());
					user.setPassword(password);
					user.setEmail(attrs.get("mail").get().toString());
					user.setAccountNonLocked(true);
					user.setAccountNonExpired(true);
					user.setCredentialsNonExpired(true);
					user.setEnabled(true);
					user.setAuthorities(getAuthority("ROLE_USER"));

				} catch (NamingException e) {
					e.printStackTrace();
				}
			} else {
				user = null;
			}
		
		if (user == null || ObjectUtils.isEmpty(user)) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return user;
	}

	private List<SimpleGrantedAuthority> getAuthority(String role) {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(long id) {
		if (Objects.nonNull(userDao.findById(id))) {
			userDao.deleteById(id);
		} else {
			throw new BusinessException("error.user.exist");
		}

	}

	@Override
	public User save(User user) {
		// check user existence
		User olduser = userDao.findByUsername(user.getUsername());
		if (olduser == null) {
			user.setUserrole("ROLE_USER");
			user = userDao.save(user);		
			//eventPublisher.publishEvent("Add", user);
			sendActivationCode(user.getUsername());
		} else {
			user = null;
			return user;
		}
		return user;
	}

	public boolean sendActivationCode(String username) {

		User user = userDao.findByUsername(username);
		boolean mailResult = false;
		String activationOtp = activationcode;
		String mailContent = "Your Registration is successful. This is your activation code: " + activationOtp;
		String[] cc = {};
		String[] bcc = {};
		String[] to = { user.getEmail() };

		if (mailFlag) {
			mailResult = MailSender.sendTextMail("sofia@techmango.net", "pencilfactory", to,
					MailServers.GMAIL.getAddress(), "Activation Mail", mailContent, cc, bcc, defaultMailTo);
		} else {
			mailResult = MailSender.sendTextMail("sofia@techmango.net", "pencilfactory", to,
					MailServers.GMAIL.getAddress(), "Activation Mail", mailContent, cc, bcc, "");
		}

		return mailResult;
	}

	public boolean verifyOTP(String otp, String username) {

		if (otp.equals(activationcode)) {
			User user = userDao.findByUsername(username);
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			user.setCredentialsNonExpired(true);
			user.setEnabled(true);
			user = userDao.save(user);
			return true;
		} else {
			return false;
		}

	}

}
