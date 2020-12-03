package Controllers.Entregas;

import Model.DBManager;
import Model.Entrega;
import Model.Paquete;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EntregasViewController {
    DBManager db = new DBManager();

    @FXML
    private TableView<Entrega> entregasHolder;
    @FXML
    private TableColumn<Entrega, String> id_entregaColumn, nombre_paqColumn, nombre_clienteColumn, nombre_provColumn  ;

    public void initialize () {

        id_entregaColumn.setCellValueFactory(
                new PropertyValueFactory<Entrega, String>("id_entrega")
        );
        nombre_paqColumn.setCellValueFactory(
                new PropertyValueFactory<Entrega, String>("nombre_paquete")
        );
        nombre_clienteColumn.setCellValueFactory(
                new PropertyValueFactory<Entrega,String>("nombre_cliente")
        );
        nombre_provColumn.setCellValueFactory(
                new PropertyValueFactory<Entrega,String>("nombre_proveedor")
        );

        entregasHolder.getItems().addAll(db.regresarEntregas());
    }

    public void refreshTable () {
        for ( int i = 0; i<entregasHolder.getItems().size(); i++) {
            entregasHolder.getItems().clear();
        }
        entregasHolder.getItems().addAll(db.regresarEntregas());
    }
}
