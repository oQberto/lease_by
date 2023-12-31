--liquibase formatted sql

--changeset alexermakov:1
ALTER TABLE chat_room
    ADD COLUMN status VARCHAR(64);