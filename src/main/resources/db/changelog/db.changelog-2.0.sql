--liquibase formatted sql

--changeset alexermakov:1
DROP TABLE image;

--changeset alexermakov:2
CREATE TABLE IF NOT EXISTS image
(
    rental_id BIGINT REFERENCES rental (id),
    path      VARCHAR(520) UNIQUE,
    bucket    VARCHAR(64),
    PRIMARY KEY (rental_id, path)
)