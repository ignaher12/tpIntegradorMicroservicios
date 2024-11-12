USE central_db;

CREATE TABLE Estudiante (
    libretaUniversitaria BIGINT AUTO_INCREMENT PRIMARY KEY,
    numeroDeDocumento BIGINT NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    edad INT NOT NULL,
    genero ENUM('masculino', 'femenino') NOT NULL,
    ciudadResidencia VARCHAR(255)
);
CREATE TABLE Carrera (
    carreraId INT PRIMARY KEY,
    nombreCarrera VARCHAR(255) NOT NULL,
    fechaCreacion DATETIME NOT NULL
);
CREATE TABLE Inscripcion (estudianteId INT,carreraId INT,graduado BOOLEAN NOT NULL,fecha_inscripcion DATETIME NOT NULL);

INSERT INTO Carrera (carreraId, nombreCarrera, fechaCreacion) VALUES (1, 'Psicología', '2023-09-05 16:10:00');
INSERT INTO Carrera (carreraId, nombreCarrera, fechaCreacion) VALUES (2, 'Ingeniería de Sistemas', '2023-01-15 08:00:00');
INSERT INTO Carrera (carreraId, nombreCarrera, fechaCreacion) VALUES (3, 'Medicina', '2023-03-10 14:30:00');
INSERT INTO Carrera (carreraId, nombreCarrera, fechaCreacion) VALUES (4, 'Derecho', '2023-05-22 09:45:00');
INSERT INTO Carrera (carreraId, nombreCarrera, fechaCreacion) VALUES (5, 'Arquitectura', '2023-07-18 11:20:00');
