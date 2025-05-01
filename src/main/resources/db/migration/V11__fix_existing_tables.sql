-- Sigurohemi që të dy kolonat janë BIGINT UNSIGNED ose të dy signed (duhet të jenë identikë)

-- 1. Rregullo faculty.id
ALTER TABLE faculty
    MODIFY id BIGINT;

-- 2. Rregullo department.tenant_id
ALTER TABLE department
    MODIFY tenant_id BIGINT;

-- 3. Tani vendos foreign key
ALTER TABLE department
    ADD CONSTRAINT fk_department_tenant FOREIGN KEY (tenant_id) REFERENCES faculty(id);
