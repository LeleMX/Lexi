package Controllers;

import Model.DBManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ItemEmpleadoController {
    DBManager db = new DBManager();

    @FXML
    private Label Lid_usuario, Lnombre, Lrol;
    public void initialize () {

    }
}
