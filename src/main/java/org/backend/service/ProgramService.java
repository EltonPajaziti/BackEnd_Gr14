package org.backend.service;


import org.backend.dto.ProgramCreateDTO;
import org.backend.model.Department;
import org.backend.model.Faculty;
import org.backend.model.Program;
import org.backend.repository.DepartmentRepository;
import org.backend.repository.FacultyRepository;
import org.backend.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramService {
    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;


    public List<Program> getAllPrograms(){
        return programRepository.findAll();
    }

    public Optional<Program> getProgramById(Long id){
        return programRepository.findById(id);
    }

    public Program getProgramByName(String name) {
        return programRepository.findProgramByName(name);
    }

    public Program getProgramByLevel(String level) {
        return programRepository.findProgramByLevel(level);
    }



    public Program createProgram(ProgramCreateDTO dto) {
        Program program = new Program();
        program.setName(dto.getName());
        program.setLevel(dto.getLevel());

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        program.setDepartment(department);

        if (dto.getTenantId() != null) {
            Faculty tenant = facultyRepository.findById(dto.getTenantId())
                    .orElseThrow(() -> new IllegalArgumentException("Faculty not found"));
            program.setTenantID(tenant);
        }

        return programRepository.save(program);
    }


    public Program updateProgram(Long id, ProgramCreateDTO dto) {
        Program existing = programRepository.findById(id).orElseThrow();
        existing.setName(dto.getName());
        existing.setLevel(dto.getLevel());
        existing.setDepartment(departmentRepository.findById(dto.getDepartmentId()).orElseThrow());
        return programRepository.save(existing);
    }

    public void deleteProgram(Long id) {
        programRepository.deleteById(id);
    }

   public List<Program> getProgramsByDepartmentId(Long departmentId) {
        return programRepository.findByDepartmentId(departmentId);
    }

}
