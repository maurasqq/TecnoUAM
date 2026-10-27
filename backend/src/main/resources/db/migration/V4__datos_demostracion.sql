INSERT INTO equipos (codigo_patrimonial, nombre, tipo, estado) VALUES
    ('PAT-001', 'Laptop Dell Latitude 5420', 'LAPTOP', 'DISPONIBLE'),
    ('PAT-002', 'Proyector Epson PowerLite X05', 'PROYECTOR', 'DISPONIBLE'),
    ('PAT-003', 'Tableta Samsung Galaxy Tab A8', 'TABLETA', 'DISPONIBLE'),
    ('PAT-004', 'Laptop HP ProBook 450', 'LAPTOP', 'PRESTADO');

INSERT INTO prestamos (
    equipo_id,
    solicitante,
    fecha_prestamo,
    fecha_prevista_devolucion,
    estado
)
SELECT
    id,
    'Ana Pérez',
    CURRENT_DATE - 2,
    CURRENT_DATE + 5,
    'ACTIVO'
FROM equipos
WHERE codigo_patrimonial = 'PAT-004';
