--liquibase formatted sql

CREATE TABLE IF NOT EXISTS link_chat
(
    linkId         BIGINT                     NOT NULL,
    chatId         BIGINT                     NOT NULL,

    PRIMARY KEY (linkId, chatId),
    FOREIGN KEY (linkId) REFERENCES links (id) ON DELETE CASCADE,
    FOREIGN KEY (chatId) REFERENCES chats (id) ON DELETE CASCADE
);
