package org.backend.controller;

import org.backend.dto.FacultyCreateDTO;
import org.backend.model.Faculty;
import org.backend.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculties")
@CrossOrigin(origins = "http://localhost:5173")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @GetMapping
    public List<Faculty> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("/{id}")
    public Faculty getFacultyById(@PathVariable Long id) {
        return facultyService.getFacultyById(id).orElseThrow();
    }

    @PostMapping("/create")
    public Faculty createFaculty(@RequestBody FacultyCreateDTO dto) {
        return facultyService.createFaculty(dto);
    }


    @PutMapping("/{id}")
    public Faculty updateFaculty(@PathVariable Long id, @RequestBody Faculty faculty) {
        return facultyService.updateFaculty(id, faculty);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }
}