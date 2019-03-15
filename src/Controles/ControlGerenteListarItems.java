package Controles;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoInventario;
import Clases.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class ControlGerenteListarItems {
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

	    @FXML private JFXButton botonActualizar;

	    @FXML private Text textoNombre;
	    @FXML private Text textoId;
	    
	    public void initialize() {
	    	consultador = new DaoInventario();
	    	botonActualizar.setDisable(true);
	    	FilteredList<Item> filteredData = new FilteredList<>(consultador.obtenerItems(), p -> true);
	    	tablaItems.setItems(filteredData);
	    	
	    	//tablaItems.setItems(consultador.obtenerItems());
	    	columnaId.setCellValueFactory(new PropertyValueFactory<Item, String>("identificador"));
	    	columnaNombre.setCellValueFactory(new PropertyValueFactory<Item, String>("nombre"));
	    	columnaColor.setCellValueFactory(new PropertyValueFactory<Item, String>("color"));
	    	columnaIPrecioUnidad.setCellValueFactory(new PropertyValueFactory<Item, Integer>("valorCompra"));
	    	columnaIngreso.setCellValueFactory(new PropertyValueFactory<Item, String>("fecha"));
	    	columnaSede.setCellValueFactory(new PropertyValueFactory<Item, String>("sede"));
	    	columnaExistencias.setCellValueFactory(new PropertyValueFactory<Item, Integer>("cantidad"));
	    	
	    	campoBuscar.textProperty().addListener((prop, old, text) -> {
	    		textoNombre.setText("");
    	    	textoId.setText("");
    	    	botonActualizar.setDisable(true);
	    	    filteredData.setPredicate(person -> {
	    	    	tablaItems.getSelectionModel().clearSelection();
	    	    	tablaItems.getSelectionModel().select(-1);
	    	        if(text == null || text.isEmpty()) return true;
	    	        
	    	        String name = person.getNombre().toLowerCase();  
	    	        return name.contains(text.toLowerCase());
	    	    });
	    	});
	    	tablaItems.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
	    	    if (nuevo != null) {
	    	    	textoNombre.setText(nuevo.getNombre());
	    	    	textoId.setText(nuevo.getIdentificador());
	    	    	botonActualizar.setDisable(false);
	    	    }
	    	});
	    }
	    
	    @FXML
	    void limpiarItems(ActionEvent event) {
	    }

	    @FXML
	    void actualizarItem(ActionEvent event) {
	    }

	    @FXML
	    void consultarItem(ActionEvent event) {
	    }	    
}
