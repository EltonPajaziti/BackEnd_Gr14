-- 1. Drop invalid column 'academic_year' (was string) and add correct FK column
ALTER TABLE enrollments
    DROP COLUMN academic_year;

-- 2. Update tenant_id type
ALTER TABLE enrollments
    MODIFY tenant_id BIGINT;

-- 3. Add proper column for academic_year_id
ALTER TABLE enrollments
    ADD COLUMN academic_year_id BIGINT;

-- 4. Add missing FK constraints
ALTER TABLE enrollments
    ADD CONSTRAINT fk_enrollment_academicyear FOREIGN KEY (academic_year_id) REFERENCES academic_year(id),
    ADD CONSTRAINT fk_enrollment_tenant FOREIGN KEY (tenant_id) REFERENCES faculty(id);