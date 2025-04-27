package org.backend.repository;

import org.backend.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long>{

    Department findByName(String name);

    List<Department> findByFacultyId(Long facultyId);
}
