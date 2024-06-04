--liquibase formatted sql
--changeset sabine:1

CREATE TABLE airport
(
    id      INT         PRIMARY KEY,
    country VARCHAR(255) NOT NULL,
    city    VARCHAR(255) NOT NULL,
    airport VARCHAR(255) NOT NULL
);
