package Controles;


import java.io.IOException;

import BaseDatos.DaoSede;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.scene.control.Alert.AlertType;


public class ControlActSedes1{
	
	private DaoSede consultador;//CAMBIO
    @FXML
    private TextField nombreSC;

    @FXML
    private TextField identificacionSC;

    @FXML
    private Button buscar;
    
   
    public void cargarInterfaz(String[] resultado,ActionEvent event ) throws IOException {
    	
    	FXMLLoader cargador = new FXMLLoader();
		cargador.setLocation(getClass().getResource("/Vistas/gerente_actualizar_sede2.fxml"));
		Parent GUI4 = (Parent)cargador.load();
		ControlActSedes2 controlador = cargador.getController();
		//CAMBIO____________________________________________________________________________________
		Pane panelCentral= (Pane)((Button)event.getSource()).getParent();
	    panelCentral.getChildren().clear();
		panelCentral.getChildren().add(GUI4);
		Scene scene = GUI4.getScene();			
		GUI4.translateXProperty().set(scene.getWidth());		
		Timeline timeline = new Timeline();
		KeyValue rango = new KeyValue(GUI4.translateXProperty(), 0, Interpolator.EASE_IN);
		KeyFrame duracion = new KeyFrame(Duration.seconds(0.4), rango);
		timeline.getKeyFrames().add(duracion);
		timeline.play();		
		controlador.iniciar(resultado);
		//_________________________________________________________________________________
		//BorderPane panelRaiz = (BorderPane)((Button)event.getSource()).getScene().getRoot();
		//panelRaiz.setCenter(GUI4);;
    }
    public void setear() {
    	nombreSC.setText("");
    	identificacionSC.setText("");
    }
    

    
    public void validar(String sede,String identificador,ActionEvent event) {
    	int  id=0;
    	if(sede.replace(" ","").equals("") || identificador.replace(" ","").equals("")) {
    		mostrarMensaje("Campos Incompletos", "Por favor llene todos los campos");
    	}else {
    		if( identificador.matches("[0-9]*")) {
    			id =Integer.parseInt(identificador);
    			consultador = new DaoSede();
    			if(sede.charAt(sede.length()-1)!= ' ') {
	        		if(consultador.consultarNombre(sede)) {
	        			String[] resultado=consultador.consultarIdentificador(sede,id);   			
	        			if(resultado[0] != "" && resultado[1] != "" && resultado[0]!= null && resultado[0]!= null ){
	         				try {
								cargarInterfaz(resultado,event);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	        			} else {
	        				mostrarMensaje("identificador NO encontrado", "Identificador no encontrado en el registro de sedes");  	
	        			}    				
	        			
	        		}else {
	        			mostrarMensaje("Nombre no encontrado", "Nombre no encontrado en el registro de sedes");
	        		}
	        	}else {
        			mostrarMensaje("Nombre escrito en formato no adecuado", "El nombre no tiene el formato adecuado");
	        		
	        	}
        	}else {
        		mostrarMensaje("identificador NO es un numero", "Identificador no es un numero");  	
        	}
    		
    		
    	}
   
    }
    
    @FXML
    void buscarSede(ActionEvent event) throws IOException {
    	String sede = nombreSC.getText();
    	String identificador= identificacionSC.getText();
    	validar(sede,identificador,event);
    	
		
    }
    public void mostrarMensaje(String titulo, String mensaje) {
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
    }
    

}