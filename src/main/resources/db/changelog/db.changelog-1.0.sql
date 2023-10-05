--liquibase formatted sql

--changeset alexermakov:1
CREATE TABLE IF NOT EXISTS profile
(
    id           BIGSERIAL PRIMARY KEY,
    avatar_img   VARCHAR(256),
    first_name   VARCHAR(256),
    last_name    VARCHAR(256),
    phone_number VARCHAR(128)
);

--changeset alexermakov:2
CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    profile_id BIGINT REFERENCES profile (id) ON DELETE CASCADE ,
    email      VARCHAR(256) UNIQUE NOT NULL,
    username   VARCHAR(128) UNIQUE NOT NULL,
    password   VARCHAR(128)        NOT NULL
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
    zip_code BIGINT       NOT NULL
);

--changeset alexermakov:5
CREATE TABLE IF NOT EXISTS address
(
    id           BIGSERIAL PRIMARY KEY,
    city_id      BIGINT REFERENCES city (id),
    street_id    BIGINT REFERENCES street (id),
    house_no     BIGINT NOT NULL,
    apartment_no BIGINT NOT NULL
);

--changeset alexermakov:6
CREATE TABLE IF NOT EXISTS about
(
    id            BIGSERIAL PRIMARY KEY,
    property_type VARCHAR(128),
    parking_type  VARCHAR(128),
    lease_term    DATE NOT NULL,
    short_term    DATE,
    furnished     VARCHAR(128),
    year_built    DATE NOT NULL
);

--changeset alexermakov:7
CREATE TABLE IF NOT EXISTS rental
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT REFERENCES users (id),
    address_id  BIGINT REFERENCES address (id),
    about_id    BIGINT REFERENCES about (id) ON DELETE CASCADE ,
    images      VARCHAR(1024) NOT NULL,
    price       NUMERIC       NOT NULL,
    description VARCHAR(520)
);

--changeset alexermakov:8
CREATE TABLE IF NOT EXISTS amenities
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(128)
);

--changeset alexermakov:9
CREATE TABLE IF NOT EXISTS feature
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(128)
);

--changeset alexermakov:10
CREATE TABLE IF NOT EXISTS rental_amenities
(
    id           BIGSERIAL PRIMARY KEY,
    rental_id    BIGINT REFERENCES rental (id),
    amenities_id BIGINT REFERENCES amenities (id)
);

--changeset alexermakov:11
CREATE TABLE IF NOT EXISTS rental_feature
(
    id         BIGSERIAL PRIMARY KEY,
    rental_id  BIGINT REFERENCES rental (id),
    feature_id BIGINT REFERENCES feature (id)
);