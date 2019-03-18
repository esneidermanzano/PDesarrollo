package Controles;

import java.util.ArrayList;

import javax.swing.MutableComboBoxModel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoEmpleado;
import BaseDatos.DaoSede;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.value.*;

public class ControlAdminRegistrar {
	private DaoSede daoSede;
	private DaoEmpleado gerente;
	
	@FXML
    private JFXComboBox<String> ComboBoxSede;
	
	@FXML
    private JFXComboBox<String> ComboBoxGenero;
	
	@FXML
    private JFXComboBox<String> ComboBoxEstadoCivil;

    @FXML
    private JFXTextField campoPassword;

    @FXML
    private JFXTextField campoTelefono;

    @FXML
    private JFXTextField campoId;

    @FXML
    private JFXButton botonRegistrar;

    @FXML
    private JFXTextField campoNombre;
    
    @FXML
    private JFXTextField campoCorreo;
    
    public void initialize() {
    	ComboBoxEstadoCivil.getItems().addAll("Soltero", "Casado", "Unión libre");
    	ComboBoxGenero.getItems().addAll("Masculino", "Femenino", "No definido");
    	daoSede = new DaoSede();
    	gerente = new DaoEmpleado();
    	
    	ArrayList<String> valores = daoSede.obtenerNombresDeSedes();
		
		for (String valor : valores) {
			ComboBoxSede.getItems().addAll(valor);
		}
		
		 // force the field to be numeric only
	    campoId.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            if (!newValue.matches("\\d*")) {
	                campoId.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
	    
	    campoTelefono.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            if (!newValue.matches("\\d*")) {
	                campoTelefono.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
	    
	    campoNombre.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, 
	            String newValue) {
	            if (!newValue.matches("\\d*")) {
	                campoNombre.setText(newValue.replaceAll("\\d", ""));
	            }
	        }
	    });
		
    }
        
    public void mostrarMensaje(String titulo, String mensaje) {
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
    }

    @FXML
    void registrarEmpleado(ActionEvent event) {
    	String nombre = campoNombre.getText();
    	String id = campoId.getText();
    	String password = campoPassword.getText();
    	String telefono = campoTelefono.getText();
    	String correo = campoCorreo.getText();
    	String sede = daoSede.getId(ComboBoxSede.getValue());
    	String genero = ComboBoxGenero.getValue();
    	String estadoCivil = ComboBoxEstadoCivil.getValue();
    	//0 = Success, 1 = error de usuario, 2 = id ya tomada
    	int valido = 0;
    	
    	if(nombre.equals("")) valido = 1;
    	if(id.equals("")) valido = 2;
    	if(password.equals("")) valido = 3;
    	if(telefono.equals("")) valido = 4;
    	if(correo.equals("")) valido = 5;
    	if(sede == null) valido =7;
    	if(genero == null) valido = 8;
    	if(estadoCivil == null) valido = 9;
    		
    	
    	if(valido == 0){
    		//System.out.println("vergas");
    		//gerente.vergas();
    		valido = gerente.crearEmpleado(nombre, id, password, telefono, correo, "Gerente", sede, estadoCivil, genero);
        	if(valido == -1) {
        		mostrarMensaje("Error", "Error: el identificador ya esta registrado");
        	}else {
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("confirmacion");
        		alert.setHeaderText(null);
        		alert.setContentText("El usuario se registro con exito");
        		alert.showAndWait();
        	}
    	}else {
    		switch(valido) {
    		case 1: mostrarMensaje("Error", "Error: Por favor digite un nombre");
    		break;
    		case 2: mostrarMensaje("Error", "Error: Por favor digite una identificacion");
    		break;
    		case 3: mostrarMensaje("Error", "Error: Por favor digite un password");
    		break;
    		case 4: mostrarMensaje("Error", "Error: Por favor digite un telefono");
    		break;
    		case 5: mostrarMensaje("Error", "Error: Por favor digite un correo electronico");
    		break;
    		case 6: mostrarMensaje("Error", "Error: Por favor elija un cargo");
    		break;
    		case 7: mostrarMensaje("Error", "Error: Por favor elija una sede");
    		break;
    		case 8: mostrarMensaje("Error", "Error: Por favor elija un genero");
    		break;
    		case 9: mostrarMensaje("Error", "Error: Por favor elija un estado civil");
    		break;
    		}
    	}
    }

}
