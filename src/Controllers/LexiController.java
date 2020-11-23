package Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class LexiController {
    @FXML
    private JFXButton paquetesButtom;
    @FXML
    private Pane employePlace;
    @FXML
    private JFXButton empleadosButtom;

    public void exitLexi () {
        Stage stage = new Stage();
        cerrarVentana();

        Main main = new Main();
        try {
            main.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cambiarVentanaEmpleados () throws IOException {
        Pane pane1 = FXMLLoader.load(getClass().getResource("/Views/EmpleadosView.fxml"));
        employePlace.getChildren().setAll(pane1);
    }

    public void cerrarVentana() {
        paquetesButtom.getParent().getScene().getWindow();
        Stage stage1 = (Stage) paquetesButtom.getParent().getScene().getWindow();
        stage1.close();
    }
}
