package org.backend.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
<<<<<<< HEAD
import org.backend.enums.ConfirmationStatus;
=======
>>>>>>> main

@Entity
@Table(name = "grade_confirmation")
public class GradeConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "grade_id", referencedColumnName = "id")
    private Grade grade;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

<<<<<<< HEAD
//    @Column(name = "status")
//    private Boolean status;
//    //Enum pasi të kryhen edhe migrimet
@Enumerated(EnumType.STRING)
@Column(name = "status", length = 20, nullable = false)
private ConfirmationStatus status;


    @Column(name = "expires_at")
    private LocalDate expiresAt;
=======
    @Column(name = "status")
    private Boolean status;
    //Enum pasi të kryhen edhe migrimet

    @Column(name = "expiers_at")
    private LocalDate expiersAt;
>>>>>>> main

    @Column(name = "responded_at")
    private LocalDate respondedAt;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private Faculty tenantID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

<<<<<<< HEAD
    public ConfirmationStatus getStatus() {
        return status;
    }

    public void setStatus(ConfirmationStatus status) {
        this.status = status;
    }


    public LocalDate getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDate expiresAt) {
        this.expiresAt = expiresAt;
=======
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDate getExpiersAt() {
        return expiersAt;
    }

    public void setExpiersAt(LocalDate expiersAt) {
        this.expiersAt = expiersAt;
>>>>>>> main
    }

    public LocalDate getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(LocalDate respondedAt) {
        this.respondedAt = respondedAt;
    }

    public Faculty getTenantID() {
        return tenantID;
    }

    public void setTenantID(Faculty tenantID) {
        this.tenantID = tenantID;
    }



}