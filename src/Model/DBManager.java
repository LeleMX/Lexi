package Model;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class DBManager {

    private static final String user = "postgres";
    private static final String pass = "Unisierra20";

    private static Connection connection;
    private static Connection connection2;

    public static void init () {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LexiDB" , user, pass);
            connection2 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bitacora", user, pass);
            System.out.println("Conectado con exito a la base de datos...");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Usuario> regresarEmpleados () {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT id_usuario, usuario, rol FROM usuarios WHERE rol = 'empleado' ORDER BY id_usuario");
            ResultSet result = stmn.executeQuery();

            while (result.next()) {
                long id = result.getLong("id_usuario");
                String nombre = result.getString("usuario");
                String rol = result.getString("rol");

                Usuario nuevo = new Usuario(id, nombre, rol);
                usuarios.add(nuevo);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return usuarios;
    }

    public static ArrayList<Paquete> regresarPaquetes () {
        ArrayList<Paquete> paquetes = new ArrayList<>();
        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT id_paquete, proveedor, cliente, nombre FROM paquete ORDER BY id_paquete;");
            ResultSet result = stmn.executeQuery();

            while (result.next()) {
                long id = result.getLong("id_paquete");
                Long proveedor = result.getLong("proveedor");
                Long cliente = result.getLong("cliente");
                String nombre = result.getString("nombre");

                Paquete nuevo = new Paquete(id, proveedor, cliente, nombre);
                paquetes.add(nuevo);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return paquetes;
    }

    static EntregaDos nuevito;
    public static EntregaDos regresarEntregasPorID (long id_paquete) {
        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT u.usuario AS nombre_proveedor, c.nombre AS nombre_cliente, p.nombre AS nombre_paquete FROM paquete p\n" +
                    "INNER JOIN usuarios u ON u.id_usuario = proveedor\n" +
                    "INNER JOIN cliente c ON c.id_cliente = cliente\n" +
                    "WHERE id_paquete = ? \n" +
                    "GROUP BY id_paquete, u.usuario, c.nombre, p.nombre");

            stmn.setLong(1, id_paquete);

            ResultSet result = stmn.executeQuery();

            while (result.next()) {
                String nombre_paquete= result.getString("nombre_paquete");
                String nombre_cliente = result.getString("nombre_cliente");
                String nombre_proveedor = result.getString("nombre_proveedor");

                System.out.println(nombre_proveedor);

                nuevito = new EntregaDos(nombre_paquete, nombre_cliente, nombre_proveedor);
                System.out.println(nuevito.getNombre_cliente() + nuevito.getNombre_paquete() + nuevito.getNombre_proveedor());
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return nuevito;
    }

    public static ArrayList<Entrega> regresarEntregas () {
        ArrayList<Entrega> entregas = new ArrayList<>();
        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT * FROM entrega");

            ResultSet result = stmn.executeQuery();

            while (result.next()) {
                long id_entrega = result.getLong("id_entrega");
                String nombre_paquete= result.getString("nombre_paquete");
                String nombre_cliente = result.getString("nombre_cliente");
                String nombre_proveedor = result.getString("nombre_proveedor");

                System.out.println(nombre_proveedor);

                Entrega nuevo = new Entrega(id_entrega, nombre_paquete, nombre_cliente, nombre_proveedor);
                entregas.add(nuevo);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return entregas;
    }

    public static boolean agregarEntrega(String nombre_paquete, String nombre_cliente, String nombre_proveedor) {
        try {
            PreparedStatement stmn = connection.prepareStatement("INSERT INTO entrega (nombre_paquete, nombre_cliente, nombre_proveedor) VALUES (?,?,?)");
            stmn.setString(1, nombre_paquete);
            stmn.setString(2, nombre_cliente);
            stmn.setString(3, nombre_proveedor);

            stmn.execute();

            JOptionPane.showMessageDialog(null, "La antrega se ha realizado con éxito");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static ArrayList<Paquete> regresarPaquetesEmpleado (long id_empleado) {
        ArrayList<Paquete> paquetes = new ArrayList<>();
        try {
            System.out.println("3 " + id_empleado);
            PreparedStatement stmn = connection.prepareStatement("SELECT id_paquete, proveedor, cliente, nombre FROM paquete WHERE proveedor = ? ORDER BY id_paquete");
            stmn.setLong(1, id_empleado);
            ResultSet result = stmn.executeQuery();

            while (result.next()) {
                long id = result.getLong("id_paquete");
                Long proveedor = result.getLong("proveedor");
                Long cliente = result.getLong("cliente");
                String nombre = result.getString("nombre");

                Paquete nuevo = new Paquete(id, proveedor, cliente, nombre);
                paquetes.add(nuevo);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return paquetes;
    }


    public static ArrayList<Cliente> regresarClientes () {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT id_cliente, nombre, apellido, direccion, telefono FROM cliente ORDER BY id_cliente;");
            ResultSet result = stmn.executeQuery();

            while (result.next()) {
                long id = result.getLong("id_cliente");
                String nombre = result.getString("nombre");
                String apellido = result.getString("apellido");
                String direccion = result.getString("direccion");
                long telefono = result.getLong("telefono");

                Cliente nuevo = new Cliente(id, nombre, apellido, direccion, telefono);
                clientes.add(nuevo);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return clientes;
    }

    static long id_proveedor;

    public static long regresarIdProveedor (String nombre) {
        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT id_usuario FROM usuarios WHERE rol = 'empleado' AND usuario = ?");
            stmn.setString(1, nombre);
            ResultSet result = stmn.executeQuery();

            while (result.next()) {
                id_proveedor = result.getLong("id_usuario");
                System.out.println("ESTE ENCUENTRA EN LA BASE " + id_proveedor);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("ESTE ENVIA DE LA BASE: " + id_proveedor);
        return id_proveedor;
    }

    public static long regresarIdCliente (String nombre) {
        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT id_cliente FROM cliente WHERE nombre = ?");
            stmn.setString(1, nombre);
            ResultSet result = stmn.executeQuery();

            while (result.next()) {
                long id_cliente = result.getLong("id_cliente");
                System.out.println(id_cliente);
                return id_cliente;
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static boolean crearCliente (String nombre, String apellido, String direccion, long telefono) {
        System.out.println("2");
        try {
            PreparedStatement stmn = connection.prepareStatement("INSERT INTO cliente (nombre, apellido, direccion, telefono) VALUES (?,?,?,?)");
            stmn.setString(1, nombre);
            stmn.setString(2, apellido);
            stmn.setString(3, direccion);
            stmn.setLong(4, telefono);

            stmn.execute();

            JOptionPane.showMessageDialog(null, "Se ha agregado el cliente con éxito");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean crearPaquete (long proveedor, long cliente, String nombre) {
        try {
            PreparedStatement validarPaquete = connection.prepareStatement("SELECT COUNT(*) AS coincidencias FROM paquete WHERE nombre = ? ");
            validarPaquete.setString(1,nombre);
            ResultSet resultSet = validarPaquete.executeQuery();
            resultSet.next();
            if (resultSet.getInt("coincidencias") == 0) {
                PreparedStatement stmn = connection.prepareStatement("INSERT INTO paquete (proveedor,cliente,nombre) VALUES (?,?,?);");
                stmn.setLong(1, proveedor);
                stmn.setLong(2, cliente);
                stmn.setString(3, nombre);

                stmn.execute();

                JOptionPane.showMessageDialog(null, "Se ha agregado el paquete con éxito");
                return true;
            } else {
                //MANDAR ALERTA DE QUE EL PAQUETE YA EXISTE...!!
                JOptionPane.showMessageDialog(null, "El paquete ya existe, intente con otro");

                System.out.println("Ya existe el usaurio");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;

    }

    public static boolean crearEmpleado (String usuario, String contraseña) {
        try {
            PreparedStatement validarUsuario = connection.prepareStatement("SELECT COUNT(*) AS coincidencias FROM usuarios WHERE usuario = ? ");
            validarUsuario.setString(1,usuario);
            ResultSet resultSet = validarUsuario.executeQuery();
            resultSet.next();
            System.out.println(resultSet.getInt("coincidencias"));
            if (resultSet.getInt("coincidencias") == 0) {
                PreparedStatement stmn = connection.prepareStatement("INSERT INTO usuarios (usuario,contraseña,rol) VALUES (?, PGP_SYM_ENCRYPT(?,'AES_KEY'), 'empleado')");
                stmn.setString(1, usuario);
                stmn.setString(2, contraseña);

                stmn.execute();

                JOptionPane.showMessageDialog(null, "Se ha agregado el usuario con éxito");
                return true;
            } else {
                //MANDAR ALERTA DE QUE EL USUARIO YA EXISTE...!!
                JOptionPane.showMessageDialog(null, "El usuario ya existe, intente con otro");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    static String nombre;

    public static String regresarNombreEmpleado (long id_empleado) {
        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT usuario FROM usuarios WHERE id_usuario = ? AND rol = 'empleado'");
            stmn.setLong(1, id_empleado);
            ResultSet result = stmn.executeQuery();

            while (result.next()) {
                nombre = result.getString("usuario");
                System.out.println(nombre);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return nombre;
    }

    public static ArrayList<String> regresarNombreProveedor () {
        ArrayList<String> nombresProveedores = new ArrayList<>();
        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT usuario FROM usuarios WHERE rol = 'empleado';");
            ResultSet result = stmn.executeQuery();

            while (result.next()) {
                nombresProveedores.add(result.getString("usuario"));
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return nombresProveedores;
    }

    public static ArrayList<String> regresarNombreCliente () {
        ArrayList<String> nombresClientes = new ArrayList<>();
        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT nombre FROM cliente");
            ResultSet result = stmn.executeQuery();

            while (result.next()) {
                nombresClientes.add(result.getString("nombre"));
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return nombresClientes;
    }

    public static boolean modificarCliente (String nombre, String apellido, String direccion, long telefono, long id_cliente) {
        System.out.println("2");
            try {
                PreparedStatement stmn = connection.prepareStatement("UPDATE cliente SET nombre = ?, apellido = ?, direccion = ?, telefono = ? WHERE id_cliente = ?");
                stmn.setString(1, nombre);
                stmn.setString(2, apellido);
                stmn.setString(3, direccion);
                stmn.setLong(4, telefono);
                stmn.setLong(5, id_cliente);

                int row = stmn.executeUpdate();

                if (row == 0) {
                    System.out.println("No se modificó nada.");
                } else {
                    System.out.println("Se actualizaron los datos.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
    }

    public static void insertPaqueteBitacora (String responsable) {
        System.out.println("ENTRO A INSERT PAQUETE");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        try {
            PreparedStatement stmn = connection2.prepareStatement("INSERT INTO bitacora (responsable, accion ,tabla ,fecha) VALUES (?,?,?,?);");
            stmn.setString(1, responsable);
            stmn.setString(2, "INSERT");
            stmn.setString(3, "PAQUETE");
            stmn.setTimestamp(4, new java.sql.Timestamp(cal.getTime().getTime()));

            stmn.execute();

            System.out.println("SALIO INSERT PAQUETE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deletePaqueteBitacora (String responsable) {
        System.out.println("ENTRO A ELIMINAR PAQUETE");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        try {
            PreparedStatement stmn = connection2.prepareStatement("INSERT INTO bitacora (responsable, accion ,tabla ,fecha) VALUES (?,?,?,?);");
            stmn.setString(1, responsable);
            stmn.setString(2, "DELETE");
            stmn.setString(3, "PAQUETE");
            stmn.setTimestamp(4, new java.sql.Timestamp(cal.getTime().getTime()));

            stmn.execute();

            System.out.println("SALIO INSERT PAQUETE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePaqueteBitacora (String responsable) {
        System.out.println("ENTRO A UPDATE PAQUETE");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        try {
            PreparedStatement stmn = connection2.prepareStatement("INSERT INTO bitacora (responsable, accion ,tabla ,fecha) VALUES (?,?,?,?);");
            stmn.setString(1, responsable);
            stmn.setString(2, "UPDATE");
            stmn.setString(3, "PAQUETE");
            stmn.setTimestamp(4, new java.sql.Timestamp(cal.getTime().getTime()));

            stmn.execute();

            System.out.println("SALIO INSERT PAQUETE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean eliminarPaquete (long id_paquete) {
        try {
            PreparedStatement stmn = connection.prepareStatement("DELETE FROM paquete WHERE id_paquete = ?");
            stmn.setLong(1, id_paquete);

            int row = stmn.executeUpdate();

            if (row == 0) {
                System.out.println("No se pudo borrar el registro de paquete.");
            } else {
                System.out.println("Borrado exitoso.");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean modificarPaquete (long id_paquete, String nombre) {
        System.out.println("2");
        try {
            PreparedStatement stmn = connection.prepareStatement("UPDATE paquete SET nombre = ? WHERE id_paquete = ?");
            stmn.setString(1, nombre);
            stmn.setLong(2, id_paquete);

            int row = stmn.executeUpdate();

            if (row == 0) {
                System.out.println("No se modificó nada.");
            } else {
                System.out.println("Se actualizaron los datos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean eliminarCliente (long id_cliente) {
        try {
            PreparedStatement stmn = connection.prepareStatement("DELETE FROM cliente WHERE id_cliente = ?");
            stmn.setLong(1, id_cliente);

            int row = stmn.executeUpdate();

            if (row == 0) {
                System.out.println("No se pudo borrar el registro de cliente.");
            } else {
                System.out.println("Borrado exitoso.");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean eliminarEmpleado (long id) {
        try {
            PreparedStatement stmn = connection.prepareStatement("DELETE FROM usuarios WHERE id_usuario = ?");
            stmn.setLong(1, id);

            int row = stmn.executeUpdate();

            if (row == 0) {
                System.out.println("No se pudo borrar el registro de Paquete.");
            } else {
                System.out.println("Borrado exitoso.");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void modificarEmpleado (long id_empleado, String usuario, String pass, String newPass) {
        System.out.println("2");
        if (validarContraseñaVieja(pass)) {
            System.out.println("3");
            try {
                PreparedStatement stmn = connection.prepareStatement("UPDATE usuarios SET usuario = ?, contraseña = PGP_SYM_ENCRYPT(?,'AES_KEY') WHERE id_usuario = ?");
                stmn.setString(1, usuario);
                stmn.setString(2, newPass);
                stmn.setLong(3, id_empleado);

                int row = stmn.executeUpdate();

                if (row == 0) {
                    System.out.println("No se modificó nada.");
                } else {
                    System.out.println("Se actualizaron los datos.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean validarContraseñaVieja (String contraseñaVieja) {
        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT COUNT(*) AS coincidencias FROM usuarios WHERE PGP_SYM_DECRYPT(contraseña::bytea, 'AES_KEY') = ?");
            stmn.setString(1, contraseñaVieja);
            ResultSet result = stmn.executeQuery();
            result.next();
            if (result.getInt("coincidencias") == 1) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Contraseña o usuario incorrectos.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean validarContraseña (String usuario, String contraseña) {

        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT COUNT(*) AS coincidencias FROM usuarios WHERE usuario = ? AND PGP_SYM_DECRYPT(contraseña::bytea, 'AES_KEY') = ?;");
            stmn.setString(1, usuario);
            stmn.setString(2, contraseña);
            ResultSet result = stmn.executeQuery();
            result.next();
            if (result.getInt("coincidencias") == 1) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Contraseña o usuario incorrectos.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static String verRol (String usuario) {
        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT  * FROM usuarios WHERE usuario = ?");
            stmn.setString(1, usuario);
            ResultSet result = stmn.executeQuery();
            result.next();
            if (result != null) {
                String rol = result.getString("rol");
                return rol;
            } else {

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "Nada";
    }
}
