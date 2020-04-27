package modelo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author usuario
 */
public class ParqueaderoTest {

    public ParqueaderoTest() {
    }

    /**
     * Test of getZona method, of class Parqueadero.
     */
    @Test
    public void testGetZona() {
        System.out.println("getZona");
        String zona = "zona 1";
        Parqueadero parqueadero = new Parqueadero();
        parqueadero.loadFile();
        Zona expResult = new Zona("zona 1", 1500.0, 10);
        Zona result = parqueadero.getZona(zona);
        assertEquals(expResult.getZona(), result.getZona());
    }

    /**
     * Test of getZona method, of class Parqueadero.
     */
    @Test
    public void testGetZonaNotFound() {
        System.out.println("testGetZonaNotFound");
        String zona = "zona 22";
        Parqueadero parqueadero = new Parqueadero();
        parqueadero.loadFile();
        Zona result = parqueadero.getZona(zona);
        assertNull(result);
    }

    /**
     * Test of reservarCupo method, of class Parqueadero.
     */
    @Test
    public void testReservarCupo() {
        System.out.println("reservarCupo");
        String nombreZona = "zona 1";
        String placa = "placa-978";
        Parqueadero parqueadero = new Parqueadero();
        parqueadero.loadFile();
        parqueadero.reservarCupo(nombreZona, placa);
        assertNotNull(parqueadero.getFacturaByPlaca(placa));
    }

}
