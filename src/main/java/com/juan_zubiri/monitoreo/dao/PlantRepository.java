package com.juan_zubiri.monitoreo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.juan_zubiri.monitoreo.model.Plant;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long>{

}
