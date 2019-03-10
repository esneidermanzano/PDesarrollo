DROP EXTENSION IF EXISTS pgcrypto;
CREATE EXTENSION pgcrypto;

DROP TABLE IF EXISTS gerentes CASCADE;
DROP TABLE IF EXISTS vendedores CASCADE;
DROP TABLE IF EXISTS jefes_taller CASCADE;

-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS sedes CASCADE;
CREATE TABLE sedes
(
	id serial,
	nombre VARCHAR(50) NOT NULL,
	telefono VARCHAR(10),
        direccion VARCHAR(50),
        tamano_sede INTEGER,
        numero_empleados INTEGER,
        fecha_apertura DATE,
	
	CONSTRAINT sede_pk PRIMARY KEY (id)
);

-----------------------------------------------------------------------------------------------------------------
INSERT INTO sedes (nombre, telefono, direccion, tamano_sede, numero_empleados, fecha_apertura) VALUES
	('Sindical', '1211111', 'Carrera 28C #54-123', 150, 10, '2010-09-28'), 
	('Villa del lago', '1311111', 'Carrera 45 #28-17', 200, 15, '2012-03-12'),
	('Ingenio', '1411111', 'Carrera 12 #9-81', 175, 12, '2015-10-15');
-----------------------------------------------------------------------------------------------------------------

-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS empleados CASCADE;
CREATE TABLE empleados
(
	id VARCHAR(50) NOT NULL,
	password TEXT NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	telefono VARCHAR(10),
	activo BIT(1) NOT NULL,
	id_sede INTEGER NOT NULL,
    correo VARCHAR(50) NOT NULL,
    perfil VARCHAR(50) NOT NULL,
    estado_civil VARCHAR(50) NOT NULL,
    genero VARCHAR(50) NOT NULL,
	
	CONSTRAINT empleados_pk PRIMARY KEY (id),

	CONSTRAINT empleados_fk1 FOREIGN KEY (id_sede)
		REFERENCES sedes (id) ON DELETE CASCADE
);

-----------------------------------------------------------------------------------------------------------------
INSERT INTO empleados VALUES('g1', crypt('xgpassword', gen_salt('bf')), 'Ximena Guzman', '1111111', B'1', '1', 'xg@correo', 'gerente', 'soltero', 'F');
INSERT INTO empleados VALUES('g2', crypt('appassword', gen_salt('bf')), 'Antonio Paz', '2222222', B'1', '2', 'ap@correo', 'gerente', 'soltero', 'M');
INSERT INTO empleados VALUES('g3', crypt('mlpassword', gen_salt('bf')), 'Maria Lara', '3333333', B'1', '3', 'ml@correo', 'gerente', 'soltero', 'F');

INSERT INTO empleados VALUES('v1', crypt('sopassword', gen_salt('bf')), 'Sara Ortega', '4444444', B'1', '1', 'so@correo', 'vendedor', 'soltero', 'F');
INSERT INTO empleados VALUES('v2', crypt('abpassword', gen_salt('bf')), 'Ana Borja', '5555555', B'1', '2', 'ab@correo', 'vendedor', 'soltero', 'F');
INSERT INTO empleados VALUES('v3', crypt('jvpassword', gen_salt('bf')), 'Juan Valencia', '6666666', B'1', '3', 'jv@correo', 'vendedor', 'soltero', 'M');

INSERT INTO empleados VALUES('jt1', crypt('dlpassword', gen_salt('bf')), 'Diana Lopez', '7777777', B'1', '1', 'dl@correo', 'jefe_taller', 'soltero', 'F');
INSERT INTO empleados VALUES('jt2', crypt('svpassword', gen_salt('bf')), 'Sebastian Vasquez', '7777777', B'1', '2', 'sv@correo', 'jefe_taller', 'soltero', 'M');
INSERT INTO empleados VALUES('jt3', crypt('jrpassword', gen_salt('bf')), 'Jairo Rosero', '7777777', B'1', '3', 'jr@correo', 'jefe_taller', 'soltero', 'M');
-----------------------------------------------------------------------------------------------------------------

/*
-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS vendedores CASCADE;
CREATE TABLE vendedores
(
	id VARCHAR(50) NOT NULL,
	password TEXT NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	telefono VARCHAR(10) NOT NULL,
	activo bit NOT NULL,
	id_sede VARCHAR(50) NOT NULL,
	
	CONSTRAINT vendedores_pk PRIMARY KEY (id),

	CONSTRAINT vendedores_fk1 FOREIGN KEY (id_sede)
		REFERENCES sedes (id) ON DELETE CASCADE
);

-----------------------------------------------------------------------------------------------------------------
INSERT INTO vendedores VALUES('v1', crypt('sopassword', gen_salt('bf')), 'Sara Ortega', '4444444', B'1', 's1');
INSERT INTO vendedores VALUES('v2', crypt('abpassword', gen_salt('bf')), 'Ana Borja', '5555555', B'1', 's2');
INSERT INTO vendedores VALUES('v3', crypt('jvpassword', gen_salt('bf')), 'Juan Valencia', '6666666', B'1', 's3');
-----------------------------------------------------------------------------------------------------------------

-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS jefes_taller CASCADE;
CREATE TABLE jefes_taller
(
	id VARCHAR(50) NOT NULL,
	password TEXT NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	telefono VARCHAR(10) NOT NULL,
	activo bit NOT NULL,
	id_sede VARCHAR(50) NOT NULL,
	
	CONSTRAINT jefes_taller_pk PRIMARY KEY (id),

	CONSTRAINT jefes_taller_fk1 FOREIGN KEY (id_sede)
		REFERENCES sedes (id) ON DELETE CASCADE
);

-----------------------------------------------------------------------------------------------------------------
INSERT INTO jefes_taller VALUES('jt1', crypt('dlpassword', gen_salt('bf')), 'Diana Lopez', '7777777', B'1', 's1');
INSERT INTO jefes_taller VALUES('jt2', crypt('svpassword', gen_salt('bf')), 'Sebastian Vasquez', '7777777', B'1', 's2');
INSERT INTO jefes_taller VALUES('jt3', crypt('jrpassword', gen_salt('bf')), 'Jairo Rosero', '7777777', B'1', 's3');
-----------------------------------------------------------------------------------------------------------------
*/

-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS inventario CASCADE;
CREATE TABLE inventario
(
	id VARCHAR(50) NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	precio_unidad INTEGER NOT NULL,
	existencias INTEGER NOT NULL,
	
	CONSTRAINT inventario_pk PRIMARY KEY (id)
);

-----------------------------------------------------------------------------------------------------------------
INSERT INTO inventario VALUES('i1', 'cama', 200000, 500);
INSERT INTO inventario VALUES('i2', 'meson', 300000, 600);
INSERT INTO inventario VALUES('i3', 'closet', 300000, 700);
INSERT INTO inventario VALUES('i4', 'escritorio', 250000, 800);
INSERT INTO inventario VALUES('i5', 'silla', 50000, 900);
-----------------------------------------------------------------------------------------------------------------

-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS clientes CASCADE;
CREATE TABLE clientes
(
	id VARCHAR(50) NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	telefono VARCHAR(10) NOT NULL,
	
	CONSTRAINT clientes_pk PRIMARY KEY (id)
);

-----------------------------------------------------------------------------------------------------------------
INSERT INTO clientes VALUES('c1', 'Marcela Arias', '7177777');
INSERT INTO clientes VALUES('c2', 'James Cuero', '7277777');
INSERT INTO clientes VALUES('c3', 'Ingrid Roa', '7377777');
INSERT INTO clientes VALUES('c4', 'Francisco Morales', '7477777');
INSERT INTO clientes VALUES('c5', 'Vanesa Narvaez', '7577777');
INSERT INTO clientes VALUES('c6', 'Ramiro Salazar', '7677777');
INSERT INTO clientes VALUES('c7', 'Juana Miranda', '7777777');
-----------------------------------------------------------------------------------------------------------------

-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS ventas CASCADE;
CREATE TABLE ventas
(
	id SERIAL,
	id_vendedor VARCHAR(50) NOT NULL,
	id_cliente VARCHAR(50) NOT NULL,
	fecha TIMESTAMP NOT NULL,
	
	CONSTRAINT ventas_pk PRIMARY KEY (id),

	CONSTRAINT ventas_fk1 FOREIGN KEY (id_vendedor)
		REFERENCES empleados (id) ON DELETE CASCADE,

	CONSTRAINT ventas_fk2 FOREIGN KEY (id_cliente)
		REFERENCES clientes (id) ON DELETE CASCADE
);

DROP SEQUENCE IF EXISTS ventas_sequence CASCADE;
CREATE SEQUENCE ventas_sequence
  start 1
  increment 1;

-----------------------------------------------------------------------------------------------------------------
INSERT INTO ventas VALUES(nextval('ventas_sequence'), 'v1', 'c1', current_timestamp);
INSERT INTO ventas VALUES(nextval('ventas_sequence'), 'v2', 'c2', current_timestamp);
INSERT INTO ventas VALUES(nextval('ventas_sequence'), 'v3', 'c3', current_timestamp);
-----------------------------------------------------------------------------------------------------------------

-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS detalle_ventas CASCADE;
CREATE TABLE detalle_ventas
(
	id_venta INTEGER,
	id_inventario VARCHAR(50) NOT NULL,
	cantidad INTEGER NOT NULL,
	
	CONSTRAINT detalle_ventas_pk PRIMARY KEY (id_venta, id_inventario),

	CONSTRAINT detalle_ventas_fk1 FOREIGN KEY (id_venta)
		REFERENCES ventas (id) ON DELETE CASCADE,

	CONSTRAINT detalle_ventas_fk2 FOREIGN KEY (id_inventario)
		REFERENCES inventario (id) ON DELETE CASCADE
);

-----------------------------------------------------------------------------------------------------------------
INSERT INTO detalle_ventas VALUES(1, 'i1', 2);
INSERT INTO detalle_ventas VALUES(1, 'i2', 3);
INSERT INTO detalle_ventas VALUES(1, 'i3', 1);

INSERT INTO detalle_ventas VALUES(2, 'i4', 1);
INSERT INTO detalle_ventas VALUES(2, 'i5', 4);

INSERT INTO detalle_ventas VALUES(3, 'i1', 1);
INSERT INTO detalle_ventas VALUES(3, 'i3', 2);
INSERT INTO detalle_ventas VALUES(3, 'i5', 1);
-----------------------------------------------------------------------------------------------------------------

-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS cotizaciones CASCADE;
CREATE TABLE cotizaciones
(
	id SERIAL,
	id_vendedor VARCHAR(50) NOT NULL,
	id_cliente VARCHAR(50) NOT NULL,
	fecha TIMESTAMP NOT NULL,
	
	CONSTRAINT cotizaciones_pk PRIMARY KEY (id),

	CONSTRAINT cotizaciones_fk1 FOREIGN KEY (id_vendedor)
		REFERENCES empleados (id) ON DELETE CASCADE,

	CONSTRAINT cotizaciones_fk2 FOREIGN KEY (id_cliente)
		REFERENCES clientes (id) ON DELETE CASCADE
);

DROP SEQUENCE IF EXISTS cotizaciones_sequence CASCADE;
CREATE SEQUENCE cotizaciones_sequence
  start 1
  increment 1;

-----------------------------------------------------------------------------------------------------------------
INSERT INTO cotizaciones VALUES(nextval('cotizaciones_sequence'), 'v1', 'c1', current_timestamp);
INSERT INTO cotizaciones VALUES(nextval('cotizaciones_sequence'), 'v2', 'c2', current_timestamp);
INSERT INTO cotizaciones VALUES(nextval('cotizaciones_sequence'), 'v3', 'c3', current_timestamp);
-----------------------------------------------------------------------------------------------------------------

-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS detalle_cotizaciones CASCADE;
CREATE TABLE detalle_cotizaciones
(
	id_cotizacion INTEGER,
	id_inventario VARCHAR(50) NOT NULL,
	cantidad INTEGER NOT NULL,
	
	CONSTRAINT detalle_cotizaciones_pk PRIMARY KEY (id_cotizacion, id_inventario),

	CONSTRAINT detalle_cotizaciones_fk1 FOREIGN KEY (id_cotizacion)
		REFERENCES ventas (id) ON DELETE CASCADE,

	CONSTRAINT detalle_cotizaciones_fk2 FOREIGN KEY (id_inventario)
		REFERENCES inventario (id) ON DELETE CASCADE
);

-----------------------------------------------------------------------------------------------------------------
INSERT INTO detalle_cotizaciones VALUES(1, 'i1', 2);
INSERT INTO detalle_cotizaciones VALUES(1, 'i2', 3);
INSERT INTO detalle_cotizaciones VALUES(1, 'i3', 1);

INSERT INTO detalle_cotizaciones VALUES(2, 'i4', 1);
INSERT INTO detalle_cotizaciones VALUES(2, 'i5', 4);

INSERT INTO detalle_cotizaciones VALUES(3, 'i1', 1);
INSERT INTO detalle_cotizaciones VALUES(3, 'i3', 2);
INSERT INTO detalle_cotizaciones VALUES(3, 'i5', 1);
-----------------------------------------------------------------------------------------------------------------

-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS ordenes_trabajo CASCADE;
CREATE TABLE ordenes_trabajo
(
	id SERIAL,
	id_jefe_taller VARCHAR(50) NOT NULL,
	id_cliente VARCHAR(50) NOT NULL,
	descripcion VARCHAR(2000),
	costo_aproximado INTEGER NOT NULL,
	fecha_creacion TIMESTAMP NOT NULL,
	fecha_entrega TIMESTAMP NOT NULL,
	
	CONSTRAINT ordenes_trabajo_pk PRIMARY KEY (id),

	CONSTRAINT ordenes_trabajo_fk1 FOREIGN KEY (id_jefe_taller)
		REFERENCES empleados (id) ON DELETE CASCADE,

	CONSTRAINT ordenes_trabajo_fk2 FOREIGN KEY (id_cliente)
		REFERENCES clientes (id) ON DELETE CASCADE
);

DROP SEQUENCE IF EXISTS ordenes_trabajo_sequence CASCADE;
CREATE SEQUENCE ordenes_trabajo_sequence
  start 1
  increment 1;

-----------------------------------------------------------------------------------------------------------------
INSERT INTO ordenes_trabajo VALUES(nextval('ordenes_trabajo_sequence'), 'jt1', 'c1', 'Porton de aluminio de 2x3', 3000000, current_timestamp, '2019-05-10 08:00:00');

INSERT INTO ordenes_trabajo VALUES(nextval('ordenes_trabajo_sequence'), 'jt2', 'c2', 'Conjunto de sala estilo victoriano', 5000000, current_timestamp, '2019-05-10 08:00:00');

INSERT INTO ordenes_trabajo VALUES(nextval('ordenes_trabajo_sequence'), 'jt3', 'c3', 'Siete estanterías en acero de 1.5*2', 2000000, current_timestamp, '2019-05-10 08:00:00');
-----------------------------------------------------------------------------------------------------------------

