--liquibase formatted sql

--changeset alexermakov:1
ALTER TABLE rental
    ADD COLUMN bedrooms SMALLINT NOT NULL DEFAULT 1;

--changeset alexermakov:2
ALTER TABLE rental
    ADD COLUMN property_size DECIMAL NOT NULL DEFAULT 0.0;