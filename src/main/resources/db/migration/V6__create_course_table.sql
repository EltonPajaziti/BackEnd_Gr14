CREATE TABLE course(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(20) NOT NULL,
    credits INT,
    semester SMALLINT,
    year_study SMALLINT,
    program_id BIGINT,
    tenant_id INT,
    description TEXT,
    created_at DATETIME,

    CONSTRAINT course_program_id
    FOREIGN KEY (program_id)
    REFERENCES program(id)
);