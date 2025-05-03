package org.backend.repository;

import org.backend.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    // Këtu mund të shtosh metoda të personalizuara nëse nevojitet
}
