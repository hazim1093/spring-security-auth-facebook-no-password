package com.example.hazim1093.repository;

import com.example.hazim1093.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hazim on 4/26/16
 */
interface UserRepository extends JpaRepository<User, Long> {
	@Transactional(readOnly = true)
	User findByFacebookId(String facebookId);
}
