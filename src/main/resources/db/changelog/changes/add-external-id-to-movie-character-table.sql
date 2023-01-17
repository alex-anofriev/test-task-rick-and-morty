--liquibase formatted sql
--changeset <postgres>:<create-movie-character-sequence-id>

ALTER TABLE public.movie_character ADD external_id bigint;

--rollback ALTER TABLE DROP COLUMN external_id;