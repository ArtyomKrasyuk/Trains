--liquibase formatted sql

--changeset artyomkrasyuk:1
alter table booking
alter column ticket_number type text;