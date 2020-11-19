package Model;

import javax.swing.*;
import java.sql.*;

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

    public static boolean crearCliente () {
        //CODIIIINNNNGGGG....!
        return false;
    }

    public static boolean ingresarPaquete (int proveedor, int cliente, String nombre) {
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
            if (resultSet.getInt("coincidencias") == 1) {
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


    public static boolean validarContraseña (String usuario, String contraseña) {

        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT COUNT(*) AS coincidencias FROM usuarios WHERE usuario = ? AND PGP_SYM_DECRYPT(contraseña::bytea, 'AES_KEY') = ?;");
            stmn.setString(1, usuario);
            stmn.setString(2, contraseña);
            ResultSet result = stmn.executeQuery();
            result.next();
            if (result.getInt("coincidencias") == 1) {
                JOptionPane.showMessageDialog(null, "Bienvenido " + usuario);
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
