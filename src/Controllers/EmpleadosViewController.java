package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class EmpleadosViewController {
    @FXML
    private VBox employeHolder;
    Node[] nodes = new Node[10];

    public void initialize () {
        for (int i = 0; i < nodes.length; i ++) {
            try {
                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("/Views/ItemEmpleado.fxml"));
                //ADD EFFECT
                nodes[j].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color: #DAA8FE ");
                });

                nodes[j].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color: #FFFFFF ");
                });

                employeHolder.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addEmpleado () {
        //CREAR METODO QUE MANDE A LA VENTANA DE AÑADIR ALGUN USUARIO A LA BASE DE DATOS, DESPUES AÑADIR UN BOTON DE
        //REFRESCAR PARA DESPUES JALAR LOS DATOS DE LA BASE DE DATOS Y PONERLOS EN LOS ITEMS
    }
}
