--liquibase formatted sql

CREATE TABLE IF NOT EXISTS links
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY,
    url             TEXT                     NOT NULL,
    checked_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    UNIQUE (url)
);
