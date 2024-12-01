package com.juan_zubiri.monitoreo.dao;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.juan_zubiri.monitoreo.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
   
	Optional<Country> findByName(String name);
	
}
