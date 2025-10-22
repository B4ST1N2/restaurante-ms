-- Elimina toda la base de datos creada por Terraform (para la evidencia de drop total)
SET FOREIGN_KEY_CHECKS=0;
DROP DATABASE IF EXISTS restaurante_db;
SET FOREIGN_KEY_CHECKS=1;
