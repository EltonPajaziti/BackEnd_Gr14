package org.backend.repository;

import org.backend.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    // Metoda bazë (automatikisht të implementuara nga Spring Data JPA)
    Faculty findByName(String name);
}