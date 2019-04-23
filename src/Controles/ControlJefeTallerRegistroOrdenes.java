package Controles;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoInventario;
import BaseDatos.DaoOrdenTrabajo;
import Clases.Item;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class ControlJefeTallerRegistroOrdenes {
		private DaoInventario consultador;
		private DaoOrdenTrabajo consultadorOrden;
	 	private Item producto;
	 	private String identificador;
	 	
	 	@FXML
	    private TableView<Item> tablaItems;
	 	@FXML private TableColumn<Item, String> columnaId;
	    @FXML private TableColumn<Item, String> columnaNombre;
	    @FXML private TableColumn<Item, String> columnaColor;	   
	    //@FXML private TableColumn<Item, Integer> columnaIPrecioUnidad;
	    //@FXML private TableColumn<Item, String> columnaIngreso;
	    @FXML private TableColumn<Item, String> columnaSede;
	    //@FXML private TableColumn<Item, Integer> columnaExistencias;
	    
	    @FXML private JFXTextField campoBuscarNombre;
	    @FXML private JFXTextField campoBuscarId;
	    @FXML private JFXTextField campoCantidad;

	    @FXML private Text textoNombre;
	    @FXML private Text textoId;
	    @FXML private Text errorCantidad;
	    @FXML private Text errorFecha;
	    @FXML private Text errorDescripcion;

	    
	    @FXML private JFXButton botonRegistro;
	    
	    @FXML private DatePicker campoFecha;

	    @FXML private JFXTextArea campoDescripcion;

	    public void initialize() {
	    	consultador = new DaoInventario();
	    	consultadorOrden = new DaoOrdenTrabajo();
	    	FilteredList<Item> filteredData = new FilteredList<>(consultador.obtenerItems(), p -> true);	    	
	    	SortedList<Item> sortedData = new SortedList<>(filteredData);
	    	
	    	sortedData.comparatorProperty().bind(tablaItems.comparatorProperty());	    	
	    	tablaItems.setItems(sortedData);
	    	
	    	columnaId.setCellValueFactory(new PropertyValueFactory<Item, String>("concatenado"));
	    	columnaNombre.setCellValueFactory(new PropertyValueFactory<Item, String>("nombre"));
	    	columnaColor.setCellValueFactory(new PropertyValueFactory<Item, String>("color"));
	    	//columnaIPrecioUnidad.setCellValueFactory(new PropertyValueFactory<Item, Integer>("valorCompra"));
	    	//columnaIngreso.setCellValueFactory(new PropertyValueFactory<Item, String>("fecha"));
	    	columnaSede.setCellValueFactory(new PropertyValueFactory<Item, String>("sede"));
	    	//columnaExistencias.setCellValueFactory(new PropertyValueFactory<Item, Integer>("cantidad"));
		    	
	    	ObjectProperty<Predicate<Item>> filtroNombre = new SimpleObjectProperty<>();
	        ObjectProperty<Predicate<Item>> filtroId = new SimpleObjectProperty<>();
	        
	        filtroNombre.bind(Bindings.createObjectBinding(() -> 
	        producto -> producto.getNombre().toLowerCase().contains(campoBuscarNombre.getText().toLowerCase()), 
	        campoBuscarNombre.textProperty()));

	        filtroId.bind(Bindings.createObjectBinding(() ->
	        producto -> producto.getConcatenado().toLowerCase().contains(campoBuscarId.getText().toLowerCase()), 
	        campoBuscarId.textProperty()));	       

	        filteredData.predicateProperty().bind(Bindings.createObjectBinding(
	                () -> filtroNombre.get().and(filtroId.get()), 
	                filtroNombre, filtroId));
	    	
	    	
	       /* tablaItems.getSelectionModel().selectedItemProperty().addListener(
	                (observable, oldValue, newValue) -> refrescarEtiquetas(newValue));*/
	    	
	        textoNombre.setText("");
	    	textoId.setText("");
	    	botonRegistro.setDisable(true);
	    	campoDescripcion.setOnKeyPressed((e) -> {
	    		errorDescripcion.setText("");
			});
	    	campoCantidad.setOnKeyPressed((e) -> {
	    		errorCantidad.setText("");
			});	
	    	campoFecha.valueProperty().addListener((ov, oldValue, newValue) -> {
	    		errorFecha.setText("");
	         });
	    	
	    	tablaItems.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
	    	    if (nuevo != null) {
	    	    	textoNombre.setText(nuevo.getNombre());
	    	    	textoId.setText(nuevo.getConcatenado());
	    	    	botonRegistro.setDisable(false);
	    	    }else {
		        	textoNombre.setText("");
	    	    	textoId.setText("");
	    	    	botonRegistro.setDisable(true);
		        }
	    	});
	    }
	    
	    public void iniciar(String id) {
	    	this.identificador = id;
	    }
	    /*
	    private void refrescarEtiquetas(Item producto) {
	        if (producto != null) {
	        	textoNombre.setText(producto.getNombre());
    	    	textoId.setText(producto.getConcatenado());	           
	        } else {
	        	textoNombre.setText("");
    	    	textoId.setText("");
	        }
	    }
	    */
	    
	    public void mostrarMensaje(String titulo, String mensaje) {
	    	Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(titulo);
			alert.setHeaderText(null);
			alert.setContentText(mensaje);
			alert.showAndWait();
	    }
	    
	    @FXML void registrarOrden(ActionEvent event) {
	    	String informacion = campoDescripcion.getText();
	    	String cantidad = campoCantidad.getText();
	    	LocalDate localDate = campoFecha.getValue();
	    	String fecha = "";
			boolean valido = true;
			if(informacion.replace(" ", "").equals("")) {
				errorDescripcion.setText(("Debe agregar una descripcion"));
				valido = false;
			}
			if(cantidad.equals("")) {
				errorCantidad.setText(("El campo cantidad no puede estar vacio"));
				valido = false;
	    	}else {
	    		if(cantidad.matches("[0-9]*")) {	    		
	    		}else {
	    			errorCantidad.setText(("El campo cantidad debe ser numerico"));
	    			valido = false;
	    		}
	    	}
	    	if(localDate == null) {
	    		errorFecha.setText(("El campo Fecha esta vacio"));
	    		valido = false;
	    	}else {
	    		LocalDate now = LocalDate.now();
		    	if(localDate.isBefore(now)) {
		    		errorFecha.setText(("No debe ser anterior a la actual"));
		    		valido = false;
		    	}else {
		    		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        	fecha = localDate.format(pattern);
		    	}
	        	
	    	}
	    	
	    	if(valido) {
	    		producto = tablaItems.getSelectionModel().getSelectedItem();
	    		valido = consultadorOrden.registrarOrden(
	    				identificador,
	    				Integer.parseInt(producto.getIdentificador()), 
	    				Integer.parseInt(producto.getIdentificador2()),
	    				campoDescripcion.getText(), 
	    				Integer.parseInt(campoCantidad.getText()), 
	    				fecha
	    				);
				if(valido) {
					Alert alert = new Alert(AlertType.INFORMATION);
		    		alert.setTitle("Confirmacion");
		    		alert.setHeaderText(null);
		    		alert.setContentText("La orden se registro con exito");
		    		alert.showAndWait();
		    		campoDescripcion.setText("");
		    		campoCantidad.setText("");
		    		campoFecha.setValue(null);
				}else {
					mostrarMensaje("Error", "Error al procesar el pedido");
				}
	    		
	    	}
	    	/*
	    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	    	LocalDate now = LocalDate.now();
	    	System.out.println(now);

	    	System.out.println(dtf.format(now));
	    	*/

	    }
}
