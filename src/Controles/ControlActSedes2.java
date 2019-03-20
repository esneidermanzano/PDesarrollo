package Controles;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoSede;
import Clases.Sede;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ControlActSedes2 {
	private DaoSede actualizacion;
    @FXML
    private Button actualizar;
    @FXML
    private TextField direccion;
    @FXML
    private TextField nEmpleados;
    @FXML
    private TextField telefono;
    @FXML
    private TextField nombre;
    @FXML
    private DatePicker apertura;
    @FXML
    private TextField tamanoSede;
    @FXML
    private Label identificador;
    @FXML
    private Label avisoNombre;
    @FXML
    private Label avisoTelefono;
    @FXML
    private Label avisoDireccion;
    @FXML
    private Label avisoTamano;

	
    public void iniciar(Sede sede) {
    	identificador.setText(sede.getId());
    	nombre.setText(sede.getNombre());
    	telefono.setText(sede.getTelefono());
    	direccion.setText(sede.getDireccion());
    	tamanoSede.setText(sede.getTamano());
    	nEmpleados.setText(sede.getNumEmpleados());
    	DateTimeFormatter formater= DateTimeFormatter.ofPattern("yyyy-mm-dd");
    	formater.withLocale(Locale.CANADA_FRENCH);
    	LocalDate fecha = LocalDate.parse(sede.getFechaApertura());
    	apertura.setValue(fecha);
    	iniciarEscucha();
    	
    }
    //MODIFICADO 18 DE MARZO VALLE
    public void iniciarEscucha() {
    	nombre.setOnKeyPressed((e)->{
    		avisoNombre.setText("");
    	}); 
    	telefono.setOnKeyPressed((e)->{
    		avisoTelefono.setText("");
    	});
    	direccion.setOnKeyPressed((e)->{
    		avisoDireccion.setText("");
    	});
    	tamanoSede.setOnKeyPressed((e)->{
    		avisoTamano.setText("");
    	});
    }
    //_______________________________________-
    public void mostrarMensaje(String titulo, String mensaje) {
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
    }
    public boolean validar(String cadena) {
    	if( cadena.matches("[0-9]*")) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    public void efectoCambio(FXMLLoader cargador, Pane panelCentral) {
		try {
			Parent gui = (Parent)cargador.load();
	    	panelCentral.getChildren().clear();
			panelCentral.getChildren().add(gui);
			Scene scene = gui.getScene();			
			gui.translateXProperty().set(scene.getWidth());		
			Timeline timeline = new Timeline();
			KeyValue rango = new KeyValue(gui.translateXProperty(), 0, Interpolator.EASE_IN);
			KeyFrame duracion = new KeyFrame(Duration.seconds(0.4), rango);
			timeline.getKeyFrames().add(duracion);
			timeline.play();
		} catch (IOException e) {
			System.out.println("Se presento un problema con la carga del modulo: " + e.getMessage());
		}		
    }
    
    public void cargarInterfazAnterior(ActionEvent event)  {
    	FXMLLoader cargador = new FXMLLoader();
		cargador.setLocation(getClass().getResource("/Vistas/gerente_consultar_sedes.fxml"));
		//CAMBIO___________________________________________________________________________________
		Pane panelCentral= (Pane)((Button)event.getSource()).getParent();
		efectoCambio(cargador, panelCentral);
		//__________________________________________________________________
		//BorderPane panelRaiz = (BorderPane)((Button)event.getSource()).getScene().getRoot();
		//panelRaiz.setCenter(GUI5);
	}
    public boolean validarCampo(String prueba, int longitud, int tipo, Label aviso) {
    	
    	boolean salida = true;
    	    	
    	if(prueba.replace(" ","").equals("")) {
    		salida = false;
    		aviso.setText("Campo vacío, por favor llene el campo");
    	}else {
    		if(prueba.length() > longitud) {
    			salida = false;
    			//aviso.setText("N° Empleados incorrecto");
    			aviso.setText("El campo  debe tener máximo " + longitud + " caracteres");
    		}else {
    			if(prueba.charAt(prueba.length()-1) != ' ' && prueba.charAt(0) != ' ') {
	    		
	    			if(tipo==1) {
	    				if( !prueba.matches("[0-9]*")) {
	    					salida=false;
	    					aviso.setText("El campo no es un número");
	    				}
	    			}
    			}else {
    				salida=false;
    				aviso.setText( "Campo con espacios");
    			}
    		}
    	}
    return salida;
    }
    public void setearLabelS() {
    	avisoNombre.setText("");
    	avisoTamano.setText("");
    	avisoTelefono.setText("");
    	avisoDireccion.setText("");
    }
    
    @FXML
    void obtenerDatos(ActionEvent event) {
    	setearLabelS();
    	String nom= nombre.getText();
    	String tel= telefono.getText();
    	String add=direccion.getText();
    	String tam= tamanoSede.getText();
    	String nEm= nEmpleados.getText();
    	boolean boolNom= validarCampo(nom,50,2,avisoNombre);
    	boolean boolTel= validarCampo(tel,11,1,avisoTelefono);
    	boolean boolAdd= validarCampo(add,50,2,avisoDireccion);
    	boolean boolTam= validarCampo(tam,50,1,avisoTamano);
    	boolean boolnEm= validarCampo(nEm,30,1,avisoTamano);
    	if(boolNom && boolTel && boolAdd && boolTam && boolnEm) {
    		int t =Integer.parseInt(tam);
    		int n  =Integer.parseInt(nEm);
    		if(t>0) {
	    		actualizacion= new DaoSede();
	    		
	    		int id =Integer.parseInt(identificador.getText());
	      			
    			if(actualizacion.consultarNombreID(id, nom)) {//Se valida si se deja el mismo nombre
		    		if(actualizacion.actualizar(id,nom,tel,add,t,n)>0) {
		    			mostrarMensaje("¡Registro exitoso!", "Datos actualizados correctamente");
		    			cargarInterfazAnterior(event);
		    		}
	    		}else {
	    			if(!actualizacion.consultarNombre(nom)) {//Se valida que el nombre al que ha cambiado no exista
	    				if(actualizacion.actualizar(id,nom,tel,add,t,n)>0) {
			    			mostrarMensaje("¡Registro exitoso!", "Datos actualizados correctamente");
			    			cargarInterfazAnterior(event);
			    		}
	    			}else {
	    				mostrarMensaje("Nombre sede existente", "El nombre ingresado ya existe");
	    			}
	    		}
    		}else {
    			avisoTamano.setText("El tamaño de la sede debe ser mayor a 0");
    		}
  		}
    }
}