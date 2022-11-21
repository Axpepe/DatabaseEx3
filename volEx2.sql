--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1
-- Dumped by pg_dump version 14.1

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: bitllet; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bitllet (
    id character varying(9) NOT NULL,
    id_vol character varying(9),
    tipus_seient character varying(20),
    preu numeric,
    max_px integer
);


ALTER TABLE public.bitllet OWNER TO postgres;

--
-- Name: client; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.client (
    dni character varying(9) NOT NULL,
    nom character varying(20),
    cognom character varying(50),
    mail character varying(60),
    telefon character varying(15),
    data_naixement date
);


ALTER TABLE public.client OWNER TO postgres;

--
-- Name: equipatge; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.equipatge (
    id_equipatge character varying(9) NOT NULL,
    num_factura integer,
    linea_factura integer,
    num_bultos integer,
    pes_kilos numeric
);


ALTER TABLE public.equipatge OWNER TO postgres;

--
-- Name: estacio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.estacio (
    id character varying(9) NOT NULL,
    nom character varying(50)
);


ALTER TABLE public.estacio OWNER TO postgres;

--
-- Name: factura; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.factura (
    num_factura integer NOT NULL,
    dni character varying(9),
    total numeric,
    data_compra date
);


ALTER TABLE public.factura OWNER TO postgres;

--
-- Name: nlinea; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nlinea (
    num_factura integer NOT NULL,
    id_bitllet character varying(9),
    preu numeric
);


ALTER TABLE public.nlinea OWNER TO postgres;

--
-- Name: vol; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vol (
    id character varying(9) NOT NULL,
    id_origen character varying(9),
    id_desti character varying(9),
    durada time without time zone,
    dia date,
    hora time without time zone,
    max_ps integer,
    max_kg numeric,
    hora_arribada time without time zone
);


ALTER TABLE public.vol OWNER TO postgres;

--
-- Data for Name: bitllet; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.bitllet (id, id_vol, tipus_seient, preu, max_px) FROM stdin;
2	6	Normal	90.0	48
3	3	Luxe	900.0	27
4	7	Luxe	500.0	49
1	3	Luxe	500.0	17
\.


--
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.client (dni, nom, cognom, mail, telefon, data_naixement) FROM stdin;
12315678E	Axel	Perez	axelperez@gmail.com	123456789	0200-09-12
123	Julio	Cesar	a	123	2002-12-12
000	Nadie	0	0	0	0001-02-02
41604654D	Jennifer	Dam	inuyashajennifer@gmail.com	999	2002-02-24
\.


--
-- Data for Name: equipatge; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.equipatge (id_equipatge, num_factura, linea_factura, num_bultos, pes_kilos) FROM stdin;
\.


--
-- Data for Name: estacio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.estacio (id, nom) FROM stdin;
1	Barcelona
2	Girona
\.


--
-- Data for Name: factura; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.factura (num_factura, dni, total, data_compra) FROM stdin;
1	123	680.0	2022-11-14
2	123	590.0	2022-11-14
3	123	990.0	2022-11-14
4	41604654D	1400.0	2022-11-14
5	123	1000.0	2022-11-14
\.


--
-- Data for Name: nlinea; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.nlinea (num_factura, id_bitllet, preu) FROM stdin;
1	1	500.0
1	2	90.0
1	2	90.0
2	2	90.0
2	1	500.0
3	3	900.0
3	2	90.0
4	3	900.0
4	1	500.0
5	4	500.0
5	1	500.0
\.


--
-- Data for Name: vol; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vol (id, id_origen, id_desti, durada, dia, hora, max_ps, max_kg, hora_arribada) FROM stdin;
1	1	2	00:01:00	2022-02-22	21:00:00	12	13.0	22:00:00
2	2	1	00:01:00	2022-02-22	15:00:00	12	23.0	23:00:00
3	2	1	00:01:00	2022-02-22	12:45:00	21	13.0	13:00:00
4	2	1	00:00:00	2022-02-22	20:00:00	21	21.0	21:00:00
5	2	1	00:00:00	2022-02-22	21:00:00	21	21.0	23:00:00
6	2	1	02:15:00	2022-02-22	18:45:00	21	21.0	21:00:00
7	2	1	05:00:00	2022-12-12	14:00:00	40	500.0	19:00:00
\.


--
-- Name: bitllet bitllet_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bitllet
    ADD CONSTRAINT bitllet_pkey PRIMARY KEY (id);


--
-- Name: client client_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (dni);


--
-- Name: equipatge equipatge_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.equipatge
    ADD CONSTRAINT equipatge_pkey PRIMARY KEY (id_equipatge);


--
-- Name: estacio estacio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estacio
    ADD CONSTRAINT estacio_pkey PRIMARY KEY (id);


--
-- Name: factura factura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.factura
    ADD CONSTRAINT factura_pkey PRIMARY KEY (num_factura);


--
-- Name: vol vol_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vol
    ADD CONSTRAINT vol_pkey PRIMARY KEY (id);


--
-- Name: factura fk_dni; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.factura
    ADD CONSTRAINT fk_dni FOREIGN KEY (dni) REFERENCES public.client(dni);


--
-- Name: vol fk_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vol
    ADD CONSTRAINT fk_id FOREIGN KEY (id_origen) REFERENCES public.estacio(id);


--
-- Name: vol fk_id_desti; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vol
    ADD CONSTRAINT fk_id_desti FOREIGN KEY (id_desti) REFERENCES public.estacio(id);


--
-- PostgreSQL database dump complete
--

