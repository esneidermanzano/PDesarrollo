package Controles;

import java.io.IOException;

import BaseDatos.DaoSede;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert.AlertType;


public class ControlActSedes1{
	
	private DaoSede consultador;
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
			controlador.iniciar(resultado);
			BorderPane panelRaiz = (BorderPane)((Button)event.getSource()).getScene().getRoot();
			panelRaiz.setCenter(GUI4);;
    }
    public void setear() {
    	nombreSC.setText("");
    	identificacionSC.setText("");
    }
    @FXML
    void buscarSede(ActionEvent event) throws IOException {
    	String sede = nombreSC.getText();
    	String identificador= identificacionSC.getText();
    	if(sede.equals("") || identificador.equals("")) {
    		mostrarMensaje("Campos Incompletos", "Por favor llene todos los campos");
    	}else {
    		consultador = new DaoSede();
    		if(consultador.consultarNombre(sede)) {
    			String[] resultado=consultador.consultarIdentificador(sede,identificador) ;   			
    			if(resultado[0] != "" && resultado[1] != "" && resultado[0]!= null && resultado[0]!= null ){
     				cargarInterfaz(resultado,event);
    			} else {
    				mostrarMensaje("identificador NO encontrado", "Identificador no encontrado en el registro de sedes");  	
    			}    				
    			
    		}else {
    			mostrarMensaje("Nombre no encontrado", "Nombre no encontrado en el registro de sedes");
    		}
    		
    	}
   
		
    }
    public void mostrarMensaje(String titulo, String mensaje) {
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
    }
    

}
