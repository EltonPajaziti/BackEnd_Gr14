package org.backend.repository;

import org.backend.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade,Long> {

    Grade findGradeById(Long id);
    //Tjera funksione

}
