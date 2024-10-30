--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

-- Started on 2024-10-22 22:04:14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 9 (class 2615 OID 17162)
-- Name: auth; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA auth;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 224 (class 1259 OID 17229)
-- Name: auth_login; Type: TABLE; Schema: auth; Owner: -
--

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


--
-- TOC entry 225 (class 1259 OID 17236)
-- Name: auth_user; Type: TABLE; Schema: auth; Owner: -
--

CREATE TABLE auth.auth_user (
                                username character varying(30) NOT NULL,
                                password character varying(255) NOT NULL,
                                email character varying(255) NOT NULL,
                                salt character varying(255) NOT NULL,
                                auth_user_id uuid DEFAULT gen_random_uuid() NOT NULL,
                                inativo boolean DEFAULT false NOT NULL
);


--
-- TOC entry 226 (class 1259 OID 17243)
-- Name: auth_user_permissao; Type: TABLE; Schema: auth; Owner: -
--

CREATE TABLE auth.auth_user_permissao (
                                          auth_user_id uuid NOT NULL,
                                          permissao_id uuid NOT NULL
);


--
-- TOC entry 227 (class 1259 OID 17246)
-- Name: cliente; Type: TABLE; Schema: auth; Owner: -
--

CREATE TABLE auth.cliente (
                              nome character varying(255) NOT NULL,
                              redirecionamento character varying(255) NOT NULL,
                              cliente_url character varying(255) NOT NULL,
                              secret character varying(255) DEFAULT gen_random_uuid() NOT NULL,
                              response_type character varying(255) DEFAULT 'code'::character varying NOT NULL,
                              cliente_id uuid DEFAULT gen_random_uuid() NOT NULL
);


--
-- TOC entry 228 (class 1259 OID 17254)
-- Name: permissao; Type: TABLE; Schema: auth; Owner: -
--

CREATE TABLE auth.permissao (
                                nome character varying(255) NOT NULL,
                                permissao_id uuid DEFAULT gen_random_uuid() NOT NULL
);


--
-- TOC entry 271 (class 1259 OID 17843)
-- Name: pessoas; Type: TABLE; Schema: auth; Owner: -
--

CREATE TABLE auth.pessoas (
                              pessoa_id uuid NOT NULL,
                              cpf character varying(255) NOT NULL,
                              dt_nasc date NOT NULL,
                              nome character varying(255) NOT NULL,
                              tenantid character varying(255),
                              auth_user_id uuid
);


--
-- TOC entry 5030 (class 0 OID 17229)
-- Dependencies: 224
-- Data for Name: auth_login; Type: TABLE DATA; Schema: auth; Owner: -
--

COPY auth.auth_login (auth_user_id, state, code, cliente_id, valido_ate, usado, auth_login_id, ativo) FROM stdin;
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	73a8fa33-07a1-4481-8aad-26a409f4b74a	5b7f9d20-8e97-4be8-9b78-ed3f201c0223	9009d6ad-bf60-4f1d-a429-a054cf775e8c	2024-10-21 09:58:03.374962	t	a48010ea-e6d5-4050-a020-a168686d13d1	t
\.


--
-- TOC entry 5031 (class 0 OID 17236)
-- Dependencies: 225
-- Data for Name: auth_user; Type: TABLE DATA; Schema: auth; Owner: -
--

COPY auth.auth_user (username, password, email, salt, auth_user_id, inativo) FROM stdin;
12345678910	ZFpzgxDpcmnqieEX/IrsLKWgHHQzYRX7E2aekM5kHaywgOPYlr30hFwOgUsmN2HiqUBEedmrJEaqgc23C0xyfQ==	12345678910@12345678910.com	/jZc6DI/uiKk0sA+ytAE+Q==	bc939748-2889-4097-9f9c-457b00d08558	f
02365495028	bfDNUkZZ8aBIgSuxHg1w2CYEjr3NuQxpYdgkscC6EXyn0u48P0YvAQIt7Trx371lcn0DEUl3zsYSB/6bm5ivew==	darlan.felisberto@iffarroupilha.edu.br	H57a/ti0h9f2ujVlrEcnbQ==	bf874c2e-85a7-4e92-93c6-f0b9b4370d15	f
\.


--
-- TOC entry 5032 (class 0 OID 17243)
-- Dependencies: 226
-- Data for Name: auth_user_permissao; Type: TABLE DATA; Schema: auth; Owner: -
--

COPY auth.auth_user_permissao (auth_user_id, permissao_id) FROM stdin;
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	1fc1d1f7-59f0-4bbb-a903-df39ec898891
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	463d740f-b0fe-48fe-a65a-4ca8673c7ca4
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	e945e9ee-53f6-418f-bc58-831a2dcb9574
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	ed6818c4-ee4a-4e31-96a2-b8bdea3efdd3
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	3e8f7ac5-4372-4691-80e1-c90a6bd91912
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	b43c01af-875b-4c3a-83ab-cb669e9f940e
bc939748-2889-4097-9f9c-457b00d08558	463d740f-b0fe-48fe-a65a-4ca8673c7ca4
\.


--
-- TOC entry 5033 (class 0 OID 17246)
-- Dependencies: 227
-- Data for Name: cliente; Type: TABLE DATA; Schema: auth; Owner: -
--

COPY auth.cliente (nome, redirecionamento, cliente_url, secret, response_type, cliente_id) FROM stdin;
angular	http://localhost:4200	http://localhost:4200	3fd70ff4-fe2b-47b6-a8a3-cd1cf281a937	TOKEN	1373b43d-76db-44ce-821c-f55d1e1dfa4f
testAuth	http://localhost:8080/testAuth	http://localhost:8080/testAuth	3fd70ff4-fe2b-47b6-a8a3-cd1cf281a937	CODE	9009d6ad-bf60-4f1d-a429-a054cf775e8c
\.


INSERT INTO auth.cliente (nome, redirecionamento, cliente_url, secret, response_type, cliente_id)
VALUES('sifw', 'http://localhost:8080/sifw', 'http://localhost:8080/sifw', '3fd70ff4-fe2b-47b6-a8a3-cd1cf281a937', 'CODE', '9009d6ad-bf60-4f1d-a429-a054cf775e8a'::uuid);

--
-- TOC entry 5034 (class 0 OID 17254)
-- Dependencies: 228
-- Data for Name: permissao; Type: TABLE DATA; Schema: auth; Owner: -
--

COPY auth.permissao (nome, permissao_id) FROM stdin;
IFFAR_MORADIA	e945e9ee-53f6-418f-bc58-831a2dcb9574
IFFAR_RU_ADMIN	ed6818c4-ee4a-4e31-96a2-b8bdea3efdd3
IFFAR_RU_CREDITO	3e8f7ac5-4372-4691-80e1-c90a6bd91912
IFFAR_RU_CATRACA	b43c01af-875b-4c3a-83ab-cb669e9f940e
MANAGER	463d740f-b0fe-48fe-a65a-4ca8673c7ca4
ADMIN	1fc1d1f7-59f0-4bbb-a903-df39ec898891
\.


--
-- TOC entry 5035 (class 0 OID 17843)
-- Dependencies: 271
-- Data for Name: pessoas; Type: TABLE DATA; Schema: auth; Owner: -
--

COPY auth.pessoas (pessoa_id, cpf, dt_nasc, nome, tenantid, auth_user_id) FROM stdin;
cf874c2e-85a7-4e92-93c6-f0b9b4370d15	02365495028	1991-02-22	darlan	feliva	bf874c2e-85a7-4e92-93c6-f0b9b4370d15
\.


--
-- TOC entry 4856 (class 2606 OID 17461)
-- Name: auth_login auth_login_pk; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_login
    ADD CONSTRAINT auth_login_pk PRIMARY KEY (auth_login_id);


--
-- TOC entry 4863 (class 2606 OID 17463)
-- Name: auth_user_permissao auth_user_permissao_un; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_user_permissao
    ADD CONSTRAINT auth_user_permissao_un UNIQUE (permissao_id, auth_user_id);


--
-- TOC entry 4859 (class 2606 OID 17465)
-- Name: auth_user auth_user_pk; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_user
    ADD CONSTRAINT auth_user_pk PRIMARY KEY (auth_user_id);


--
-- TOC entry 4865 (class 2606 OID 17467)
-- Name: cliente cliente_pk; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.cliente
    ADD CONSTRAINT cliente_pk PRIMARY KEY (cliente_id);


--
-- TOC entry 4867 (class 2606 OID 17830)
-- Name: cliente clientes_un; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.cliente
    ADD CONSTRAINT clientes_un UNIQUE (nome);


--
-- TOC entry 4869 (class 2606 OID 17471)
-- Name: permissao permissao_pk; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.permissao
    ADD CONSTRAINT permissao_pk PRIMARY KEY (permissao_id);


--
-- TOC entry 4871 (class 2606 OID 17841)
-- Name: permissao permissao_un; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.permissao
    ADD CONSTRAINT permissao_un UNIQUE (nome);


--
-- TOC entry 4873 (class 2606 OID 17849)
-- Name: pessoas pessoas_pkey; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.pessoas
    ADD CONSTRAINT pessoas_pkey PRIMARY KEY (pessoa_id);


--
-- TOC entry 4875 (class 2606 OID 17851)
-- Name: pessoas uk7tc4dvyrblo5u6wh5en7190kh; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.pessoas
    ADD CONSTRAINT uk7tc4dvyrblo5u6wh5en7190kh UNIQUE (auth_user_id);


--
-- TOC entry 4861 (class 2606 OID 17477)
-- Name: auth_user usuario_un; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_user
    ADD CONSTRAINT usuario_un UNIQUE (username);


--
-- TOC entry 4857 (class 1259 OID 17564)
-- Name: auth_login_usado_code_idx; Type: INDEX; Schema: auth; Owner: -
--

CREATE INDEX auth_login_usado_code_idx ON auth.auth_login USING btree (usado, code);


--
-- TOC entry 4876 (class 2606 OID 17565)
-- Name: auth_login auth_login_aut_user_fk; Type: FK CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_login
    ADD CONSTRAINT auth_login_aut_user_fk FOREIGN KEY (auth_user_id) REFERENCES auth.auth_user(auth_user_id);


--
-- TOC entry 4877 (class 2606 OID 17570)
-- Name: auth_login auth_login_fk; Type: FK CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_login
    ADD CONSTRAINT auth_login_fk FOREIGN KEY (cliente_id) REFERENCES auth.cliente(cliente_id);


--
-- TOC entry 4878 (class 2606 OID 17575)
-- Name: auth_user_permissao auth_user_permissao_1_fk; Type: FK CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_user_permissao
    ADD CONSTRAINT auth_user_permissao_1_fk FOREIGN KEY (auth_user_id) REFERENCES auth.auth_user(auth_user_id);


--
-- TOC entry 4879 (class 2606 OID 17580)
-- Name: auth_user_permissao auth_user_permissao_fk; Type: FK CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_user_permissao
    ADD CONSTRAINT auth_user_permissao_fk FOREIGN KEY (permissao_id) REFERENCES auth.permissao(permissao_id);


--
-- TOC entry 4880 (class 2606 OID 17852)
-- Name: pessoas fke35rof3jss53tmnk11qwb0ahk; Type: FK CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.pessoas
    ADD CONSTRAINT fke35rof3jss53tmnk11qwb0ahk FOREIGN KEY (auth_user_id) REFERENCES auth.auth_user(auth_user_id);


-- Completed on 2024-10-22 22:04:14

--
-- PostgreSQL database dump complete
--

