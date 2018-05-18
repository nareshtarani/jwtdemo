package org.jwtdemo.security.repository;

import java.util.List;

import org.jwtdemo.model.security.Property;
import org.jwtdemo.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
	List<Property> findByUserId(Long user_id);
}
