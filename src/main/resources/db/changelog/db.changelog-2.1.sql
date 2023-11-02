--liquibase formatted sql

--changeset alexermakov:1
ALTER TABLE image
    DROP COLUMN bucket;