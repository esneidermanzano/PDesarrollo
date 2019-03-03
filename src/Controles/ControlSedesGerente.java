package Controles;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControlSedesGerente {
    @FXML
    private JFXButton botonRegistroSedes;

    @FXML
    private TextField direccion;

    @FXML
    private TextField empleados;

    
    @FXML
    void mostrarTexto(ActionEvent event) {
    	//String nombre = empleados.getText();
    	System.out.print("stemen");
    }

}
