package Controles;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoInventario;
import Clases.Item;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
	    
	    @FXML private JFXTextField campoBuscar;

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
	    	
	    	campoBuscar.textProperty().addListener((prop, old, text) -> {
	    		textoNombre.setText("");
    	    	textoId.setText("");
	    	    filteredData.setPredicate(person -> {
	    	    	//tablaItems.getSelectionModel().clearSelection();
	    	    	//tablaItems.getSelectionModel().select(-1);
	    	        if(text == null || text.isEmpty()) return true;
	    	        
	    	        String name = person.getNombre().toLowerCase();  
	    	        return name.contains(text.toLowerCase());
	    	    });
	    	});
	    	
	    	tablaItems.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
	    	    if (nuevo != null) {
	    	    	textoNombre.setText(nuevo.getNombre());
	    	    	textoId.setText(nuevo.getConcatenado());
	    	    }
	    	});
	    }


    
}
