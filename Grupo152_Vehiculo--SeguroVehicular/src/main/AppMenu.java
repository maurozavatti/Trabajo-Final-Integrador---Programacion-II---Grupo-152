package main;

import entities.SeguroVehicular;
import entities.Vehiculo;
import service.VehiculoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AppMenu {

    private final VehiculoService vehiculoService = new VehiculoService();
    private final Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        String opcion;
        do {
            imprimirMenu();
            opcion = scanner.nextLine().trim().toUpperCase();

            try {
                switch (opcion) {
                    case "1": crearVehiculoConSeguro(); break;
                    case "2": listarVehiculos(); break;
                    case "3": buscarVehiculoPorId(); break;
                    case "4": actualizarVehiculo(); break;
                    case "5": eliminarVehiculo(); break;
                    case "X": System.out.println("Saliendo..."); break;
                    default: System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }

        } while (!"X".equals(opcion));
    }

    private void imprimirMenu() {
        System.out.println("\n=== Menú Vehículo / Seguro Vehicular ===");
        System.out.println("1. Crear vehículo + seguro (transacción)");
        System.out.println("2. Listar vehículos");
        System.out.println("3. Buscar vehículo por ID");
        System.out.println("4. Actualizar vehículo");
        System.out.println("5. Eliminar vehículo (y su seguro si existe)");
        System.out.println("X. Salir");
        System.out.print("Ingrese opción: ");
    }

    private void crearVehiculoConSeguro() throws Exception {
        System.out.print("Dominio (patente): ");
        String dominio = scanner.nextLine().trim();

        System.out.print("ID de auto (FK a tabla autos): ");
        int idAuto = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Fecha inicio póliza (YYYY-MM-DD): ");
        LocalDate inicio = LocalDate.parse(scanner.nextLine().trim());

        System.out.print("Fecha fin póliza (YYYY-MM-DD): ");
        LocalDate fin = LocalDate.parse(scanner.nextLine().trim());

        System.out.print("Monto total asegurado (opcional, Enter para null): ");
        String montoTxt = scanner.nextLine().trim();
        Double monto = montoTxt.isBlank() ? null : Double.parseDouble(montoTxt);

        Vehiculo v = new Vehiculo(null, dominio, idAuto);
        SeguroVehicular s = new SeguroVehicular(null, null, inicio, fin, monto);

        vehiculoService.crearVehiculoConSeguro(v, s);
        System.out.println("✅ Vehículo y póliza creados con éxito.");
    }

    private void listarVehiculos() throws Exception {
        List<Vehiculo> list = vehiculoService.getAll();
        if (list.isEmpty()) {
            System.out.println("No hay vehículos cargados.");
            return;
        }
        list.forEach(v -> {
            String resumenSeguro = (v.getSeguro() == null) ? "(sin póliza)" :
                    "Póliza ID " + v.getSeguro().getIdPoliza() + " | Vigencia " + v.getSeguro().getFechaInicio() + " → " + v.getSeguro().getFechaFin();
            System.out.printf("#%d | %s | id_auto=%d | %s%n",
                    v.getIdVehiculoAsegurado(), v.getDominio(), v.getIdAuto(), resumenSeguro);
        });
    }

    private void buscarVehiculoPorId() throws Exception {
        System.out.print("ID de vehiculo_asegurado: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Optional<Vehiculo> opt = vehiculoService.getById(id);
        if (opt.isPresent()) {
            Vehiculo v = opt.get();
            System.out.println(v);
            if (v.getSeguro() != null) {
                System.out.println("Seguro: " + v.getSeguro());
            } else {
                System.out.println("Sin póliza asociada.");
            }
        } else {
            System.out.println("No existe un vehículo con ese ID.");
        }
    }

    private void actualizarVehiculo() throws Exception {
        System.out.print("ID de vehiculo_asegurado a actualizar: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Optional<Vehiculo> opt = vehiculoService.getById(id);
        if (opt.isEmpty()) {
            System.out.println("No existe.");
            return;
        }
        Vehiculo v = opt.get();
        System.out.println("Dominio actual: " + v.getDominio());
        System.out.print("Nuevo dominio (enter para mantener): ");
        String dom = scanner.nextLine().trim();
        if (!dom.isBlank()) v.setDominio(dom);

        System.out.println("id_auto actual: " + v.getIdAuto());
        System.out.print("Nuevo id_auto (enter para mantener): ");
        String idAutoTxt = scanner.nextLine().trim();
        if (!idAutoTxt.isBlank()) v.setIdAuto(Integer.parseInt(idAutoTxt));

        vehiculoService.actualizar(v);
        System.out.println("✅ Vehículo actualizado.");
    }

    private void eliminarVehiculo() throws Exception {
        System.out.print("ID de vehiculo_asegurado a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        boolean ok = vehiculoService.eliminar(id);
        System.out.println(ok ? "✅ Eliminado correctamente." : "No se encontró el vehículo.");
    }
}
