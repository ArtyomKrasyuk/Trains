--liquibase formatted sql

--changeset artyomkrasyuk:1
alter table carriage
add top_block_width smallint;

--changeset artyomkrasyuk:2
alter table carriage
add bottom_block_width smallint;

--changeset artyomkrasyuk:3
alter table place
drop column row_number;