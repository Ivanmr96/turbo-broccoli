INSERT INTO Coches VALUES ('Mercedes', 'Clase A', 29500);

SELECT * FROM Configuraciones

INSERT INTO PiezasCoches VALUES (1, 'Mercedes', 'Clase A');

INSERT INTO Configuraciones VALUES (NEWID(), 'testuser', '2019-9-6 15:31:47', NULL, NULL)

DELETE FROM Configuraciones WHERE ID = '137D8ABC-FA8C-458D-891A-25B76CDD96BD'

print NEWID()

SELECT * FROM Motores
SELECT * FROM Piezas
SELECT * FROM Cuentas

BEGIN TRAN
DELETE FROM Coches WHERE Modelo = 'A1'
SELECT * FROM Coches
SELECT * FROM Configuraciones
SELECT * FROM Votaciones
ROLLBACK

DELETE FROM Piezas WHERE ID = 10

BEGIN TRAN
INSERT INTO Motores (IDPieza, Traccion, NumeroVelocidades, Autonomia, Potencia) VALUES (4, 'D', 5, 200, 110);
INSERT INTO Piezas (ID, Nombre, Descripcion, Precio) VALUES (15, 'Juan', 'claro', 25);
INSERT INTO Motores (IDPieza, Traccion, NumeroVelocidades, Autonomia, Potencia, Tipo) VALUES (3, 'D', 5, 250, 115, 'G');
INSERT INTO Piezas VALUES (15, 'illo ke', 'sis', 13221, 'motor');
ROLLBACK