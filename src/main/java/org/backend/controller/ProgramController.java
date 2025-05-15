package org.backend.controller;

import org.backend.dto.ProgramCreateDTO;
import org.backend.model.Program;
import org.backend.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/programs")
@CrossOrigin(origins = "http://localhost:5173")

public class ProgramController {

    @Autowired
    private ProgramService programService;

    @GetMapping
    public ResponseEntity<List<Program>> getAllPrograms() {
        List<Program> programs = programService.getAllPrograms();
        if (programs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(programs);
    }

    @GetMapping("/{id}")
    public Program getProgramById(@PathVariable Long id) {
        return programService.getProgramById(id).orElseThrow();
    }



    // PUT update program
/*
    @PutMapping("/{id}")
    public ResponseEntity<Program> updateProgram(@PathVariable Long id, @RequestBody Program updatedProgram) {
        try {
            Program updated = programService.updateProgram(id, updatedProgram);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
*/

    @PostMapping("/create")
    public Program createProgram(@RequestBody ProgramCreateDTO dto) {
        return programService.createProgram(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteProgram(@PathVariable Long id) {
        programService.deleteProgram(id);
    }


    @GetMapping("/department/{departmentId}")
    public List<Program> getProgramsByDepartmentId(@PathVariable Long departmentId) {
    return programService.getProgramsByDepartmentId(departmentId);
    }

}
