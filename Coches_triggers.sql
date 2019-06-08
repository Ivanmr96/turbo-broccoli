GO
--Trigger para que solo puedan utilizarse piezas validas para el modelo de coche (definido en la tabla PiezasCoche)
ALTER TRIGGER PiezasValidas ON PiezasConfiguracionCoche
AFTER INSERT, UPDATE
AS
BEGIN

	IF EXISTS ( (SELECT * FROM inserted AS INS
	WHERE IDPieza NOT IN ( SELECT IDPieza FROM PiezasCoches WHERE MarcaCoche = INS.MarcaCoche AND ModeloCoche = INS.ModeloCoche )) )
	BEGIN

		RAISERROR('Pieza no valida para el coche', 16, 1)
		ROLLBACK

	END --IF

END --TRIGGER
GO

--Trigger para que un usuario no pueda votar una configuracion realizada por él mismo
CREATE TRIGGER VotacionUsuarioDiferente ON Votaciones
AFTER INSERT, UPDATE
AS
BEGIN

	

END --TRIGGER