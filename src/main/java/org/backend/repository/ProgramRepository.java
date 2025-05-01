package org.backend.repository;

import org.backend.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    Program findProgramByName(String name);
    Program findProgramByLevel(String level);
    List<Program> findByDepartmentId(Long departmentId);


}
