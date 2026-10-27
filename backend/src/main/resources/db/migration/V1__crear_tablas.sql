CREATE TABLE equipos (
                         id BIGSERIAL PRIMARY KEY,
                         codigo_patrimonial VARCHAR(255) NOT NULL UNIQUE,
                         nombre VARCHAR(255) NOT NULL,
                         tipo VARCHAR(255) NOT NULL,
                         estado VARCHAR(20) NOT NULL DEFAULT 'DISPONIBLE',
                         CONSTRAINT ck_estado_equipo
                             CHECK (estado IN ('DISPONIBLE', 'PRESTADO'))
);

CREATE TABLE prestamos (
                           id BIGSERIAL PRIMARY KEY,
                           equipo_id BIGINT NOT NULL REFERENCES equipos(id),
                           solicitante VARCHAR(255) NOT NULL,
                           fecha_prestamo DATE NOT NULL,
                           fecha_prevista_devolucion DATE NOT NULL,
                           estado VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
                           CONSTRAINT ck_estado_prestamo
                               CHECK (estado IN ('ACTIVO', 'DEVUELTO')),
                           CONSTRAINT ck_fechas_prestamo
                               CHECK (fecha_prevista_devolucion >= fecha_prestamo)
);

CREATE UNIQUE INDEX ux_prestamo_activo_equipo
    ON prestamos(equipo_id)
    WHERE estado = 'ACTIVO'