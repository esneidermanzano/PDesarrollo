package Controles;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoInventario;
import BaseDatos.DaoSede;
import Clases.Item;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ControlGerenteActualizarItems {
	private DaoInventario consultaInventario;
	private DaoSede consultaSede;
	private Item producto;
    @FXML private JFXComboBox<String> campoNombre;
    @FXML private JFXComboBox<String> campoColor;
    @FXML private JFXComboBox<String> campoSede;    

    @FXML private Text textoIdentificador;
    @FXML private Text textoFecha;

    @FXML private JFXTextField campoPrecio;
    @FXML private JFXTextField campoExistencias;

    @FXML private JFXButton botonActualizar;


	    public void initialize() {
	    	consultaInventario = new DaoInventario();
	    	consultaSede = new DaoSede();
	    	//botonActualizar.setDisable(true);
	    	campoNombre.setItems(consultaInventario.obtenerNombres());
	    	campoColor.setItems(consultaInventario.obtenerColores());
	    	ArrayList<String> valores = consultaSede.obtenerNombresDeSedes();			
			for (String valor : valores) {
				campoSede.getItems().addAll(valor);
			}
			

	    }
	    
	    public void iniciar(Item producto) {
	    	this.producto = producto;
	    	textoIdentificador.setText(producto.getConcatenado());
	    	textoFecha.setText(producto.getFecha());
	    	campoPrecio.setText(Integer.toString(producto.getValorCompra()));
	    	campoExistencias.setText(Integer.toString(producto.getCantidad()));
	    	campoNombre.setValue(producto.getNombre());
	    	campoColor.setValue(producto.getColor());
	    	campoSede.setValue(producto.getSede());
	    }
	        
	    public void verificarCampos() {	  
	    	int valido = 0;
	    	String precio = campoPrecio.getText();
	    	String existencias = campoExistencias.getText();

	    	if(precio.replace(" ", "").equals("")) {
	    		valido = 1;
	    	}else {
	    		if(precio.matches("[0-9]*")) {	    		
	    		}else {valido=1;}
	    	}
	    	if(existencias.replace(" ", "").equals("")) {
	    		valido = 2;
	    	}else {
	    		if(existencias.matches("[0-9]*")) {	    		
	    		}else {valido=2;}
	    	}
	    	
	    	if(valido == 0) {
	    		consultaInventario.actualizarItem(
					    				Integer.parseInt(producto.getIdentificador()), 
					    				Integer.parseInt(producto.getIdentificador2()),
					    				campoNombre.getValue(), campoColor.getValue(), 
					    				Integer.parseInt(campoPrecio.getText()), 					    			
					    				campoSede.getValue(),
					    				Integer.parseInt(campoExistencias.getText())
					    				);
	        	if(valido == -1) {
	        		mostrarMensaje("Error", "Error al procesar el pedido");
	        	}else {
	        		Alert alert = new Alert(AlertType.INFORMATION);
	        		alert.setTitle("confirmacion");
	        		alert.setHeaderText(null);
	        		alert.setContentText("El item se actualizo con exito");
	        		alert.showAndWait();
	        	}
	    	}else {    		    	
	    		switch(valido) {
	    		case 1: mostrarMensaje("Error", "Error: El precio es incorrecto");break;
	    		case 2: mostrarMensaje("Error", "La fecha no es valida");break;
	    		case 3: mostrarMensaje("Error", "Las existencias no son validas");break;	    		
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
	    void actualizarItem(ActionEvent event) {
	    	verificarCampos();
	    }
	
	    
	    @FXML
	    void limpiarItems(ActionEvent event) throws IOException {
	    	FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(getClass().getResource("/Vistas/gerente_listar_items.fxml"));
			Pane panelCentral= (Pane)((Button)event.getSource()).getParent();
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
			ControlGerenteListarItems controlador = cargador.getController();
	    }
}
