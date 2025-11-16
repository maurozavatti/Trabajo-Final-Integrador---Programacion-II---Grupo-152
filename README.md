# Grupo152_Vehiculo--SeguroVehicular

**TFI – Programación II** · Relación 1→1 unidireccional (Vehículo → SeguroVehicular) · JDBC + DAO + MySQL · Menú consola (ES)

**Integrantes**: Mauro Zavatti · Rodrigo Agüero · Francisco Alarcón

## Requisitos
- Java 21
- NetBeans 21 (o compatible)
- MySQL Server 8.x
- Driver JDBC: `mysql-connector-j-8.4.0.jar`

## Configuración de conexión
Archivo: `src/config/database.properties`
```properties
db.url=jdbc:mysql://localhost:3306/aseguradora?useSSL=false&serverTimezone=UTC
db.user=grupo152
db.password=abc$123
db.driver=com.mysql.cj.jdbc.Driver
```

> **Nota**: Agregar el `mysql-connector-j-8.4.0.jar` al classpath del proyecto (NetBeans: clic derecho en el proyecto → Properties → Libraries → Add JAR/Folder).

## Estructura
```
src/
 ├─ config/ (DatabaseConnection, properties)
 ├─ entities/ (Vehiculo, SeguroVehicular)
 ├─ dao/ (GenericDao, VehiculoDao, SeguroVehicularDao)
 ├─ service/ (GenericService, VehiculoService, SeguroVehicularService)
 └─ main/ (AppMenu, MainApp)
```

## Scripts SQL básicos
> *La base real ya existe y no se modifica.* Estos scripts son de referencia.
```sql
CREATE DATABASE IF NOT EXISTS aseguradora;
USE aseguradora;

-- Vehículos asegurados
CREATE TABLE IF NOT EXISTS vehiculos_asegurados (
  id_vehiculo_asegurado INT AUTO_INCREMENT PRIMARY KEY,
  dominio VARCHAR(10) NOT NULL UNIQUE,
  id_auto INT NOT NULL
);

-- Pólizas
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
```

## Ejecución
1. Abrir en NetBeans.
2. Agregar `mysql-connector-j-8.4.0.jar` al classpath.
3. Ejecutar `main/MainApp.java`.
4. Menú (consola):
   ```
   1. Crear vehículo + seguro (transacción)
   2. Listar vehículos
   3. Buscar vehículo por ID
   4. Actualizar vehículo
   5. Eliminar vehículo (y su seguro si existe)
   X. Salir
   ```

## Transacciones
- `VehiculoService.crearVehiculoConSeguro(...)` usa una **única conexión** con `setAutoCommit(false)`. Si cualquier paso falla, se hace `rollback()`.

## Notas
- "Eliminado" es **lógico y solo en memoria** (no persiste en BD).
- Validaciones mínimas: dominio requerido, `fecha_fin` > `fecha_inicio`.
- Consultas parametrizadas con `PreparedStatement`.

## Video YouTube
- https://youtu.be/6gtUfe6Zaik
