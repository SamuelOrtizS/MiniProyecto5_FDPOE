package univalle.fdpoe;

import java.io.Serializable;

/**
 * Contiene todos los datos relacionados a una Marca
 */
public class Marca implements Serializable {
    private int idMarca;
    private String nombreMarca;

    /**
     * Constructor con todos los datos del Producto
     * @param idMarca entero unico para cada marca
     * @param nombreMarca cadena con el nombre de la marca
     */
    public Marca(int idMarca, String nombreMarca) {
        this.idMarca = idMarca;
        this.nombreMarca = nombreMarca;
    }

    public Marca() {

    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }
}
