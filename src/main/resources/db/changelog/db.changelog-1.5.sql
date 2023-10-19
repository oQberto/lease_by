--liquibase formatted sql

--changeset alexermakov:1
ALTER TABLE users_aud
    ALTER COLUMN email TYPE VARCHAR (256);

--changeset alexermakov:2
ALTER TABLE users_aud
    ALTER COLUMN username TYPE VARCHAR (128);

--changeset alexermakov:3
ALTER TABLE users_aud
    ALTER COLUMN password TYPE VARCHAR (128);

--changeset alexermakov:4
ALTER TABLE users_aud
    ALTER COLUMN role TYPE VARCHAR (64);