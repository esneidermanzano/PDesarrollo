package Controles;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoLogin;
import Clases.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ControlLogin {
	private DaoLogin consultador;
	
    @FXML
    private JFXTextField campoUsuarioLogin;
    @FXML
    private JFXPasswordField campoPasswordLogin;
    @FXML
    private JFXButton botonLogin;

    public void mostrarMensaje(String titulo, String mensaje) {
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
    }


    @FXML
    void consultarUsuario(ActionEvent event) {
    	String usuario = campoUsuarioLogin.getText();
    	if(usuario.equals("")) {
    		System.out.print("Introducir usuario por facor");
    	}else {
    		consultador = new DaoLogin();
    		if(consultador.consultarUsuario(usuario)) {
    			if(consultador.consultarContrasena(campoUsuarioLogin.getText(), campoPasswordLogin.getText())) {
    				System.out.println("Positivo");
    				try {
						Principal.iniciarGerente(campoUsuarioLogin.getText());
					} catch (IOException e) {
						e.printStackTrace();
					}
    			}else{
    				mostrarMensaje("Contraseña incorrecta", "La contraseña no es valida, llamando a la policia");
    			}
    		}else {
    			mostrarMensaje("Usurio incorrecto", "El usuario no es valido! intentelo nuevamente");

    		}
    		
    	}
    }

}
