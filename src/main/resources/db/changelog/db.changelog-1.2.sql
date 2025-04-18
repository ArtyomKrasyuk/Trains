--liquibase formatted sql

--changeset artyomkrasyuk:1
alter table client
add birthday date;