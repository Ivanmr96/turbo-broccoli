CREATE 
--DROP
DATABASE Coches

USE Coches

CREATE TABLE Cuentas
(
	NombreUsuario varchar(25) NOT NULL,
	Contraseña varchar(32) NOT NULL

	CONSTRAINT PK_Cuentas PRIMARY KEY (NombreUsuario)
)

CREATE TABLE Coches
(
	Marca varchar(20) NOT NULL,
	Modelo varchar(25) NOT NULL,
	PrecioBase money NOT NULL,

	CONSTRAINT PK_Coches PRIMARY KEY (Marca, Modelo)
)

CREATE TABLE Piezas
(
	ID int,
	Nombre varchar(max),
	Descripcion varchar(max),
	Precio smallmoney,
	Tipo varchar(10),

	CONSTRAINT PK_Piezas PRIMARY KEY (ID)
)

CREATE TABLE Motores
(
	IDPieza int NOT NULL,
	Carburante varchar(10) NULL,
	Traccion varchar(10) NOT NULL,
	NumeroVelocidades varchar(15) NOT NULL,
	Consumo decimal(4,2) NULL,
	Autonomia smallint NULL,
	Potencia smallint NULL,
	Tipo char(1) NOT NULL,

	CONSTRAINT PK_Motores PRIMARY KEY(IDPieza),
	CONSTRAINT FK_Motores_IDPieza FOREIGN KEY (IDPieza) REFERENCES Piezas(ID) ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE Pinturas
(
	IDPieza int NOT NULL,
	Color varchar(20) NOT NULL,
	Acabado varchar(20) NULL,

	CONSTRAINT PK_Pinturas PRIMARY KEY(IDPieza),
	CONSTRAINT FK_Pinturas_IDPieza FOREIGN KEY (IDPieza) REFERENCES Piezas(ID) ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE Llantas
(
	IDPieza int NOT NULL,
	Pulgadas tinyint NOT NULL,

	CONSTRAINT PK_Llantas PRIMARY KEY(IDPieza),
	CONSTRAINT FK_Llantas_IDPieza FOREIGN KEY (IDPieza) REFERENCES Piezas(ID) ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE Configuraciones
(
	ID uniqueidentifier DEFAULT NEWID() NOT NULL,
	Usuario varchar(25) NOT NULL,
	Fecha datetime NULL, 
	MarcaCoche varchar(20) NOT NULL,
	ModeloCoche varchar(25) NOT NULL,
	IDMotor int NULL,
	IDLlantas int NULL,
	IDPintura int NULL,

	CONSTRAINT PK_Configuraciones PRIMARY KEY (ID),
	CONSTRAINT FK_Configuraciones_Usuario FOREIGN KEY (Usuario) REFERENCES Cuentas(NombreUsuario) ON DELETE NO ACTION ON UPDATE CASCADE, -- Se deberá borrar la cuenta con un procedimiento
	CONSTRAINT FK_Configuraciones_Coche FOREIGN KEY (MarcaCoche, ModeloCoche) REFERENCES Coches(Marca, Modelo) ON DELETE CASCADE ON UPDATE CASCADE ,
	CONSTRAINT FK_Configuraciones_Motor FOREIGN KEY (IDMotor) REFERENCES Motores(IDPieza) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT FK_Configuraciones_Llantas FOREIGN KEY (IDLlantas) REFERENCES Llantas(IDPieza) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT FK_Configuraciones_Pintura FOREIGN KEY (IDPintura) REFERENCES Pinturas(IDPieza) ON DELETE NO ACTION ON UPDATE NO ACTION
)

CREATE TABLE PiezasConfiguracionCoche
(
	IDPieza int NOT NULL,
	IDConfiguracion uniqueidentifier NOT NULL,

	CONSTRAINT PK_PiezasConfiguracionCoche PRIMARY KEY (IDPieza, IDConfiguracion),
	CONSTRAINT FK_PiezasConfiguracionCoche_IDPieza FOREIGN KEY (IDPieza) REFERENCES Piezas(ID) ON DELETE CASCADE ON UPDATE CASCADE, 
	CONSTRAINT FK_PiezasConfiguracionCoche_IDConfiguracion FOREIGN KEY (IDConfiguracion) REFERENCES Configuraciones(ID) ON DELETE CASCADE ON UPDATE CASCADE 
)

CREATE TABLE Votaciones
(
	ID uniqueidentifier DEFAULT NEWID() NOT NULL,
	Calificacion tinyint NOT NULL,
	Fecha datetime NOT NULL,
	IDConfiguracion uniqueidentifier NOT NULL,
	Usuario varchar(25) NOT NULL,

	CONSTRAINT PK_Votaciones PRIMARY KEY (ID),
	CONSTRAINT FK_Votaciones_IDConfiguracion FOREIGN KEY (IDConfiguracion) REFERENCES Configuraciones(ID) ON DELETE NO ACTION ON UPDATE CASCADE,
	CONSTRAINT FK_Votaciones_Usuario FOREIGN KEY (Usuario) REFERENCES Cuentas(NombreUsuario) ON DELETE NO ACTION ON UPDATE NO ACTION, -- Se deberá borrar la cuenta con un procedimiento
	CONSTRAINT UQ_Votaciones_Usuario1SolaVotacion UNIQUE (IDConfiguracion, Usuario)
)

CREATE TABLE PiezasCoches
(
	IDPieza int NOT NULL,
	MarcaCoche varchar(20) NOT NULL,
	ModeloCoche varchar(25) NOT NULL,

	CONSTRAINT PK_PiezasCoches PRIMARY KEY (IDPieza, MarcaCoche, ModeloCoche),
	CONSTRAINT FK_PiezasCoches_IDPieza FOREIGN KEY (IDPieza) REFERENCES Piezas(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FK_PiezasCochces_MarcaModeloCoche FOREIGN KEY (MarcaCoche, ModeloCoche) REFERENCES Coches(Marca, Modelo) ON DELETE CASCADE ON UPDATE CASCADE
)

--PROCEDIMIENTOS, TRIGGERS Y FUNCIONES

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

GO
/* INTERFAZ
Comentario: Borra una configuracion en cascada, es decir borra tambien sus votaciones y en la tabla PiezasConfiguracionCoche borra las filas asociadas a dicha 
Prototipo: PROCEDURE BorrarConfiguracion @IDConfiguracion AS uniqueidentifier
Entrada: Un uniqueidentifier con el ID de la configuración a borrar
Precondiciones: No hay
Salida: No hay
Postcondiciones: La configuración queda borrada, así como las votaciones asociadas a ella y las tuplas de la tabla PiezasConfiguracionCoche con el ID de la configuracion dada.
*/
CREATE PROCEDURE BorrarConfiguracion
	@IDConfiguracion AS uniqueidentifier
AS
BEGIN
	DELETE FROM Votaciones
	WHERE IDConfiguracion = @IDConfiguracion

	DELETE FROM PiezasConfiguracionCoche
	WHERE IDConfiguracion = @IDConfiguracion

	DELETE FROM Configuraciones
	WHERE ID = @IDConfiguracion
END --PROCEDURE

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