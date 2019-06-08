BEGIN TRAN
	USE Coches
	INSERT INTO Cuentas (NombreUsuario, Contraseña) VALUES ('testuser', '123')

	INSERT INTO Coches VALUES ('AUDI', 'A1', 12580)

	DECLARE @configuracion AS int

	INSERT INTO Configuraciones VALUES ('testuser')
	
	SET @configuracion = ( SELECT ID FROM Configuraciones WHERE Usuario = 'testuser' )

	INSERT INTO Coches VALUES
    (N'AUDI',N'A1',28580);

	INSERT INTO Piezas VALUES
    (1,N'Pintura gris oscuro', NULL, 450),
    (2,N'Llantas 5 brazos', NULL, 975),
    (3,N'30 TFSI 6 vel.', NULL, 0),
    (4,N'Pintura azul metalizado', NULL, 450);

	INSERT INTO Motores VALUES
    (3,N'Gasolina',N'Delantera',6,4.80,116);

	INSERT INTO Llantas VALUES
    (2,15);
	
	INSERT INTO Pinturas VALUES
    (1,'gris oscuro'),
    (4,'azul');

	INSERT INTO PiezasCoches VALUES
	(1,N'AUDI',N'A1'),
    (2,N'AUDI',N'A1'),
    (3,N'AUDI',N'A1'),
    (4,N'AUDI',N'A1');

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