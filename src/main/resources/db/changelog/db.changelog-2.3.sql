--liquibase formatted sql

--changeset alexermakov:1
ALTER TABLE rental
ADD COLUMN intro_image VARCHAR(128)