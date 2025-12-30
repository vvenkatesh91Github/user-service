ALTER TABLE user_service.users
    ADD COLUMN phone_number VARCHAR(20) NULL AFTER email,
ADD COLUMN is_app_logged_in BOOLEAN NOT NULL DEFAULT FALSE AFTER phone_number;