package org.backend.repository;

import org.backend.model.Exam_Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamPeriodRepository extends JpaRepository<Exam_Period, Long> {
    // Mund të shtosh metoda të personalizuara nëse është nevoja
}
