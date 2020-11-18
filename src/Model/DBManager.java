package Model;

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

    public static boolean validarContraseña (String usuario, String contraseña) {

        try {
            PreparedStatement stmn = connection.prepareStatement("SELECT COUNT(*) AS coincidencias FROM usuarios WHERE usuario = ? AND PGP_SYM_DECRYPT(contraseña::bytea, 'AES_KEY') = ?;");
            stmn.setString(1, usuario);
            stmn.setString(2, contraseña);
            ResultSet result = stmn.executeQuery();
            result.next();
            System.out.println(result.getInt("coincidencias"));
            if (result.getInt("coincidencias") == 1) {
                return true;
            } else {
                System.out.println("No se encontraron coincidencias");
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
                System.out.println("No se encontraron coincidencias");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "Nada";
    }
}
