package Controllers.Paquetes;

import Model.DBManager;
import Model.EntregaDos;
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

public class PaquetesViewEmpleado {
    DBManager db = new DBManager();

    @FXML
    private TableView<Paquete> packageHolder;
    @FXML
    private TableColumn<Paquete, String> id_paqueteColumn, id_proveedorColumn, id_clienteColumn, nombreColumn;

    static long empleadoID;

    static String nombreE;

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

        packageHolder.getItems().addAll(db.regresarPaquetesEmpleado(empleadoID));
    }

    public void addPaqueteEmpleado () {
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

    public void eliminarPaqueteEmpleado () {
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
                db.deletePaqueteBitacora(nombreE);
                refreshTable();
            } else {
                System.out.println("TDBN");
            }
        }
    }

    public void haceEntrega() {
        if (packageHolder.getSelectionModel().getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(null  , "No seleccionó ningún paquete.");
        }
        else
        {
            int input = JOptionPane.showConfirmDialog(null,
                    "Quieres hacer entrega de " + packageHolder.getSelectionModel().getSelectedItem().getNombre(), "Selecciona una opción",JOptionPane.YES_NO_CANCEL_OPTION);
            if (input == 0) {

                long id_paquete = packageHolder.getSelectionModel().getSelectedItem().getId_paquete();

                EntregaDos entregita = db.regresarEntregasPorID(id_paquete);
                System.out.println(entregita.getNombre_cliente() + entregita.getNombre_proveedor() + entregita.getNombre_paquete());

                db.agregarEntrega(entregita.getNombre_paquete(), entregita.getNombre_cliente(), entregita.getNombre_proveedor());
                db.eliminarPaquete(id_paquete);
                db.deletePaqueteBitacora(nombreE);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(null, "Ok no te preocupes, a la otra");
            }
        }
    }

    public void VentanaEditarEmpleado () throws IOException {
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

    public void recibirNombreEmpleado (String nombre_empleado) {
        System.out.println("Este resibe  " + nombre_empleado);
        nombreE = nombre_empleado;
    }

    public void recibirIdEmpleado (long id_empleado) {
        System.out.println("Este resibe  " + id_empleado);
        empleadoID = id_empleado;
    }

    public void refreshTable () {
        for ( int i = 0; i<packageHolder.getItems().size(); i++) {
            packageHolder.getItems().clear();
        }
        packageHolder.getItems().addAll(db.regresarPaquetesEmpleado(empleadoID));
    }
}
