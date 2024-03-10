--liquibase formatted sql

CREATE TABLE IF NOT EXISTS link_chats
(
    link_id         BIGINT                     NOT NULL,
    chat_id         BIGINT                     NOT NULL,

    PRIMARY KEY (link_id, chat_id),
    FOREIGN KEY (link_id) REFERENCES links (id) ON DELETE CASCADE,
    FOREIGN KEY (chat_id) REFERENCES chats (id) ON DELETE CASCADE
);
