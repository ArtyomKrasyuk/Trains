--liquibase formatted sql

--changeset artyomkrasyuk:1
alter table client
alter column password type text;