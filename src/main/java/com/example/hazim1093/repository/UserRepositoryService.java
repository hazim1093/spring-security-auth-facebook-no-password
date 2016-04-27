package com.example.hazim1093.repository;

import com.example.hazim1093.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hazim on 4/26/16
 */
@Service
@Transactional
public class UserRepositoryService {
	@Autowired
	private UserRepository userRepository;

	public User findByFacebookId(String facebookId){
		return userRepository.findByFacebookId(facebookId);
	}

	public User save(User user){
		return userRepository.save(user);
	}
}
