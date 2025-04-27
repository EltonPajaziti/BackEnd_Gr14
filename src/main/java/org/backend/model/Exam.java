package org.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course; // Lënda e provimit

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_period_id", referencedColumnName = "id")
    private Exam_Period examPeriod; // Lidhja me periudhën e provimeve

    @Column(name = "exam_date", nullable = false)
    private LocalDate examDate; // Data e provimit

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime; // Ora e fillimit

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime; // Ora e përfundimit

    @Column(name = "room", length = 50)
    private String room; // Salla ku mbahet provimi

    @Enumerated(EnumType.STRING)
    @Column(name = "exam_type", nullable = false)
    private ExamType examType; // Lloji i provimit (PARCIAL, FINAL, etc.)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", referencedColumnName = "id")
    private Professor professor; // Profesori korrigjues

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private Faculty tenant; // Multi-tenancy

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Enum për llojet e provimeve
    public enum ExamType {
        PARCIAL, FINAL, RIMARRJE, SEMINAR, PROJEKT
    }

    // Konstruktor
    public Exam() {}

    // Metodat e automatizuara për datat
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ======== Getters & Setters ========
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public Exam_Period getExamPeriod() { return examPeriod; }
    public void setExamPeriod(Exam_Period examPeriod) { this.examPeriod = examPeriod; }

    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public ExamType getExamType() { return examType; }
    public void setExamType(ExamType examType) { this.examType = examType; }

    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }

    public Faculty getTenant() { return tenant; }
    public void setTenant(Faculty tenant) { this.tenant = tenant; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}