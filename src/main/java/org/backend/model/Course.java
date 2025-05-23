package org.backend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "code", length = 20, nullable = false)
    private String code;

    @Column(name = "credits")
    private Integer credits;

    @Column(name = "semester")
    private Short semester;

    @Column(name = "year_study")
    private Short yearStudy;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private Faculty tenantID;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Course_Professor> courseProfessors;

    public Course() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public Short getSemester() { return semester; }
    public void setSemester(Short semester) { this.semester = semester; }

    public Short getYearStudy() { return yearStudy; }
    public void setYearStudy(Short yearStudy) { this.yearStudy = yearStudy; }

    public Program getProgram() { return program; }
    public void setProgram(Program program) { this.program = program; }

    public Faculty getTenant() { return tenantID; }
    public void setTenant(Faculty tenantID) { this.tenantID = tenantID; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<Course_Professor> getCourseProfessors() { return courseProfessors; }
    public void setCourseProfessors(List<Course_Professor> courseProfessors) {
        this.courseProfessors = courseProfessors;
    }
}
