package Model;

public class Paquete {
    private Long id_paquete;
    private Long proveedor;
    private Long cliente;
    private String nombre;

    public Paquete(Long id_paquete, Long proveedor, Long cliente, String nombre) {
        this.id_paquete = id_paquete;
        this.proveedor = proveedor;
        this.cliente = cliente;
        this.nombre = nombre;
    }

    public Long getId_paquete() {
        return id_paquete;
    }

    public void setId_paquete(Long id_paquete) {
        this.id_paquete = id_paquete;
    }

    public Long getProveedor() {
        return proveedor;
    }

    public void setProveedor(Long proveedor) {
        this.proveedor = proveedor;
    }

    public Long getCliente() {
        return cliente;
    }

    public void setCliente(Long cliente) {
        this.cliente = cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
