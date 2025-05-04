package org.backend.repository;

import org.backend.model.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {
    List<FAQ> findByCreatedById(Long createdById);
    List<FAQ> findByTenantIDId(Long tenantId);
    List<FAQ> findByCategory(String category);
}