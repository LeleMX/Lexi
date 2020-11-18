package Controllers;

import Model.DBManager;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;

public class LoginController {
    @FXML
    private JFXTextField Fcorreo;
    @FXML
    private JFXPasswordField Fcontrasena;

    public void validarInicio () {

        DBManager.init();

        String contraseña = Fcontrasena.getText().toLowerCase();
        String email = Fcorreo.getText();

        if (DBManager.validarContraseña(email,contraseña)) {
            if (DBManager.verRol(email).equals("jefe")) {
                ventanaJefe();
            }
            if (DBManager.verRol(email).equals("empleado")) {
                ventanaEmpleados();
            }
        } else {
            JOptionPane.showMessageDialog(null, "CONTRASEÑA O USUARIO INCORRECTO!");
        }
    }

    public void ventanaJefe () {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/LexiView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Lexi");
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();

            cerrarVentana();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ventanaEmpleados () {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/LexiEmployeView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle("Empleados");
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();

            cerrarVentana();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cerrarVentana () {
        Fcorreo.getParent().getScene().getWindow();
        Stage stage1 = (Stage) Fcorreo.getParent().getScene().getWindow();

        stage1.close();
    }
}
