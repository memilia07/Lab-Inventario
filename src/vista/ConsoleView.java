package vista;

import controlador.ActivoController;
import controlador.MantenimientoController;
import modelo.Activo;
import modelo.EstadoActivo;
import modelo.Hardware;
import modelo.Licencia;
import modelo.Periferico;
import modelo.RegistroMantenimiento;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private final Scanner sc = new Scanner(System.in);
    private final ActivoController activoController;
    private final MantenimientoController mantenimientoController;

    public ConsoleView(ActivoController activoController, MantenimientoController mantenimientoController) {
        this.activoController = activoController;
        this.mantenimientoController = mantenimientoController;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Opcion: ");
            try {
                procesarOpcion(opcion);
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n===== MENU INVENTARIO =====");
        System.out.println("1. Registrar Hardware");
        System.out.println("2. Registrar Periferico");
        System.out.println("3. Registrar Licencia");
        System.out.println("4. Listar activos");
        System.out.println("5. Buscar activo");
        System.out.println("6. Asignar activo");
        System.out.println("7. Eliminar activo");
        System.out.println("8. Registrar mantenimiento");
        System.out.println("9. Ver historial de mantenimiento");
        System.out.println("10. Costo total de mantenimiento");
        System.out.println("0. Salir");
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 ->
                registrarHardware();
            case 2 ->
                registrarPeriferico();
            case 3 ->
                registrarLicencia();
            case 4 ->
                listarActivos();
            case 5 ->
                buscarActivo();
            case 6 ->
                asignarActivo();
            case 7 ->
                eliminarActivo();
            case 8 ->
                registrarMantenimiento();
            case 9 ->
                verHistorial();
            case 10 ->
                verCostoTotal();
            case 0 ->
                System.out.println("Saliendo...");
            default ->
                System.out.println("Opcion invalida");
        }
    }

    private void registrarHardware() {
        System.out.println("--- Registrar Hardware ---");
        String codigo = leerTexto("Codigo: ");
        String nombre = leerTexto("Nombre: ");
        int ram = leerEntero("RAM (GB): ");
        String procesador = leerTexto("Procesador: ");
        Activo activo = new Hardware(codigo, nombre, LocalDate.now(), EstadoActivo.DISPONIBLE, ram, procesador);
        activoController.registrar(activo);
        System.out.println("Hardware registrado.");
    }

    private void registrarPeriferico() {
        System.out.println("--- Registrar Periferico ---");
        String codigo = leerTexto("Codigo: ");
        String nombre = leerTexto("Nombre: ");
        String tipo = leerTexto("Tipo periferico (Mouse/Teclado/Monitor/Impresora): ");
        Activo activo = new Periferico(codigo, nombre, LocalDate.now(), EstadoActivo.DISPONIBLE, tipo);
        activoController.registrar(activo);
        System.out.println("Periferico registrado.");
    }

    private void registrarLicencia() {
        System.out.println("--- Registrar Licencia ---");
        String codigo = leerTexto("Codigo: ");
        String nombre = leerTexto("Nombre: ");
        int dias = leerEntero("Dias hasta expiracion: ");
        LocalDate fechaExpiracion = LocalDate.now().plusDays(dias);
        Activo activo = new Licencia(codigo, nombre, LocalDate.now(), EstadoActivo.DISPONIBLE, fechaExpiracion);
        activoController.registrar(activo);
        System.out.println("Licencia registrada.");
    }

    private void listarActivos() {
        List<Activo> activos = activoController.listar();
        if (activos.isEmpty()) {
            System.out.println("No hay activos registrados.");
            return;
        }
        activos.forEach(System.out::println);
    }

    private void buscarActivo() {
        String codigo = leerTexto("Codigo a buscar: ");
        System.out.println(activoController.buscar(codigo));
    }

    private void asignarActivo() {
        String codigo = leerTexto("Codigo a asignar: ");
        activoController.asignar(codigo);
        System.out.println("Activo asignado.");
    }

    private void eliminarActivo() {
        String codigo = leerTexto("Codigo a eliminar: ");
        activoController.eliminar(codigo);
        System.out.println("Activo eliminado.");
    }

    private void registrarMantenimiento() {
        String codigo = leerTexto("Codigo de activo: ");
        RegistroMantenimiento registro = mantenimientoController.registrarMantenimiento(codigo);
        System.out.println("Mantenimiento registrado: " + registro);
    }

    private void verHistorial() {
        String codigo = leerTexto("Codigo de activo: ");
        List<RegistroMantenimiento> historial = mantenimientoController.historialDe(codigo);
        if (historial.isEmpty()) {
            System.out.println("Sin historial de mantenimiento.");
            return;
        }
        historial.forEach(System.out::println);
    }

    private void verCostoTotal() {
        System.out.printf("Costo total de mantenimiento: %.2f%n", mantenimientoController.costoTotal());
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un numero valido: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }
}
