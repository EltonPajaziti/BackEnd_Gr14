package org.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.backend.model.FAQ;
import org.backend.service.FAQService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FAQControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FAQService faqService;

    @InjectMocks
    private FAQController faqController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(faqController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAll_ReturnsFAQList() throws Exception {
        FAQ faq = new FAQ();
        faq.setId(1L);
        faq.setQuestion("Sample question?");
        faq.setAnswer("Sample answer.");

        when(faqService.getAll()).thenReturn(Collections.singletonList(faq));

        mockMvc.perform(get("/api/faqs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].question").value("Sample question?"))
                .andExpect(jsonPath("$[0].answer").value("Sample answer."));

        verify(faqService, times(1)).getAll();
    }

    @Test
    void testGetById_Found() throws Exception {
        FAQ faq = new FAQ();
        faq.setId(1L);
        faq.setQuestion("Question?");
        faq.setAnswer("Answer.");

        when(faqService.getById(1L)).thenReturn(Optional.of(faq));

        mockMvc.perform(get("/api/faqs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.question").value("Question?"))
                .andExpect(jsonPath("$.answer").value("Answer."));

        verify(faqService).getById(1L);
    }

    @Test
    void testGetById_NotFound() throws Exception {
        when(faqService.getById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/faqs/999"))
                .andExpect(status().isNotFound());

        verify(faqService).getById(999L);
    }

    @Test
    void testCreate_ValidFAQ_ReturnsOk() throws Exception {
        FAQ faq = new FAQ();
        faq.setQuestion("Valid question?");
        faq.setAnswer("Valid answer.");

        when(faqService.create(any(FAQ.class))).thenReturn(faq);

        String json = objectMapper.writeValueAsString(faq);

        mockMvc.perform(post("/api/faqs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question").value("Valid question?"))
                .andExpect(jsonPath("$.answer").value("Valid answer."));

        verify(faqService).create(any(FAQ.class));
    }

    @Test
    void testUpdate_ValidFAQ_ReturnsOk() throws Exception {
        FAQ faq = new FAQ();
        faq.setQuestion("Updated question?");
        faq.setAnswer("Updated answer.");

        when(faqService.update(eq(1L), any(FAQ.class))).thenReturn(faq);

        String json = objectMapper.writeValueAsString(faq);

        mockMvc.perform(put("/api/faqs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.question").value("Updated question?"))
                .andExpect(jsonPath("$.answer").value("Updated answer."));

        verify(faqService).update(eq(1L), any(FAQ.class));
    }

    @Test
    void testUpdate_NotFound_ReturnsNotFound() throws Exception {
        FAQ faq = new FAQ();
        faq.setQuestion("Updated question?");
        faq.setAnswer("Updated answer.");

        when(faqService.update(eq(1L), any(FAQ.class))).thenThrow(new RuntimeException("Not found"));

        String json = objectMapper.writeValueAsString(faq);

        mockMvc.perform(put("/api/faqs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());

        verify(faqService).update(eq(1L), any(FAQ.class));
    }

    @Test
    void testDelete_Success() throws Exception {
        doNothing().when(faqService).delete(1L);

        mockMvc.perform(delete("/api/faqs/1"))
                .andExpect(status().isNoContent());

        verify(faqService).delete(1L);
    }

    @Test
    void testDelete_NotFound() throws Exception {
        doThrow(new RuntimeException("Not found")).when(faqService).delete(999L);

        mockMvc.perform(delete("/api/faqs/999"))
                .andExpect(status().isNotFound());

        verify(faqService).delete(999L);
    }

    @Test
    void testGetByCreatedBy_ReturnsFAQs() throws Exception {
        FAQ faq = new FAQ();
        faq.setId(1L);
        faq.setQuestion("Question by creator");
        faq.setAnswer("Answer");

        when(faqService.getByCreatedById(10L)).thenReturn(Collections.singletonList(faq));

        mockMvc.perform(get("/api/faqs/created-by/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].question").value("Question by creator"));

        verify(faqService).getByCreatedById(10L);
    }

    @Test
    void testGetByTenant_ReturnsFAQs() throws Exception {
        FAQ faq = new FAQ();
        faq.setId(2L);
        faq.setQuestion("Question by tenant");
        faq.setAnswer("Answer");

        when(faqService.getByTenantId(20L)).thenReturn(Collections.singletonList(faq));

        mockMvc.perform(get("/api/faqs/tenant/20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].question").value("Question by tenant"));

        verify(faqService).getByTenantId(20L);
    }

    @Test
    void testGetByCategory_ReturnsFAQs() throws Exception {
        FAQ faq = new FAQ();
        faq.setId(3L);
        faq.setCategory("General");
        faq.setQuestion("General question?");
        faq.setAnswer("Answer");

        when(faqService.getByCategory("General")).thenReturn(Collections.singletonList(faq));

        mockMvc.perform(get("/api/faqs/category/General"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3L))
                .andExpect(jsonPath("$[0].category").value("General"))
                .andExpect(jsonPath("$[0].question").value("General question?"));

        verify(faqService).getByCategory("General");
    }
}
