package Controllers.Cliente;

import Model.Cliente;
import Model.DBManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

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

    public void addCliente () {
        //CREAR METODO QUE MANDE A LA VENTANA DE AÑADIR ALGUN USUARIO A LA BASE DE DATOS, DESPUES AÑADIR UN BOTON DE
        //REFRESCAR PARA DESPUES JALAR LOS DATOS DE LA BASE DE DATOS Y PONERLOS EN LOS ITEMS

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/Cliente/AddClienteView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Cliente");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshTable();
    }

    public void VentanaEditar () throws IOException {
        if (clientHolder.getSelectionModel().getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(null  , "No seleccionó ningún cliente.");
        }
        else
        {
            long id_cliente = clientHolder.getSelectionModel().getSelectedItem().getId_cliente();
            long telefono = clientHolder.getSelectionModel().getSelectedItem().getTelefono();
            System.out.println(id_cliente + "  -  " +  telefono);

            Cliente editar = clientHolder.getSelectionModel().getSelectedItem();

            EditClienteController editClienteController = new EditClienteController();

            editClienteController.recibirInformacion(editar, id_cliente, telefono);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/Cliente/EditClienteView.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();

                stage.setTitle("Editar");
                stage.setResizable(false);
                stage.setScene(new Scene(root1));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        refreshTable();
    }

    public void elimiarCliente () {
        if (clientHolder.getSelectionModel().getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(null  , "No seleccionó ningún cliente.");
        }
        else
        {
            int input = JOptionPane.showConfirmDialog(null,
                    "Quieres eliminar a " + clientHolder.getSelectionModel().getSelectedItem().getNombre(), "Selecciona una opción",JOptionPane.YES_NO_CANCEL_OPTION);
            if (input == 0) {
                long id_cliente = clientHolder.getSelectionModel().getSelectedItem().getId_cliente();
                db.eliminarCliente(id_cliente);
                refreshTable();
            } else {
                System.out.println("TDBN");
            }
        }
        refreshTable();
    }


    public void refreshTable () {
        for ( int i = 0; i<clientHolder.getItems().size(); i++) {
            clientHolder.getItems().clear();
        }
        clientHolder.getItems().addAll(db.regresarClientes());
    }
}
