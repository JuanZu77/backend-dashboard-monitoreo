package com.juan_zubiri.monitoreo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.juan_zubiri.monitoreo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	// Buscar usuario por email
    Optional<User> findByEmail(String email);

}
