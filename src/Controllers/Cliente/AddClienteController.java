package Controllers.Cliente;

import Model.DBManager;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AddClienteController {
    DBManager dbManager = new DBManager();


    @FXML
    private JFXTextField NombreField, ApellidoField, DireccionField, TelefonoField;


    public void AltaCliente () {
        System.out.println("1");

        long telefono = Long.parseLong(TelefonoField.getText());

        dbManager.crearCliente(NombreField.getText(), ApellidoField.getText(), DireccionField.getText(), telefono);

        closeWindow();
    }

    public void closeWindow () {
        NombreField.getParent().getScene().getWindow();
        Stage stage1 = (Stage) NombreField.getParent().getScene().getWindow();

        stage1.close();
    }
}
