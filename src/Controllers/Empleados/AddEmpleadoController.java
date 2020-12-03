package Controllers.Empleados;

import Model.DBManager;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import javax.swing.*;

public class AddEmpleadoController {
    DBManager dbManager = new DBManager();

    @FXML
    private JFXTextField UserField;
    @FXML
    private JFXPasswordField PassField, PassConfirmField;

    public void Alta () {
        if (!PassField.getText().equals(PassConfirmField.getText())) {
            JOptionPane.showMessageDialog(null, "Las contraseñas no coínciden");
        } else {
            dbManager.crearEmpleado(UserField.getText(), PassField.getText());

            closeWindow();
        }
    }

    public void closeWindow () {
        UserField.getParent().getScene().getWindow();
        Stage stage1 = (Stage) UserField.getParent().getScene().getWindow();

        stage1.close();
    }
}
