package Model;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DBManager {

    private static final String user = "postgres";
    private static final String pass = "Unisierra20";

    private static Connection connection;

    public static void init () {

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LexiDB" , user, pass);
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

    public static ArrayList<Cliente> regresarClientes () {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT id_cliente, nombre, apellido, direccion, telefono FROM cliente WHERE rol = 'empleado'ORDER BY id_cliente;");
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

    public static boolean crearCliente (String nombre, String apellido, String direccion, long telefono) {
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

    public static boolean crearPaquete (int proveedor, int cliente, String nombre) {
        try {
            PreparedStatement validarPaquete = connection.prepareStatement("SELECT COUNT(*) AS coincidencias FROM paquetes WHERE nombre = ? ");
            validarPaquete.setString(1,nombre);
            ResultSet resultSet = validarPaquete.executeQuery();
            resultSet.next();
            if (resultSet.getInt("coincidencias") == 1) {
                PreparedStatement stmn = connection.prepareStatement("INSERT INTO paquete (proveedor,cliente,nombre) VALUES (?,?,?);");
                stmn.setInt(1, proveedor);
                stmn.setInt(2, cliente);
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
