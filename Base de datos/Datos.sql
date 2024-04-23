-- Valores de ejemplo para la tabla Usuario
INSERT INTO Usuario (usuario, contrasena, nombre, apellidos, dtype) VALUES
('usuario1', 'password1', 'Juan', 'Gomez', 'P'),
('usuario2', 'password2', 'Maria', 'Lopez', 'T'),
('usuario3', 'password3', 'Pedro', 'Martinez', 'P'),
('usuario4', 'password4', 'Jose', 'Fernandez', 'T'),
('usuario5', 'password5', 'Eduardo', 'Martinez', 'P');

-- Valores de ejemplo para la tabla Tutor_legal
INSERT INTO Tutor_legal (usuario_id, telef_contacto, domicilio) VALUES
(1mensaje, '123456789', 'Calle Mayor, 123'),
(2, '987654321', 'Avenida Libertad, 456'),
(3, '555555555', 'Plaza Espana, 789');

-- Valores de ejemplo para la tabla Profesor
INSERT INTO Profesor (usuario_id, dni, correo) VALUES
(4, '12345678A', 'juan@example.com'),
(5, '87654321B', 'maria@example.com');

-- Valores de ejemplo para la tabla Curso
INSERT INTO Curso (nombre) VALUES
('DAM 2B'),
('DAW 1A'),
('3o ESO A');
-- Valores de ejemplo para la tabla Alumno
INSERT INTO Alumno (nombre, apellidos, id_curso) VALUES
('Luis', 'Gomez Gutierrez', 1),
('Ana', 'Fernandez Perez', 1),
('Carlos', 'Ruiz Garcia', 2);



-- Valores de ejemplo para la tabla Tutor_legal_Alumno
INSERT INTO Tutor_legal_Alumno (id_tutor_legal, id_alumno) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Valores de ejemplo para la tabla Profesor_Curso
INSERT INTO Profesor_Curso (id_profesor, id_curso) VALUES
(4, 1),
(5, 2);

-- Valores de ejemplo para la tabla Horario
INSERT INTO Horario (asignatura, dia, hora, id_curso) VALUES
('Matematicas', 'L', '1', 1),
('Historia', 'M', '2', 1),
('Ciencias Naturales', 'X', '3', 1);

/*-- Valores de ejemplo para la tabla Curso_Horario
INSERT INTO Curso_Horario (id_curso, id_horario) VALUES
(1, 1),
(2, 2),
(3, 3);*/

-- Valores de ejemplo para la tabla Faltas
INSERT INTO Faltas (hora, fecha, id_alumno) VALUES
('1', '2024-03-01', 1),
('2', '2024-03-02', 2),
('3', '2024-03-03', 3);

-- Valores de ejemplo para la tabla Chat
INSERT INTO Chat (boletin) VALUES
(FALSE),
(FALSE),
(TRUE);

-- Valores de ejemplo para la tabla Usuario_Chat
INSERT INTO Usuario_Chat (id_usuario, id_chat) VALUES
(1, 1),
(2, 1),
(3, 2);

-- Valores de ejemplo para la tabla Mensaje
INSERT INTO Mensaje (id_usuario, id_chat, contenido, fecha) VALUES
(1, 1, 'Hola, ¿como estas?', '2024-03-01'),
(2, 1, 'Bien, gracias. ¿Y tu?', '2024-03-01'),
(3, 2, '¿Alguien puede ayudarme con la tarea?', '2024-03-02');

SELECT * FROM profesor;

SELECT * FROM profesor_curso;

ALTER TABLE Faltas MODIFY hora INT NOT NULL;

