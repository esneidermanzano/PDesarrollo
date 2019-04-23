package Controles;

import java.util.ArrayList;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoEmpleado;
import BaseDatos.DaoSede;
import Clases.Validador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class ControlGerenteRegistrarEmpleados {
	private DaoSede daoSede;
	private DaoEmpleado gerente;
	private Validador V = new Validador();
	
	@FXML
    private JFXComboBox<String> ComboBoxCargo;
	
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
    
	@FXML 
	private Label errorNombre;
    
    @FXML
    private Label errorId;
    
    @FXML
    private Label errorPass;
    
    @FXML
    private Label errorTelefono;
    
    @FXML
    private Label errorMail;
    
    @FXML
    private Label errorEmpty;
    
    public void initialize() {
    	initEscuchas();
    	ComboBoxCargo.getItems().addAll("Vendedor", "Jefe de taller");
    	ComboBoxEstadoCivil.getItems().addAll("Soltero", "Casado", "Unión libre");
    	ComboBoxGenero.getItems().addAll("Masculino", "Femenino", "No definido");
    	daoSede = new DaoSede();
    	gerente = new DaoEmpleado();
    	
    	ArrayList<String> valores = daoSede.obtenerNombresDeSedes();
		
		for (String valor : valores) {
			ComboBoxSede.getItems().addAll(valor);
		}
		
		/* // force the field to be numeric only
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
	    });*/
		
    }
        
    public void mostrarMensaje(String titulo, String mensaje) {
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
    }
    
	//Inicializa las escuchas de los campos:
	public void initEscuchas() {		
		campoNombre.setOnKeyPressed((e) -> {
			errorNombre.setText("");
		});
		campoTelefono.setOnKeyPressed((e) -> {
			errorTelefono.setText("");
		});
		campoCorreo.setOnKeyPressed((e) -> {
			errorMail.setText("");
		});
		campoId.setOnKeyPressed((e) -> {
			errorId.setText("");
		});
		campoPassword.setOnKeyPressed((e) -> {
			errorPass.setText("");
		});
		ComboBoxCargo.setOnAction((e) -> {
			errorEmpty.setText("");
		});
		ComboBoxSede.setOnAction((e) -> {
			errorEmpty.setText("");
		});
		ComboBoxGenero.setOnAction((e) -> {
			errorEmpty.setText("");
		});
		ComboBoxEstadoCivil.setOnAction((e) -> {
			errorEmpty.setText("");
		});
	}
    
    public void cleanLabels(){
    	errorNombre.setText("");
    	errorId.setText("");
    	errorPass.setText("");
    	errorTelefono.setText("");
    	errorMail.setText("");
    	errorEmpty.setText("");
    }
    
    //Valida los campos de la interfaz:
    public boolean validar() {
    	cleanLabels();
    	boolean A = V.validarCampo(campoNombre, 50, 2, errorNombre);
    	boolean B = V.validarCampo(campoId, 50, 1, errorId);
    	boolean C = V.validarVacios(campoPassword.getText(), errorPass);
    	boolean D = V.validarCampo(campoTelefono, 10, 1, errorTelefono);
    	boolean E = V.validarCampo(campoCorreo, 50, 3, errorMail);
    	boolean F = true;
    	
    	if(ComboBoxCargo.getValue() == null || ComboBoxSede.getValue() == null || ComboBoxGenero.getValue() == null || ComboBoxEstadoCivil.getValue() == null){
    		F = false;
    		errorEmpty.setText("Por favos verifique que todos los campos se hayan rellenado");
    	}
    	
    	
    	
    	return A && B && C && D && E && F;    	
    }

    @FXML
    void registrarEmpleado(ActionEvent event) {
		if(validar()) {
	    	String nombre = campoNombre.getText();
	    	String id = campoId.getText();
	    	String password = campoPassword.getText();
	    	String telefono = campoTelefono.getText();
	    	String correo = campoCorreo.getText();
	    	String cargo = ComboBoxCargo.getValue();
	    	String sede = daoSede.getId(ComboBoxSede.getValue());
	    	String genero = ComboBoxGenero.getValue();
	    	String estadoCivil = ComboBoxEstadoCivil.getValue();
	    	int valido = 0;
	    	
	    	valido = gerente.crearEmpleado(nombre, id, password, telefono, correo, cargo, sede, estadoCivil, genero);
        	if(valido == -1) {
        		mostrarMensaje("Error", "Error: el identificador ya esta registrado");
        	}else {
        		V.mostrarMensaje(1, "El usuario se registro con exito", "Exito");
        	}
	    	
		}
		/*
    	//0 = Success, 1 = error de usuario, 2 = id ya tomada

    	
    	if(nombre.equals("")) valido = 1;
    	if(id.equals("")) valido = 2;
    	if(password.equals("")) valido = 3;
    	if(telefono.equals("")) valido = 4;
    	if(correo.equals("")) valido = 5;
    	if(cargo == null) valido = 6;
    	if(sede == null) valido =7;
    	if(genero == null) valido = 8;
    	if(estadoCivil == null) valido = 9;
    		
    	
    	if(valido == 0){
    		//System.out.println("vergas");
    		//gerente.vergas();
    		
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
    	}*/
    }

}
