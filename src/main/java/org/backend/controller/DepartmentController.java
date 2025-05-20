package org.backend.controller;


import org.backend.dto.DepartmentCreateDTO;
import org.backend.model.Department;
import org.backend.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "http://localhost:5173")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        try {
            return ResponseEntity.ok(departmentService.getAllDepartments());
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id).orElseThrow();
    }



    @PostMapping("/create")
    public ResponseEntity<?> createDepartment(@RequestBody DepartmentCreateDTO dto) {
        try {
            Department created = departmentService.createDepartment(dto);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            e.printStackTrace(); // Log the full stack trace
            return ResponseEntity.status(500).body("Error creating department: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody DepartmentCreateDTO dto) {
        try {
            Department updated = departmentService.updateDepartment(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count/by-tenant/{tenantId}")
    public Long countDepartmentsByTenant(@PathVariable("tenantId") Long tenantId) {
        return departmentService.countDepartmentsByTenant(tenantId);
    }
    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }


}
