--liquibase formatted sql

--changeset artyomkrasyuk:1
CREATE TABLE IF NOT EXISTS client (
  client_id integer primary key,
  firstname VARCHAR(20),
  lastname VARCHAR(45),
  patronymic VARCHAR(20),
  login VARCHAR(254),
  password VARCHAR(15),
  phone VARCHAR(12),
  role VARCHAR(45),
  gender smallint
);

--changeset artyomkrasyuk:2
CREATE TABLE IF NOT EXISTS train (
  train_id integer primary key,
  train_name VARCHAR(45)
);

--changeset artyomkrasyuk:3
CREATE TABLE IF NOT EXISTS city (
  city_id integer primary key,
  city_name VARCHAR(45),
  range_factor double precision
);

--changeset artyomkrasyuk:4
CREATE TABLE IF NOT EXISTS carriage_type (
  carriage_type_id integer primary key,
  type_name VARCHAR(45),
  place_price double precision
);

--changeset artyomkrasyuk:5
CREATE TABLE IF NOT EXISTS trip (
  trip_id integer primary key,
  train_id integer references train(train_id),
  destination integer references city(city_id),
  departure_time timestamp(6) without time zone,
  arrival_time timestamp(6) without time zone
);

--changeset artyomkrasyuk:6
CREATE TABLE IF NOT EXISTS carriage (
  carriage_id integer primary key,
  train_id integer references train(train_id),
  type integer references carriage_type(carriage_type_id),
  number_of_seats integer,
  carriage_number smallint
);

--changeset artyomkrasyuk:7
CREATE TABLE IF NOT EXISTS place (
  place_id integer primary key,
  carriage_id integer references carriage(carriage_id),
  passenger_gender smallint,
  comfort_factor double precision,
  position smallint,
  row_number smallint
);

--changeset artyomkrasyuk:8
CREATE TABLE IF NOT EXISTS booking (
  booking_id integer primary key,
  client_id integer references client(client_id),
  place_id integer references place(place_id),
  trip_id integer references trip(trip_id),
  passport_series_and_number VARCHAR(10),
  passport_issue_date DATE,
  who_issued VARCHAR(100),
  price double precision
);