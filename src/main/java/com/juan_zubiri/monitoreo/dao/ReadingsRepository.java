package com.juan_zubiri.monitoreo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.juan_zubiri.monitoreo.model.Readings;

@Repository
public interface ReadingsRepository extends JpaRepository<Readings, Long>{

	    @Query("SELECT MAX(r.readings_number) FROM Readings r WHERE r.plant.id = :plantId")
	    Integer findMaxReadingsNumberByPlant(@Param("plantId") Long plantId);
	

}
