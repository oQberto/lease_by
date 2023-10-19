--liquibase formatted sql

--changeset alexermakov:1
ALTER TABLE users
    DROP COLUMN created_by;

--changeset alexermakov:2
ALTER TABLE users
    DROP COLUMN modified_by;