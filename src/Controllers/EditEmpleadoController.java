package Controllers;

import Model.DBManager;
import Model.Usuario;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

import javax.swing.*;

public class EditEmpleadoController {
    DBManager dbManager = new DBManager();
    @FXML
    private JFXTextField UserField;
    @FXML
    private JFXPasswordField PassField, NewPassField;

    static long id_usuario;
    static String nombre;

    public void initialize () {
        UserField.setText(nombre);
    }

    public void modificarEmpleado () {
        System.out.println("1");
        if (UserField.getText().isEmpty() || PassField.getText().isEmpty() || NewPassField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltan datos de llenar...");
        } else {
            System.out.println("5");
            dbManager.modificarEmpleado(id_usuario, UserField.getText(), PassField.getText(), NewPassField.getText());
        }
    }

    public void recibirDatos (long id, String user) {
        id_usuario = id;
        nombre = user;
    }
}
