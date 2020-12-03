package Model;

public class EntregaDos {
    private String nombre_paquete;
    private String nombre_cliente;
    private String nombre_proveedor;

    public EntregaDos(String nombre_paquete, String nombre_cliente, String nombre_proveedor) {
        this.nombre_paquete = nombre_paquete;
        this.nombre_cliente = nombre_cliente;
        this.nombre_proveedor = nombre_proveedor;
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

    public String getNombre_proveedor() {
        return nombre_proveedor;
    }

    public void setNombre_proveedor(String nombre_proveedor) {
        this.nombre_proveedor = nombre_proveedor;
    }
}
