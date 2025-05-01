-- Nuk perfshihet tabela enrollment sepse duhet shtuar AcademicYear si tabele njehere me migrim, e me pas
--enrollment per shkak te FK

--  FAKULTETI: Ndrysho ID në BIGINT, Email në UNIQUE
ALTER TABLE faculty
    MODIFY id BIGINT AUTO_INCREMENT PRIMARY KEY,
    MODIFY name VARCHAR(50) NOT NULL,
    MODIFY email VARCHAR(100) NOT NULL UNIQUE,
    MODIFY address VARCHAR(100),
    MODIFY created_at DATETIME;

--  USERS: Përputhje me modelet Java (gender si ENUM, tenant_id si BIGINT, roli NOT NULL)
ALTER TABLE users
    MODIFY id BIGINT AUTO_INCREMENT PRIMARY KEY,
    MODIFY first_name VARCHAR(50) NOT NULL,
    MODIFY last_name VARCHAR(50) NOT NULL,
    MODIFY email VARCHAR(100) NOT NULL UNIQUE,
    MODIFY alt_email VARCHAR(100),
    MODIFY phone VARCHAR(20),
    MODIFY address VARCHAR(100),
    MODIFY gender VARCHAR(10), -- ENUM: MALE, FEMALE
    MODIFY date_of_birth DATE,
    MODIFY national_id VARCHAR(20),
    MODIFY profile_picture VARCHAR(255),
    MODIFY role VARCHAR(20) NOT NULL,
    MODIFY last_login DATETIME,
    MODIFY password VARCHAR(255) NOT NULL,
    MODIFY tenant_id BIGINT,
    MODIFY created_at DATETIME;

--  FOREIGN KEY për tenant_id që lidhet me faculty(id)
ALTER TABLE users
    ADD CONSTRAINT fk_users_tenant
    FOREIGN KEY (tenant_id) REFERENCES faculty(id);

-- DEPARTMENT: ndrysho emrin dhe tipin e kolonës faculty_id në tenant_id (BIGINT)
ALTER TABLE department
    DROP FOREIGN KEY faculty_department_id;

ALTER TABLE department
    CHANGE Faculty_ID tenant_id BIGINT;

ALTER TABLE department
    ADD CONSTRAINT fk_department_tenant
    FOREIGN KEY (tenant_id) REFERENCES faculty(id);

-- PROGRAM: përputhje me modelin (tenant_id -> BIGINT + shtim FK)
ALTER TABLE program
    MODIFY tenant_id BIGINT;

ALTER TABLE program
    ADD CONSTRAINT fk_program_tenant
    FOREIGN KEY (tenant_id) REFERENCES faculty(id);


-- COURSE: përditësim tenant_id dhe FK
ALTER TABLE course
    MODIFY tenant_id BIGINT;

ALTER TABLE course
    ADD CONSTRAINT fk_course_tenant
    FOREIGN KEY (tenant_id) REFERENCES faculty(id);

-- STUDENTS: përditësim tenant_id dhe FK
ALTER TABLE students
    MODIFY tenant_id BIGINT;

ALTER TABLE students
    ADD CONSTRAINT fk_students_tenant
    FOREIGN KEY (tenant_id) REFERENCES faculty(id);
