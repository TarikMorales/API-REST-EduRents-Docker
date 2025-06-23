-- =============================
-- Inserción de roles
-- =============================
INSERT INTO roles (nombre)
SELECT 'ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'ADMIN');

INSERT INTO roles (nombre)
SELECT 'USER'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'USER');

INSERT INTO roles (nombre)
SELECT 'SELLER'
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'SELLER');


-- =============================
-- Inserción de categorías
-- =============================
INSERT INTO categorias (nombre)
SELECT 'Tecnología'
    WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nombre = 'Tecnología');

INSERT INTO categorias (nombre)
SELECT 'Libros'
    WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nombre = 'Libros');

INSERT INTO categorias (nombre)
SELECT 'Electrodomésticos'
    WHERE NOT EXISTS (SELECT 1 FROM categorias WHERE nombre = 'Electrodomésticos');


-- =============================
-- Inserción de carreras
-- =============================
INSERT INTO carreras (nombre, codigo)
SELECT 'Ingeniería de Sistemas', 'IS01'
    WHERE NOT EXISTS (SELECT 1 FROM carreras WHERE codigo = 'IS01');

INSERT INTO carreras (nombre, codigo)
SELECT 'Administración de Empresas', 'AE01'
    WHERE NOT EXISTS (SELECT 1 FROM carreras WHERE codigo = 'AE01');


-- =============================
-- Inserción de cursos
-- =============================
INSERT INTO cursos (nombre, codigo)
SELECT 'Estructuras de Datos', 'CS201'
    WHERE NOT EXISTS (SELECT 1 FROM cursos WHERE codigo = 'CS201');

INSERT INTO cursos (nombre, codigo)
SELECT 'Contabilidad Financiera', 'AF101'
    WHERE NOT EXISTS (SELECT 1 FROM cursos WHERE codigo = 'AF101');

INSERT INTO cursos (nombre, codigo)
SELECT 'Programación Avanzada', 'CS301'
    WHERE NOT EXISTS (SELECT 1 FROM cursos WHERE codigo = 'CS301');


-- =============================
-- Inserción en cursos_carreras
-- =============================
-- Solo si las combinaciones no existen aún (usamos subquery con los IDs exactos)
INSERT INTO cursos_carreras (id_curso, id_carrera)
SELECT c.id, ca.id
FROM cursos c, carreras ca
WHERE c.codigo = 'CS201' AND ca.codigo = 'IS01'
  AND NOT EXISTS (
    SELECT 1 FROM cursos_carreras cc
    WHERE cc.id_curso = c.id AND cc.id_carrera = ca.id
);

INSERT INTO cursos_carreras (id_curso, id_carrera)
SELECT c.id, ca.id
FROM cursos c, carreras ca
WHERE c.codigo = 'CS301' AND ca.codigo = 'IS01'
  AND NOT EXISTS (
    SELECT 1 FROM cursos_carreras cc
    WHERE cc.id_curso = c.id AND cc.id_carrera = ca.id
);

INSERT INTO cursos_carreras (id_curso, id_carrera)
SELECT c.id, ca.id
FROM cursos c, carreras ca
WHERE c.codigo = 'AF101' AND ca.codigo = 'AE01'
  AND NOT EXISTS (
    SELECT 1 FROM cursos_carreras cc
    WHERE cc.id_curso = c.id AND cc.id_carrera = ca.id
);

