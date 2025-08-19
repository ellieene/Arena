--liquibase formatted sql

-- changeset ellieene:edit-user
ALTER TABLE users ADD COLUMN description VARCHAR(255);

-- rollback ALTER TABLE users DROP COLUMN description;