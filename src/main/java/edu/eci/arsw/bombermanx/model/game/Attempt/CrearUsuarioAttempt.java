package edu.eci.arsw.bombermanx.model.game.Attempt;

/**
 *
 * @author kevin
 */
public class CrearUsuarioAttempt {
    
    private String nombre;
    private String correo;
    private String apodo;
    private String clave;
    private String imagen;

    public CrearUsuarioAttempt() {
    }

    public CrearUsuarioAttempt(String nombre, String correo, String apodo, String clave, String imagen) {
        this.nombre = nombre;
        this.correo = correo;
        this.apodo = apodo;
        this.clave = clave;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
}
