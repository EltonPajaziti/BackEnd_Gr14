package org.backend.service;

import org.backend.dto.DepartmentCreateDTO;
import org.backend.model.Department;
import org.backend.model.Faculty;
import org.backend.repository.DepartmentRepository;
import org.backend.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private final DepartmentRepository departmentRepository;


    @Autowired
    private FacultyRepository facultyRepository;


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

    public List<Department> getDepartmentsByFacultyId(Long tenantID){
        return departmentRepository.findByTenantID_Id(tenantID);
    }


    public Department createDepartment(DepartmentCreateDTO departmentDTO) {
        Department department = new Department();
        department.setName(departmentDTO.getName());
        if (departmentDTO.getTenantID() == null) {
            throw new IllegalArgumentException("Faculty ID must be provided");
        }
        Faculty faculty = facultyRepository.findById(departmentDTO.getTenantID())
                .orElseThrow(() -> new RuntimeException("Faculty not found with ID: " + departmentDTO.getTenantID()));

        department.setTenantID(faculty);

        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department updateDepartment){
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamenti nuk u gjet"));

        department.setName(updateDepartment.getName());


        return departmentRepository.save(department);

    }
    public Long countDepartmentsByTenant(Long tenantId) {
        return departmentRepository.countByTenantID_Id(tenantId);
    }
    public  void deleteDepartment(Long id){
        departmentRepository.deleteById(id);
    }
}
