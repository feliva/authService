
-- SET statement_timeout = 0;
-- SET lock_timeout = 0;
-- SET idle_in_transaction_session_timeout = 0;
-- SET client_encoding = 'UTF8';
-- SET standard_conforming_strings = on;
-- SELECT pg_catalog.set_config('search_path', '', false);
-- SET check_function_bodies = false;
-- SET xmloption = content;
-- SET client_min_messages = warning;
-- SET row_security = off;
--
-- DROP DATABASE base;
--
-- CREATE DATABASE auth WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
--
-- \connect base

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

CREATE SCHEMA auth;

SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE TABLE auth.auth_login (
                                 auth_user_id uuid NOT NULL,
                                 state uuid NOT NULL,
                                 code uuid NOT NULL,
                                 cliente_id uuid,
                                 valido_ate timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
                                 usado boolean DEFAULT true NOT NULL,
                                 auth_login_id uuid DEFAULT gen_random_uuid() NOT NULL,
                                 ativo boolean DEFAULT true NOT NULL
);

CREATE TABLE auth.auth_user (
                                username character varying(30) NOT NULL,
                                password character varying(500) NOT NULL,
                                email character varying(100) NOT NULL,
                                salt character varying(100) NOT NULL,
                                auth_user_id uuid DEFAULT gen_random_uuid() NOT NULL,
                                inativo boolean DEFAULT false NOT NULL
);

CREATE TABLE auth.auth_user_permissao (
                                          auth_user_id uuid NOT NULL,
                                          permissao_id uuid NOT NULL
);

CREATE TABLE auth.cliente (
                              nome character varying(50) NOT NULL,
                              redirecionamento character varying(100) NOT NULL,
                              cliente_url character varying(100) NOT NULL,
                              secret character varying(100) DEFAULT gen_random_uuid() NOT NULL,
                              response_type character varying DEFAULT 'code'::character varying NOT NULL,
                              cliente_id uuid DEFAULT gen_random_uuid() NOT NULL
);

CREATE TABLE auth.permissao (
                                nome character varying(30) NOT NULL,
                                permissao_id uuid DEFAULT gen_random_uuid() NOT NULL
);

COPY auth.auth_user (username, password, email, salt, auth_user_id, inativo) FROM stdin;
12345678910	ZFpzgxDpcmnqieEX/IrsLKWgHHQzYRX7E2aekM5kHaywgOPYlr30hFwOgUsmN2HiqUBEedmrJEaqgc23C0xyfQ==	12345678910@12345678910.com	/jZc6DI/uiKk0sA+ytAE+Q==	bc939748-2889-4097-9f9c-457b00d08558	f
02365495028	bfDNUkZZ8aBIgSuxHg1w2CYEjr3NuQxpYdgkscC6EXyn0u48P0YvAQIt7Trx371lcn0DEUl3zsYSB/6bm5ivew==	darlanfelisberto@gmail.com	H57a/ti0h9f2ujVlrEcnbQ==	bf874c2e-85a7-4e92-93c6-f0b9b4370d15	f
\.

COPY auth.auth_user_permissao (auth_user_id, permissao_id) FROM stdin;
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	1fc1d1f7-59f0-4bbb-a903-df39ec898891
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	463d740f-b0fe-48fe-a65a-4ca8673c7ca4
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	e945e9ee-53f6-418f-bc58-831a2dcb9574
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	ed6818c4-ee4a-4e31-96a2-b8bdea3efdd3
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	3e8f7ac5-4372-4691-80e1-c90a6bd91912
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	b43c01af-875b-4c3a-83ab-cb669e9f940e
bc939748-2889-4097-9f9c-457b00d08558	463d740f-b0fe-48fe-a65a-4ca8673c7ca4
\.

COPY auth.cliente (nome, redirecionamento, cliente_url, secret, response_type, cliente_id) FROM stdin;
angular	http://localhost:4200	http://localhost:4200	3fd70ff4-fe2b-47b6-a8a3-cd1cf281a937	TOKEN	1373b43d-76db-44ce-821c-f55d1e1dfa4f
testAuth	http://localhost:8080/testAuth	http://localhost:8080/testAuth	3fd70ff4-fe2b-47b6-a8a3-cd1cf281a937	CODE	9009d6ad-bf60-4f1d-a429-a054cf775e8c
\.

COPY auth.permissao (nome, permissao_id) FROM stdin;
IFFAR_MORADIA	e945e9ee-53f6-418f-bc58-831a2dcb9574
IFFAR_RU_ADMIN	ed6818c4-ee4a-4e31-96a2-b8bdea3efdd3
IFFAR_RU_CREDITO	3e8f7ac5-4372-4691-80e1-c90a6bd91912
IFFAR_RU_CATRACA	b43c01af-875b-4c3a-83ab-cb669e9f940e
MANAGER	463d740f-b0fe-48fe-a65a-4ca8673c7ca4
ADMIN	1fc1d1f7-59f0-4bbb-a903-df39ec898891
\.

ALTER TABLE ONLY auth.auth_login
    ADD CONSTRAINT auth_login_pk PRIMARY KEY (auth_login_id);

ALTER TABLE ONLY auth.auth_user_permissao
    ADD CONSTRAINT auth_user_permissao_un UNIQUE (permissao_id, auth_user_id);

ALTER TABLE ONLY auth.auth_user
    ADD CONSTRAINT auth_user_pk PRIMARY KEY (auth_user_id);

ALTER TABLE ONLY auth.cliente
    ADD CONSTRAINT cliente_pk PRIMARY KEY (cliente_id);

ALTER TABLE ONLY auth.cliente
    ADD CONSTRAINT clientes_un UNIQUE (nome);

ALTER TABLE ONLY auth.permissao
    ADD CONSTRAINT permissao_pk PRIMARY KEY (permissao_id);

ALTER TABLE ONLY auth.permissao
    ADD CONSTRAINT permissao_un UNIQUE (nome);

ALTER TABLE ONLY auth.auth_user
    ADD CONSTRAINT usuario_un UNIQUE (username);

CREATE INDEX auth_login_usado_code_idx ON auth.auth_login USING btree (usado, code);

ALTER TABLE ONLY auth.auth_login
    ADD CONSTRAINT auth_login_aut_user_fk FOREIGN KEY (auth_user_id) REFERENCES auth.auth_user(auth_user_id);

ALTER TABLE ONLY auth.auth_login
    ADD CONSTRAINT auth_login_fk FOREIGN KEY (cliente_id) REFERENCES auth.cliente(cliente_id);

ALTER TABLE ONLY auth.auth_user_permissao
    ADD CONSTRAINT auth_user_permissao_1_fk FOREIGN KEY (auth_user_id) REFERENCES auth.auth_user(auth_user_id);

ALTER TABLE ONLY auth.auth_user_permissao
    ADD CONSTRAINT auth_user_permissao_fk FOREIGN KEY (permissao_id) REFERENCES auth.permissao(permissao_id);
