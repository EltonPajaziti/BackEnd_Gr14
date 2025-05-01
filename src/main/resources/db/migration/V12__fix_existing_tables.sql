-- USERS: Update types and add FK
ALTER TABLE users
    MODIFY id BIGINT AUTO_INCREMENT,
    MODIFY tenant_id BIGINT,
    MODIFY first_name VARCHAR(50) NOT NULL,
    MODIFY last_name VARCHAR(50) NOT NULL,
    MODIFY email VARCHAR(100) NOT NULL,
    MODIFY alt_email VARCHAR(100),
    MODIFY phone VARCHAR(20),
    MODIFY address VARCHAR(100),
    MODIFY gender VARCHAR(10),
    MODIFY date_of_birth DATE,
    MODIFY national_id VARCHAR(20),
    MODIFY profile_picture VARCHAR(255),
    MODIFY role VARCHAR(20) NOT NULL,
    MODIFY last_login DATETIME,
    MODIFY password VARCHAR(255) NOT NULL,
    MODIFY created_at DATETIME;

ALTER TABLE users
    ADD CONSTRAINT fk_users_tenant FOREIGN KEY (tenant_id) REFERENCES faculty(id);

-- PROGRAM: fix types and FKs
ALTER TABLE program
    MODIFY department_id BIGINT,
    MODIFY tenant_id BIGINT;

ALTER TABLE program
    DROP FOREIGN KEY program_department_foreign_id;

ALTER TABLE program
    ADD CONSTRAINT fk_program_department FOREIGN KEY (department_id) REFERENCES department(id),
    ADD CONSTRAINT fk_program_tenant FOREIGN KEY (tenant_id) REFERENCES faculty(id);

-- COURSE
ALTER TABLE course
    MODIFY program_id BIGINT,
    MODIFY tenant_id BIGINT;

ALTER TABLE course
    DROP FOREIGN KEY course_program_id;

ALTER TABLE course
    ADD CONSTRAINT fk_course_program FOREIGN KEY (program_id) REFERENCES program(id),
    ADD CONSTRAINT fk_course_tenant FOREIGN KEY (tenant_id) REFERENCES faculty(id);

-- STUDENTS
ALTER TABLE students
    MODIFY user_id BIGINT,
    MODIFY program_id BIGINT,
    MODIFY tenant_id BIGINT;

ALTER TABLE students
    DROP FOREIGN KEY fk_student_user,
    DROP FOREIGN KEY fk_student_program;

ALTER TABLE students
    ADD CONSTRAINT fk_student_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_student_program FOREIGN KEY (program_id) REFERENCES program(id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_students_tenant FOREIGN KEY (tenant_id) REFERENCES faculty(id);
