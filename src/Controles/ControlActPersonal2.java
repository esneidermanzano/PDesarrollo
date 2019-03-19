package Controles;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoEmpleado;
import BaseDatos.DaoSede;
import Clases.Empleado;
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
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.scene.control.Label;

public class ControlActPersonal2 {	
	
	@FXML private Label errorTelefono;
	@FXML private Label errorCorreo;
	@FXML private Label errorNombre;
	@FXML private JFXComboBox<String> estado;
    @FXML private JFXButton actualizar;
    @FXML private JFXComboBox<String> sede;
    @FXML private JFXTextField correo;
    @FXML private JFXComboBox<String> genero;
    @FXML private JFXComboBox<String> estadoCivil;
    @FXML private Label id;
    @FXML private JFXTextField telefono;
    @FXML private JFXTextField nombre;
    @FXML private JFXComboBox<String> perfil;
    private Validador V = new Validador();
    private DaoSede consultador = new DaoSede();
    private String estadoAntes;
    private String sedeAntes;
    
    //Valida los campos de la interfaz:
    public boolean validar() {    	
    	boolean A = V.validarCampo(nombre, 50, 2, errorNombre);
    	boolean B = V.validarCampo(correo, 50, 3, errorCorreo);
    	boolean C = V.validarCampo(telefono, 10, 1, errorTelefono);    	
    	return A && B && C;    	
    }
       
    //Actualiza los datos de un usuario:
    @FXML void actualizarPersonal(ActionEvent event) throws IOException {    	
    	if(validar()) {    		
    		String nombreS = null, idS = null, telefonoS = null, estadoS = null;
            String sedeS = null, correoS = null, perfilS = null, estadoCivilS= null, generoS = null;   		
    		String partes[] = id.getText().split(" ");
    		idS = partes[1];    		
    		nombreS = nombre.getText();
    		telefonoS = telefono.getText();    		
      		if(estado.getSelectionModel().getSelectedItem().compareTo("Activo") == 0) {
      			estadoS = "B'1'";
          	}else {
          		estadoS = "B'0'";
          	}     		
    		sedeS = consultador.getId(sede.getSelectionModel().getSelectedItem());
    		correoS = correo.getText();
    	 	perfilS = perfil.getSelectionModel().getSelectedItem();
    		estadoCivilS = estadoCivil.getSelectionModel().getSelectedItem();
        	generoS = genero.getSelectionModel().getSelectedItem();          	
        	Empleado E = new Empleado(nombreS,idS,telefonoS,estadoS,sedeS,correoS,perfilS,estadoCivilS,generoS);
        	DaoEmpleado actualizador = new DaoEmpleado();        	
        	if(actualizador.actualizar(E, estadoAntes, sedeAntes) > 0) {
        		V.mostrarMensaje(1, "El usuario se ha actualizado exitosamente", "Actualización de usuario");
        		cargarInterfazAnterior(event);
        	}     	
    	}  
    }    
    
    //Cambia de la interfaz de actualización de personal 2 a la 1:
    public void cargarInterfazAnterior(ActionEvent event) throws IOException  {    	
    	FXMLLoader cargador = new FXMLLoader();
		cargador.setLocation(getClass().getResource("/Vistas/gerente_actualizar_personal1.fxml"));
		Parent GUI;
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
    }
    
    //Inicializa los comboBox:
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
	
	//Inicializa las escuchas de los campos:
	public void initEscuchas() {		
		nombre.setOnKeyPressed((e) -> {
			errorNombre.setText("");
		});
		telefono.setOnKeyPressed((e) -> {
			errorTelefono.setText("");
		});
		correo.setOnKeyPressed((e) -> {
			errorCorreo.setText("");
		});		
	}
		
	//Inicializa los widgets de la interfaz:
	public void cargar(Empleado E) {    	
    	initComboBox();
    	String nombreS = E.getNombre(), idS = E.getId(), telefonoS = E.getTelefono(); 
        String sedeS = E.getSede(), correoS = E.getCorreo(), perfilS = E.getPerfil(); 
    	String estadoS = E.getEstado(), estadoCivilS = E.getEstadoCivil(), generoS = E.getGenero();
        if(estadoS.compareTo("1") == 0) {
    		estado.getSelectionModel().select("Activo");
    		estadoAntes = "B'1'";
    	}else {
    		estado.getSelectionModel().select("Inactivo");
    		estadoAntes = "B'0'";
    	}    	
     	genero.getSelectionModel().select(generoS);
     	estadoCivil.getSelectionModel().select(estadoCivilS);    	
     	sede.getSelectionModel().select(consultador.getNombre(sedeS));
     	sedeAntes = sedeS;
     	perfil.getSelectionModel().select(perfilS);    	
       	correo.setText(correoS);
     	id.setText("ID: " + idS);
     	telefono.setText(telefonoS);
     	nombre.setText(nombreS);     	
     	initEscuchas();    		    	
    }
	
}

