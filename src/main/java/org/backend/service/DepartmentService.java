package org.backend.service;

import org.backend.model.Department;
import org.backend.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private  final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments(){
        return  departmentRepository.findAll();
    }

    public Optional<Department> getDepartmentById(Long id){
        return  departmentRepository.findById(id);
    }

    public Department getDepartmentByName(String name){
        return departmentRepository.findByName(name);
    }

    public List<Department> getDepartmentsByFacultyId(Long facultyId){
        return departmentRepository.findByFacultyId(facultyId);
    }

    public Department createDepartment(Department department){
        return  departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department updateDepartment){
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamenti nuk u gjet"));

        department.setName(updateDepartment.getName());
        department.setFaculty(updateDepartment.getFaculty());

        return departmentRepository.save(department);

    }

    public  void deleteDepartment(Long id){
        departmentRepository.deleteById(id);
    }
}
