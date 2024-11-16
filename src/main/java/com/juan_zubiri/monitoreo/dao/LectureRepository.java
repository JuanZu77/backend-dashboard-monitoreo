package com.juan_zubiri.monitoreo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.juan_zubiri.monitoreo.model.Lectures;

@Repository
public interface LectureRepository extends JpaRepository<Lectures, Long>{

}
