--liquibase formatted sql

--changeset alexermakov:1
CREATE TABLE IF NOT EXISTS password_token
(
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT REFERENCES users(id) ,
    token VARCHAR(1024) ,
    expire_date TIMESTAMP
);