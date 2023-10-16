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
    id         BIGINT,
    rev        INT REFERENCES revision (id),
    revtype    SMALLINT,
    email      VARCHAR(128) NOT NULL UNIQUE,
    username   VARCHAR(64)  NOT NULL UNIQUE,
    password   VARCHAR(32),
    role       VARCHAR(32),
    company_id INT
);