CREATE SCHEMA auth;

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
                                password character varying(255) NOT NULL,
                                email character varying(255) NOT NULL,
                                salt character varying(255) NOT NULL,
                                auth_user_id uuid DEFAULT gen_random_uuid() NOT NULL,
                                inativo boolean DEFAULT false NOT NULL
);



CREATE TABLE auth.auth_user_permissao (
                                          auth_user_id uuid NOT NULL,
                                          permissao_id uuid NOT NULL
);


CREATE TABLE auth.cliente (
                              nome character varying(255) NOT NULL,
                              redirecionamento character varying(255) NOT NULL,
                              cliente_url character varying(255) NOT NULL,
                              secret character varying(255) DEFAULT gen_random_uuid() NOT NULL,
                              response_type character varying(255) DEFAULT 'code'::character varying NOT NULL,
                              cliente_id uuid DEFAULT gen_random_uuid() NOT NULL
);

CREATE TABLE auth.permissao (
                                nome character varying(255) NOT NULL,
                                permissao_id uuid DEFAULT gen_random_uuid() NOT NULL
);

CREATE TABLE auth.pessoas (
                              pessoa_id uuid DEFAULT gen_random_uuid() NOT NULL,
                              cpf character varying(255) NOT NULL,
                              dt_nasc date NOT NULL,
                              nome character varying(255) NOT NULL,
                              tenantid character varying(255),
                              auth_user_id uuid
);


COPY auth.auth_user (username, password, email, salt, auth_user_id, inativo) FROM stdin;
02365495028	bfDNUkZZ8aBIgSuxHg1w2CYEjr3NuQxpYdgkscC6EXyn0u48P0YvAQIt7Trx371lcn0DEUl3zsYSB/6bm5ivew==	darlan.felisberto@iffarroupilha.edu.br	H57a/ti0h9f2ujVlrEcnbQ==	bf874c2e-85a7-4e92-93c6-f0b9b4370d15	f
09603940613	R082x51R+gEIckn/GuOqY6htrUB8ONia5Th7Dk3IC7ghPAIXswiOC31+PWYgl+Q1tNdoJF+6uS/e4BJNFjNbqA==	a@b.c	H57a/ti0h9f2ujVlrEcnbQ==	99455dab-4a61-4cd7-ac2f-c2c9ff391188	f
\.

COPY auth.auth_user_permissao (auth_user_id, permissao_id) FROM stdin;
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	1fc1d1f7-59f0-4bbb-a903-df39ec898891
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	463d740f-b0fe-48fe-a65a-4ca8673c7ca4
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	e945e9ee-53f6-418f-bc58-831a2dcb9574
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	ed6818c4-ee4a-4e31-96a2-b8bdea3efdd3
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	3e8f7ac5-4372-4691-80e1-c90a6bd91912
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	b43c01af-875b-4c3a-83ab-cb669e9f940e
bf874c2e-85a7-4e92-93c6-f0b9b4370d15	a722cc9c-87e4-4ec4-8459-c6b46e8f9d02
\.


--
-- TOC entry 5030 (class 0 OID 17246)
-- Dependencies: 227
-- Data for Name: cliente; Type: TABLE DATA; Schema: auth; Owner: -
--

COPY auth.cliente (nome, redirecionamento, cliente_url, secret, response_type, cliente_id) FROM stdin;
angular	http://localhost:4200	http://localhost:4200	3fd70ff4-fe2b-47b6-a8a3-cd1cf281a937	TOKEN	1373b43d-76db-44ce-821c-f55d1e1dfa4f
testAuth	http://localhost:8080/testAuth	http://localhost:8080/testAuth	3fd70ff4-fe2b-47b6-a8a3-cd1cf281a937	CODE	9009d6ad-bf60-4f1d-a429-a054cf775e8c
sifw	http://localhost:8080/sifw	http://localhost:8080/sifw	3fd70ff4-fe2b-47b6-a8a3-cd1cf281a937	CODE	9009d6ad-bf60-4f1d-a429-a054cf775e8a
\.


--
-- TOC entry 5031 (class 0 OID 17254)
-- Dependencies: 228
-- Data for Name: permissao; Type: TABLE DATA; Schema: auth; Owner: -
--

COPY auth.permissao (nome, permissao_id) FROM stdin;
IFFAR_MORADIA	e945e9ee-53f6-418f-bc58-831a2dcb9574
IFFAR_RU_ADMIN	ed6818c4-ee4a-4e31-96a2-b8bdea3efdd3
IFFAR_RU_CREDITO	3e8f7ac5-4372-4691-80e1-c90a6bd91912
IFFAR_RU_CATRACA	b43c01af-875b-4c3a-83ab-cb669e9f940e
MANAGER	463d740f-b0fe-48fe-a65a-4ca8673c7ca4
IFFAR_RU	a722cc9c-87e4-4ec4-8459-c6b46e8f9d02
IFFAR_ADMIN	1fc1d1f7-59f0-4bbb-a903-df39ec898891
IFFAR_DOCENTE	4bb4c079-601b-474d-b5f4-5c19f6a3a7cf
IFFAR_MORADIA_CHAMADA	29279804-57b9-47ec-b07d-a68e1ffa93e5
IFFAR_USER_SEARCH	d3632b20-91d4-407c-a010-c1624754ffb5
\.


--
-- TOC entry 5032 (class 0 OID 17843)
-- Dependencies: 267
-- Data for Name: pessoas; Type: TABLE DATA; Schema: auth; Owner: -
--

COPY auth.pessoas (pessoa_id, cpf, dt_nasc, nome, tenantid, auth_user_id) FROM stdin;
cf874c2e-85a7-4e92-93c6-f0b9b4370d15	02365495028	1991-02-22	Darlan	feliva	bf874c2e-85a7-4e92-93c6-f0b9b4370d15
99455dab-4a61-4cd7-ac2f-c2c9ff391188	09603940613	1999-04-19	Ana	feliva	99455dab-4a61-4cd7-ac2f-c2c9ff391188
\.


--
-- TOC entry 4853 (class 2606 OID 17461)
-- Name: auth_login auth_login_pk; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_login
    ADD CONSTRAINT auth_login_pk PRIMARY KEY (auth_login_id);


--
-- TOC entry 4860 (class 2606 OID 17463)
-- Name: auth_user_permissao auth_user_permissao_un; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_user_permissao
    ADD CONSTRAINT auth_user_permissao_un UNIQUE (permissao_id, auth_user_id);


--
-- TOC entry 4856 (class 2606 OID 17465)
-- Name: auth_user auth_user_pk; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_user
    ADD CONSTRAINT auth_user_pk PRIMARY KEY (auth_user_id);


--
-- TOC entry 4862 (class 2606 OID 17467)
-- Name: cliente cliente_pk; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.cliente
    ADD CONSTRAINT cliente_pk PRIMARY KEY (cliente_id);


--
-- TOC entry 4864 (class 2606 OID 17830)
-- Name: cliente clientes_un; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.cliente
    ADD CONSTRAINT clientes_un UNIQUE (nome);


--
-- TOC entry 4866 (class 2606 OID 17471)
-- Name: permissao permissao_pk; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.permissao
    ADD CONSTRAINT permissao_pk PRIMARY KEY (permissao_id);


--
-- TOC entry 4868 (class 2606 OID 17841)
-- Name: permissao permissao_un; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.permissao
    ADD CONSTRAINT permissao_un UNIQUE (nome);


--
-- TOC entry 4870 (class 2606 OID 17849)
-- Name: pessoas pessoas_pkey; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.pessoas
    ADD CONSTRAINT pessoas_pkey PRIMARY KEY (pessoa_id);


--
-- TOC entry 4872 (class 2606 OID 26072)
-- Name: pessoas pessoas_unique_cpf; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.pessoas
    ADD CONSTRAINT pessoas_unique_cpf UNIQUE (cpf);


--
-- TOC entry 4858 (class 2606 OID 17477)
-- Name: auth_user username_unque; Type: CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_user
    ADD CONSTRAINT username_unque UNIQUE (username);


--
-- TOC entry 4854 (class 1259 OID 17564)
-- Name: auth_login_usado_code_idx; Type: INDEX; Schema: auth; Owner: -
--

CREATE INDEX auth_login_usado_code_idx ON auth.auth_login USING btree (usado, code);


--
-- TOC entry 4873 (class 2606 OID 17565)
-- Name: auth_login auth_login_aut_user_fk; Type: FK CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_login
    ADD CONSTRAINT auth_login_aut_user_fk FOREIGN KEY (auth_user_id) REFERENCES auth.auth_user(auth_user_id);


--
-- TOC entry 4874 (class 2606 OID 17570)
-- Name: auth_login auth_login_fk; Type: FK CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_login
    ADD CONSTRAINT auth_login_fk FOREIGN KEY (cliente_id) REFERENCES auth.cliente(cliente_id);


--
-- TOC entry 4875 (class 2606 OID 17575)
-- Name: auth_user_permissao auth_user_permissao_1_fk; Type: FK CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_user_permissao
    ADD CONSTRAINT auth_user_permissao_1_fk FOREIGN KEY (auth_user_id) REFERENCES auth.auth_user(auth_user_id);


--
-- TOC entry 4876 (class 2606 OID 17580)
-- Name: auth_user_permissao auth_user_permissao_fk; Type: FK CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.auth_user_permissao
    ADD CONSTRAINT auth_user_permissao_fk FOREIGN KEY (permissao_id) REFERENCES auth.permissao(permissao_id);


--
-- TOC entry 4877 (class 2606 OID 17852)
-- Name: pessoas fke35rof3jss53tmnk11qwb0ahk; Type: FK CONSTRAINT; Schema: auth; Owner: -
--

ALTER TABLE ONLY auth.pessoas
    ADD CONSTRAINT fke35rof3jss53tmnk11qwb0ahk FOREIGN KEY (auth_user_id) REFERENCES auth.auth_user(auth_user_id);


-- Completed on 2024-11-23 17:41:56

--
-- PostgreSQL database dump complete
--

