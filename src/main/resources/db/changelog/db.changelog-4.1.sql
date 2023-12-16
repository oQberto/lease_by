--liquibase formatted sql

--changeset alexermakov:1
ALTER TABLE users
    ADD COLUMN status VARCHAR(64) DEFAULT 'ACTIVE';