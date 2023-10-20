--liquibase formatted sql

--changeset alexermakov:1
ALTER TABLE users
    ADD COLUMN created_by VARCHAR(255);

--changeset alexermakov:2
ALTER TABLE users
    ADD COLUMN modified_by VARCHAR(255);