ALTER TABLE course_material
ADD COLUMN course_id BIGINT,
ADD CONSTRAINT fk_course_material_course FOREIGN KEY (course_id) REFERENCES course(id);
