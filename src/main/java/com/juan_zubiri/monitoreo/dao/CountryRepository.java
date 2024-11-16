package com.juan_zubiri.monitoreo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juan_zubiri.monitoreo.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>{

}
