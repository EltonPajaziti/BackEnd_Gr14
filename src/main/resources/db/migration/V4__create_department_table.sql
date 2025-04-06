CREATE TABLE department(
    ID bigint AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Faculty_ID INT,
    Created_At DATETIME,
        CONSTRAINT faculty_department_id FOREIGN KEY (Faculty_ID)
             REFERENCES faculty(ID)
);