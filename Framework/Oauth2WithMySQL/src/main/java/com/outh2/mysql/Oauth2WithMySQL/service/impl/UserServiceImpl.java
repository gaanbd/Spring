package com.outh2.mysql.Oauth2WithMySQL.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.outh2.mysql.Oauth2WithMySQL.dao.UserDao;
import com.outh2.mysql.Oauth2WithMySQL.model.User;
import com.outh2.mysql.Oauth2WithMySQL.service.UserService;
import com.techmango.common.exception.BusinessException;
import com.techmango.common.mail.MailSender;
import com.techmango.common.mail.MailServers;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

	@Autowired
	private UserDao userDao;

	@Value("${default.mail.sending.flag}")
	private static boolean mailFlag = true;

	@Value("${default.mail.to}")
	private static String defaultMailTo = "sofia@techmango.net";

	@Value("${otp.length}")
	private static int otpLength;

	String activationcode = "435436";

	@Override
	public UserDetails loadUserByUsername(String userId) {

		User user = new User();
		user = userDao.findByUsername(userId);
		user.setAuthorities(getAuthority(user.getUserrole()));

		if (ObjectUtils.isEmpty(user)) {
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
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
			user.setPassword(encoder.encode(user.getPassword()));
			user = userDao.save(user);
			sendActivationCode(user.getUsername(), true);
		} else {

			if (olduser.isEnabled()) {
				user = null;
				return user;
			} else {
				sendActivationCode(user.getUsername(), true);
				return olduser;
			}
		}
		return user;
	}

	public boolean sendActivationCode(String username, boolean isFromRegistration) {

		User user = userDao.findByUsername(username);
		boolean mailResult = false;
		String activationOtp = activationcode;
		String mailContent = "";
		if (isFromRegistration) {
			mailContent = "Your Registration is successful. This is your activation code: " + activationOtp;
		} else {
			mailContent = "Your activation code to change password: " + activationOtp;
		}
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

	public boolean verifyOTP(String otp, String username, boolean isFromRegisteration) {

		if (isFromRegisteration) {

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
		} else {
			if (otp.equals(activationcode)) {
				return true;
			} else {
				return false;
			}
		}

	}

	public boolean updatepassword(String username, String password) {
		User user = userDao.findByUsername(username);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
		user.setPassword(encoder.encode(password));
		user = userDao.save(user);
		if (user == null || ObjectUtils.isEmpty(user)) {
			return false;
		} else {
			return true;
		}
	}

}
