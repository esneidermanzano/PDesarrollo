package Controles;


import java.util.function.Predicate;

import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoInventario;
import Clases.Item;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class ControlVendedorListarItems {
		private DaoInventario consultador;
	 	
	 	
	 	@FXML
	    private TableView<Item> tablaItems;
	 	@FXML private TableColumn<Item, String> columnaId;
	    @FXML private TableColumn<Item, String> columnaNombre;
	    @FXML private TableColumn<Item, String> columnaColor;	   
	    @FXML private TableColumn<Item, Integer> columnaIPrecioUnidad;
	    @FXML private TableColumn<Item, String> columnaIngreso;
	    @FXML private TableColumn<Item, String> columnaSede;
	    @FXML private TableColumn<Item, Integer> columnaExistencias;
	    
	    @FXML private JFXTextField campoBuscarNombre;
	    @FXML private JFXTextField campoBuscarId;

	    @FXML private Text textoNombre;
	    @FXML private Text textoId;
	    
	    public void initialize() {
	    	consultador = new DaoInventario();
	    	FilteredList<Item> filteredData = new FilteredList<>(consultador.obtenerItems(), p -> true);	    	
	    	SortedList<Item> sortedData = new SortedList<>(filteredData);
	    	
	    	sortedData.comparatorProperty().bind(tablaItems.comparatorProperty());	    	
	    	tablaItems.setItems(sortedData);
	    	
	    	columnaId.setCellValueFactory(new PropertyValueFactory<Item, String>("concatenado"));
	    	columnaNombre.setCellValueFactory(new PropertyValueFactory<Item, String>("nombre"));
	    	columnaColor.setCellValueFactory(new PropertyValueFactory<Item, String>("color"));
	    	columnaIPrecioUnidad.setCellValueFactory(new PropertyValueFactory<Item, Integer>("valorCompra"));
	    	columnaIngreso.setCellValueFactory(new PropertyValueFactory<Item, String>("fecha"));
	    	columnaSede.setCellValueFactory(new PropertyValueFactory<Item, String>("sede"));
	    	columnaExistencias.setCellValueFactory(new PropertyValueFactory<Item, Integer>("cantidad"));
		    	
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
	    	
	    	
	        tablaItems.getSelectionModel().selectedItemProperty().addListener(
	                (observable, oldValue, newValue) -> refrescarEtiquetas(newValue));
	    	
	    	refrescarEtiquetas(null);

	    	
	    	tablaItems.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
	    	    if (nuevo != null) {
	    	    	textoNombre.setText(nuevo.getNombre());
	    	    	textoId.setText(nuevo.getConcatenado());
	    	    }
	    	});
	    }

	    private void refrescarEtiquetas(Item producto) {
	        if (producto != null) {
	        	textoNombre.setText(producto.getNombre());
    	    	textoId.setText(producto.getConcatenado());

	           
	        } else {
	        	textoNombre.setText("");
    	    	textoId.setText("");
	        }
	    }
    
}
