package Controllers.Cliente;

import Model.Cliente;
import Model.DBManager;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import javax.swing.*;

public class EditClienteController {
    DBManager dbManager = new DBManager();
    @FXML
    private JFXTextField NombreField, ApellidoField, DireccionField, TelefonoField;

    static Cliente cliente;

    static long id_clienteF;
    static long telefonoF;

    public void initialize () {
        NombreField.setText(cliente.getNombre());
        ApellidoField.setText(cliente.getApellido());
        DireccionField.setText(cliente.getDireccion());
        TelefonoField.setText("" + cliente.getTelefono());
    }

    public void editarCliente () {
        if (NombreField.getText().isEmpty() || ApellidoField.getText().isEmpty() || DireccionField.getText().isEmpty() || TelefonoField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null  , "Faltan datos de llenar.");
        } else {
            dbManager.modificarCliente(NombreField.getText(), ApellidoField.getText(), DireccionField.getText(), telefonoF, id_clienteF);
            JOptionPane.showMessageDialog(null  , "Se ha editado con éxito");
            closeWindow();
        }
    }

    public void recibirInformacion (Cliente cl, long id_cliente, long telefono) {
        cliente = cl;
        id_clienteF = id_cliente;
        telefonoF = telefono;
    }

    public void closeWindow () {
        NombreField.getParent().getScene().getWindow();
        Stage stage1 = (Stage) NombreField.getParent().getScene().getWindow();

        stage1.close();
    }
}
