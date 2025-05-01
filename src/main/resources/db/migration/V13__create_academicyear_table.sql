CREATE TABLE academic_year (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE ,
    is_active BOOLEAN,
    created_at DATETIME,
    tenant_id BIGINT,

    CONSTRAINT fk_academicyear_tenant FOREIGN KEY (tenant_id) REFERENCES faculty(id)
);