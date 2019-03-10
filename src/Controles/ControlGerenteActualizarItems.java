package Controles;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoInventario;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ControlGerenteActualizarItems {
	private DaoInventario inventario;
	
	@FXML
	private Text labelIdentificador;
  	@FXML
    private Text labelNombre;
    @FXML
    private Text labelPrecio;
    @FXML
    private Text labelExistencias;
   
    @FXML
    private JFXTextField campoIdentificador;
    @FXML
    private JFXTextField campoNombre;
    @FXML
    private JFXTextField campoPrecio;
    @FXML
    private JFXTextField campoExistencias;

    @FXML
    private JFXButton botonActualizar;
    @FXML
    private JFXButton botonConsultar;

	    public void initialize() {
	    	inventario = new DaoInventario();
	    	botonActualizar.setDisable(true);
	    	
	    	campoNombre.setOnKeyPressed((e) -> {
	   	 		mostrarLabel(labelNombre, campoNombre);
			});   	 	
	    	campoIdentificador.setOnKeyPressed((e) -> {
	   	 		mostrarLabel(labelIdentificador, campoIdentificador);
	    	});
	    	campoPrecio.setOnKeyPressed((e) -> {
	   	 		mostrarLabel(labelPrecio, campoPrecio);
	    	});
	    	campoExistencias.setOnKeyPressed((e) -> {
	   	 		mostrarLabel(labelExistencias, campoExistencias);
	    	});
	    }
	    
	    public void mostrarLabel(Text label, JFXTextField textField) {
	    	FadeTransition transicion = new FadeTransition(Duration.millis(200), label);
		 	if(textField.getText().equals("")) {transicion.setToValue(0);}
		 	else { transicion.setToValue(1);}
		 	transicion.play();
	    }
	    
	    public void verificarCampos() {
	    	
			String nombre = campoNombre.getText();
	    	String precio = campoPrecio.getText();
	    	String existencias = campoExistencias.getText();
			int valido = 0;
			if(nombre.replace(" ", "").equals("")) valido = 1;
	    	if(precio.replace(" ", "").equals("")) {
	    		valido = 2;
	    	}else {
	    		if(precio.matches("[0-9]*")) {	    		
	    		}else {valido=2;}
	    	}
	    	if(existencias.equals("")) {
	    		valido = 3;
	    	}else {
	    		if(existencias.matches("[0-9]*")) {	    		
	    		}else {valido=3;}
	    	}
	    	
	    	if(valido == 0) {
	    		valido = inventario.actualizarItem(campoIdentificador.getText(), nombre, Integer.parseInt(precio), Integer.parseInt(existencias));
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
	    		case 1: mostrarMensaje("Error", "Error: Por favor digite un nombre");break;
	    		case 2: mostrarMensaje("Error", "Error: El precio no es valiso");break;
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
	    void consultarItem(ActionEvent event) {	
	    	String id = campoIdentificador.getText();
	    	if(id.replace(" ", "").equals("")) {
	    		mostrarMensaje("Error", "Error: Por favor digite un identificador");
	    	}else {
	    		String[] resultado = new String[3];
	    		resultado = inventario.consultarId(id);
	    		
	    		if(resultado[0]==null) {
	    			mostrarMensaje("Error", "No hay un producto con este identificador");
	    		}else {
	    			botonActualizar.setDisable(false);
	    			campoIdentificador.setDisable(true);
	    			botonConsultar.setDisable(true);
	    			campoNombre.setText(resultado[0]);
	    			campoPrecio.setText(resultado[1]);
	    			campoExistencias.setText(resultado[2]);
	    			mostrarLabel(labelNombre, campoNombre);			
		   	 		mostrarLabel(labelIdentificador, campoIdentificador);		    	
		   	 		mostrarLabel(labelPrecio, campoPrecio);		    			    	
		   	 		mostrarLabel(labelExistencias, campoExistencias);
	    		}
	    	}	    	
	    }
	    
	    @FXML
	    void limpiarItems(ActionEvent event) {
	    	campoIdentificador.setText("");
	    	campoNombre.setText("");
			campoPrecio.setText("");
			campoExistencias.setText("");
			campoIdentificador.setDisable(false);
			botonConsultar.setDisable(false);
			botonActualizar.setDisable(true);
			mostrarLabel(labelNombre, campoNombre);			
   	 		mostrarLabel(labelIdentificador, campoIdentificador);		    	
   	 		mostrarLabel(labelPrecio, campoPrecio);		    			    	
   	 		mostrarLabel(labelExistencias, campoExistencias);
	    }

}
