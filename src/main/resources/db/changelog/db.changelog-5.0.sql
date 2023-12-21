--liquibase formatted sql

--changeset alexermakov:1
ALTER TABLE users
    ADD COLUMN network_status VARCHAR(64);

--changeset alexermakov:2
CREATE TABLE chat_room
(
    id           BIGSERIAL PRIMARY KEY,
    sender_id    BIGINT NOT NULL REFERENCES users (id),
    recipient_id BIGINT NOT NULL REFERENCES users (id)
);

--changeset alexermakov:3
CREATE TABLE chat_message
(
    id           BIGSERIAL PRIMARY KEY,
    chat_id      BIGINT        NOT NULL REFERENCES chat_room (id),
    sender_id    BIGINT        NOT NULL REFERENCES users (id),
    recipient_id BIGINT        NOT NULL REFERENCES users (id),
    content      VARCHAR(1024) NOT NULL,
    sending_time TIMESTAMP     NOT NULL
);