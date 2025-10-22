-- Datos de ejemplo para validar selects

INSERT INTO cliente (nombre, celular, correo) VALUES
('Ana', '3001112222', 'ana@ejemplo.com'),
('Luis', '3003334444', 'luis@ejemplo.com')
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), celular = VALUES(celular);

INSERT INTO cocineros (nombre, salario, especialidad) VALUES
('Carlos', 2500000.00, 'Pastas'),
('María',  2900000.00, 'Parrilla');

-- Ojo: Mesero exige que TU pongas el código (no es auto incremental)
INSERT INTO mesero (codigo_empleado, nombre, salario, celular) VALUES
(101, 'Pedro', 1800000.00, '3015556666'),
(102, 'Sofía', 1850000.00, '3027778888')
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), salario = VALUES(salario), celular = VALUES(celular);

-- Visualizaciones
SELECT * FROM cliente ORDER BY id;
SELECT * FROM cocineros ORDER BY codigo_empleado;
SELECT * FROM mesero ORDER BY codigo_empleado;
