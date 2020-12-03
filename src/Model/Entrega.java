package Model;

public class Entrega {
    private long id_entrega;
    private String nombre_paquete;
    private String nombre_cliente;
    private String nombre_proveedor;

    public Entrega(long id_entrega, String nombre_paquete, String nombre_cliente, String nombre_proveedor) {
        this.id_entrega = id_entrega;
        this.nombre_paquete = nombre_paquete;
        this.nombre_cliente = nombre_cliente;
        this.nombre_proveedor = nombre_proveedor;
    }

    public long getId_entrega() {
        return id_entrega;
    }

    public void setId_entrega(long id_entrega) {
        this.id_entrega = id_entrega;
    }

    public String getNombre_paquete() {
        return nombre_paquete;
    }

    public void setNombre_paquete(String nombre_paquete) {
        this.nombre_paquete = nombre_paquete;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getNombre_proeveedor() {
        return nombre_proveedor;
    }

    public void setNombre_proeveedor(String nombre_proveedor) {
        this.nombre_proveedor = nombre_proveedor;
    }
}
