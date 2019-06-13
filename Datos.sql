USE Coches

DELETE FROM Coches

INSERT INTO Coches VALUES
    (N'Mercedes',N'Clase A',29225),
    (N'Toyota',N'Prius',29990),
    (N'Audi',N'A1',20730);

DELETE FROM Piezas

INSERT INTO Piezas VALUES
    (1,N'Pintura gris oscuro',NULL,450,N'pintura'),
    (2,N'Llantas 5 brazos',NULL,975,N'llantas'),
    (3,N'30 TFSI 6 vel.',NULL,0,N'motor'),
    (4,N'Pintura azul metalizado',NULL,450,N'motor'),
    (5,N'Asistente de aparcamiento',NULL,815,NULL),
	(10, 'Motor diesel', NULL, 1000, 'motor'),
    (11,N'Motor E-CVT 125H Automatico',NULL,0,N'motor'),
	(12,'Blanco',NULL,0,'pintura'),
    (13,N'Rojo Emocion',NULL,825,N'pintura'),
    (14,N'Gris Grafito',NULL,550,N'pintura'),
    (15,N'Plata',NULL,550,N'pintura'),
    (16,N'Bronce Platino',NULL,550,N'pintura'),
    (17,N'Azul Indigo',NULL,550,N'pintura'),
    (18,N'Negro Cosmo',NULL,550,N'pintura'),
    (19,N'Blanco Perlado',NULL,825,N'pintura'),
    (20,N'Llantas de Invierno 16"',NULL,108,N'llantas'),
    (21,N'Llantas de aleacion 17"',NULL,0,N'llantas'),
    (22,N'Sistema de Presion de nuematicos',NULL,194,NULL),
    (23,N'Soporte tablet',NULL,77,NULL),
    (24,N'Alfombrillas de goma',NULL,64,NULL),
    (25,N'Motor A 180',NULL,0,N'motor'),
    (26,N'Motor A 180 d',NULL,1750,N'motor'),
    (27,N'Motor A 250 4MATIC',NULL,20050,N'motor'),
    (28,N'Negro noche',NULL,0,N'pintura'),
    (29,N'Azul denim',NULL,817.38,N'pintura'),
    (30,N'Blanco digital',NULL,817.38,N'pintura'),
    (31,N'Rojo jupiter',NULL,0,N'pinuta'),
    (32,N'Llantas aleacion 17 pulgadas',NULL,0,N'llantas'),
    (33,N'Llantas aleacion 18 pulgadas color negro',NULL,1106.60,N'llantas'),
    (34,N'Llantas aleacion 19 multirradio titanio',NULL,829.95,N'llantas'),
    (35,N'Techo corredizo panoramico',NULL,1288.94,NULL),
    (36,N'Enganche para remolque',NULL,1106.60,NULL),
    (37,N'Asiento del acompañante abatible',NULL,207,NULL),
    (38,N'Motor 25 TFSI 5 vel. ',NULL,0,N'motor'),
    (39,N'Motor 35 TFSI S tronic',NULL,4650,N'motor'),
    (40,N'Bianco cortina',NULL,0,N'pintura'),
    (41,N'Verde tioman',NULL,325,N'pintura'),
    (42,N'Amarillo Pitón metalizado',NULL,645,N'pintura'),
    (43,N'Gris Manhattan metalizado',NULL,645,N'pintura'),
    (44,N'Rojo Misano perla',NULL,645,N'pintura'),
    (45,N'Llantas 15 pulgadas',NULL,0,N'llantas'),
    (46,N'Llantas 16 pulgadas',N'Llantas de aleacion ligera  de 10 radios',765,N'llantas'),
    (47,N'Llantas 17 pulgadas',N'Llantas de aleación ligera de 17" en diseño de 5 radios con inserciones en plástico gris platino mate ',1585,N'llantas'),
    (48,N'Asientos deportivos',NULL,400,NULL),
    (49,N'Volante de cuero',N'Volante de cuero de 3 radios multifunción plus ',95,NULL),
    (50,N'MMI Navegacion Plus',NULL,2290,NULL),
    (51,N'Cristales traseros oscurecidos',NULL,410,NULL),
    (52,N'Climatizador automatico',N'Cimatizador automatico confort de 2 zonas',470,NULL),
    (53,N'Camara de marcha atrás',NULL,410,NULL),
    (54,N'Asistente de velocidad adaptativo ',NULL,645,NULL);

DELETE FROM PiezasCoches

INSERT INTO PiezasCoches VALUES
    (1,N'Mercedes',N'Clase A'),
    (11,N'Toyota',N'Prius'),
    (13,N'Toyota',N'Prius'),
    (14,N'Toyota',N'Prius'),
    (15,N'Toyota',N'Prius'),
    (16,N'Toyota',N'Prius'),
    (17,N'Toyota',N'Prius'),
    (18,N'Toyota',N'Prius'),
    (19,N'Toyota',N'Prius'),
    (20,N'Toyota',N'Prius'),
    (21,N'Toyota',N'Prius'),
    (22,N'Toyota',N'Prius'),
    (23,N'Toyota',N'Prius'),
    (24,N'Toyota',N'Prius'),
    (25,N'Mercedes',N'Clase A'),
    (26,N'Mercedes',N'Clase A'),
    (27,N'Mercedes',N'Clase A'),
    (28,N'Mercedes',N'Clase A'),
    (29,N'Mercedes',N'Clase A'),
    (30,N'Mercedes',N'Clase A'),
    (31,N'Mercedes',N'Clase A'),
    (32,N'Mercedes',N'Clase A'),
    (33,N'Mercedes',N'Clase A'),
    (34,N'Mercedes',N'Clase A'),
    (35,N'Mercedes',N'Clase A'),
    (36,N'Mercedes',N'Clase A'),
    (37,N'Mercedes',N'Clase A'),
    (38,N'Audi',N'A1'),
    (39,N'Audi',N'A1'),
    (40,N'Audi',N'A1'),
    (41,N'Audi',N'A1'),
    (42,N'Audi',N'A1'),
    (43,N'Audi',N'A1'),
    (44,N'Audi',N'A1'),
    (45,N'Audi',N'A1'),
    (46,N'Audi',N'A1'),
    (47,N'Audi',N'A1'),
    (48,N'Audi',N'A1'),
    (49,N'Audi',N'A1'),
    (50,N'Audi',N'A1'),
    (51,N'Audi',N'A1'),
    (52,N'Audi',N'A1'),
    (53,N'Audi',N'A1'),
    (54,N'Audi',N'A1');

DELETE FROM Motores

INSERT INTO Motores VALUES
    (3,N'Gasolina',N'D',6,48,250,116,N'G'),
    (10,N'Diesel',N'T',6,NULL,300,160,N'D'),
    (11,NULL,N'D',0,41,230,122,N'H'),
    (25,N'Gasolina',N'D',6,61,600,136,N'G'),
    (26,N'Diesel',N'D',6,47,700,116,N'D'),
    (27,N'Gasolina',N'4',7,73,550,224,N'G'),
    (38,N'Gasolina',N'D',5,46,700,95,N'G'),
    (39,N'Gasolina',N'D',7,57,700,150,N'G');

DELETE FROM Pinturas

INSERT INTO Pinturas VALUES
    (1,N'gris oscuro',N'metalizado'),
    (4,N'azul',N'mate'),
    (12,N'blanco',N'solido'),
    (13,N'rojo',N'metalizado'),
    (14,N'gris oscuro',N'metalizado'),
    (15,N'plata',N'metalizado'),
    (16,N'bronce',N'metalizado'),
    (17,N'azul',N'metalizado'),
    (18,N'negro',N'metalizado'),
    (19,N'blanco',N'perlado'),
    (28,N'negro',N'solido'),
    (29,N'azul',N'metalizado'),
    (30,N'blanco',N'metalizado'),
    (31,N'rojo',N'solido'),
    (40,N'blanco',N'solido'),
    (41,N'verde',N'solido'),
    (42,N'amarillo',N'metalizado'),
    (43,N'gris',N'metalizado'),
    (44,N'rojo',N'perlado');

DELETE FROM Llantas

INSERT INTO Llantas VALUES
    (2,15),
    (20,16),
    (21,17),
    (32,17),
    (33,18),
    (34,19),
    (45,15),
    (46,16),
    (47,17);

DELETE FROM Cuentas

INSERT INTO Cuentas VALUES ('testuser', '202cb962ac59075b964b07152d234b70')

DELETE FROM Configuraciones

INSERT INTO Configuraciones VALUES
(N'{137D8ABC-FA8C-458D-891A-25B76CDD96BD}',N'testuser','2019-07-08 00:00:00',N'Toyota',N'Prius',NULL,NULL,NULL),
(N'{5E825DA3-140D-4B16-BD28-31B359EA79AA}',N'testuser','2019-06-09 00:00:00',N'Mercedes',N'Clase A',NULL,NULL,NULL),
(N'{480C17B8-EEF1-4449-B5D7-E21F90F9752D}',N'testuser','2019-06-10 00:00:00',N'Toyota',N'Prius',NULL,NULL,NULL);

