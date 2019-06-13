GO
--Trigger para que solo puedan utilizarse piezas validas para el modelo de coche (definido en la tabla PiezasCoche)
CREATE TRIGGER PiezasValidas ON PiezasConfiguracionCoche
AFTER INSERT, UPDATE
AS
BEGIN

	IF EXISTS ( (SELECT IDPieza FROM inserted AS INS
			INNER JOIN Configuraciones AS confi ON confi.ID = INS.IDConfiguracion
			WHERE IDPieza NOT IN ( SELECT IDPieza FROM PiezasCoches WHERE MarcaCoche = confi.MarcaCoche AND ModeloCoche = confi.ModeloCoche )) )
	BEGIN

		RAISERROR('Pieza no valida para el coche', 16, 1)
		ROLLBACK

	END --IF

END --TRIGGER
GO

BEGIN TRAN

SELECT * FROM Configuraciones

SELECT * FROM PiezasConfiguracionCoche

SELECT * FROM PiezasCoches

INSERT INTO PiezasCoches VALUES (1, 'Toyota', 'Prius')

DELETE FROM PiezasCoches WHERE MarcaCoche = 'Toyota'

INSERT INTO PiezasConfiguracionCoche VALUES (1, 'F9827DE8-30A7-4C4B-B4C1-1C3A3F28C27C')

SELECT IDPieza FROM PiezasConfiguracionCoche AS INS
			INNER JOIN Configuraciones AS confi ON confi.ID = INS.IDConfiguracion
			WHERE IDPieza NOT IN ( SELECT IDPieza FROM PiezasCoches WHERE MarcaCoche = confi.MarcaCoche AND ModeloCoche = confi.ModeloCoche )

ROLLBACK

GO
--Trigger para que un usuario no pueda votar una configuracion realizada por él mismo
CREATE TRIGGER VotacionUsuarioDiferente ON Votaciones
AFTER INSERT, UPDATE
AS
BEGIN

	IF EXISTS( (SELECT ins.ID FROM inserted AS ins
	INNER JOIN Configuraciones AS Conf ON Conf.ID = ins.IDConfiguracion
	WHERE ins.Usuario = Conf.Usuario) )
	BEGIN

		RAISERROR('Un usuario no puede votar una configuracion propia', 16, 1)
		ROLLBACK

	END --IF

END --TRIGGER
GO