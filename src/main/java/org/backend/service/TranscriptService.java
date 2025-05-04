package org.backend.service;

import org.backend.model.Transcript;
import org.backend.repository.TranscriptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TranscriptService {

    private final TranscriptRepository transcriptRepository;

    @Autowired
    public TranscriptService(TranscriptRepository transcriptRepository) {
        this.transcriptRepository = transcriptRepository;
    }

    @Transactional(readOnly = true)
    public List<Transcript> getAll() {
        return transcriptRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Transcript> getById(Long id) {
        return transcriptRepository.findById(id);
    }

    @Transactional
    public Transcript create(Transcript transcript) {
        validateTranscript(transcript);
        return transcriptRepository.save(transcript);
    }

    @Transactional
    public Transcript update(Long id, Transcript updatedTranscript) {
        validateTranscript(updatedTranscript);
        Transcript existingTranscript = transcriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transcript not found"));
        updatedTranscript.setId(id);
        return transcriptRepository.save(updatedTranscript);
    }

    @Transactional
    public void delete(Long id) {
        if (!transcriptRepository.existsById(id)) {
            throw new RuntimeException("Transcript not found");
        }
        transcriptRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Transcript> getByStudentId(Long studentId) {
        return transcriptRepository.findByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public List<Transcript> getByTenantId(Long tenantId) {
        return transcriptRepository.findByTenantId(tenantId);
    }

    @Transactional(readOnly = true)
    public List<Transcript> getByAcademicYearId(Long academicYearId) {
        return transcriptRepository.findByAcademicYearId(academicYearId);
    }

    private void validateTranscript(Transcript transcript) {
        if (transcript.getStudent() == null || transcript.getStudent().getId() == null) {
            throw new IllegalArgumentException("Student is required");
        }
        if (transcript.getTenant() == null || transcript.getTenant().getId() == null) {
            throw new IllegalArgumentException("Tenant (Faculty) is required");
        }
        if (transcript.getAcademicYear() == null || transcript.getAcademicYear().getId() == null) {
            throw new IllegalArgumentException("Academic year is required");
        }
    }
}