package Controllers.Paquetes;

import Model.DBManager;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import javax.swing.*;

public class EditPaqueteController {
    DBManager dbManager = new DBManager();
    @FXML
    private JFXTextField NombreField;

    static long id_paqueteF;

    static String nombreP;

    static String empleadoName;

    public void initialize () {
        NombreField.setText(nombreP);
    }

    public void modificarPaquete () {
        if (NombreField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltan datos de llenar...");
        } else {
            dbManager.modificarPaquete(id_paqueteF, NombreField.getText());
            System.out.println(empleadoName);
            dbManager.updatePaqueteBitacora(empleadoName);
            closeWindow();
        }
    }

    public void recibirNombreEmpleado (String nombre) {
        empleadoName = nombre;
    }

    public void recibirInformacion (String nombre, long id_paquete) {
        nombreP = nombre;
        id_paqueteF = id_paquete;
    }

    public void closeWindow () {
        NombreField.getParent().getScene().getWindow();
        Stage stage1 = (Stage) NombreField.getParent().getScene().getWindow();

        stage1.close();
    }
}
