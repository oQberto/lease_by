--liquibase formatted sql

--changeset alexermakov:1
ALTER TABLE rental_details
    DROP COLUMN lease_term;

ALTER TABLE rental_details
    ADD COLUMN lease_term VARCHAR(128);

--changeset alexermakov:2
ALTER TABLE rental_details
    DROP COLUMN short_term;

ALTER TABLE rental_details
    ADD COLUMN short_term BOOLEAN;

--changeset alexermakov:3
ALTER TABLE rental_details
    ADD COLUMN pet_friendly BOOLEAN;

--changeset alexermakov:4
CREATE TABLE IF NOT EXISTS utility
(
    rental_details_id BIGINT REFERENCES rental_details(id),
    name VARCHAR(256),
    PRIMARY KEY (rental_details_id, name)
);

--changeset alexermakov:5
CREATE TABLE IF NOT EXISTS category
(
    rental_details_id BIGINT REFERENCES rental_details(id),
    name VARCHAR(256),
    PRIMARY KEY (rental_details_id, name)
);