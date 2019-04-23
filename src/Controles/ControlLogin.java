package Controles;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoEmpleado;
import Clases.Principal;
import Clases.Validador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class ControlLogin {
	private DaoEmpleado consultador;
	private Validador validador;
   
	@FXML
    private Label mensajeAdvertencia;
    @FXML
    private JFXTextField campoUsuarioLogin;
    @FXML
    private JFXPasswordField campoPasswordLogin;
    @FXML
    private JFXButton botonLogin;

    public void procesarUsuario() {
    	validador = new Validador();
    	String usuario = campoUsuarioLogin.getText();
    	String[] perfil = new String[2];      

    	if (validador.validarCampo(campoUsuarioLogin, 50, 2, mensajeAdvertencia)) {
    		mensajeAdvertencia.setText("");
    		if(!campoPasswordLogin.getText().equals("")) {
    			consultador = new DaoEmpleado();
        		if(consultador.consultarUsuario(usuario)) {
        			perfil = consultador.consultarContrasena(campoUsuarioLogin.getText(), campoPasswordLogin.getText());	
    		         if(perfil[0] != null){
    		        	 if (perfil[0].equals("Gerente")) {
    		    			try {
    							Principal.iniciarGerente(campoUsuarioLogin.getText(), perfil);
    						} catch (IOException e) {
    							e.printStackTrace();
    						}
    		        	 }else if(perfil[0].equals("Vendedor")){
    		        		 try {
    							Principal.iniciarVendedor(campoUsuarioLogin.getText(), perfil);
    						} catch (IOException e) {
    							e.printStackTrace();
    						}
    		        	 }else if(perfil[0].equals("Jefe de taller")) {
    		        		 try {
    								Principal.iniciarJefeTaller(campoUsuarioLogin.getText(), perfil);
    							} catch (IOException e) {
    								e.printStackTrace();
    							}
    		        	 }
    		        	 else if(perfil[0].equals("Administrador")) {
    		        		 try {
    								Principal.iniciarAdministrador(campoUsuarioLogin.getText(), perfil);
    							} catch (IOException e) {
    								e.printStackTrace();
    							}
    		        	 }
    		        	 else {
    		        		 Alert alert = new Alert(AlertType.WARNING);
    		        		 alert.setTitle("Advertencia");
    	        			 alert.setHeaderText(null);
    	        			 alert.setContentText("Usted no esta habilitado para ingresar");
    	        			 alert.showAndWait();
    	        			 mensajeAdvertencia.setText("");
    		        	 }
        			}else{
        				mensajeAdvertencia.setText("La contraseña es incorrecta");
        			}
        		}else {
        			mensajeAdvertencia.setText("Usuario no valido");
        		}  
    		}else {
    			mensajeAdvertencia.setText("Digite una contraseña");
    		}
    		
    	}
    }
    
    @FXML
    public void onEnter(ActionEvent e){
    	procesarUsuario();
    }
    
    @FXML
    void consultarUsuario(ActionEvent event) {
    	procesarUsuario();
    }
}
