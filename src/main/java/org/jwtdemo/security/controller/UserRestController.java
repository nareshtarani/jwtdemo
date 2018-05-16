package org.jwtdemo.security.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jwtdemo.model.security.Authority;
import org.jwtdemo.model.security.AuthorityName;
import org.jwtdemo.model.security.User;
import org.jwtdemo.security.JwtTokenUtil;
import org.jwtdemo.security.JwtUser;
import org.jwtdemo.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	@Qualifier("jwtUserDetailsService")
	private JwtUserDetailsService userDetailsService;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@RequestMapping(value = "user", method = RequestMethod.GET)
	public JwtUser getAuthenticatedUser(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader).substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
		return user;
	}

	@RequestMapping(value = "${jwt.route.authentication.path}/create", method = RequestMethod.POST)
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Authority authority = new Authority();
		authority.setId(1L);
		authority.setName(AuthorityName.ROLE_USER);
		List<Authority> authorities = Arrays.asList(authority);
		user.setAuthorities(authorities);
		user.setLastPasswordResetDate(new Date());
		userDetailsService.saveUser(user);
		return ResponseEntity.ok().body("Done");
	}

}
