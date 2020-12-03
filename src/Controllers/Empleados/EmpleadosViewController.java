package Controllers.Empleados;

import Model.DBManager;
import Model.Usuario;
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


public class EmpleadosViewController {
    @FXML
    private TableView<Usuario> employeHolder;
    @FXML
    private TableColumn<Usuario, String> id_empleadoColumn, nombreColumn, rolColumn;

    DBManager db = new DBManager();

    public void initialize () {
        id_empleadoColumn.setCellValueFactory(
                new PropertyValueFactory<Usuario, String>("id_usuario")
        );
        nombreColumn.setCellValueFactory(
                new PropertyValueFactory<Usuario, String>("nombre")
        );
        rolColumn.setCellValueFactory(
                new PropertyValueFactory<Usuario,String>("rol")
        );

        employeHolder.getItems().addAll(db.regresarEmpleados());
    }

    public void addEmpleado () {
        //CREAR METODO QUE MANDE A LA VENTANA DE AÑADIR ALGUN USUARIO A LA BASE DE DATOS, DESPUES AÑADIR UN BOTON DE
        //REFRESCAR PARA DESPUES JALAR LOS DATOS DE LA BASE DE DATOS Y PONERLOS EN LOS ITEMS
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/Empleado/AddEmpleadoView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Empleado");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshTable();
    }

    public void eliminarEmpleado () {
        if (employeHolder.getSelectionModel().getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(null  , "No seleccionó ningún cliente.");
        }
        else
        {
            int input = JOptionPane.showConfirmDialog(null,
                    "Quieres eliminar a " + employeHolder.getSelectionModel().getSelectedItem().getNombre(), "Selecciona una opción",JOptionPane.YES_NO_CANCEL_OPTION);
            if (input == 0) {
                long id_empleado = employeHolder.getSelectionModel().getSelectedItem().getId_usuario();
                db.eliminarEmpleado(id_empleado);
                refreshTable();
            } else {
                System.out.println("TDBN");
            }
        }
    }

    public void VentanaEditar () throws IOException {
        if (employeHolder.getSelectionModel().getSelectedItem() == null)
        {
            JOptionPane.showMessageDialog(null  , "No seleccionó ningún cliente.");
        }
        else
        {
            long id_usuario = employeHolder.getSelectionModel().getSelectedItem().getId_usuario();
            String nombre = employeHolder.getSelectionModel().getSelectedItem().getNombre();
            System.out.println(id_usuario + nombre);

            EditEmpleadoController ed = new EditEmpleadoController();

            ed.recibirDatos(id_usuario, nombre);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/Empleado/EditEmpleadoView.fxml"));
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
        for ( int i = 0; i<employeHolder.getItems().size(); i++) {
            employeHolder.getItems().clear();
        }
        employeHolder.getItems().addAll(db.regresarEmpleados());
    }
}
