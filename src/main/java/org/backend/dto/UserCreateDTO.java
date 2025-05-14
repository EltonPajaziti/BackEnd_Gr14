package org.backend.dto;

public class UserCreateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String gender; // e.g., "MALE" ose "FEMALE"
    private String dateOfBirth; // format: yyyy-MM-dd
    private String nationalId;
    private String profilePicture;
    private String roleName; // e.g., "STUDENT", "PROFESSOR", "ADMIN"
    private String password;

    // Getters & Setters

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
