package org.jwtdemo.security.service;

import org.jwtdemo.model.security.User;
import org.jwtdemo.security.JwtUser;
import org.jwtdemo.security.JwtUserFactory;
import org.jwtdemo.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }
    
    public void saveUser(User user) {
    	userRepository.save(user);
    }

    public User getUser(String username) {
    	return userRepository.findByUsername(username);
    }
    
}
