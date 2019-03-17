package Controles;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoEmpleado;
import Clases.Empleado;
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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ControlActPersonal1 {

    @FXML private JFXTextField idEntrada;    
    @FXML private Label errorID;
    @FXML private JFXButton buscar;
    private DaoEmpleado consultador = new DaoEmpleado();
           
    //Retorna un usuario de la base de datos:
    public Empleado buscarUsuario(String idEntrada) {     	
    	return consultador.consultarEmpleado(idEntrada);   	
    }
        
    //Aplica el efecto de transición:
    public void efectoCambio(FXMLLoader cargador, Empleado E, Pane panelCentral) throws IOException {
		cargador.setLocation(getClass().getResource("/Vistas/gerente_actualizar_personal2.fxml"));
		Parent GUI = (Parent)cargador.load();    	    		
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
		controlador.cargar(E);
    }
          
    //Cambia de la interfaz de actualización de personal 1 a la 2:
    @FXML void cargarInterfazAP2(ActionEvent event) throws IOException {    	   	
    	Validador V = new Validador();    	   	
    	if(V.validarCampo(idEntrada, 50, 1, errorID)) {
        	if(consultador.existe(idEntrada.getText())) {
        		Empleado E = buscarUsuario(idEntrada.getText());
        		if(!E.compararPerfil("Gerente")) {
        			FXMLLoader cargador = new FXMLLoader();   	    		
            		Pane panelCentral = (Pane)((Button)event.getSource()).getParent();
            		efectoCambio(cargador,E,panelCentral);
            		ControlActPersonal2 controlador = cargador.getController();
            		controlador.cargar(E);
        		}else {
        			V.mostrarMensaje(3, "No tienes permisos para actualizar gerentes", "Error en la actualización");
        		}	        	
        	}else {
        		V.mostrarMensaje(3, "El usuario no existe", "Error en la actualización");
        	}        	
    	}    	    	
    }      	        
}
