package org.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @Column(name = "academic_title", length = 100)
    private String academicTitle;

    @Column(name = "hired_date")
    private LocalDate hiredDate;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private Faculty tenantID;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Professor() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Users getUser() { return user; }
    public void setUser(Users user) { this.user = user; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public String getAcademicTitle() { return academicTitle; }
    public void setAcademicTitle(String academicTitle) { this.academicTitle = academicTitle; }

    public LocalDate getHiredDate() { return hiredDate; }
    public void setHiredDate(LocalDate hiredDate) { this.hiredDate = hiredDate; }

    public Faculty getTenantID() { return tenantID; }
    public void setTenantID(Faculty tenantID) { this.tenantID = tenantID; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
