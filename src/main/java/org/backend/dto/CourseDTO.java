package org.backend.dto;

public class CourseDTO {
    private Long id;
    private String code;
    private String name;
    private Integer credits;


     private String professorName;

    public CourseDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }


     public String getProfessorName() { return professorName; }
     public void setProfessorName(String professorName) { this.professorName = professorName; }
}
