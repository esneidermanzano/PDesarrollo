package Controles;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoEmpleado;
import BaseDatos.DaoSede;
import Clases.Usuario;
import Clases.Validador;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.scene.control.Label;

public class ControlActPersonal2 {	
	
	@FXML
    private JFXComboBox<String> estado;

    @FXML
    private JFXButton actualizar;

    @FXML
    private JFXComboBox<String> sede;

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
    private DaoSede consultador = new DaoSede();
    
    public boolean validar() {
    	
    	boolean A = V.validarCampo(nombre, 50, "del nombre", 2);
    	boolean B = V.validarCampo(correo, 50, "del correo electrónico", 3);
    	boolean C = V.validarCampo(telefono, 10, "del teléfono/celular", 1);
    	
    	return A && B && C;
    	
    }
       
    @FXML
    void actualizarPersonal(ActionEvent event) {
    	
    	if(validar()) {
    		
    		String[] datos = new String[9];
    		
    		String partes[] = id.getText().split(" ");
    		datos[0] = partes[1];
    		
    		datos[1] = nombre.getText();
    		datos[2] = telefono.getText();
    		
      		if(estado.getSelectionModel().getSelectedItem().compareTo("Activo") == 0) {
      			datos[3] = "B'1'";
          	}else {
          		datos[3] = "B'0'";
          	}
     		
    		datos[4] = consultador.getId(sede.getSelectionModel().getSelectedItem());
    		datos[5] = correo.getText();
    	 	datos[6] = perfil.getSelectionModel().getSelectedItem();
    		datos[7] = estadoCivil.getSelectionModel().getSelectedItem();
        	datos[8] = genero.getSelectionModel().getSelectedItem();    
        	
        	Usuario U = new Usuario(datos);
        	DaoEmpleado actualizador = new DaoEmpleado();
        	
        	if(actualizador.actualizar(U) > 0) {
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
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
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
	    	
	    	DaoSede consultador = new DaoSede();
	    	List<String> sedes = consultador.obtenerNombresDeSedes();
	    	ObservableList<String> listaSedes = FXCollections.observableArrayList(sedes);
	    	sede.setItems(listaSedes);
	    	    	
	}
		
	public void cargar(Usuario U) {
    	
    	initComboBox();
    	String[] datos = U.getUsuario();
    	
    	if(datos[3].compareTo("1") == 0) {
    		estado.getSelectionModel().select("Activo");
    	}else {
    		estado.getSelectionModel().select("Inactivo");
    	}
    	
     	genero.getSelectionModel().select(datos[8]);
     	estadoCivil.getSelectionModel().select(datos[7]);    	
     	sede.getSelectionModel().select(consultador.getNombre(datos[4]));
     	perfil.getSelectionModel().select(datos[6]);    	
       	correo.setText(datos[5]);
     	id.setText("ID: " + datos[0]);
     	telefono.setText(datos[2]);
     	nombre.setText(datos[1]);
    		    	
    }
	
}

