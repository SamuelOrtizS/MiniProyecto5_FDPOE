package univalle.fdpoe;

import java.io.Serializable;

/**
 * Contiene todos los datos relacionados a un Producto
 */
public class Producto implements Serializable {
    private int idProducto,idMarca;
    private String descripcionProducto;

    /**
     * Constructor con todos los datos del Producto
     * @param idProducto entero unico para cada producto
     * @param idMarca entero unico para marca que relaciona al producto con la marca
     * @param descripcionProducto cadena de texto que describe el producto
     */
    public Producto(int idProducto, int idMarca, String descripcionProducto) {
        this.idProducto = idProducto;
        this.idMarca = idMarca;
        this.descripcionProducto = descripcionProducto;
    }

    public Producto() {

    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }
}
