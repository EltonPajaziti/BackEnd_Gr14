package org.backend.dto;

public class DepartmentCreateDTO {

    private String name;

    private Long tenantID;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Long getTenantID(){
        return tenantID;
    }

    public void setTenantID(Long facultyId){
        this.tenantID = facultyId;
    }


}
