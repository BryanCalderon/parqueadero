package modelo;

/**
 *
 * @author estudiante
 */
public class Zona {

    private final String zona;
    private final Double tarifa;
    private final int capacidad;
    private int espaciosOcupados;

    public Zona(String zona, Double tarifa, int capacidad) {
        this.zona = zona;
        this.tarifa = tarifa;
        this.capacidad = capacidad;
        this.espaciosOcupados = 0;
    }

    public String getZona() {
        return zona;
    }

    public Double getTarifa() {
        return tarifa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getEspaciosOcupados() {
        return espaciosOcupados;
    }

    public void ocuparEspacio() {
        this.espaciosOcupados++;
    }

    public void liberarEspacio() {
        this.espaciosOcupados--;
    }

    public int obtenerEspacioDisponible() {
        return this.capacidad - this.espaciosOcupados;
    }

    public boolean hayDisponibilidad() {
        return this.espaciosOcupados < this.capacidad;
    }

    @Override
    public String toString() {
        return zona;
    }

    public String getString() {
        return zona + "," + tarifa + "," + capacidad;
    }

}
