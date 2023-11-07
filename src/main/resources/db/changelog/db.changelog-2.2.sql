--liquibase formatted sql

--changeset alexermakov:1
ALTER TABLE rental
    ADD COLUMN created_at TIMESTAMP;

ALTER TABLE rental
    ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE rental
    ADD COLUMN created_by VARCHAR(32);

ALTER TABLE rental
    ADD COLUMN modified_by VARCHAR(32);

--changeset alexermakov:2
ALTER TABLE rental_details
    ADD COLUMN created_at TIMESTAMP;

ALTER TABLE rental_details
    ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE rental_details
    ADD COLUMN created_by VARCHAR(32);

ALTER TABLE rental_details
    ADD COLUMN modified_by VARCHAR(32);

--changeset alexermakov:3
CREATE TABLE IF NOT EXISTS rental_aud
(
    id          BIGINT,
    rev         INT REFERENCES revision (id),
    revtype     SMALLINT,
    user_id     BIGINT REFERENCES users (id),
    address_id  BIGINT  NOT NULL REFERENCES address (id),
    price       NUMERIC NOT NULL,
    description VARCHAR(520),
    PRIMARY KEY (id, rev)
);

--changeset alexermakov:4
CREATE TABLE IF NOT EXISTS rental_details_aud
(
    id            BIGINT,
    rev           INT REFERENCES revision (id),
    revtype       SMALLINT,
    rental_id     BIGINT NOT NULL UNIQUE REFERENCES rental (id),
    property_type VARCHAR(128),
    parking_type  VARCHAR(128),
    furnished     VARCHAR(128),
    lease_term    VARCHAR(128),
    short_term    BOOLEAN,
    pet_friendly  BOOLEAN,
    year_built    DATE
);