--liquibase formatted sql

-- changeset ellieene:create-database-waiting
CREATE TABLE waiting_users (
   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   username VARCHAR(255) NOT NULL UNIQUE,
   lvl INT NOT NULL,
   joined_at BIGINT NOT NULL
);

-- changeset ellieene:create-database-fight
CREATE TABLE fights (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    players_one VARCHAR(255) NOT NULL,
    players_two VARCHAR(255) NOT NULL,
    exercise VARCHAR(255) NOT NULL UNIQUE,
    created_at BIGINT NOT NULL,
    winner VARCHAR(255)
);

-- rollback DROP TABLE fights;
-- rollback DROP TABLE waiting_users;