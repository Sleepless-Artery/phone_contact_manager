CREATE TABLE IF NOT EXISTS contact (
    PRIMARY KEY (id),
    id           SERIAL,
    name         VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    note         VARCHAR(100)
);
