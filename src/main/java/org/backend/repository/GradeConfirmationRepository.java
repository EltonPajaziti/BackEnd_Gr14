package org.backend.repository;

import org.backend.model.GradeConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface GradeConfirmationRepository extends JpaRepository<GradeConfirmation, Long> {
    GradeConfirmation findGradeConfirmationByExpiresAt(LocalDate expiresAt);
}