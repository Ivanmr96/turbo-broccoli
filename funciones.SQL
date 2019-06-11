IF EXISTS ( SELECT * FROM Votaciones) 
BEGIN
	print 'si'
END

GO
CREATE FUNCTION ExisteConfiguracion (@IDConfiguracion uniqueidentifier)
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

SELECT dbo.ExisteConfiguracion('5E825DA3-140D-4B16-BD28-31B359EA79AA')