package Controles;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoInventario;
import BaseDatos.DaoSede;

import Clases.Item;
import Clases.Validador;
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
	private Validador validador;

    @FXML private JFXComboBox<String> campoColor;
    @FXML private JFXComboBox<String> campoSede;    

    @FXML private Text campoNombre;
    @FXML private Text textoIdentificador;
    @FXML private Text textoFecha;

    @FXML private JFXTextField campoPrecio;
    @FXML private JFXTextField campoExistencias;

    @FXML private JFXButton botonActualizar;


	    public void initialize() {
	    	consultaInventario = new DaoInventario();
	    	consultaSede = new DaoSede();
	    	validador = new Validador();
	    	//botonActualizar.setDisable(true);
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
	    	campoNombre.setText(producto.getNombre());
	    	campoColor.setValue(producto.getColor());
	    	campoSede.setValue(producto.getSede());
	    }
	        
	    public void verificarCampos() {  

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
	    	int valido = 0;
	    	String mensaje = "";
	    	String precio = campoPrecio.getText();
	    	String existencias = campoExistencias.getText();

	    	if(precio.replace(" ", "").equals("")) {
	    		mensaje = "El precio no puede estar vacio";
	    		valido = 1;
	    	}else {
	    		precio = validador.ajustar(precio);
	    		campoPrecio.setText(precio);
	    		if(precio.matches("[0-9]*")) {
	    			if(precio.length()>10) {
	    				valido=1;
	    				mensaje = "El precio no tener mas de 10 numeros";
	    			}
	    			if(precio.matches("[0]*")) {
	    				mensaje = "El precio no puede ser cero";
	    				valido=1;
	    			}
	    			
	    		}else {
	    			valido=1;
	    			mensaje = "El precio Debe ser un valor numerico";
	    		}
	    	}
	    	
	    	if(existencias.replace(" ", "").equals("")) {
	    		valido = 1;
	    		mensaje = "Las existencias no beden ser vacias";
	    	}else {
	    		existencias = validador.ajustar(existencias);
	    		campoExistencias.setText(existencias);
	    		if(existencias.matches("[0-9]*")) {	    		
	    		}else {
	    			valido=2;
	    			mensaje = "Las existencias deben ser un valor numerico";
	    		}
	    	}
	    	
	    	if(valido == 0) {
	    		SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd");
	    		Date date = null;
				try {
					date = format.parse(producto.getFecha());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		valido = consultaInventario.actualizarItem(
					    				Integer.parseInt(producto.getIdentificador()), 
					    				Integer.parseInt(producto.getIdentificador2()),
					    				campoColor.getValue(), 
					    				Integer.parseInt(campoPrecio.getText()), 
					    				date,
					    				Integer.parseInt(consultaSede.getId(campoSede.getValue())),
					    				Integer.parseInt(campoExistencias.getText())
					    				);
	        	if(valido == -1) {
	        		mostrarMensaje("Error", "Error al procesar el pedido");
	        	}else {
	        		Alert alert = new Alert(AlertType.INFORMATION);
	        		alert.setTitle("confirmacion");
	        		alert.setHeaderText(null);
	        		alert.setContentText("El Producto se actualizo con exito");
	        		alert.showAndWait();
	        		try {
						regresarAnterior(event);
					} catch (IOException e) {
						e.printStackTrace();
					}
	        	}
	    	}else {    		    	
	    		mostrarMensaje("Error", mensaje);    		
	    		  		        	
	    	}
	    }
		   
	    public void regresarAnterior(ActionEvent event) throws IOException {
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
	    }
}
