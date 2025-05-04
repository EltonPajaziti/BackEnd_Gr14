package org.backend.service;

import org.backend.model.FAQ;
import org.backend.repository.FAQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FAQService {

    private final FAQRepository faqRepository;

    @Autowired
    public FAQService(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @Transactional(readOnly = true)
    public List<FAQ> getAll() {
        return faqRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<FAQ> getById(Long id) {
        return faqRepository.findById(id);
    }

    @Transactional
    public FAQ create(FAQ faq) {
        validateFAQ(faq);
        return faqRepository.save(faq);
    }

    @Transactional
    public FAQ update(Long id, FAQ updatedFAQ) {
        validateFAQ(updatedFAQ);
        FAQ existingFAQ = faqRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FAQ not found"));
        updatedFAQ.setId(id);
        return faqRepository.save(updatedFAQ);
    }

    @Transactional
    public void delete(Long id) {
        if (!faqRepository.existsById(id)) {
            throw new RuntimeException("FAQ not found");
        }
        faqRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<FAQ> getByCreatedById(Long createdById) {
        return faqRepository.findByCreatedById(createdById);
    }

    @Transactional(readOnly = true)
    public List<FAQ> getByTenantId(Long tenantId) {
        return faqRepository.findByTenantIDId(tenantId);
    }

    @Transactional(readOnly = true)
    public List<FAQ> getByCategory(String category) {
        return faqRepository.findByCategory(category);
    }

    private void validateFAQ(FAQ faq) {
        if (faq.getQuestion() == null || faq.getQuestion().isBlank()) {
            throw new IllegalArgumentException("Question is required");
        }
        if (faq.getCategory() != null && faq.getCategory().length() > 100) {
            throw new IllegalArgumentException("Category must not exceed 100 characters");
        }
    }
}