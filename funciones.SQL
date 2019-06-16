IF EXISTS ( SELECT * FROM Votaciones) 
BEGIN
	print 'si'
END

GO
/* INTERFAZ
Comentario: Comprueba si existe una configuracion buscando por su ID
Prototipo: FUNCTION ExisteConfiguracion (@IDConfiguracion uniqueidentifier)
Entrada: Un uniqueidentifer con el ID de la configuracion a comprobar
Precondiciones: No hay
Salida: un bit indicando si al configuración existe o no.
Postcondiciones: Asociado al nombre devuelve un bit, que será 1 si la configuracion existe o 0 si no existe.
*/
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