-- Tablas alineadas con tus entidades JPA

-- CLIENTE  (Entity: Cliente, id AUTO_INCREMENT)
CREATE TABLE IF NOT EXISTS cliente (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre   VARCHAR(100) NOT NULL,
  celular  VARCHAR(30),
  correo   VARCHAR(150) UNIQUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- COCINERO (Entity: Cocinero, @Table(name="cocineros"), PK auto)
CREATE TABLE IF NOT EXISTS cocineros (
  codigo_empleado INT AUTO_INCREMENT PRIMARY KEY,
  nombre       VARCHAR(100) NOT NULL,
  salario      DECIMAL(12,2) NOT NULL,
  especialidad VARCHAR(120),
  created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- MESERO   (Entity: Mesero, PK NO AUTO — tu entidad no usa @GeneratedValue)
CREATE TABLE IF NOT EXISTS mesero (
  codigo_empleado INT PRIMARY KEY,
  nombre    VARCHAR(100) NOT NULL,
  salario   DECIMAL(12,2) NOT NULL,
  celular   VARCHAR(30),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
