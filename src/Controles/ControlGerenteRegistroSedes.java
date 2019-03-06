package Controles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoSede;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;

public class ControlGerenteRegistroSedes {
	private DaoSede sede;
    @FXML
    private DatePicker fechaRegistroSedes;
	@FXML
    private JFXTextField tamRegistroSede;

    @FXML
    private JFXTextField empleadosRegistroSedes;

    @FXML
    private JFXTextField nombreRegistroSede;

    @FXML
    private JFXButton botonRegistroSedes;

    @FXML
    private JFXTextField idResgistroSede;

    @FXML
    private JFXTextField dirRegistroSede;

    @FXML
    private JFXTextField telefonoRegistroSede;

    public void initialize() {
    	
    	sede = new DaoSede();
    }
    public void verificarCampos() {
    	String identificador = idResgistroSede.getText();
		String nombre = nombreRegistroSede.getText();
		String telefono = telefonoRegistroSede.getText();
    	String direccion = dirRegistroSede.getText();
    	String tamano = tamRegistroSede.getText();
    	String empleados = empleadosRegistroSedes.getText();
    	LocalDate localDate = fechaRegistroSedes.getValue();
    	String fecha = "";
		int valido = 0;
    	if(identificador.equals("")) valido = 1;
		if(nombre.equals("")) valido = 2;
    	if(telefono.equals("")) {
    		valido = 3;
    	}else {
    		if(telefono.matches("[0-9]*")) {	    		
    		}else {valido=3;}
    	}
    	if(direccion.equals("")) valido = 4;
    	if(tamano.equals("")) {
    		valido = 5;
    	}else {
    		if(tamano.matches("[0-9]*")) {	    		
    		}else {valido=5;}
    	}
    	if(empleados.equals("")) {
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
    		valido = sede.crearSede(identificador, nombre, telefono, direccion, tamano, empleados, fecha);
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
    		case 1: mostrarMensaje("Error", "El identificador no es valido"); break;
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
