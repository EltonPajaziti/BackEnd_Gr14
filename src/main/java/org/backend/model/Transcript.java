package org.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "transcript")
public class Transcript {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Student student;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", referencedColumnName = "id", nullable = false)
    private Faculty tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_year_id", referencedColumnName = "id", nullable = false)
    private AcademicYear academicYear;

    public Transcript() {}

    @PrePersist
    protected void onCreate() {
      protected void onCreate() {
        this.generatedAt = LocalDateTime.now();
        }
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

     public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }

    public Faculty getTenant() { return tenant; }
    public void setTenant(Faculty tenant) { this.tenant = tenant; }

    public AcademicYear getAcademicYear() { return academicYear; }
    public void setAcademicYear(AcademicYear academicYear) { this.academicYear = academicYear; }

}
