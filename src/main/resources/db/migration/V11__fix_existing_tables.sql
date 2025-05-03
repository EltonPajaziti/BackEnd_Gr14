-- 1. Shto kolonën tenant_id (pa IF NOT EXISTS që shkakton probleme në MySQL)
ALTER TABLE department
    ADD COLUMN tenant_id BIGINT;

-- 2. Modifiko faculty.id në BIGINT nëse nuk është tashmë
ALTER TABLE faculty
    MODIFY id BIGINT;

-- 3. Vendos foreign key midis tenant_id dhe faculty.id
ALTER TABLE department
    ADD CONSTRAINT fk_department_tenant
    FOREIGN KEY (tenant_id) REFERENCES faculty(id);

-- 4. Opsionale: Konverto Faculty_ID në BIGINT nëse doni që të dy të jenë të njëjtin tip
ALTER TABLE department
    MODIFY Faculty_ID BIGINT;