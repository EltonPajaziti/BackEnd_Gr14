
-- =============================================================
-- MIGRIMI FINAL  - STRUKTURA E PLOTE E BAZES DS_GR14
-- =============================================================

-- FACULTY (Tenant Bazë)
CREATE TABLE faculty (
    id BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    address VARCHAR(100),
    created_at DATETIME
);

-- USER_ROLES
CREATE TABLE user_roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- USERS
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    alt_email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(100),
    gender VARCHAR(10),
    date_of_birth DATE,
    national_id VARCHAR(20),
    profile_picture VARCHAR(255),
    role_id BIGINT NOT NULL,
    last_login DATETIME,
    password VARCHAR(255) NOT NULL,
    tenant_id BIGINT,
    created_at DATETIME,
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES user_roles(id),
    CONSTRAINT fk_users_tenant FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);

-- DEPARTMENT
CREATE TABLE department (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    tenant_id BIGINT,
    created_at DATETIME,
    FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);

-- PROGRAM
CREATE TABLE program (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    level VARCHAR(50) NOT NULL,
    department_id BIGINT NOT NULL,
    tenant_id BIGINT,
    created_at DATETIME,
    FOREIGN KEY (department_id) REFERENCES department(id),
    FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);

-- PROFESSOR
CREATE TABLE professor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    department_id BIGINT,
    academic_title VARCHAR(100),
    hired_date DATE,
    tenant_id BIGINT,
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (department_id) REFERENCES department(id),
    FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);

-- STUDENTS
CREATE TABLE students (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    program_id BIGINT NOT NULL,
    tenant_id BIGINT,
    enrollment_date DATE,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (program_id) REFERENCES program(id),
    FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);

-- COURSE
CREATE TABLE course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(20) NOT NULL,
    credits INT,
    semester SMALLINT,
    year_study SMALLINT,
    program_id BIGINT,
    tenant_id BIGINT,
    description TEXT,
    created_at DATETIME,
    FOREIGN KEY (program_id) REFERENCES program(id),
    FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);

-- COURSE_PROFESSOR
CREATE TABLE course_professor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT,
    professor_id BIGINT,
    semester VARCHAR(20),
    year_study VARCHAR(10),
    tenant_id BIGINT,
    created_at DATETIME,
    FOREIGN KEY (course_id) REFERENCES course(id),
    FOREIGN KEY (professor_id) REFERENCES professor(id),
    FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);

-- COURSE_MATERIAL
CREATE TABLE course_material (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    file_url VARCHAR(255) NOT NULL,
    uploaded_at DATETIME,
    tenant_id BIGINT NOT NULL,
    FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);

-- ACADEMIC_YEAR
CREATE TABLE academic_year (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    is_active BOOLEAN,
    created_at DATETIME,
    tenant_id BIGINT,
    FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);


-- EXAM_PERIOD
CREATE TABLE exam_period (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    registration_start DATE NOT NULL,
    registration_end DATE NOT NULL,
    is_active BOOLEAN NOT NULL,
    academic_year_id BIGINT NOT NULL,
    tenant_id BIGINT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (academic_year_id) REFERENCES academic_year(id),
    FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);

-- EXAMS
CREATE TABLE exams (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    course_id BIGINT NOT NULL,
    exam_period_id BIGINT NOT NULL,
    exam_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    room VARCHAR(50),
    exam_type VARCHAR(50) NOT NULL,
    professor_id BIGINT,
    tenant_id BIGINT NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (course_id) REFERENCES course(id),
    FOREIGN KEY (exam_period_id) REFERENCES exam_period(id),
    FOREIGN KEY (professor_id) REFERENCES professor(id),
    FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);

-- GRADE
CREATE TABLE grade (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    academic_year_id BIGINT NOT NULL,
    grade_value INT,
    graded_by BIGINT NOT NULL,
    graded_at DATE,
    tenant_id BIGINT,
    exam_id BIGINT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (academic_year_id) REFERENCES academic_year(id),
    FOREIGN KEY (graded_by) REFERENCES professor(id),
    FOREIGN KEY (tenant_id) REFERENCES faculty(id),
    FOREIGN KEY (exam_id) REFERENCES exams(id)
);

-- GRADE_CONFIRMATION
CREATE TABLE grade_confirmation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    grade_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    expires_at DATE,
    responded_at DATE,
    tenant_id BIGINT,
    FOREIGN KEY (grade_id) REFERENCES grade(id),
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);


-- TRANSCRIPT
CREATE TABLE transcript (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    generated_at DATETIME,
    tenant_id BIGINT NOT NULL,
    academic_year_id BIGINT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (tenant_id) REFERENCES faculty(id),
    FOREIGN KEY (academic_year_id) REFERENCES academic_year(id)
);


-- ENROLLMENTS
CREATE TABLE enrollments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    enrollment_date DATE NOT NULL,
    academic_year BIGINT NOT NULL,
    tenant_id BIGINT NOT NULL,
    created_at DATETIME,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_id) REFERENCES course(id),
    FOREIGN KEY (academic_year) REFERENCES academic_year(id),
    FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);

-- SCHOLARSHIP_APPLICATION
CREATE TABLE scholarship_application (
    application_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    academic_year_id BIGINT NOT NULL,
    tenant_id BIGINT,
    gpa DOUBLE,
    status VARCHAR(50),
    request_date DATETIME,
    decision_date DATETIME,
    note TEXT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (academic_year_id) REFERENCES academic_year(id),
    FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);

-- FAQ
CREATE TABLE faq (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    question TEXT NOT NULL,
    answer TEXT,
    category VARCHAR(100),
    is_visible BOOLEAN,
    created_by BIGINT,
    created_at DATETIME,
    updated_at DATETIME,
    tenant_id BIGINT,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);
-- LECTURE_SCHEDULE
CREATE TABLE lecture_schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    day_of_week VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    room VARCHAR(50) NOT NULL,
    tenant_id BIGINT NOT NULL,
    course_professor_id BIGINT NOT NULL,
    program_id BIGINT NOT NULL,
    created_at DATETIME,
    FOREIGN KEY (tenant_id) REFERENCES faculty(id),
    FOREIGN KEY (course_professor_id) REFERENCES course_professor(id),
    FOREIGN KEY (program_id) REFERENCES program(id)
);















