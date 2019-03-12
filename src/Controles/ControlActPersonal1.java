package Controles;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoEmpleado;
import Clases.Usuario;
import Clases.Validador;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ControlActPersonal1 {

    @FXML
    private JFXTextField idEntrada;
    
    private DaoEmpleado consultador = new DaoEmpleado();
    
    public Usuario buscarUsuario(String idEntrada) {    	
    	
    	return consultador.consultarUsuario(idEntrada);
    	
    }
          
    //Cambia de la interfaz de actualización de personal 1 a la 2:
    @FXML
    void cargarInterfazAP2(ActionEvent event) throws IOException {
    	
    	Validador V = new Validador();
    	
    	if(V.validarCampo(idEntrada, 50, "de búsqueda", 1)) {
    		if(consultador.existe(idEntrada.getText())) {
    			Usuario U = buscarUsuario(idEntrada.getText());
    			if(!U.gerente()) {
    				FXMLLoader cargador = new FXMLLoader();
    	    		cargador.setLocation(getClass().getResource("/Vistas/gerente_actualizar_personal2.fxml"));
    	    		Parent GUI = (Parent)cargador.load();    	    		
    	    		Pane panelCentral = (Pane)((Button)event.getSource()).getParent();
    	    		panelCentral.getChildren().clear();
    	    		panelCentral.getChildren().add(GUI);
    	    		Scene scene = GUI.getScene();
    	    		GUI.translateXProperty().set(scene.getWidth());
    	    		Timeline timeline = new Timeline();
    	    		KeyValue rango = new KeyValue(GUI.translateXProperty(), 0, Interpolator.EASE_IN);
    	    		KeyFrame duracion = new KeyFrame(Duration.seconds(0.4), rango);
    	    		timeline.getKeyFrames().add(duracion);
    	    		timeline.play();
    	    		ControlActPersonal2 controlador = cargador.getController();
    	    		controlador.cargar(U);
    			}else {
    				V.mostrarMensaje(3, "No tienes permisos para actualizar gerentes", "Error en la actualización");
    			}	        	
    		}else {
    			V.mostrarMensaje(3, "El usuario no existe", "Error en la actualización");
    		}
    	}    	
    	
    }
        
}
