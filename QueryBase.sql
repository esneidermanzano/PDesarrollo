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
-----------------------------------------------------------------------------------------------------------------

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
-----------------------------------------------------------------------------------------------------------------
-- PROCESO CONTROLADOR/LOGIC/DAO
DROP TABLE IF EXISTS detalle_ventas CASCADE;
CREATE TABLE detalle_ventas
(
	id_venta INTEGER,
	numero_de_ejemplar INTEGER NOT NULL,
	id_item INTEGER NOT NULL,
	cantidad INTEGER NOT NULL,
	
	CONSTRAINT detalle_ventas_pk PRIMARY KEY (id_venta, numero_de_ejemplar, id_item),

	CONSTRAINT detalle_ventas_fk1 FOREIGN KEY (id_venta)
		REFERENCES ventas (id) ON DELETE CASCADE,

	CONSTRAINT detalle_ventas_fk2 FOREIGN KEY (numero_de_ejemplar, id_item)
		REFERENCES ejemplares (numero_de_ejemplar, id_item) ON DELETE CASCADE
);
												 
-- Procedimiento para actualizar la cantidad de un ejemplar al realizar una venta:
CREATE OR REPLACE FUNCTION restarCantidad() RETURNS TRIGGER AS $$
DECLARE 
	item INTEGER := NEW.id_item;
	ejemplar INTEGER := NEW.numero_de_ejemplar;
	cantidadVendida INTEGER := NEW.cantidad;
BEGIN 
	UPDATE ejemplares SET cantidad = cantidad - cantidadVendida WHERE numero_de_ejemplar = ejemplar AND id_item = item;
	RETURN NEW;
END 
$$ LANGUAGE plpgsql;
					
-- Disparador para activar el procedimiento anterior al realizar un INSERT:
CREATE TRIGGER registrar_venta AFTER INSERT ON detalle_ventas FOR EACH ROW EXECUTE PROCEDURE restarCantidad();												
-----------------------------------------------------------------------------------------------------------------
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
-----------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW ordenes_trabajo_view AS SELECT ordenes_trabajo.id, nombre, id_articulo, descripcion, cantidad, fecha_creacion, fecha_entrega, ordenes_trabajo.estado FROM ordenes_trabajo INNER JOIN empleados ON ordenes_trabajo.id_jefe_taller=empleados.id;

-----------------------------------------------------------------------------------------------------------------
-------------- **************************     REGISTROS     ************************* ---------------------------
-----------------------------------------------------------------------------------------------------------------
 
-- SEDES:
INSERT INTO sedes (nombre, telefono, direccion, tamano_sede, numero_empleados, fecha_apertura) VALUES
	('Sindical', '6645798', 'Carrera 28C #54-123', 150, 10, '2010-09-28'), 
	('Villa del Lago', '4454689', 'Carrera 45 #28-17', 200, 15, '2012-03-12'),
	('Alfonso López', '7895125', 'Calle 78 #45-78', 180, 20, '2015-07-25'),	
	('Meléndez', '4458789', 'Calle 71 #13-56', 325, 25, '2016-01-19'),
	('Vallegrande', '2234567', 'Calle 5A #13-74', 170, 13, '2011-06-11'),
	('Siloé', '6625798', 'Avenida 49 #45-13', 220, 18, '2017-05-13'),
	('Centro', '4456789', 'Avenida 53 #28-17', 240, 15, '2010-05-17'),
	('Jardín Plaza', '3578945', 'Avenida 17 #45-18', 140, 11, '2013-02-21'),
	('Cosmocentro', '4495678', 'Avenida 13 #13-74', 290, 10, '2015-09-30'),
	('Chipichape', '1132548', 'Avenida 24 #15-63', 300, 16, '2018-09-11'),
	('Unicentro', '4468975', 'Avenida 10 #13-45', 275, 19, '2017-01-25'),
	('Ulpiano Lloreda', '5515247', 'Carrera 44 #10-20', 215, 20, '2018-05-13'),
	('Ricardo Balcázar', '3346957', 'Carrera 70 #48-75', 120, 27, '2009-06-22'),
	('Pondaje', '1324789', 'Carrera 10 #54-63', 130, 21, '2014-12-24'),
	('Charco Azul', '4579567', 'Carrera 1C #14-63', 125, 16, '2015-11-19'),
	('Caney', '5578934', 'Carrera 14 #19-52', 110, 13, '2017-10-31'),
	('Mariano Ramos', '1157896', 'Calle 74A #74-77', 90, 19, '2010-02-26'),
	('Los Naranjos', '7748569', 'Calle 56 #26-30', 236, 12, '2018-10-26'),
	('Ciudad Jardín', '6654897', 'Calle 52 #19-89', 315, 13, '2019-01-16'),
	('Pance', '3365214', 'Calle 13 #14-15', 250, 8, '2011-09-09'),
	('Florida', '7781300', 'Calle 79 #15-17', 145, 19, '2016-04-24'),
	('Puerto Rellena', '8850012', 'Calle 66 #28-56', 195, 21, '2015-07-18'),
	('7 de Agosto', '44350123', 'Calle 49 #79-58', 166, 22, '2015-09-13'),
	('El Troncal', '3250127', 'Calle 71 #11-53', 245, 25, '2013-03-21'),
	('La Base', '6654012', 'Carrera 23 #17-15', 350, 17, '2012-07-17'),
	('Villacolombia', '3380301', 'Carrera 14H #16-12', 135, 15, '2013-12-25'),
	('Las Ceibas', '8901547', 'Carrera 41 #33-52', 362, 15, '2014-06-24'),
	('Refugio', '22460015', 'Calle 27 #64-18', 159, 16, '2010-03-26'),
	('Calipso', '4058974', 'Calle 18 #14-20', 139, 14, '2017-05-16'),
	('Ingenio', '3051345', 'Carrera 12 #9-81', 175, 12, '2015-10-15');		
	
-- EMPLEADOS:
INSERT INTO empleados VALUES
	('1166448855', crypt('xgpassword', gen_salt('bf')), 'Ximena Guzman', '1135465', B'1', '1', 'xg@correo', 'Gerente', 'Soltero', 'Femenino'),
	('1113548954', crypt('rgpassword', gen_salt('bf')), 'Ruben Gonzalez', '8448954', B'1', '11', 'rg@correo', 'Gerente', 'Soltero', 'Femenino'),	
	('1112345974', crypt('mcpassword', gen_salt('bf')), 'Marybel Cifuentes', '1235421', B'1', '12', 'mc@correo', 'Gerente', 'Soltero', 'Femenino'),
	('1131549215', crypt('appassword', gen_salt('bf')), 'Antonio Paz', '6464244', B'1', '2', 'ap@correo', 'Gerente', 'Soltero', 'Masculino'),
	('1135498455', crypt('mlpassword', gen_salt('bf')), 'Maria Lara', '4647864', B'1', '3', 'ml@correo', 'Gerente', 'Soltero', 'Femenino'),
	('1324546579', crypt('sopassword', gen_salt('bf')), 'Sara Ortega', '1355648', B'1', '4', 'so@correo', 'Vendedor', 'Soltero', 'Femenino'),
	('2342176325', crypt('abpassword', gen_salt('bf')), 'Ana Borja', '4478632', B'1', '29', 'ab@correo', 'Vendedor', 'Soltero', 'Femenino'),
	('0313056125', crypt('jvpassword', gen_salt('bf')), 'Juan Valencia', '1135441', B'1', '22', 'jv@correo', 'Vendedor', 'Soltero', 'Masculino'),
	('5406012351', crypt('dlpassword', gen_salt('bf')), 'Diana Lopez', '1145879', B'1', '17', 'dl@correo', 'Jefe de taller', 'Soltero', 'Femenino'),
	('1035461517', crypt('svpassword', gen_salt('bf')), 'Sebastian Vasquez', '3321547', B'1', '16', 'sv@correo', 'Jefe de taller', 'Soltero', 'Masculino'),
	('0022135487', crypt('jrpassword', gen_salt('bf')), 'Jairo Rosero', '3364985', B'1', '3', 'jr@correo', 'Jefe de taller', 'Soltero', 'Masculino'),
	('1324684952', crypt('kspassword', gen_salt('bf')), 'Kimi Sagi', '4578962', B'1', '13', 'ks@correo', 'Administrador', 'Soltero', 'Masculino'),
	('1142153454', crypt('sgpassword', gen_salt('bf')), 'Sonia Garrido', '1354712', B'1', '16', 'sg@correo', 'Gerente', 'Soltero', 'Femenino'),
	('0235165122', crypt('fgpassword', gen_salt('bf')), 'Falcao Garcia', '3142578', B'1', '19', 'fg@correo', 'Gerente', 'Soltero', 'Masculino'),
	('3131549854', crypt('jjpassword', gen_salt('bf')), 'Junior Jein', '4951358', B'1', '15', 'jj@correo', 'Jefe de taller', 'Soltero', 'Masculino'),
	('1126354533', crypt('dtpassword', gen_salt('bf')), 'Donald Trump', '1151485', B'1', '18', 'dt@correo', 'Jefe de taller', 'Soltero', 'Masculino'),
	('4473215382', crypt('rmpassword', gen_salt('bf')), 'Ricardo Milos', '3351657', B'1', '22', 'rm@correo', 'Vendedor', 'Soltero', 'No definido'),	
	('2354382341', crypt('drpassword', gen_salt('bf')), 'Daniel Rendon', '3756454', B'1', '23', 'dr@correo', 'Vendedor', 'Soltero', 'Masculino'),
	('7623342345', crypt('gvpassword', gen_salt('bf')), 'Gabriel Valencia', '6675263', B'1', '24', 'gv@correo', 'Gerente', 'Soltero', 'Masculino'),
	('5143513454', crypt('mtpassword', gen_salt('bf')), 'Melissa Toro', '6649254', B'1', '28', 'mt@correo', 'Gerente', 'Soltero', 'Femenino'),
	('1235446534', crypt('jtpassword', gen_salt('bf')), 'Jhonny Test', '4495123', B'1', '19', 'jt@correo', 'Vendedor', 'Soltero', 'Masculino'),
	('4324913453', crypt('agpassword', gen_salt('bf')), 'Ariana Grande', '4495135', B'1', '21', 'ag@correo', 'Vendedor', 'Soltero', 'Femenino'),
	('1361384165', crypt('sgpassword', gen_salt('bf')), 'Selena Gomez', '5598854', B'1', '23', 'sg@correo', 'Vendedor', 'Soltero', 'Femenino'),
	('9165325413', crypt('lppassword', gen_salt('bf')), 'Luisa Perdomo', '1135454', B'1', '16', 'lp@correo', 'Jefe de taller', 'Soltero', 'Femenino'),
	('1351343238', crypt('oapassword', gen_salt('bf')), 'Ovidio Aristizabal', '2244658', B'1', '26', 'oa@correo', 'Jefe de taller', 'Soltero', 'Masculino'),
	('4831353555', crypt('jrpassword', gen_salt('bf')), 'Jhonatan Rodriguez', '3315486', B'1', '24', 'jr@correo', 'Jefe de taller', 'Soltero', 'Masculino'),
	('1437651384', crypt('japassword', gen_salt('bf')), 'Jenny Ambuila', '1489557', B'1', '13', 'ja@correo', 'Administrador', 'Soltero', 'Femenino'),
	('3184842685', crypt('cmpassword', gen_salt('bf')), 'Charles Manson', '5567896', B'1', '7', 'cm@correo', 'Vendedor', 'Soltero', 'Masculino'),
	('4644853545', crypt('jgpassword', gen_salt('bf')), 'Julio Garavito', '1154567', B'1', '9', 'jg@correo', 'Vendedor', 'Soltero', 'No definido'),
	('1152354596', crypt('akpassword', gen_salt('bf')), 'Ash Ketchum', '8895765', B'1', '5', 'ak@correo', 'Gerente', 'Soltero', 'No definido');
			
-- INVENTARIO:
INSERT INTO inventario VALUES
	(nextval('inventario_sequence'), 'cama'),
	(nextval('inventario_sequence'), 'meson'),
	(nextval('inventario_sequence'), 'closet'),
	(nextval('inventario_sequence'), 'escritorio'),
	(nextval('inventario_sequence'), 'sofa'),
	(nextval('inventario_sequence'), 'banca'),
	(nextval('inventario_sequence'), 'silla'),
	(nextval('inventario_sequence'), 'repisa'),
	(nextval('inventario_sequence'), 'mueble'),
	(nextval('inventario_sequence'), 'tablero'),
	(nextval('inventario_sequence'), 'comedor'),
	(nextval('inventario_sequence'), 'nochero'),
	(nextval('inventario_sequence'), 'perchero');
	
-- EJEMPLARES:
INSERT INTO ejemplares VALUES
	(nextval('ejemplares_sequence'), 1, 'azul', 25000, 32000, '2019-03-13', 2, 960),
	(nextval('ejemplares_sequence'), 1, 'rojo', 23000, 32000, '2018-09-15', 5, 920),
	(nextval('ejemplares_sequence'), 2, 'verde', 21000, 32000, '2017-10-27', 7, 998),
	(nextval('ejemplares_sequence'), 2, 'negro', 75000, 95000, '2019-05-14', 24, 975),
	(nextval('ejemplares_sequence'), 3, 'blanco', 70000, 95000, '2016-12-23', 28, 955),
	(nextval('ejemplares_sequence'), 3, 'rojo', 77000, 96000, '2017-04-17', 3, 957),
	(nextval('ejemplares_sequence'), 4, 'amarillo', 52000, 67000, '2019-02-25', 6, 938),
	(nextval('ejemplares_sequence'), 4, 'violeta', 51000, 66000, '2011-08-14', 4, 928),
	(nextval('ejemplares_sequence'), 5, 'violeta', 50000, 66000, '2012-11-15', 9, 921),
	(nextval('ejemplares_sequence'), 5, 'cafe', 33000, 41000, '2018-05-20', 17, 934),	
	(nextval('ejemplares_sequence'), 7, 'azul', 27000, 35000, '2017-04-13', 19, 946),
	(nextval('ejemplares_sequence'), 8, 'rojo', 23000, 36000, '2015-02-24', 15, 927),
	(nextval('ejemplares_sequence'), 9, 'verde', 35000, 48000, '2014-01-14', 2, 935),
	(nextval('ejemplares_sequence'), 2, 'negro', 95000, 158000, '2011-10-09', 1, 959),
	(nextval('ejemplares_sequence'), 12, 'blanco', 19000, 47000, '2010-12-10', 3, 953),
	(nextval('ejemplares_sequence'), 1, 'rojo', 78000, 85000, '2018-11-28', 1, 940),
	(nextval('ejemplares_sequence'), 2, 'amarillo', 65000, 79000, '2018-04-17', 25, 958),
	(nextval('ejemplares_sequence'), 8, 'violeta', 34000, 69000, '2019-09-26', 27, 929),
	(nextval('ejemplares_sequence'), 8, 'violeta', 44000, 88000, '2019-02-24', 23, 980),
	(nextval('ejemplares_sequence'), 9, 'cafe', 36000, 75000, '2019-01-18', 21, 936),	
	(nextval('ejemplares_sequence'), 10, 'azul', 29000, 36000, '2017-08-13', 19, 900),
	(nextval('ejemplares_sequence'), 12, 'rojo', 12000, 54000, '2016-02-16', 12, 925),
	(nextval('ejemplares_sequence'), 12, 'verde', 64000, 125000, '2014-01-04', 7, 970),
	(nextval('ejemplares_sequence'), 2, 'negro', 77000, 180000, '2015-03-02', 9, 935),
	(nextval('ejemplares_sequence'), 13, 'blanco', 95000, 235000, '2015-10-13', 14, 945),
	(nextval('ejemplares_sequence'), 3, 'rojo', 84000, 165000, '2015-12-13', 13, 923),
	(nextval('ejemplares_sequence'), 4, 'amarillo', 56000, 168000, '2019-07-29', 23, 910),
	(nextval('ejemplares_sequence'), 6, 'violeta', 17000, 49000, '2011-04-25', 26, 980),
	(nextval('ejemplares_sequence'), 7, 'violeta', 46000, 85000, '2011-01-27', 17, 949),
	(nextval('ejemplares_sequence'), 9, 'cafe', 38000, 95000, '2016-08-13', 29, 954);
						 
-- CLIENTES:
INSERT INTO clientes VALUES
	('1145879852', 'Marcela Arias', '1548795'),
	('2512354862', 'James Cuero', '1234578'),
	('3213543876', 'Ingrid Roa', '4456487'),
	('4156543784', 'Francisco Morales', '3356184'),
	('5253373232', 'Vanesa Narvaez', '5574895'),
	('4534567856', 'Ramiro Salazar', '3364598'),
	('1134678455', 'Gary Oak', '0021357'),
	('2345642117', 'Felipe Perez', '1401578'),
	('3654375659', 'Raul Jimenez', '3350164'),
	('4466485386', 'Pablo Armero', '4495876'),
	('5456876253', 'Light Yagami', '7756481'),
	('6456431856', 'L Lawliet', '3620519'),
	('1164643466', 'Jhony Bravo', '1149578'),
	('2522486655', 'Pedro Picapiedra', '3625419'),
	('3346466456', 'Yurani Restrepo', '4465789'),
	('4265648466', 'Patricio Estrella', '4425136'),
	('5746868455', 'Bob Esponja', '0011354'),
	('6656486848', 'Yunishi Matsuda', '7749658'),
	('1546854354', 'Rosario Tijeras', '3361244'),
	('2453842354', 'James Rodriguez', '9984571'),
	('3468434354', 'Santiago Arias', '3320158'),
	('4486351344', 'Leonel Ospina', '3364597'),
	('5434643581', 'Angelica Zuñiga', '1015645'),
	('6468486666', 'Toromax Torpedo', '4578952'),
	('1455471531', 'Roberto Batman', '3364589'),
	('2153845347', 'Bruce Wayne', '1524054'),
	('3486947865', 'Peter Parker', '6654895'),
	('4869576847', 'Steve Rogers', '1142357'),
	('5486935764', 'Tony Stark', '4486578'),
	('6786846518', 'Natasha Romanof', '5543697'),
	('7676644615', 'Homero Simpson', '1147588'),
	('1144208998', 'Christian Taborda', '3163611275');
				
-- VENTAS:
INSERT INTO ventas VALUES
	(nextval('ventas_sequence'), '1324546579', '1144208998', '2019-03-23 19:54:25-01'),
	(nextval('ventas_sequence'), '1324546579', '4486351344', '2019-01-25 15:12:25-02'),
	(nextval('ventas_sequence'), '1324546579', '4486351344', '2019-02-15 19:10:25-05'),
	(nextval('ventas_sequence'), '2342176325', '4486351344', '2019-03-15 19:15:25-04'),
	(nextval('ventas_sequence'), '2342176325', '1144208998', '2019-03-14 18:10:25-04'),
	(nextval('ventas_sequence'), '2342176325', '1134678455', '2019-03-07 19:17:25-08'),
	(nextval('ventas_sequence'), '2342176325', '1134678455', '2019-02-05 19:10:25-07'),
	(nextval('ventas_sequence'), '2342176325', '1134678455', '2019-02-13 19:26:25-02'),
	(nextval('ventas_sequence'), '1324546579', '2522486655', '2019-01-08 19:10:25-03'),
	(nextval('ventas_sequence'), '4473215382', '5434643581', '2019-02-13 19:25:25-04'),
	(nextval('ventas_sequence'), '4473215382', '5434643581', '2019-03-14 18:10:25-09'),
	(nextval('ventas_sequence'), '1324546579',  '5434643581', '2018-07-13 19:54:25-05'),
	(nextval('ventas_sequence'), '4473215382', '2522486655', '2019-04-17 19:10:25-07'),
	(nextval('ventas_sequence'), '4473215382', '2522486655', '2018-07-17 17:14:25-01'),
	(nextval('ventas_sequence'), '1324546579', '1144208998', '2018-05-13 19:10:25-07'),
	(nextval('ventas_sequence'), '4473215382', '5746868455', '2018-09-27 19:45:25-10'),
	(nextval('ventas_sequence'), '1324546579', '2522486655', '2018-12-13 14:10:25-09'),
	(nextval('ventas_sequence'), '1324546579', '1144208998', '2018-10-31 19:19:25-07'),
	(nextval('ventas_sequence'), '2354382341', '2522486655', '2018-01-13 19:10:25-04'),
	(nextval('ventas_sequence'), '1324546579', '1144208998', '2018-01-25 22:08:25-05'),
	(nextval('ventas_sequence'), '1324546579',  '2522486655', '2019-01-13 19:10:25-07'),
	(nextval('ventas_sequence'), '2354382341', '5746868455', '2019-04-14 19:10:25-03'),
	(nextval('ventas_sequence'), '2354382341', '5746868455', '2019-02-11 19:10:25-07'),
	(nextval('ventas_sequence'), '2354382341', '5746868455', '2018-08-15 15:10:25-05'),
	(nextval('ventas_sequence'), '2354382341', '5746868455', '2019-02-18 19:11:25-07'),
	(nextval('ventas_sequence'), '1324546579', '5746868455', '2019-01-11 19:12:25-08'),
	(nextval('ventas_sequence'), '2354382341', '1144208998', '2019-04-08 17:10:25-07'),
	(nextval('ventas_sequence'), '2354382341', '5746868455', '2019-03-07 18:18:25-04'),
	(nextval('ventas_sequence'), '2354382341', '5746868455', '2018-07-09 17:15:25-07'),
	(nextval('ventas_sequence'), '1324546579', '5746868455', '2018-03-03 18:10:25-05'),
	(nextval('ventas_sequence'), '1324546579', '3346466456', '2019-03-13 19:10:21-07');
				
-- DETALLES DE VENTAS:
INSERT INTO detalle_ventas VALUES
	(30, 21, 10, 8),
	(22, 1, 1, 10),
	(11, 1, 1, 9),
	(2, 2, 1, 5),
	(1, 2, 1, 7),
	(1, 3, 2, 8),
	(3, 4, 2, 6),
	(21, 1, 1, 5),
	(11, 4, 2, 4),
	(31, 4, 2, 2),
	(25, 4, 2, 2),
	(24, 4, 2, 5),
	(23, 3, 2, 8),
	(13, 1, 1, 7),
	(15, 1, 1, 6),
	(11, 5, 3, 9),
	(17, 6, 3, 9),
	(8, 4, 2, 9),
	(20, 5, 3, 7),
	(31, 6, 3, 4),
	(30, 5, 3, 7),
	(21, 5, 3, 8),
	(1, 5, 3, 5),
	(11, 3, 2, 9),
	(28, 3, 2, 4),
	(27, 3, 2, 3),
	(16, 6, 3, 6),
	(24, 5, 3, 8),
	(17, 1, 1, 4),
	(12, 21, 10, 2),
	(10, 1, 1, 5),
	(12, 5, 3, 9),
	(2, 1, 1, 4),
	(3, 3, 2, 8),
	(23, 5, 3, 4),
	(17, 21, 10, 8),
	(21, 2, 1, 7),
	(11, 21, 10, 5),
	(22, 21, 10, 6),
	(2, 6, 3, 3),
	(13, 6, 3, 5),
	(1, 21, 10, 7),
	(19, 3, 2, 9),
	(18, 2, 1, 5),
	(16, 3, 2, 1),
	(25, 21, 10, 5),
	(2, 21, 10, 2),
	(3, 21, 10, 3),
	(20, 21, 10, 2),
	(18, 1, 1, 5),
	(14, 1, 1, 4),
	(9, 3, 2, 2),
	(20, 2, 1, 5),
	(25, 7, 4, 6),
	(1, 6, 3, 5),
	(3, 7, 4, 5),
	(10, 21, 10, 4),
	(11, 7, 4, 2),
	(12, 7, 4, 5),
	(2, 8, 4, 4),
	(17, 5, 3, 2),
	(4, 6, 3, 6),
	(3, 8, 4, 1),
	(15, 8, 4, 4),
	(19, 8, 4, 4),
	(1, 8, 4, 7),
	(21, 4, 2, 6 ),
	(29, 5, 3, 5),
	(30, 6, 3, 4),
	(3, 2, 1, 2),
	(13, 2, 1, 3);
				
-- COTIZACIONES:
INSERT INTO cotizaciones VALUES
	(nextval('cotizaciones_sequence'), '1324546579','Toromax Torpedo', '6468486666', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '4473215382','Toromax Torpedo', '6468486666', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '1324546579','Christian Taborda', '1144208998', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '4473215382','Christian Taborda', '1144208998', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '4473215382','Christian Taborda', '1144208998', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '1324546579','Christian Taborda', '1144208998', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '1324546579','Christian Taborda', '1144208998', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '1324546579','Christian Taborda', '1144208998', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '4473215382','Gary Oak', '1134678455', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '4473215382','Peter Parker', '3486947865', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '4473215382','Peter Parker', '3486947865', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '4473215382','Gary Oak', '1134678455', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '2354382341','Patricio Estrella', '4265648466', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '2354382341','Patricio Estrella', '4265648466', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '2354382341','Patricio Estrella', '4265648466', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '2354382341','Gary Oak', '1134678455', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '2354382341','Toromax Torpedo', '6345786435', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '2354382341','Peter Parker', '3486947865', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '2354382341','Peter Parker', '3486947865', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '2354382341','Patricio Estrella', '4265648466', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '1324546579','Patricio Estrella', '4265648466', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '1324546579','Patricio Estrella', '4265648466', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '1324546579','Patricio Estrella', '4265648466', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '2354382341','Peter Parker', '3486947865', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '2342176325','Peter Parker', '3486947865', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '2354382341','Toromax Torpedo', '9855436854', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '2354382341','Pedro Picapiedra', '2522486655', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '2354382341','Toromax Torpedo', '6468486666', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '1324546579','Pedro Picapiedra', '2522486655', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '1324546579','Toromax Torpedo', '6468486666', '2019-03-13 19:10:25-07'),
	(nextval('cotizaciones_sequence'), '2354382341','Pedro Picapiedra', '4489987097', '2019-03-13 19:10:25-07');

-- DETALLES DE COTIZACIONES:
INSERT INTO detalle_cotizaciones VALUES
	(21, 12, 25, 2),
	(11, 3, 24, 3),
	(17, 4, 15, 1),
	(25, 2, 15, 1),
	(26, 3, 6, 4),
	(30, 7, 7, 1),
	(3, 3, 18, 2),
	(1, 2, 22, 2),
	(1, 8, 14, 3),
	(7, 9, 5, 1),
	(8, 4, 25, 1),
	(2, 1, 26, 4),
	(23, 12, 27, 1),
	(13, 9, 18, 2),
	(1, 6, 2, 2),
	(15, 5, 4, 3),
	(12, 4, 15, 1),
	(22, 12, 25, 1),
	(22, 8, 6, 4),
	(29, 2, 27, 1),
	(30, 9, 18, 2),
	(1, 2, 12, 2),
	(19, 7, 14, 3),
	(14, 4, 5, 1),
	(27, 4, 15, 1),
	(2, 3, 16, 4),
	(3, 2, 7, 1),
	(23, 3, 28, 2),
	(11, 12, 2, 2),
	(21, 3, 14, 3),
	(1, 4, 15, 1),
	(24, 6, 5, 1),
	(25, 5, 6, 4),
	(13, 2, 27, 1),
	(7, 3, 18, 2),
	(13, 8, 19, 1);

-- ÓRDENES DE TRABAJO:
INSERT INTO ordenes_trabajo VALUES
	(nextval('ordenes_trabajo_sequence'), '5406012351', 2, 3, 'Orden # mueble etc', 5, '2019-02-10 08:00:00', '2018-05-10','recibida'),
	(nextval('ordenes_trabajo_sequence'), '0022135487', 2, 3, 'Orden # mueble xy', 17, '2019-03-10 08:00:00', '2017-05-10', 'desarrollo'),
	(nextval('ordenes_trabajo_sequence'), '5406012351', 3, 5, 'Orden # mueble etc', 6, '2012-02-10 08:00:00', '2019-05-10','finalizada'),
	(nextval('ordenes_trabajo_sequence'), '0022135487', 3, 5, 'Orden # mueble xy', 7, '2012-03-10 08:00:00', '2012-05-10', 'desarrollo'),
	(nextval('ordenes_trabajo_sequence'), '0022135487', 4, 7, 'Orden # mueble etc', 5, '2013-02-10 08:00:00', '2011-05-10','recibida'),
	(nextval('ordenes_trabajo_sequence'), '0022135487', 2, 3, 'Orden # mueble xy', 7, '2016-03-10 08:00:00', '2019-05-10', 'desarrollo'),
	(nextval('ordenes_trabajo_sequence'), '0022135487', 4, 7, 'Orden # mueble etc', 8, '2017-02-10 08:00:00', '2015-05-10','recibida'),
	(nextval('ordenes_trabajo_sequence'), '1035461517', 4, 7, 'Orden # mueble xy', 7, '2016-03-10 08:00:00', '2019-05-10', 'finalizada'),
	(nextval('ordenes_trabajo_sequence'), '1035461517', 2, 3, 'Orden # mueble etc', 14, '2017-02-10 08:00:00', '2013-05-10','recibida'),
	(nextval('ordenes_trabajo_sequence'), '0022135487', 2, 3, 'Orden # mueble etc', 25, '2019-02-10 08:00:00', '2019-05-10','finalizada'),
	(nextval('ordenes_trabajo_sequence'), '5406012351', 4, 7, 'Orden # mueble xy', 17, '2017-03-10 08:00:00', '2019-05-10', 'desarrollo'),
	(nextval('ordenes_trabajo_sequence'), '0022135487', 4, 7, 'Orden # mueble etc', 5, '2013-02-10 08:00:00', '2019-05-10','finalizada'),
	(nextval('ordenes_trabajo_sequence'), '3131549854', 4, 7, 'Orden # mueble xy', 7, '2012-03-10 08:00:00', '2014-05-10', 'desarrollo'),
	(nextval('ordenes_trabajo_sequence'), '3131549854', 4, 7, 'Orden # mueble etc', 25, '2010-02-10 08:00:00', '2014-05-10','recibida'),
	(nextval('ordenes_trabajo_sequence'), '3131549854', 4, 7, 'Orden # mueble xy', 7, '2019-03-10 08:00:00', '2015-05-10', 'desarrollo'),
	(nextval('ordenes_trabajo_sequence'), '3131549854', 3, 5, 'Orden # mueble etc', 13, '2010-02-10 08:00:00', '2019-05-10','recibida'),
	(nextval('ordenes_trabajo_sequence'), '3131549854', 3, 5, 'Orden # mueble xy', 7, '2010-03-10 08:00:00', '2019-05-10', 'desarrollo'),
	(nextval('ordenes_trabajo_sequence'), '3131549854', 2, 3, 'Orden # mueble etc', 16, '2019-02-10 08:00:00', '2014-05-10','recibida'),
	(nextval('ordenes_trabajo_sequence'), '3131549854', 3, 5, 'Orden # mueble xy', 7, '2009-03-10 08:00:00', '2017-05-10', 'desarrollo'),
	(nextval('ordenes_trabajo_sequence'), '3131549854', 2, 3, 'Orden # mueble etc', 15, '2009-02-10 08:00:00', '2018-05-10','recibida'),
	(nextval('ordenes_trabajo_sequence'), '3131549854', 3, 5, 'Orden # mueble xy', 7, '2011-03-10 08:00:00', '2017-05-10', 'desarrollo'),
	(nextval('ordenes_trabajo_sequence'), '1035461517', 3, 6, 'Orden # mueble etc', 5, '2017-02-10 08:00:00', '2019-05-10','recibida'),
	(nextval('ordenes_trabajo_sequence'), '5406012351', 4, 7, 'Orden # mueble xy', 7, '2019-03-10 08:00:00', '2016-05-10', 'desarrollo'),
	(nextval('ordenes_trabajo_sequence'), '1035461517', 3, 6, 'Orden # mueble etc', 23, '2019-02-10 08:00:00', '2015-05-10','finalizada'),
	(nextval('ordenes_trabajo_sequence'), '5406012351', 2, 3, 'Orden # mueble xy', 7, '2018-03-10 08:00:00', '2017-05-10', 'desarrollo'),
	(nextval('ordenes_trabajo_sequence'), '5406012351', 3, 6, 'Orden # mueble etc', 6, '2019-02-10 08:00:00', '2019-05-10','recibida'),
	(nextval('ordenes_trabajo_sequence'), '1035461517', 2, 3, 'Orden # mueble etc', 4, '2012-02-10 08:00:00', '2019-05-10','recibida'),
	(nextval('ordenes_trabajo_sequence'), '1035461517', 3, 5,  'Orden # mueble xy', 7, '2015-03-10 08:00:00', '2011-05-10', 'desarrollo'),
	(nextval('ordenes_trabajo_sequence'), '1035461517', 4, 7, 'Orden # mueble et al.', 8, '2017-03-10 08:00:00', '2010-05-10', 'finalizada');

												 