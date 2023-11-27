--liquibase formatted sql

--changeset alexermakov:1
ALTER TABLE users
    ALTER COLUMN modified_by TYPE VARCHAR(256)