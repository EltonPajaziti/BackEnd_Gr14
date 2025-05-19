package org.backend.dto;

public class EnrollmentRequestDTO {
    private Long studentId;
    private Long courseId;
    private Long academicYearId;
    private Long tenantId;

    // Getters
    public Long getStudentId() {
        return studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Long getAcademicYearId() {
        return academicYearId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    // Setters
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setAcademicYearId(Long academicYearId) {
        this.academicYearId = academicYearId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
