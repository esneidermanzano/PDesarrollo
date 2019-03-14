package Controles;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoInventario;
import Clases.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControlGerenteListarItems {
		private DaoInventario consultador;
	 	
	 	
	 	@FXML
	    private TableView<Item> tablaItems;
	 	@FXML private TableColumn<Item, String> columnaId;
	    @FXML private TableColumn<Item, String> columnaNombre;
	    @FXML private TableColumn<Item, String> columnaColor;	   
	    @FXML private TableColumn<Item, Integer> columnaIPrecioUnidad;
	    @FXML private TableColumn<Item, String> columnaIngreso;
	    @FXML private TableColumn<Item, Integer> columnaSede;
	    @FXML private TableColumn<Item, Integer> columnaExistencias;
	    
	    @FXML
	    private JFXTextField campoIdentificador;
	    @FXML
	    private JFXTextField campoPrecio;
	    @FXML
	    private JFXTextField campoExistencias;
	    @FXML
	    private JFXTextField campoNombre;

	    @FXML
	    private JFXButton botonActualizar;
	    @FXML
	    private JFXButton botonConsultar;
	    @FXML
	    private JFXButton botonLimpiar;
	    
	    public void initialize() {
	    	
	    	consultador = new DaoInventario();
	    	tablaItems.setItems(consultador.obtenerItems());
	    	columnaId.setCellValueFactory(new PropertyValueFactory<Item, String>("identificador"));
	    	columnaNombre.setCellValueFactory(new PropertyValueFactory<Item, String>("nombre"));
	    	columnaColor.setCellValueFactory(new PropertyValueFactory<Item, String>("color"));
	    	columnaIPrecioUnidad.setCellValueFactory(new PropertyValueFactory<Item, Integer>("valorCompra"));
	    	columnaIngreso.setCellValueFactory(new PropertyValueFactory<Item, String>("fecha"));
	    	columnaSede.setCellValueFactory(new PropertyValueFactory<Item, Integer>("sede"));
	    	columnaExistencias.setCellValueFactory(new PropertyValueFactory<Item, Integer>("cantidad"));
	    	eventoSeleccion();
	    }
	    
	    public void eventoSeleccion() {
	    	tablaItems.getSelectionModel().selectedItemProperty().addListener(
	    			new ChangeListener<Item>() {
	    				public void changed(ObservableValue<? extends Item> arg0,
	    						Item valorViejo, Item valorNuevo) {
	    						campoNombre.setText(valorNuevo.getNombre());
	    						campoIdentificador.setText(valorNuevo.getIdentificador());
	    				}
	    			}
	    	);
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
