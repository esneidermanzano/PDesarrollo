package Controles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoSede;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;
public class ControlGerenteRegistroSedes {
	private DaoSede sede;
	
    @FXML
    private JFXTextField campoNombre;
    @FXML
    private JFXTextField campoTelefono;
    @FXML
    private JFXTextField campoDireccion;
    @FXML
    private JFXTextField campoTamano;
    @FXML
    private JFXTextField campoEmpleados;
    @FXML
    private DatePicker fechaRegistroSedes;
    @FXML
    private JFXButton botonRegistroSedes;

    public void initialize() {
    	
    	sede = new DaoSede();
   	 
    }
    
    public void verificarCampos() {
		String nombre = campoNombre.getText();
		String telefono = campoTelefono.getText();
    	String direccion = campoDireccion.getText();
    	String tamano = campoTamano.getText();
    	String empleados = campoEmpleados.getText();
    	LocalDate localDate = fechaRegistroSedes.getValue();
    	String fecha = "";
		int valido = 0;
		if(nombre.replace(" ", "").equals("")) valido = 2;
    	if(telefono.replace(" ", "").equals("")) {
    		valido = 3;
    	}else {
    		if(telefono.matches("[0-9]*")) {	    		
    		}else {valido=3;}
    	}
    	if(direccion.replace(" ", "").equals("")) valido = 4;
    	if(tamano.equals("")) {
    		valido = 5;
    	}else {
    		if(tamano.matches("[0-9]*")) {	    		
    		}else {valido=5;}
    	}
    	if(empleados.replace(" ", "").equals("")) {
    		valido = 6;
    	}else {
    		if(empleados.matches("[0-9]*")) { 		    		
    		}else {valido=6;}
    	}
    	if(localDate == null) {
    		valido = 7;
    	}else {
        	DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        	fecha = localDate.format(pattern);
    	}

    	
    	if(valido == 0) {
    		valido = sede.crearSede(nombre, telefono, direccion, tamano, empleados, fecha);
        	if(valido == -1) {
        		mostrarMensaje("Error", "Error: el identificador ya esta registrado");
        	}else {
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("confirmacion");
        		alert.setHeaderText(null);
        		alert.setContentText("la sede se registro con exito");
        		alert.showAndWait();
        	}
    	}else {    		    	
    		switch(valido) {
    		case 2: mostrarMensaje("Error", "Error: Por favor digite un nombre");break;
    		case 3: mostrarMensaje("Error", "El telefono no es valido");break;
    		case 4: mostrarMensaje("Error", "Error: Por favor digite una direccion");break;
    		case 5: mostrarMensaje("Error", "El tamaño no es valido");break;
    		case 6: mostrarMensaje("Error", "La cantidad de empleados no es valida");break;
    		case 7: mostrarMensaje("Error", "La fecha no es valida");break;
    		}    		        	
    	}
    }
    
    public void mostrarMensaje(String titulo, String mensaje) {
    	Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
    }
    
    
    @FXML
    void registrarSede(ActionEvent event) {
    	verificarCampos(); 
    }
  

}
