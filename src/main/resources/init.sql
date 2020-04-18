-- Prueba de archivo.sql
DROP DATABASE if exists "ventas_db";
CREATE DATABASE "ventas_db"  WITH OWNER = "ventas" ENCODING = 'UTF8' TABLESPACE = jzurita_tbsp LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8' CONNECTION LIMIT = -1;

--DROP SEQUENCE venta_seq;
--DROP SEQUENCE venta_detalle_seq;

CREATE SEQUENCE venta_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER TABLE venta_seq
OWNER TO "ventas";

CREATE SEQUENCE venta_detalle_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER TABLE venta_detalle_seq
OWNER TO "ventas";


create table venta(id bigint not null, total double precision );
ALTER TABLE venta OWNER TO "ventas";

create table venta_detalle(id bigint not null, venta_id bigint not null, descripcion_producto varchar, cantidad double precision, precio double precision, total double precision );
ALTER TABLE venta_detalle OWNER TO "ventas";