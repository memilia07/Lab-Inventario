package modelo;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;

public class ActivoTest {

    @Test
    public void testHardware() {
        Hardware hw = new Hardware("HW01", "Laptop Dell", LocalDate.now(), EstadoActivo.DISPONIBLE, 16, "Intel i7");
        assertEquals(130.0, hw.calcularCostoMantenimiento(), 0.001);
        assertEquals("HARDWARE", hw.obtenerTipo());
    }

    @Test
    public void testPeriferico() {
        Periferico mouse = new Periferico("PE01", "Mouse Logitech", LocalDate.now(), EstadoActivo.DISPONIBLE, "Mouse");
        assertEquals(15.0, mouse.calcularCostoMantenimiento(), 0.001);

        Periferico impresora = new Periferico("PE02", "Impresora HP", LocalDate.now(), EstadoActivo.DISPONIBLE, "Impresora");
        assertEquals(50.0, impresora.calcularCostoMantenimiento(), 0.001);
        assertEquals("PERIFERICO", impresora.obtenerTipo());
    }

    @Test
    public void testLicencia() {
        Licencia vigente = new Licencia("LI01", "Windows 11", LocalDate.now(), EstadoActivo.DISPONIBLE, LocalDate.now().plusDays(400));
        assertEquals(25.0, vigente.calcularCostoMantenimiento(), 0.001);

        Licencia porVencer = new Licencia("LI02", "Office 365", LocalDate.now(), EstadoActivo.DISPONIBLE, LocalDate.now().plusDays(10));
        assertEquals(60.0, porVencer.calcularCostoMantenimiento(), 0.001);

        Licencia vencida = new Licencia("LI03", "Antivirus", LocalDate.now(), EstadoActivo.DISPONIBLE, LocalDate.now().minusDays(5));
        assertEquals(100.0, vencida.calcularCostoMantenimiento(), 0.001);
        assertEquals("LICENCIA", vencida.obtenerTipo());
    }
}
