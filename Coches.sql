CREATE 
--DROP
DATABASE Coches

USE Coches

IF EXISTS (SELECT * FROM Votaciones)
BEGIN
	print 'si'
END
ELSE
BEGIN
	print 'no'
END

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

CREATE TABLE Configuraciones
(
	ID uniqueidentifier DEFAULT NEWID() NOT NULL,
	Usuario varchar(25) NOT NULL,
	Fecha datetime NULL, 
	MarcaCoche varchar(20) NOT NULL,
	ModeloCoche varchar(25) NOT NULL,

	CONSTRAINT PK_Configuraciones PRIMARY KEY (ID),
	CONSTRAINT FK_Configuraciones_Usuario FOREIGN KEY (Usuario) REFERENCES Cuentas(NombreUsuario) ON DELETE NO ACTION ON UPDATE CASCADE, -- Se deberá borrar la cuenta con un procedimiento
	CONSTRAINT FK_Configuraciones_Coche FOREIGN KEY (MarcaCoche, ModeloCoche) REFERENCES Coches(Marca, Modelo) ON DELETE CASCADE ON UPDATE CASCADE 
)

CREATE TABLE Piezas
(
	ID int,
	Nombre varchar(30),
	Descripcion varchar(max),
	Precio smallmoney,
	Tipo varchar(10),

	CONSTRAINT PK_Piezas PRIMARY KEY (ID)
)

CREATE TABLE PiezasConfiguracionCoche
(
	--MarcaCoche varchar(20) NOT NULL,
	--ModeloCoche varchar(25) NOT NULL,
	IDPieza int NOT NULL,
	IDConfiguracion uniqueidentifier NOT NULL,

	CONSTRAINT PK_PiezasConfiguracionCoche PRIMARY KEY (IDPieza, IDConfiguracion),
	--CONSTRAINT FK_PiezasConfiguracionCoche_MarcaCoche FOREIGN KEY (MarcaCoche, ModeloCoche) REFERENCES Coches(Marca, Modelo) ON DELETE NO ACTION ON UPDATE CASCADE,
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
	CONSTRAINT FK_Votaciones_Usuario FOREIGN KEY (Usuario) REFERENCES Cuentas(NombreUsuario) ON DELETE NO ACTION ON UPDATE NO ACTION -- Se deberá borrar la cuenta con un procedimiento
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

CREATE TABLE PiezasCoches
(
	IDPieza int NOT NULL,
	MarcaCoche varchar(20) NOT NULL,
	ModeloCoche varchar(25) NOT NULL,

	CONSTRAINT PK_PiezasCoches PRIMARY KEY (IDPieza, MarcaCoche, ModeloCoche),
	CONSTRAINT FK_PiezasCoches_IDPieza FOREIGN KEY (IDPieza) REFERENCES Piezas(ID) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT FK_PiezasCochces_MarcaModeloCoche FOREIGN KEY (MarcaCoche, ModeloCoche) REFERENCES Coches(Marca, Modelo) ON DELETE CASCADE ON UPDATE CASCADE
)