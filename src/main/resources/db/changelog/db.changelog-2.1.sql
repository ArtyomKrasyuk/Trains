--liquibase formatted sql

--changeset artyomkrasyuk:1
alter table booking
add ticket_number integer unique;