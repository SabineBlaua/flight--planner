--liquibase formatted sql
--changeset sabine:1

CREATE TABLE flight
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    from_id        INT NOT NULL,
    to_id          INT NOT NULL,
    carrier        VARCHAR(255) NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time   TIMESTAMP NOT NULL,
    CONSTRAINT fk_from_airport FOREIGN KEY (from_id) REFERENCES airport (id),
    CONSTRAINT fk_to_airport FOREIGN KEY (to_id) REFERENCES airport (id)
);