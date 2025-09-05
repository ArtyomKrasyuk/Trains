--liquibase formatted sql

--changeset artyomkrasyuk:1
alter table booking alter column booking_id add generated always as identity;

--changeset artyomkrasyuk:2
alter table carriage alter column carriage_id add generated always as identity;

--changeset artyomkrasyuk:3
alter table carriage_type alter column carriage_type_id add generated always as identity;
insert into carriage_type(type_name, place_price) values('Плацкарт', 1500);
insert into carriage_type(type_name, place_price) values('Купе', 3000);
insert into carriage_type(type_name, place_price) values('СВ', 6000);
insert into carriage_type(type_name, place_price) values('Сидячий', 300);

--changeset artyomkrasyuk:4
alter table city alter column city_id add generated always as identity;

--changeset artyomkrasyuk:5
alter table client alter column client_id add generated always as identity;

--changeset artyomkrasyuk:6
alter table passenger alter column passenger_id add generated always as identity;

--changeset artyomkrasyuk:7
alter table place alter column place_id add generated always as identity;

--changeset artyomkrasyuk:8
alter table trip alter column trip_id add generated always as identity;