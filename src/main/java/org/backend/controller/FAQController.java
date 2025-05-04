package org.backend.controller;

import org.backend.model.FAQ;
import org.backend.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/faqs")
@CrossOrigin(origins = "*")
public class FAQController {

    private final FAQService faqService;

    @Autowired
    public FAQController(FAQService faqService) {
        this.faqService = faqService;
    }

    @GetMapping
    public List<FAQ> getAll() {
        return faqService.getAll();
    }

    @GetMapping("/{id}")
    public FAQ getById(@PathVariable Long id) {
        return faqService.getById(id)
                .orElseThrow(() -> new RuntimeException("FAQ not found"));
    }

    @PostMapping
    public ResponseEntity<FAQ> create(@Valid @RequestBody FAQ faq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(faqService.create(faq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FAQ> update(@PathVariable Long id, @Valid @RequestBody FAQ faq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(faqService.update(id, faq));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            faqService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/created-by/{createdById}")
    public List<FAQ> getByCreatedBy(@PathVariable Long createdById) {
        return faqService.getByCreatedById(createdById);
    }

    @GetMapping("/tenant/{tenantId}")
    public List<FAQ> getByTenant(@PathVariable Long tenantId) {
        return faqService.getByTenantId(tenantId);
    }

    @GetMapping("/category/{category}")
    public List<FAQ> getByCategory(@PathVariable String category) {
        return faqService.getByCategory(category);
    }
}