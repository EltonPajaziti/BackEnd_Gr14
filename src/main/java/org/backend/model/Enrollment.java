package org.backend.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;
    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;
//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "academic_year", referencedColumnName = "id")
//    private AcademicYear academicYear;
    @ManyToOne
    @JoinColumn(name = "academic_year", referencedColumnName = "id", nullable = true)
    private AcademicYear academicYear;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")

    private Faculty tenantID;



    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    public Enrollment() {}
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }
    public AcademicYear getAcademicYear() { return academicYear; }
    public void setAcademicYear(AcademicYear academicYear) { this.academicYear = academicYear; }

    public Faculty getTenantID() { return tenantID; }
    public void setTenantID(Faculty tenantID) { this.tenantID = tenantID; }



    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}