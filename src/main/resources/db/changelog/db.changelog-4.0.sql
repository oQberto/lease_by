--liquibase formatted sql

--changeset alexermakov:1
ALTER TABLE users
    ADD COLUMN is_verified BOOLEAN DEFAULT false;

--changeset alexermakov:2
ALTER TABLE password_token
    RENAME TO verification_token;