IF EXISTS ( SELECT * FROM Votaciones) 
BEGIN
	print 'si'
END

GO
ALTER FUNCTION ExisteConfiguracion (@IDConfiguracion uniqueidentifier)
	RETURNS bit AS
BEGIN
	DECLARE @Existe AS bit
	SET @Existe = 0

	IF EXISTS (SELECT ID FROM Configuraciones WHERE ID = @IDConfiguracion)
	BEGIN
		SET @Existe = 1
	END

	RETURN @Existe
END
GO

SELECT dbo.ExisteConfiguracion('C59F23B1-BB9A-4C0B-B8C0-0854D291157F')