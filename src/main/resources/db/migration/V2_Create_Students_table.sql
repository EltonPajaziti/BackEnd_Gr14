CREATE TABLE students (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    user_id BIGINT NOT NULL,
    program_id INT NOT NULL,
    tenant_id INT,

    enrollment_date DATE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_student_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_student_program FOREIGN KEY (program_id) REFERENCES programs(id),
    CONSTRAINT fk_student_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id)
);
