package Controllers;

import Controllers.Paquetes.PaquetesViewEmpleado;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LexiEmployeController {
    PaquetesViewEmpleado paquetesViewEmpleado = new PaquetesViewEmpleado();
    @FXML
    private JFXButton paquetesButtom;
    @FXML
    private Pane employePlace;
    @FXML
    private Label UsuarioField;

    static long id_empleadoO;

    static String nombre;

    public void initialize () {

    }

    public void exitEmpleados () {
        Stage stage = new Stage();
        cerrarVentana();

        Main main = new Main();

        try {
            main.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cambiarVentanaPaquetes () throws IOException {
        paquetesViewEmpleado.recibirIdEmpleado(id_empleadoO);
        System.out.println("eSTE ES el id q le mando " + id_empleadoO);
        Pane pane1 = FXMLLoader.load(getClass().getResource("/Views/Paquetes/PaquetesViewEmpleado.fxml"));
        employePlace.getChildren().setAll(pane1);
    }

    public void cambiarVentanaClientes () throws IOException {
        Pane pane1 = FXMLLoader.load(getClass().getResource("/Views/Cliente/ClientesView.fxml"));
        employePlace.getChildren().setAll(pane1);
    }

    public void recibirID (long id_empleado) {
        System.out.println(id_empleado);
        id_empleadoO = id_empleado;
    }

    public void cerrarVentana() {
        paquetesButtom.getParent().getScene().getWindow();
        Stage stage1 = (Stage) paquetesButtom.getParent().getScene().getWindow();
        stage1.close();
    }
}
