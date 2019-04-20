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
	nombre VARCHAR(50) NOT NULL UNIQUE,
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
INSERT INTO empleados VALUES('1111111111', crypt('xgpassword', gen_salt('bf')), 'Ximena Guzman', '1111111', B'1', '1', 'xg@correo', 'Gerente', 'Soltero', 'Femenino');
INSERT INTO empleados VALUES('2222222222', crypt('appassword', gen_salt('bf')), 'Antonio Paz', '2222222', B'1', '2', 'ap@correo', 'Gerente', 'Soltero', 'Masculino');
INSERT INTO empleados VALUES('3333333333', crypt('mlpassword', gen_salt('bf')), 'Maria Lara', '3333333', B'1', '3', 'ml@correo', 'Gerente', 'Soltero', 'Femenino');

INSERT INTO empleados VALUES('4444444444', crypt('sopassword', gen_salt('bf')), 'Sara Ortega', '4444444', B'1', '1', 'so@correo', 'Vendedor', 'Soltero', 'Femenino');
INSERT INTO empleados VALUES('5555555555', crypt('abpassword', gen_salt('bf')), 'Ana Borja', '5555555', B'1', '2', 'ab@correo', 'Vendedor', 'Soltero', 'Femenino');
INSERT INTO empleados VALUES('6666666666', crypt('jvpassword', gen_salt('bf')), 'Juan Valencia', '6666666', B'1', '3', 'jv@correo', 'Vendedor', 'Soltero', 'Masculino');

INSERT INTO empleados VALUES('7777777777', crypt('dlpassword', gen_salt('bf')), 'Diana Lopez', '7777777', B'1', '1', 'dl@correo', 'Jefe de taller', 'Soltero', 'Femenino');
INSERT INTO empleados VALUES('8888888888', crypt('svpassword', gen_salt('bf')), 'Sebastian Vasquez', '7777777', B'1', '2', 'sv@correo', 'Jefe de taller', 'Soltero', 'Masculino');
INSERT INTO empleados VALUES('9999999999', crypt('jrpassword', gen_salt('bf')), 'Jairo Rosero', '7777777', B'1', '3', 'jr@correo', 'Jefe de taller', 'Soltero', 'Masculino');

INSERT INTO empleados VALUES('0123456789', crypt('kspassword', gen_salt('bf')), 'Kimi Sagi', '88888888', B'1', '3', 'ks@correo', 'Administrador', 'Soltero', 'Masculino');

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
	id INTEGER NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	
	CONSTRAINT inventario_pk PRIMARY KEY (id)
);

DROP SEQUENCE IF EXISTS inventario_sequence CASCADE;
CREATE SEQUENCE inventario_sequence
  start 1
  increment 1;

-----------------------------------------------------------------------------------------------------------------
INSERT INTO inventario VALUES(nextval('inventario_sequence'), 'cama');
INSERT INTO inventario VALUES(nextval('inventario_sequence'), 'meson');
INSERT INTO inventario VALUES(nextval('inventario_sequence'), 'closet');
INSERT INTO inventario VALUES(nextval('inventario_sequence'), 'escritorio');
INSERT INTO inventario VALUES(nextval('inventario_sequence'), 'silla');
-----------------------------------------------------------------------------------------------------------------


-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS ejemplares CASCADE;
CREATE TABLE ejemplares
(
	numero_de_ejemplar INTEGER NOT NULL,
	id_item INTEGER NOT NULL,
	color VARCHAR(50) NOT NULL,
	valor_compra INTEGER NOT NULL,
	valor_venta INTEGER NOT NULL,
	fecha_ingreso DATE NOT NULL,
	id_sede INTEGER NOT NULL,
	cantidad INTEGER NOT NULL,
	
	CONSTRAINT ejemplares_pk PRIMARY KEY (numero_de_ejemplar, id_item),

	CONSTRAINT ejemplares_fk1 FOREIGN KEY (id_item)
		REFERENCES inventario (id) ON DELETE CASCADE,

	CONSTRAINT ejemplares_fk2 FOREIGN KEY (id_sede)
		REFERENCES sedes (id) ON DELETE CASCADE
);

DROP SEQUENCE IF EXISTS ejemplares_sequence CASCADE;
CREATE SEQUENCE ejemplares_sequence
  start 1
  increment 1;
-----------------------------------------------------------------------------------------------------------------
INSERT INTO ejemplares VALUES(nextval('ejemplares_sequence'), 2, 'azul', 25000, 32000, '2019-03-13', 2, 40);
INSERT INTO ejemplares VALUES(nextval('ejemplares_sequence'), 2, 'rojo', 23000, 32000, '2019-02-13', 2, 20);
INSERT INTO ejemplares VALUES(nextval('ejemplares_sequence'), 2, 'verde', 21000, 32000, '2019-01-13', 2, 30);
INSERT INTO ejemplares VALUES(nextval('ejemplares_sequence'), 3, 'negro', 75000, 95000, '2019-03-13', 2, 15);
INSERT INTO ejemplares VALUES(nextval('ejemplares_sequence'), 3, 'blanco', 70000, 95000, '2019-03-13', 2, 5);
INSERT INTO ejemplares VALUES(nextval('ejemplares_sequence'), 3, 'rojo', 77000, 96000, '2019-03-13', 3, 25);
INSERT INTO ejemplares VALUES(nextval('ejemplares_sequence'), 4, 'amarillo', 52000, 67000, '2019-02-13', 3, 18);
INSERT INTO ejemplares VALUES(nextval('ejemplares_sequence'), 4, 'violeta', 51000, 66000, '2019-02-13', 3, 28);
INSERT INTO ejemplares VALUES(nextval('ejemplares_sequence'), 4, 'violeta', 50000, 66000, '2019-02-13', 3, 20);
INSERT INTO ejemplares VALUES(nextval('ejemplares_sequence'), 5, 'cafe', 33000, 41000, '2019-01-13', 3, 34);
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
INSERT INTO clientes VALUES('1', 'Marcela Arias', '7177777');
INSERT INTO clientes VALUES('2', 'James Cuero', '7277777');
INSERT INTO clientes VALUES('3', 'Ingrid Roa', '7377777');
INSERT INTO clientes VALUES('4', 'Francisco Morales', '7477777');
INSERT INTO clientes VALUES('5', 'Vanesa Narvaez', '7577777');
INSERT INTO clientes VALUES('6', 'Ramiro Salazar', '7677777');
INSERT INTO clientes VALUES('7', 'Juana Miranda', '7777777');
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
INSERT INTO ventas VALUES(nextval('ventas_sequence'), '4444444444', '1', '2019-03-13 19:10:25-07');
INSERT INTO ventas VALUES(nextval('ventas_sequence'), '5555555555', '2', '2019-03-13 19:10:25-07');
INSERT INTO ventas VALUES(nextval('ventas_sequence'), '6666666666', '3', '2019-03-13 19:10:25-07');
-----------------------------------------------------------------------------------------------------------------

-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS detalle_ventas CASCADE;
CREATE TABLE detalle_ventas
(
	id_venta INTEGER,
	numero_de_ejemplar INTEGER NOT NULL,
	id_item INTEGER NOT NULL,
	
	CONSTRAINT detalle_ventas_pk PRIMARY KEY (id_venta, numero_de_ejemplar, id_item),

	CONSTRAINT detalle_ventas_fk1 FOREIGN KEY (id_venta)
		REFERENCES ventas (id) ON DELETE CASCADE,

	CONSTRAINT detalle_ventas_fk2 FOREIGN KEY (numero_de_ejemplar, id_item)
		REFERENCES ejemplares (numero_de_ejemplar, id_item) ON DELETE CASCADE
);

-----------------------------------------------------------------------------------------------------------------
INSERT INTO detalle_ventas VALUES(1, 1, 2);
INSERT INTO detalle_ventas VALUES(1, 2, 2);
INSERT INTO detalle_ventas VALUES(1, 3, 2);

INSERT INTO detalle_ventas VALUES(2, 4, 3);
INSERT INTO detalle_ventas VALUES(2, 5, 3);

INSERT INTO detalle_ventas VALUES(3, 6, 3);
INSERT INTO detalle_ventas VALUES(3, 7, 4);
INSERT INTO detalle_ventas VALUES(3, 8, 4);
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
		REFERENCES empleados (id) ON DELETE CASCADE

	/*
	CONSTRAINT cotizaciones_fk2 FOREIGN KEY (id_cliente)
		REFERENCES clientes (id) ON DELETE CASCADE*/
);

-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS cotizaciones CASCADE;
CREATE TABLE cotizaciones
(
	id SERIAL,
	id_vendedor VARCHAR(50) NOT NULL,
	nombre_cliente VARCHAR(50) NOT NULL,--MODIFICO VALLE
	id_cliente VARCHAR(50) NOT NULL,
	fecha TIMESTAMP NOT NULL,
	
	CONSTRAINT cotizaciones_pk PRIMARY KEY (id),

	CONSTRAINT cotizaciones_fk1 FOREIGN KEY (id_vendedor)
		REFERENCES empleados (id) ON DELETE CASCADE

	/*
	CONSTRAINT cotizaciones_fk2 FOREIGN KEY (id_cliente)
		REFERENCES clientes (id) ON DELETE CASCADE*/
);

DROP SEQUENCE IF EXISTS cotizaciones_sequence CASCADE;
CREATE SEQUENCE cotizaciones_sequence
  start 1
  increment 1;

-----------------------------------------------------------------------------------------------------------------
INSERT INTO cotizaciones VALUES(nextval('cotizaciones_sequence'), '4444444444','Pepito Perez', '6345786435', '2019-03-13 19:10:25-07');--MODIFICO VALLE AGREGO NOMBRE CLIENTE
INSERT INTO cotizaciones VALUES(nextval('cotizaciones_sequence'), '5555555555','Esneider', '9855436854', '2019-03-13 19:10:25-07');--MODIFICO VALLE AGREGO NOMBRE CLIENTE
INSERT INTO cotizaciones VALUES(nextval('cotizaciones_sequence'), '6666666666','Estemen Lafrego', '4489987097', '2019-03-13 19:10:25-07');--MODIFICO VALLE AGREGO NOMBRE CLIENTE
-----------------------------------------------------------------------------------------------------------------

-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS detalle_cotizaciones CASCADE;
CREATE TABLE detalle_cotizaciones
(
	id_cotizacion INTEGER NOT NULL,
	id_inventario INTEGER NOT NULL,
	id_ejemplar INTEGER NOT NULL,--MODIFICO VALLE
	cantidad INTEGER NOT NULL,
	
	CONSTRAINT detalle_cotizaciones_pk PRIMARY KEY (id_cotizacion, id_inventario,id_ejemplar),

	CONSTRAINT detalle_cotizaciones_fk1 FOREIGN KEY (id_cotizacion)
		REFERENCES cotizaciones (id) ON DELETE CASCADE,

	CONSTRAINT detalle_cotizaciones_fk2 FOREIGN KEY (id_inventario)
		REFERENCES inventario (id) ON DELETE CASCADE
);

-----------------------------------------------------------------------------------------------------------------
INSERT INTO detalle_cotizaciones VALUES(1, 2, 2, 2);--AGREGUE EL OTRO ID A CADA REGISTRO(VALLE)
INSERT INTO detalle_cotizaciones VALUES(1, 3, 4, 3);
INSERT INTO detalle_cotizaciones VALUES(1, 4, 5, 1);

INSERT INTO detalle_cotizaciones VALUES(2, 2, 5, 1);
INSERT INTO detalle_cotizaciones VALUES(2, 3, 6, 4);

INSERT INTO detalle_cotizaciones VALUES(3, 2, 7, 1);
INSERT INTO detalle_cotizaciones VALUES(3, 3,8, 2);
INSERT INTO detalle_cotizaciones VALUES(3, 4,9, 1);
-----------------------------------------------------------------------------------------------------------------
-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS ordenes_trabajo CASCADE;
CREATE TABLE ordenes_trabajo
(
	id SERIAL,
	id_jefe_taller VARCHAR(50) NOT NULL,
	id_articulo INTEGER NOT NULL,
	id_ejemplar INTEGER NOT NULL,
	descripcion VARCHAR(2000),
	cantidad INTEGER NOT NULL,
	fecha_creacion TIMESTAMP NOT NULL,
	fecha_entrega DATE NOT NULL,
	estado VARCHAR(30) NOT NULL,
	
	CONSTRAINT ordenes_trabajo_pk PRIMARY KEY (id),

	CONSTRAINT ordenes_trabajo_fk1 FOREIGN KEY (id_jefe_taller)
		REFERENCES empleados (id) ON DELETE CASCADE,

	CONSTRAINT ordenes_trabajo_fk2 FOREIGN KEY (id_articulo, id_ejemplar)
		REFERENCES ejemplares (id_item, numero_de_ejemplar) ON DELETE CASCADE
);

DROP SEQUENCE IF EXISTS ordenes_trabajo_sequence CASCADE;
CREATE SEQUENCE ordenes_trabajo_sequence
  start 1
  increment 1;

-----------------------------------------------------------------------------------------------------------------
INSERT INTO ordenes_trabajo VALUES(nextval('ordenes_trabajo_sequence'), '7777777777', 2, 2, 'Orden 2345 mesa etc', 5, '2019-02-10 08:00:00', '2019-05-10','recibida');
INSERT INTO ordenes_trabajo VALUES(nextval('ordenes_trabajo_sequence'), '8888888888', 2, 3, 'Orden 35 asiento xy', 7, '2019-03-10 08:00:00', '2019-05-10', 'desarrollo');
INSERT INTO ordenes_trabajo VALUES(nextval('ordenes_trabajo_sequence'), '9999999999', 4, 7, 'Orden 33 puerta et al.', 3, '2019-03-10 08:00:00', '2019-05-10', 'finalizada');
-----------------------------------------------------------------------------------------------------------------
create or replace view ordenes_trabajo_view as select ordenes_trabajo.id, nombre, id_articulo, descripcion, cantidad, fecha_creacion, fecha_entrega, ordenes_trabajo.estado from ordenes_trabajo inner join empleados on ordenes_trabajo.id_jefe_taller=empleados.id;

