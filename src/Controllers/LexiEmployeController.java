package Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class LexiEmployeController {
    @FXML
    private JFXButton paquetesButtom;

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

    public void cerrarVentana() {
        paquetesButtom.getParent().getScene().getWindow();
        Stage stage1 = (Stage) paquetesButtom.getParent().getScene().getWindow();
        stage1.close();
    }
}
