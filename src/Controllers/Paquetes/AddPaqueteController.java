package Controllers.Paquetes;

import Model.DBManager;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddPaqueteController {
    DBManager dbManager = new DBManager();
    @FXML
    private HBox hBox = new HBox();
    @FXML
    private ComboBox proveedores, clientes;
    @FXML
    private JFXTextField NombreField;

    final ObservableList optionsProv = FXCollections.observableArrayList();
    final ObservableList optionCli = FXCollections.observableArrayList();

    public void initialize () {
        fillComboBox();

        proveedores.getItems().addAll(optionsProv);
        proveedores.setMaxHeight(30);

        clientes.getItems().addAll(optionCli);
        clientes.setMaxHeight(30);
    }

    public void addPaquete () {
        String proveSelected = (String) proveedores.getValue();
        System.out.println(proveSelected);
        String cliSelected = (String) clientes.getValue();
        System.out.println(cliSelected);



        long id_proveedor = dbManager.regresarIdProveedor(proveSelected);
        long id_cliente = dbManager.regresarIdCliente(cliSelected);

        dbManager.crearPaquete(id_proveedor, id_cliente, NombreField.getText());
        dbManager.insertPaqueteBitacora(dbManager.regresarNombreEmpleado(id_proveedor));

        closeWindow();
    }

    public void fillComboBox () {
        ArrayList<String> nombresProv =  dbManager.regresarNombreProveedor();
        for (int i = 0; i < nombresProv.size(); i ++) {
            optionsProv.add(nombresProv.get(i));
        }

        ArrayList<String> nombresCli =  dbManager.regresarNombreCliente();
        for (int i = 0; i < nombresCli.size(); i ++) {
            optionCli.add(nombresCli.get(i));
        }
    }

    public void closeWindow () {
        NombreField.getParent().getScene().getWindow();
        Stage stage1 = (Stage) NombreField.getParent().getScene().getWindow();

        stage1.close();
    }
}
