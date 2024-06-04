--liquibase formatted sql
--changeset sabine:1

CREATE TABLE flight
(
    id              INT      PRIMARY KEY,
    from_airport    VARCHAR(255) NOT NULL,
    to_airport      VARCHAR(255) NOT NULL,
    carrier         VARCHAR(255) NOT NULL,
    departure_time  TIMESTAMP    NOT NULL,
    arrival_time    TIMESTAMP    NOT NULL,
    CONSTRAINT fk_from_airport FOREIGN KEY (from_airport) REFERENCES airport(id),
    CONSTRAINT fk_to_airport FOREIGN KEY (to_airport) REFERENCES airport(id)
);
