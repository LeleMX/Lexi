package Controllers;

import Model.DBManager;
import Model.Paquete;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PaquetesViewController {
    DBManager db = new DBManager();

    @FXML
    private TableView<Paquete> packageHolder;
    @FXML
    private TableColumn<Paquete, String> id_paqueteColumn, id_proveedorColumn, id_clienteColumn, nombreColumn;

    public void initialize () {

        id_paqueteColumn.setCellValueFactory(
                new PropertyValueFactory<Paquete, String>("id_paquete")
        );
        id_proveedorColumn.setCellValueFactory(
                new PropertyValueFactory<Paquete, String>("proveedor")
        );
        id_clienteColumn.setCellValueFactory(
                new PropertyValueFactory<Paquete,String>("cliente")
        );
        nombreColumn.setCellValueFactory(
                new PropertyValueFactory<Paquete,String>("nombre")
        );

        packageHolder.getItems().addAll(db.regresarPaquetes());
    }

}
