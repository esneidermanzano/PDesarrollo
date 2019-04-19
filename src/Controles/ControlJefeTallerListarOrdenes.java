package Controles;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

//import BaseDatos.DaoInventario;
import BaseDatos.DaoOrdenTrabajo;
import Clases.OrdenTrabajo;
import Clases.Validador;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
//import Clases.OrdenTrabajo;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
//import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ControlJefeTallerListarOrdenes {
	
 	private DaoOrdenTrabajo consultador = new DaoOrdenTrabajo();
 	String Estado="";
 	Validador val = new Validador();
 	@FXML
    private TableView<OrdenTrabajo> tablaOrdenesTrabajo;
	@FXML private TableColumn<OrdenTrabajo, Integer> columnaId;
    @FXML private TableColumn<OrdenTrabajo, Integer> columnaId_articulo;
    @FXML private TableColumn<OrdenTrabajo, String> columnaDescripcion;	   
    @FXML private TableColumn<OrdenTrabajo, String> columnaJefeResponsable;
    @FXML private TableColumn<OrdenTrabajo, String> columnaFechaEntrega;
    @FXML private TableColumn<OrdenTrabajo, String> columnaFechaIngreso;
    @FXML private TableColumn<OrdenTrabajo, Integer> columnaCantidad;
    @FXML private TableColumn<OrdenTrabajo, String> columnaEstado;
    
    
    @FXML private JFXTextField campoBuscar;

    @FXML private JFXButton actualizarOrden;

    @FXML private Text textoDescripcion;
    @FXML private Text textoOrden;
    @FXML private Text textoFecha;
    
    @FXML private JFXButton eliminarOrden;
    
    
    
    public void initialize() {
    	actualizarOrden.setDisable(true);
    	eliminarOrden.setDisable(true);
    	
    	FilteredList<OrdenTrabajo> filteredData= cargarTabla();
     	
    	campoBuscar.textProperty().addListener((prop, old, text) -> {
    		textoDescripcion.setText("");
	    	textoOrden.setText("");
	    	actualizarOrden.setDisable(true);
	    	eliminarOrden.setDisable(true);
	    	
    	    filteredData.setPredicate(person -> {
    	    	//tablaItems.getSelectionModel().clearSelection();
    	    	//tablaItems.getSelectionModel().select(-1);
    	        if(text == null || text.isEmpty()) return true;
    	        
    	        String name = person.getJefe().toLowerCase();  
    	        return name.contains(text.toLowerCase()) 
    	        		|| 
    	        		person.getEstado().toLowerCase().contains(text.toLowerCase())
    	        		||
    	        		person.getDescripcion().toLowerCase().contains(text.toLowerCase());
    	    });
    	});
    	
    	tablaOrdenesTrabajo.getSelectionModel().selectedItemProperty().addListener((obs, viejo, nuevo) -> {
    	    if (nuevo != null) {
    	    	textoDescripcion.setText(nuevo.getDescripcion());
    	    	textoOrden.setText("" + nuevo.getId());
    	    	textoFecha.setText(nuevo.getF_entrega());
    	    	Estado = nuevo.getEstado();
    	    	actualizarOrden.setDisable(false);
    	    	eliminarOrden.setDisable(false);
    	    	
    	    }
    	});
    	
    }
    
    @FXML 
    void eliminarOrden (ActionEvent e) {
    	
    	switch(this.Estado)
    	{
    	case "eliminada":
    		System.out.println("eliminada");
    		val.mostrarMensaje(1, "Orden eliminada, imposible eliminar", "ORDEN ELIMINADA");
    		break;
    	case "terminada":
    		val.mostrarMensaje(1, "Orden terminada anterirormente, inesesario eliminar ", "ORDEN TERMINADA");
    		break;
    	case "recibida":
    		DaoOrdenTrabajo.cambiarEstadoOrden(textoOrden.getText(), "eliminada");
    		val.mostrarMensaje(2, "Orden orden eliminada satisfactorimanete ", "ORDEN ELIMINADA");
    		cargarTabla();
    		break;
    	case "desarrollo":
    		DaoOrdenTrabajo.cambiarEstadoOrden(textoOrden.getText(), "eliminada");
    		val.mostrarMensaje(2, "Orden eliminada satisfactoriamente", "ORDEN ELIMINADA");
    		cargarTabla();
    		break;
    	default:
    		DaoOrdenTrabajo.cambiarEstadoOrden(textoOrden.getText(), "eliminada");
    		val.mostrarMensaje(2, "Orden eliminada satisfactoriamente", "ORDEN ELIMINADA");
    		cargarTabla();
    	}
    	
    }
    	
    
   
    
    @FXML
    void actualizarOrden(ActionEvent event) throws IOException {
    	FXMLLoader cargador = new FXMLLoader();
		cargador.setLocation(getClass().getResource("/Vistas/jefe_taller_actualizar_ordenes.fxml"));
		Parent gui = (Parent)cargador.load();
		ControlJefeTallerActualizarOrdenes controlador = cargador.getController();
		Pane panelCentral= (Pane)((Button)event.getSource()).getParent();
	    panelCentral.getChildren().clear();
		panelCentral.getChildren().add(gui);
		Scene scene = gui.getScene();			
		gui.translateXProperty().set(scene.getWidth());		
		Timeline timeline = new Timeline();
		KeyValue rango = new KeyValue(gui.translateXProperty(), 0, Interpolator.EASE_IN);
		KeyFrame duracion = new KeyFrame(Duration.seconds(0.4), rango);
		timeline.getKeyFrames().add(duracion);
		timeline.play();		
		controlador.inicializar(tablaOrdenesTrabajo.getSelectionModel().getSelectedItem());
    	
    }
    
    FilteredList<OrdenTrabajo> cargarTabla() {
    	FilteredList<OrdenTrabajo> filteredData = new FilteredList<>(consultador.obtenerOrdenes(), p -> true);	
    	SortedList<OrdenTrabajo> sortedData = new SortedList<>(filteredData);
    	sortedData.comparatorProperty().bind(tablaOrdenesTrabajo.comparatorProperty());	 
    	tablaOrdenesTrabajo.setItems(sortedData);
    	
    	columnaId.setCellValueFactory(new PropertyValueFactory<OrdenTrabajo, Integer>("id"));
    	columnaJefeResponsable.setCellValueFactory(new PropertyValueFactory<OrdenTrabajo, String>("jefe"));
    	columnaId_articulo.setCellValueFactory(new PropertyValueFactory<OrdenTrabajo, Integer>("id_articulo"));
    	columnaDescripcion.setCellValueFactory(new PropertyValueFactory<OrdenTrabajo, String>("descripcion"));
    	columnaFechaEntrega.setCellValueFactory(new PropertyValueFactory<OrdenTrabajo, String>("f_entrega"));
    	columnaFechaIngreso.setCellValueFactory(new PropertyValueFactory<OrdenTrabajo, String>("f_creacion"));
    	columnaCantidad.setCellValueFactory(new PropertyValueFactory<OrdenTrabajo, Integer>("cantidad"));
    	columnaEstado.setCellValueFactory(new PropertyValueFactory<OrdenTrabajo, String>("estado"));
    	
    	return filteredData;
    }

}
