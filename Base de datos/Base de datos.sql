DROP DATABASE IF EXISTS institucion;
CREATE DATABASE IF NOT EXISTS institucion;
USE institucion;

-- Crear la tabla Usuario
CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(255),
    contrasena VARCHAR(255),
    nombre VARCHAR(255),
    apellidos VARCHAR(255),
    dtype CHAR(1) CHECK (dtype IN ('P', 'T', 'A'))
);

-- Crear la tabla Tutor_legal
CREATE TABLE Tutor_legal (
    usuario_id INT PRIMARY KEY,
    telef_contacto VARCHAR(20),
    domicilio VARCHAR(255),
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);

-- Crear la tabla Profesor
CREATE TABLE Profesor (
    usuario_id INT PRIMARY KEY,
    dni VARCHAR(20),
    correo VARCHAR(255),
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);

CREATE TABLE Curso (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255)
);
-- Tabla Alumno
CREATE TABLE Alumno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    apellidos VARCHAR(255),
    id_curso INT,
    FOREIGN KEY (id_curso) REFERENCES Curso(id)
);

-- Tabla Curso


-- Tabla Tutor_legal_Alumno (Relación N:M)
CREATE TABLE Tutor_legal_Alumno (
    id_tutor_legal INT,
    id_alumno INT,
    PRIMARY KEY (id_tutor_legal, id_alumno),
    FOREIGN KEY (id_tutor_legal) REFERENCES Tutor_legal(usuario_id),
    FOREIGN KEY (id_alumno) REFERENCES Alumno(id)
);

-- Tabla Profesor_Curso (Relación N:M)
CREATE TABLE Profesor_Curso (
    id_profesor INT,
    id_curso INT,
    PRIMARY KEY (id_profesor, id_curso),
    FOREIGN KEY (id_profesor) REFERENCES Profesor(usuario_id),
    FOREIGN KEY (id_curso) REFERENCES Curso(id)
);

-- Tabla Horario
CREATE TABLE Horario (
    asignatura VARCHAR(255),
    id INT AUTO_INCREMENT PRIMARY KEY,
    dia ENUM('L', 'M', 'X', 'J', 'V'),
    hora INT NOT NULL,
    id_curso INT, 
    foreign key (id_curso) references Curso(id)
);

/*-- Tabla Curso_Horario (Relación 1:1)
CREATE TABLE Curso_Horario (
    id_curso INT PRIMARY KEY,
    id_horario INT,
    FOREIGN KEY (id_curso) REFERENCES Curso(id),
    FOREIGN KEY (id_horario) REFERENCES Horario(id)
);*/

-- Tabla Faltas
CREATE TABLE Faltas (
    hora int,
    fecha DATE,
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_alumno INT,
    FOREIGN KEY (id_alumno) REFERENCES Alumno(id)
);

-- Tabla Chat
CREATE TABLE Chat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    boletin BOOLEAN
);

-- Tabla Usuario_Chat (Relación N:M)
CREATE TABLE Usuario_Chat (
    id_usuario INT,
    id_chat INT,
    PRIMARY KEY (id_usuario, id_chat),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id),
    FOREIGN KEY (id_chat) REFERENCES Chat(id)
);

-- Tabla Mensaje (Relación 1:N)
CREATE TABLE Mensaje (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_chat INT,
    contenido TEXT,
    fecha DATE,
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id),
    FOREIGN KEY (id_chat) REFERENCES Chat(id)
);

-- Añadir campo "justificada" y "contenido" a la tabla Faltas
ALTER TABLE Faltas
ADD COLUMN justificada BOOLEAN;

-- Añadir campo "foto" a la tabla Alumno
ALTER TABLE Alumno
ADD COLUMN foto VARCHAR(255);

-- Modificar la tabla Mensaje para incluir el nombre de usuario
ALTER TABLE Mensaje
ADD COLUMN nombre_usuario VARCHAR(255);

ALTER TABLE Chat
ADD COLUMN nombreChat VARCHAR(255);

-- Actualizar las restricciones de clave foránea para la tabla Mensaje
ALTER TABLE Mensaje
ADD CONSTRAINT FK_Mensaje_Usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(id);
/*DROP FOREIGN KEY FK_Mensaje_Usuario,*/


-- También, actualiza el esquema de la tabla Usuario_Chat para usar el id_usuario de la tabla Mensaje
ALTER TABLE Usuario_Chat
ADD CONSTRAINT FK_Usuario_Chat_Usuario FOREIGN KEY (id_usuario) REFERENCES Usuario(id);



