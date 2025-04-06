package org.backend.service;

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


    public Faculty createFaculty(Faculty faculty) {
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