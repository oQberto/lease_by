--liquibase formatted sql

--changeset alexermakov:1
CREATE TABLE IF NOT EXISTS revision
(
    id        SERIAL PRIMARY KEY,
    timestamp BIGINT NOT NULL
);

--changeset alexermakov:2
CREATE TABLE IF NOT EXISTS users_aud
(
    id       BIGINT,
    rev      INT REFERENCES revision (id),
    revtype  SMALLINT,
    email    VARCHAR(256) NOT NULL,
    username VARCHAR(256) NOT NULL,
    password VARCHAR(256),
    role     VARCHAR(64),
    PRIMARY KEY (id, rev)
);

--changeset alexermakov:3
ALTER TABLE users
    ALTER COLUMN username TYPE VARCHAR(256);

--changeset alexermakov:4
ALTER TABLE users
    ALTER COLUMN password TYPE VARCHAR(256);

--changeset alexermakov:5
ALTER TABLE profile
    ALTER COLUMN phone_number TYPE VARCHAR(24);