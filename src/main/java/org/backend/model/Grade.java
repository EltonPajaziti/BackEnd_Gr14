package org.backend.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id")
    private AcademicYear academicYear;

    @Column(name = "grade_value")
    private Integer grade_value;

    @ManyToOne(optional = false)
    @JoinColumn(name = "graded_by", referencedColumnName = "id")
    private Professor professor;

    @Column(name = "graded_at")
    private LocalDate gradedAt;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private Faculty tenantID;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exam_id", referencedColumnName = "id")
    private Exam exam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public Integer getGrade_value() {
        return grade_value;
    }

    public void setGrade_value(Integer grade_value) {
        this.grade_value = grade_value;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public LocalDate getGradedAt() {
        return gradedAt;
    }

    public void setGradedAt(LocalDate gradedAt) {
        this.gradedAt = gradedAt;
    }

    public Faculty getTenantID() {
        return tenantID;
    }

    public void setTenantID(Faculty tenantID) {
        this.tenantID = tenantID;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}




