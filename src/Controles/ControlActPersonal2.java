package Controles;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoEmpleado;
import Clases.Usuario;
import Clases.Validador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;

public class ControlActPersonal2 {	
	
	@FXML
    private JFXComboBox<String> estado;

    @FXML
    private JFXButton actualizar;

    @FXML
    private JFXTextField sede;

    @FXML
    private JFXTextField apellido;

    @FXML
    private JFXTextField correo;

    @FXML
    private JFXComboBox<String> genero;

    @FXML
    private JFXComboBox<String> estadoCivil;

    @FXML
    private Label id;

    @FXML
    private JFXTextField telefono;

    @FXML
    private JFXTextField nombre;

    @FXML
    private JFXComboBox<String> perfil;
    
    private Validador V = new Validador();
    
    @FXML
    void actualizarPersonal(ActionEvent event) {
    	
    	if(validar()) {
    		
    		String estadoS;
    		if(estado.getPromptText().compareTo("Activo") == 0) {
    			estadoS = "1";
    		}else {
    			estadoS = "0";
    		}
        	String generoS = genero.getPromptText();
        	String estadoCivilS = estadoCivil.getPromptText();
        	String perfilS = perfil.getPromptText();
        	String sedeS = sede.getText();
        	String correoS = correo.getText();
        	String telefonoS = telefono.getText();
        	String nombreS = nombre.getText() + " " + apellido.getText();
        	String partes[] = id.getText().split(" ");
        	String idS = partes[1];
        	
        	Usuario U = new Usuario(idS, nombreS, telefonoS, estadoS, sedeS, correoS, perfilS, estadoCivilS, generoS);
        	DaoEmpleado actualizador = new DaoEmpleado();
        	if(actualizador.actualizar(U)>0) { 
        	V.mostrarMensaje(1, "El usuario se ha actualizado exitosamente", "Actualización de usuario");
        	cargarInterfazAnterior(event);
        	}
        	
        	       	
    	}  
    }    
    
    public void cargarInterfazAnterior(ActionEvent event)  {
    	FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(getClass().getResource("/Vistas/gerente_actualizar_personal1.fxml"));
			Parent GUI;
			try {
				GUI = (Parent)cargador.load();
				ControlActPersonal1 controlador = cargador.getController();
				BorderPane panelRaiz = (BorderPane)((Button)event.getSource()).getScene().getRoot();
				panelRaiz.setCenter(GUI);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
			    
    public boolean validar() {
    	
    	boolean A = V.validarCampo(nombre, 20, "del nombre", 2);
    	boolean B = V.validarCampo(apellido, 20, "del apellido", 2);
    	boolean C = V.validarID(sede,"de la sede");
    	boolean D = V.validarCampo(correo, 49, "del correo electrónico", 3);
    	boolean E = V.validarCampo(telefono, 10, "del teléfono/celular", 1);
    	
    	return A && B && C && D && E;
    	
    }
    
	public void initComboBox() {
	    	
	    	List<String> estados = Arrays.asList("Activo", "Inactivo");
	    	ObservableList<String> listaEstados = FXCollections.observableArrayList(estados);
	    	estado.setItems(listaEstados);
	    	
	    	List<String> generos = Arrays.asList("Masculino", "Femenino", "No definido");
	    	ObservableList<String> listaGeneros = FXCollections.observableArrayList(generos);
	    	genero.setItems(listaGeneros);
	
	    	List<String> estadosCiviles = Arrays.asList("Soltero", "Casado", "Unión libre");
	    	ObservableList<String> listaEstadosCiviles = FXCollections.observableArrayList(estadosCiviles);
	    	estadoCivil.setItems(listaEstadosCiviles);
	    	
	    	List<String> perfiles = Arrays.asList("Vendedor", "Jefe de taller");
	    	ObservableList<String> listaPerfiles = FXCollections.observableArrayList(perfiles);
	    	perfil.setItems(listaPerfiles);	
	    	    	
	}
	
	public void cargar(Usuario U) {
    	
    	initComboBox();
     	   	   
    	if(U.getEstado().compareTo("1") == 0) {
    		estado.setPromptText("Activo");
    	}else {
    		estado.setPromptText("Inactivo");
    	}
     	genero.setPromptText(U.getGenero());
     	estadoCivil.setPromptText(U.getEstadoCivil());
     	sede.setText(U.getSede());
     	perfil.setPromptText(U.getPerfil());
       	correo.setText(U.getCorreo());
     	id.setText("ID: " + U.getId());
     	telefono.setText(U.getTelefono());
     	String partes[] = U.getNombre().split(" ");
     	nombre.setText(partes[0]);
     	apellido.setText(partes[1]);
    		
    	}
    	
    }

