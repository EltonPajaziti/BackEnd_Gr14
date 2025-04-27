CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    alt_email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(100),
    gender VARCHAR(1),
    date_of_birth DATE,
    national_id VARCHAR(20),
    profile_picture VARCHAR(255),
    role VARCHAR(20),
    last_login DATETIME,
    password VARCHAR(255) NOT NULL,
    tenant_id INT,
    created_at DATETIME
);
