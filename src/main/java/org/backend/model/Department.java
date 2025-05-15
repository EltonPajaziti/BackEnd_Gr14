package org.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;




    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private Faculty tenantID;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Department(){}

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
    public  String getName(){
        return name;
    }

    public  void setName(String name){
        this.name = name;
    }




    public Faculty getTenantID() { return tenantID; }
    public void setTenantID(Faculty tenantID) { this.tenantID = tenantID; }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}