package univalle.fdpoe;

import java.io.Serializable;

/**
 * Contiene todos los datos relacionados al Detalle de una Factura de Venta
 */
public class DetalleFactura implements Serializable {
    private int idDetalleFactura, idFacturaVenta, idProducto, cantidadProductos, valorProducto;

    /**
     * Constructor con todos los datos del Detalle de la Factura
     * @param idDetalleFactura entero unico para cada DetalleFactura
     * @param idFacturaVenta entero unico para Factura y relaciona al detalle con la factura
     * @param idProducto entero unico para Producto y relaciona al detalle con el producto
     * @param cantidadProductos entero que representa la cantidad adquirida del producto
     * @param valorProducto entero que representa el valor del producto adquirido
     */
    public DetalleFactura(int idDetalleFactura, int idFacturaVenta, int idProducto, int cantidadProductos, int valorProducto) {
        this.idDetalleFactura = idDetalleFactura;
        this.idFacturaVenta = idFacturaVenta;
        this.idProducto = idProducto;
        this.cantidadProductos = cantidadProductos;
        this.valorProducto = valorProducto;
    }

    public DetalleFactura() {

    }


    public int getIdDetalleFactura() {
        return idDetalleFactura;
    }

    public void setIdDetalleFactura(int idDetalleFactura) {
        this.idDetalleFactura = idDetalleFactura;
    }

    public int getIdFacturaVenta() {
        return idFacturaVenta;
    }

    public void setIdFacturaVenta(int idFacturaVenta) {
        this.idFacturaVenta = idFacturaVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(int cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    public int getValorProducto() {
        return valorProducto;
    }

    public void setValorProducto(int valorProducto) {
        this.valorProducto = valorProducto;
    }
}
