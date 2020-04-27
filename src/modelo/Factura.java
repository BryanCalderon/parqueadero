package modelo;

import java.util.Date;

/**
 *
 * @author estudiante
 */
public class Factura {

    private final int consecutivo;
    private final Carro carro;
    private final Date fechaIngreso;
    private Date fechaSalida;
    private Double totalPagar;
    private final Zona zona;
    private boolean cancelada;

    public Factura(Carro carro, Zona zona, int consecutivo) {
        this.consecutivo = consecutivo;
        this.carro = carro;
        this.zona = zona;
        this.fechaIngreso = new Date();
        this.cancelada = false;
    }

    public Factura(int consecutivo, Carro carro, Date fechaIngreso, Date fechaSalida, Double totalPagar, Zona zona, boolean cancelada) {
        this.consecutivo = consecutivo;
        this.carro = carro;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.totalPagar = totalPagar;
        this.zona = zona;
        this.cancelada = cancelada;
    }

    public int getConsecutivo() {
        return consecutivo;
    }

    public Zona getZona() {
        return zona;
    }

    public Carro getCarro() {
        return carro;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void calcularTotalPagar() {
        registrarTiempoSalida();
        long time = calcularTiempo();
        long hours = getHours(time);
        if (getMinutes(time) > 0) {
            hours++;
        }
        totalPagar = hours * zona.getTarifa();
    }

    public long calcularTiempo() {
        return fechaSalida.getTime() - fechaIngreso.getTime();
    }

    public long getHours(long time) {
        return time / (60 * 60 * 1000) % 24;
    }

    public long getMinutes(long time) {
        return time / (60 * 1000) % 60;
    }

    public String getTime() {
        long time = calcularTiempo();
        return getHours(time) + ":" + getMinutes(time);
    }

    public void registrarTiempoSalida() {
        this.fechaSalida = new Date();
    }

    public void realizarPago() {
        this.cancelada = true;
    }

    public boolean isFacturaCancelada() {
        return this.cancelada;
    }

    public Double getTotalPagar() {
        return this.totalPagar;
    }

    @Override
    public String toString() {
        return consecutivo + "," + carro.getPlaca() + "," + fechaIngreso.getTime() + "," + (fechaSalida != null ? fechaSalida.getTime() : "") + "," + (totalPagar != null ? totalPagar : "") + "," + zona.getZona() + "," + cancelada;
    }

}
