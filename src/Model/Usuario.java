package Model;

public class Usuario {
    private long id_usuario;
    private String nombre;
    private String rol;

    public Usuario(long id_usuario, String nombre, String rol) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.rol = rol;
    }

    public Usuario () {

    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
