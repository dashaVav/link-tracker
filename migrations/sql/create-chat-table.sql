--liquibase formatted sql
--changeset yourname:create-link-table

CREATE TABLE IF NOT EXISTS chats
(
    id      BIGINT,
    name    TEXT    NOT NULL,

    PRIMARY KEY (id)
);
