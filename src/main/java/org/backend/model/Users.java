package org.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "alt_email", length = 100)
    private String altEmail;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String address;

    @Column(length = 1)
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "national_id", length = 20)
    private String nationalId;

    @Column(name = "profile_picture", length = 255)
    private String profilePicture;

    @Column(length = 20)
    private String role;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(length = 255, nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private Faculty tenantID;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Users() {}

    // Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAltEmail() { return altEmail; }
    public void setAltEmail(String altEmail) { this.altEmail = altEmail; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Faculty getTenantID() { return tenantID; }
    public void setTenantID(Faculty tenantID) { this.tenantID = tenantID; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
