ALTER TABLE app_users
    ADD COLUMN password_hash VARCHAR(100) NULL,
    ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'USER';

UPDATE app_users
SET password_hash = '$2a$10$7EqJtq98hPqEX7fNZaFWoO5uR7ZqXwJxY6sL6S9lEOhAandQKUWjK'
WHERE password_hash IS NULL;

ALTER TABLE app_users
    MODIFY COLUMN password_hash VARCHAR(100) NOT NULL;
