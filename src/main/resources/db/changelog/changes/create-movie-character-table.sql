--liquibase formatted sql
--changeset <postgres>:<create-movie-character-sequence-id>

CREATE TABLE IF NOT EXISTS public.movie_character
(
    id bigint NOT NULL DEFAULT nextval('movie_character_id_seq'),
    name character varying(256) NOT NULL,
    status character varying(256) NOT NULL,
    gender character varying(256) NOT NULL,
    CONSTRAINT movie_character_pk PRIMARY KEY (id)
);

--rollback DROP TABLE movie_character