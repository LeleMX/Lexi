package Controllers;

import Model.Cliente;
import Model.DBManager;
import Model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientesViewController {

    DBManager db = new DBManager();

    @FXML
    private TableView<Cliente> clientHolder;
    @FXML
    private TableColumn<Cliente, String> id_clienteColumn, nombreColumn, apellidoColumn, direccionColumn, telefonoColumn;

    public void initialize () {

        id_clienteColumn.setCellValueFactory(
                new PropertyValueFactory<Cliente, String>("id_cliente")
        );
        nombreColumn.setCellValueFactory(
                new PropertyValueFactory<Cliente, String>("nombre")
        );
        apellidoColumn.setCellValueFactory(
                new PropertyValueFactory<Cliente,String>("apellido")
        );
        direccionColumn.setCellValueFactory(
                new PropertyValueFactory<Cliente,String>("direccion")
        );
        telefonoColumn.setCellValueFactory(
                new PropertyValueFactory<Cliente,String>("telefono")
        );

        clientHolder.getItems().addAll(db.regresarClientes());
    }


    public void refreshTable () {
        clientHolder.refresh();
    }
}
