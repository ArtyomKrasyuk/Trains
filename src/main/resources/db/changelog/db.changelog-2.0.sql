--liquibase formatted sql

--changeset artyomkrasyuk:1
CREATE TABLE IF NOT EXISTS passenger (
  passenger_id integer primary key,
  firstname VARCHAR(20),
  lastname VARCHAR(45),
  patronymic VARCHAR(20),
  email VARCHAR(254),
  phone VARCHAR(12),
  gender smallint,
  birthday date,
  passport_series_and_number VARCHAR(10)
);

--changeset artyomkrasyuk:2
alter table booking
add passenger_id integer references passenger(passenger_id);
alter table booking
drop column passport_series_and_number;
alter table booking
drop column passport_issue_date;
alter table booking
drop column who_issued;