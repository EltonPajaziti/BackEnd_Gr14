package org.backend.controller;

import org.backend.dto.LectureScheduleDTO;
import org.backend.model.LectureSchedule;
import org.backend.service.LectureScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/lecture-schedules")
@CrossOrigin(origins = "*")
public class LectureScheduleController {

    private final LectureScheduleService lectureScheduleService;

    @Autowired
    public LectureScheduleController(LectureScheduleService lectureScheduleService) {
        this.lectureScheduleService = lectureScheduleService;
    }

    @GetMapping
    public List<LectureSchedule> getAll() {
        return lectureScheduleService.getAll();
    }

    @GetMapping("/{id}")
    public LectureSchedule getById(@PathVariable Long id) {
        return lectureScheduleService.getById(id)
                .orElseThrow(() -> new RuntimeException("Lecture schedule not found"));
    }

    @PostMapping
    public ResponseEntity<LectureSchedule> create(@Valid @RequestBody LectureSchedule lectureSchedule, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(lectureScheduleService.create(lectureSchedule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LectureSchedule> update(@PathVariable Long id, @Valid @RequestBody LectureSchedule lectureSchedule, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(lectureScheduleService.update(id, lectureSchedule));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            lectureScheduleService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tenant/{tenantId}")
    public List<LectureSchedule> getByTenant(@PathVariable Long tenantId) {
        return lectureScheduleService.getByTenantId(tenantId);
    }

    @GetMapping("/course-professor/{courseProfessorId}")
    public List<LectureSchedule> getByCourseProfessor(@PathVariable Long courseProfessorId) {
        return lectureScheduleService.getByCourseProfessorId(courseProfessorId);
    }

    @GetMapping("/program/{programId}")
    public List<LectureSchedule> getByProgram(@PathVariable Long programId) {
        return lectureScheduleService.getByProgramId(programId);
    }

    @GetMapping("/student")
    public ResponseEntity<List<LectureScheduleDTO>> getStudentSchedule(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "tenantId") Long tenantId) {
        List<LectureScheduleDTO> schedule = lectureScheduleService.getStudentSchedule(userId, tenantId);
        return ResponseEntity.ok(schedule);
    }

}