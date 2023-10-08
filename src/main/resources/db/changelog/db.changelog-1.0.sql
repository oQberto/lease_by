--liquibase formatted sql

--changeset alexermakov:1
CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    email    VARCHAR(256) UNIQUE NOT NULL,
    username VARCHAR(128) UNIQUE NOT NULL,
    password VARCHAR(128)        NOT NULL,
    role     VARCHAR(64)         NOT NULL DEFAULT 'USER'
);

--changeset alexermakov:2
CREATE TABLE IF NOT EXISTS profile
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT NOT NULL UNIQUE REFERENCES users (id),
    avatar_img   VARCHAR(256),
    first_name   VARCHAR(256),
    last_name    VARCHAR(256),
    phone_number VARCHAR(12)
);

--changeset alexermakov:3
CREATE TABLE IF NOT EXISTS city
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(128) NOT NULL,
    image VARCHAR(256) NOT NULL
);

--changeset alexermakov:4
CREATE TABLE IF NOT EXISTS street
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(256) NOT NULL,
    zip_code VARCHAR(255) NOT NULL
);

--changeset alexermakov:5
CREATE TABLE IF NOT EXISTS address
(
    id        BIGSERIAL PRIMARY KEY,
    city_id   BIGINT REFERENCES city (id),
    street_id BIGINT REFERENCES street (id),
    house_no  INT NOT NULL
);

--changeset alexermakov:6
CREATE TABLE IF NOT EXISTS rental
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT REFERENCES users (id),
    address_id  BIGINT  NOT NULL REFERENCES address (id),
    price       NUMERIC NOT NULL,
    description VARCHAR(520)
);

--changeset alexermakov:7
CREATE TABLE image
(
    id         BIGSERIAL PRIMARY KEY,
    rental_id  BIGINT UNIQUE       NOT NULL REFERENCES rental (id),
    image_path VARCHAR(520) UNIQUE NOT NULL
);

--changeset alexermakov:8
CREATE TABLE IF NOT EXISTS about
(
    id            BIGSERIAL PRIMARY KEY,
    rental_id     BIGINT NOT NULL UNIQUE REFERENCES rental (id),
    property_type VARCHAR(128),
    parking_type  VARCHAR(128),
    furnished     VARCHAR(128),
    lease_term    DATE   NOT NULL,
    short_term    DATE,
    year_built    DATE
);

--changeset alexermakov:9
CREATE TABLE IF NOT EXISTS amenities
(
    rental_id BIGINT REFERENCES rental (id),
    name      VARCHAR(128),
    PRIMARY KEY (rental_id, name)
);

--changeset alexermakov:10
CREATE TABLE IF NOT EXISTS feature
(
    rental_id BIGINT REFERENCES rental (id),
    name      VARCHAR(128),
    PRIMARY KEY (rental_id, name)
);