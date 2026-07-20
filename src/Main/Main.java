package Main;

import controlador.ActivoController;
import controlador.MantenimientoController;
import repositorio.ActivoRepository;
import repositorio.ActivoRepositorySQLite;
import repositorio.MantenimientoRepository;
import repositorio.MantenimientoRepositorySQLite;
import servicio.ActivoServiceImpl;
import servicio.IActivoService;
import servicio.IMantenimientoService;
import servicio.MantenimientoServiceImpl;
import vista.ConsoleView;

public class Main {

    public static void main(String[] args) {
        ActivoRepository activoRepository = new ActivoRepositorySQLite();
        MantenimientoRepository mantenimientoRepository = new MantenimientoRepositorySQLite();

        IActivoService activoService = new ActivoServiceImpl(activoRepository);
        IMantenimientoService mantenimientoService = new MantenimientoServiceImpl(mantenimientoRepository, activoRepository);

        ActivoController activoController = new ActivoController(activoService);
        MantenimientoController mantenimientoController = new MantenimientoController(mantenimientoService);

        ConsoleView vista = new ConsoleView(activoController, mantenimientoController);
        vista.iniciar();
    }
}