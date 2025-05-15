package org.backend.service;

import org.backend.dto.FacultyCreateDTO;
import org.backend.model.Faculty;
import org.backend.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }


    public Optional<Faculty> getFacultyById(Long id) {
        return facultyRepository.findById(id);
    }


    public Faculty createFaculty(FacultyCreateDTO facultyDTO) {
        Faculty faculty = new Faculty();
        faculty.setId(facultyDTO.getFacultyId());
        faculty.setName(facultyDTO.getName());
        faculty.setEmail(facultyDTO.getEmail());
        faculty.setAddress(facultyDTO.getAddress());

        return facultyRepository.save(faculty);
    }


    public Faculty updateFaculty(Long id, Faculty updatedFaculty) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow();

        faculty.setName(updatedFaculty.getName());
        faculty.setAddress(updatedFaculty.getAddress());


        return facultyRepository.save(faculty);
    }


    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
}