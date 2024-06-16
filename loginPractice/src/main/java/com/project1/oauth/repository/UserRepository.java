package com.project1.oauth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project1.oauth.domain.UserDomain;

@Repository
public interface UserRepository extends CrudRepository<UserDomain,String> {

	Optional<UserDomain> findByEmailAndOauthType(String emailm, String oauthType);
	
}
