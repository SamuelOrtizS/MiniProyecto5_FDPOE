package univalle.fdpoe;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Contiene todos los datos relacionados a una Factura de Venta
 */
public class FacturaVenta implements Serializable {
    private int idFacturaVenta;
    private LocalDate fechaFacturaVenta;
    private LocalTime horaFacturaVenta;

    /**
     * Constructor con todos los datos de la Factura
     * @param idFacturaVenta entero unico para cada factura
     * @param fechaFacturaVenta LocalDate con la fecha de emision de la factura
     * @param horaFacturaVenta LocalTime con la hora de emision de la factura
     */
    public FacturaVenta(int idFacturaVenta, LocalDate fechaFacturaVenta, LocalTime horaFacturaVenta) {
        this.idFacturaVenta = idFacturaVenta;
        this.fechaFacturaVenta = fechaFacturaVenta;
        this.horaFacturaVenta = horaFacturaVenta;

    }

    public FacturaVenta() {

    }

    public int getIdFacturaVenta() {
        return idFacturaVenta;
    }

    public void setIdFacturaVenta(int idFacturaVenta) {
        this.idFacturaVenta = idFacturaVenta;
    }


    public LocalDate getFechaFacturaVenta() {
        return fechaFacturaVenta;
    }

    public void setFechaFacturaVenta(LocalDate fechaFacturaVenta) {
        this.fechaFacturaVenta = fechaFacturaVenta;
    }

    public LocalTime getHoraFacturaVenta() {
        return horaFacturaVenta;
    }

    public void setHoraFacturaVenta(LocalTime horaFacturaVenta) {
        this.horaFacturaVenta = horaFacturaVenta;
    }
}
