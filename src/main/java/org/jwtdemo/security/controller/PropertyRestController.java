package org.jwtdemo.security.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.jwtdemo.model.security.Property;
import org.jwtdemo.model.security.User;
import org.jwtdemo.security.JwtTokenUtil;
import org.jwtdemo.security.repository.PropertyRepository;
import org.jwtdemo.security.service.JwtUserDetailsService;
import org.jwtdemo.security.service.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertyRestController {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	@Qualifier("jwtUserDetailsService")
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private PropertyRepository propertyRepository;

	@RequestMapping(value = "property", method = RequestMethod.POST, produces = "application/json")
	public RestResponse getAuthenticatedUser(HttpServletRequest request, @RequestBody Property prop) {
		String token = request.getHeader(tokenHeader).substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		User user = userDetailsService.getUser(username);
		prop.setUser(user);
		propertyRepository.save(prop);
		return new RestResponse("Property Added");
	}

	@RequestMapping(path = "/getproperty", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Property>> getPerson(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader).substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		User user = userDetailsService.getUser(username);
		List<Property> p = propertyRepository.findByUserId(user.getId());
		return ResponseEntity.ok(p);
	}

}
