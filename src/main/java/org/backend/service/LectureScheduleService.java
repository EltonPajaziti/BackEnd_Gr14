package org.backend.service;

import org.backend.model.LectureSchedule;
import org.backend.repository.LectureScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LectureScheduleService {

    private final LectureScheduleRepository lectureScheduleRepository;

    @Autowired
    public LectureScheduleService(LectureScheduleRepository lectureScheduleRepository) {
        this.lectureScheduleRepository = lectureScheduleRepository;
    }

    @Transactional(readOnly = true)
    public List<LectureSchedule> getAll() {
        return lectureScheduleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<LectureSchedule> getById(Long id) {
        return lectureScheduleRepository.findById(id);
    }

    @Transactional
    public LectureSchedule create(LectureSchedule lectureSchedule) {
        validateLectureSchedule(lectureSchedule);
        return lectureScheduleRepository.save(lectureSchedule);
    }

    @Transactional
    public LectureSchedule update(Long id, LectureSchedule updatedLectureSchedule) {
        validateLectureSchedule(updatedLectureSchedule);
        LectureSchedule existingLectureSchedule = lectureScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lecture schedule not found"));
        updatedLectureSchedule.setId(id);
        return lectureScheduleRepository.save(updatedLectureSchedule);
    }

    @Transactional
    public void delete(Long id) {
        if (!lectureScheduleRepository.existsById(id)) {
            throw new RuntimeException("Lecture schedule not found");
        }
        lectureScheduleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<LectureSchedule> getByTenantId(Long tenantId) {
        return lectureScheduleRepository.findByTenantId(tenantId);
    }

    @Transactional(readOnly = true)
    public List<LectureSchedule> getByCourseProfessorId(Long courseProfessorId) {
        return lectureScheduleRepository.findByCourseProfessorId(courseProfessorId);
    }

    @Transactional(readOnly = true)
    public List<LectureSchedule> getByProgramId(Long programId) {
        return lectureScheduleRepository.findByProgramId(programId);
    }

    private void validateLectureSchedule(LectureSchedule lectureSchedule) {
        if (lectureSchedule.getDayOfWeek() == null || lectureSchedule.getDayOfWeek().isBlank()) {
            throw new IllegalArgumentException("Day of week is required");
        }
        if (lectureSchedule.getStartTime() == null) {
            throw new IllegalArgumentException("Start time is required");
        }
        if (lectureSchedule.getEndTime() == null) {
            throw new IllegalArgumentException("End time is required");
        }
        if (lectureSchedule.getRoom() == null || lectureSchedule.getRoom().isBlank()) {
            throw new IllegalArgumentException("Room is required");
        }
        if (lectureSchedule.getTenant() == null || lectureSchedule.getTenant().getId() == null) {
            throw new IllegalArgumentException("Tenant (Faculty) is required");
        }
        if (lectureSchedule.getCourseProfessor() == null || lectureSchedule.getCourseProfessor().getId() == null) {
            throw new IllegalArgumentException("Course professor is required");
        }
        if (lectureSchedule.getProgram() == null || lectureSchedule.getProgram().getId() == null) {
            throw new IllegalArgumentException("Program is required");
        }
    }
}