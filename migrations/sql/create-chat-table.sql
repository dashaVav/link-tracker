--liquibase formatted sql

CREATE TABLE IF NOT EXISTS chats
(
    id      BIGINT,
    name    TEXT    NOT NULL,

    PRIMARY KEY (id)
);
