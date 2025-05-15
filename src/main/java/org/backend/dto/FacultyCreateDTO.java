package org.backend.dto;

public class FacultyCreateDTO {

    private Long facultyId;
    private String name;
    private String email;
    private String address;

    public Long getFacultyId(){
        return facultyId;
    }

    public void setFacultyId(Long facultyId){
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
