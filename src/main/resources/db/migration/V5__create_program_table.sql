CREATE TABLE program (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    level VARCHAR(50) NOT NULL,
    department_id BIGINT NOT NULL,
    tenant_id INT,
    created_at DATETIME,

    CONSTRAINT program_department_foreign_id
    FOREIGN KEY (department_id)
    REFERENCES department(ID)
);
