-- 1. Drop FK before any change
ALTER TABLE department
    DROP FOREIGN KEY faculty_department_id;

-- 2. Change Faculty_ID type to BIGINT (safe to do now)
ALTER TABLE department
    MODIFY Faculty_ID BIGINT;
