-- Instrucciones básicas (referencia) · La base real ya existe
CREATE DATABASE IF NOT EXISTS aseguradora;
USE aseguradora;

-- Estas definiciones son ilustrativas; en producción usamos las tablas reales existentes
CREATE TABLE IF NOT EXISTS vehiculos_asegurados (
  id_vehiculo_asegurado INT AUTO_INCREMENT PRIMARY KEY,
  dominio VARCHAR(10) NOT NULL UNIQUE,
  id_auto INT NOT NULL
);

CREATE TABLE IF NOT EXISTS polizas (
  id_poliza INT AUTO_INCREMENT PRIMARY KEY,
  id_vehiculo_asegurado INT UNIQUE,
  fecha_inicio DATE NOT NULL,
  fecha_fin DATE NOT NULL,
  monto_total_asegurado DOUBLE,
  CONSTRAINT fk_poliza_veh FOREIGN KEY (id_vehiculo_asegurado)
    REFERENCES vehiculos_asegurados(id_vehiculo_asegurado)
    ON DELETE CASCADE
);
