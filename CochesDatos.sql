BEGIN TRAN
	USE Coches
	INSERT INTO Coches VALUES ('AUDI', 'A1', 20650)

	INSERT INTO Piezas VALUES
	(5,'Asistente de aparcamiento', NULL, 815, NULL),
    (1,N'Pintura gris oscuro', NULL, 450, 'pintura'),
    (2,N'Llantas 5 brazos', NULL, 975, 'llantas'),
    (3,N'30 TFSI 6 vel.', NULL, 0, 'motor'),
    (4,N'Pintura azul metalizado', NULL, 450, 'pintura');

	INSERT INTO Motores VALUES
    (3,N'Gasolina',N'Delantera',6,4.80,250,116,'G');

	INSERT INTO Llantas VALUES
    (2,15);
	
	INSERT INTO Pinturas VALUES
    (1,'gris oscuro', 'metalizado'),
    (4,'azul', 'mate');

	INSERT INTO PiezasCoches VALUES
	(1,N'AUDI',N'A1'),
    (2,N'AUDI',N'A1'),
    (3,N'AUDI',N'A1'),
    (4,N'AUDI',N'A1'),
	(5,N'AUDI',N'A1');

	SELECT * FROM Votaciones
	SELECT * FROM Configuraciones

	INSERT INTO Votaciones VALUES (NEWID(), 6, CURRENT_TIMESTAMP, ( SELECT ID FROM Configuraciones WHERE Usuario = 'testuser' ), 'testuser');

--COMMIT
ROLLBACK

--SELECT * FROM Coches
--SELECT * FROM Piezas
--SELECT * FROM Motores
--SELECT * FROM Pinturas
--SELECT * FROM Llantas
--SELECT * FROM PiezasCoches
--SELECT * FROM Cuentas
--SELECT * FROM Configuraciones

--BEGIN TRAN

--SELECT * FROM Cuentas

--INSERT INTO Configuraciones VALUES ('prueba')

--print @@IDENTITY

--ROLLBACK