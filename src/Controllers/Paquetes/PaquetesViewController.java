package Controllers.Paquetes;

import Model.DBManager;
import Model.Paquete;
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

public class PaquetesViewController {
    DBManager db = new DBManager();

    @FXML
    private TableView<Paquete> packageHolder;
    @FXML
    private TableColumn<Paquete, String> id_paqueteColumn, id_proveedorColumn, id_clienteColumn, nombreColumn;

    static String empleadoName;

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

    public void addPaquete () {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/Paquetes/AddPaqueteView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Paquete");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshTable();
    }

    public void eliminarPaquete () {
        if (packageHolder.getSelectionModel().getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(null  , "No seleccionó ningún paquete.");
        }
        else
        {
            int input = JOptionPane.showConfirmDialog(null,
                    "Quieres eliminar a " + packageHolder.getSelectionModel().getSelectedItem().getNombre(), "Selecciona una opción",JOptionPane.YES_NO_CANCEL_OPTION);
            if (input == 0) {
                long id_paquete = packageHolder.getSelectionModel().getSelectedItem().getId_paquete();
                db.eliminarPaquete(id_paquete);
                db.deletePaqueteBitacora(empleadoName);
                refreshTable();
            } else {
                System.out.println("TDBN");
            }
        }
    }

    public void recibirNombreEmpleado (String nombre) {
        empleadoName = nombre;
    }

    public void VentanaEditar () throws IOException {
        if (packageHolder.getSelectionModel().getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(null  , "No seleccionó ningún cliente.");
        }
        else
        {
            long id_paquete = packageHolder.getSelectionModel().getSelectedItem().getId_paquete();
            String nombre = packageHolder.getSelectionModel().getSelectedItem().getNombre();
            System.out.println(id_paquete + nombre);

            EditPaqueteController editPaqueteController = new EditPaqueteController();

            editPaqueteController.recibirInformacion(nombre, id_paquete);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/Paquetes/EditPaqueteView.fxml"));
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

    public void refreshTable () {
        for ( int i = 0; i<packageHolder.getItems().size(); i++) {
            packageHolder.getItems().clear();
        }
        packageHolder.getItems().addAll(db.regresarPaquetes());
    }
}
