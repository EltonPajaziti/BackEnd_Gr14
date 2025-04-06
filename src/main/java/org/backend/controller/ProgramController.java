package org.backend.controller;

import org.backend.model.Program;
import org.backend.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    @Autowired
    private ProgramService programService;

    @GetMapping
    public List<Program> getAllPrograms() {
        return programService.getAllPrograms();
    }

    @GetMapping("/{id}")
    public Program getProgramById(@PathVariable Long id) {
        return programService.getProgramById(id).orElseThrow();
    }

    // POST create new program
    @PostMapping
    public Program createProgram(@RequestBody Program program) {
        return programService.createProgram(program);
    }

    // PUT update program
    @PutMapping("/{id}")
    public ResponseEntity<Program> updateProgram(@PathVariable Long id, @RequestBody Program updatedProgram) {
        try {
            Program updated = programService.updateProgram(id, updatedProgram);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProgram(@PathVariable Long id) {
        programService.deleteProgram(id);
    }


    @GetMapping("/department/{departmentId}")
    public List<Program> getProgramsByDepartment(@PathVariable Long departmentId) {
    return programService.getProgramsByDepartmentId(departmentId);
    }

}
