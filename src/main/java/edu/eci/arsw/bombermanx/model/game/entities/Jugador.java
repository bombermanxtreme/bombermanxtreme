package edu.eci.arsw.bombermanx.model.game.entities;

/**
 *
 * @author Kvn CF <ECI>
 */
public class Jugador {

    private String nombre;
    private String correo;
    private String apodo;
    private String clave;
    private String imagen;
    private int record;

    /**
     *
     * @param nombre
     * @param correo
     * @param apodo
     * @param clave
     * @param imagen
     */
    public Jugador(String nombre, String correo, String apodo, String clave, String imagen) {
        this.nombre = nombre;
        this.correo = correo;
        this.apodo = apodo;
        this.clave = clave;
        this.imagen = imagen;
        this.record = record <= 0 ? 0 : record;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getClave() {
        return clave;
    }

    public int getRecord() {
        return record;
    }

    public String getApodo() {
        return apodo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "{\"Nombre\":\"" + nombre + "\", \"Record\":\"" + record + "\", Correo\":\"" + correo + "\", url\":\"" + imagen + "\", Apodo\":\"" + apodo + "\"}";
    }
}
