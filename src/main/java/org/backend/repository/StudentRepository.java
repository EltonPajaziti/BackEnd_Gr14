package org.backend.repository;

import org.backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // Mund të shtoni metoda të tjera nëse kërkoni kërkesa të personalizuara.
}
