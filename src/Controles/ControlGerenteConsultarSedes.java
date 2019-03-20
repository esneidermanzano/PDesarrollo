package Controles;

import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoEmpleado;
import BaseDatos.DaoSede;
import Clases.Empleado;
import Clases.Sede;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Predicate;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ControlGerenteConsultarSedes {
	@FXML
    private TableView<Sede> tablaIndiceSedes;
	
	private DaoSede daoSedes;
	
	private ObservableList<Sede> sedes = FXCollections.observableArrayList();

    @FXML
    private Label labelNumEmpleados;

    @FXML
    private Label labelId;

    @FXML
    private Label labelDireccion;

    @FXML
    private TableColumn<Sede, String> columnaNombres;

    @FXML
    private Label labelTelefono;

    @FXML
    private Label labelFechaApertura;

    @FXML
    private TableColumn<Sede, String> columnaIdentificacion;

    @FXML
    private Label labelTamano;
    @FXML
    private Label labelNombre;
    @FXML
    private Label labelAvisoTomaSede;    
    @FXML
    private JFXTextField buscarNombre;
    
    @FXML
    private JFXTextField buscarId;
    @FXML
    private Button botonActualizar;

    
    public void initialize() {
    	
    	daoSedes = new DaoSede();
    	
    	/*
    	// Initialize the Sedes table with the two columns.
    	columnaNombres.setCellValueFactory(cellData -> cellData.getValue().getNombreP());
    	columnaIdentificacion.setCellValueFactory(cellData -> cellData.getValue().getIdP());
    	
    	inicializarSedes();
    	
    	refrescarEtiquetas(null);*/
    	
    	columnaNombres.setCellValueFactory(new PropertyValueFactory<Sede, String>("nombre"));
    	columnaIdentificacion.setCellValueFactory(new PropertyValueFactory<Sede, String>("id"));
    	
    	ObjectProperty<Predicate<Sede>> filtroNombre = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Sede>> filtroId = new SimpleObjectProperty<>();
    	
    	filtroNombre.bind(Bindings.createObjectBinding(() -> 
        sede -> sede.getNombre().toLowerCase().contains(buscarNombre.getText().toLowerCase()), 
        buscarNombre.textProperty()));


        filtroId.bind(Bindings.createObjectBinding(() ->
        sede -> sede.getId().toLowerCase().contains(buscarId.getText().toLowerCase()), 
        buscarId.textProperty()));
        
        FilteredList<Sede> filteredItems = new FilteredList<>(daoSedes.consultarSedes(), p -> true);
        tablaIndiceSedes.setItems(filteredItems);

        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(
                () -> filtroNombre.get().and(filtroId.get()), 
                filtroNombre, filtroId));
    	
    	tablaIndiceSedes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> refrescarEtiquetas(newValue));
    	
    	refrescarEtiquetas(null);
    }
    public void inicializarSedes() {
    	sedes = daoSedes.consultarSedes();
    	tablaIndiceSedes.setItems(sedes);
    }
    private void refrescarEtiquetas(Sede sede) {
        if (sede != null) {
            // Fill the labels with info from the sede object.
            labelNombre.setText(sede.getNombre());
            labelId.setText(sede.getId());
            labelTelefono.setText(sede.getTelefono());
            labelDireccion.setText(sede.getDireccion());
            labelTamano.setText(sede.getTamano());
            labelNumEmpleados.setText(sede.getNumEmpleados());
            labelFechaApertura.setText(sede.getFechaApertura());

        } else {
            // Sede is null, remove all the text.
        	labelNombre.setText("");
            labelId.setText("");
            labelTelefono.setText("");
            labelDireccion.setText("");
            labelTamano.setText("");
            labelNumEmpleados.setText("");
            labelFechaApertura.setText("");
        }
    }
    @FXML
    void actualizar_sede(ActionEvent event) throws IOException {
    	try {
	    	Sede sede_tomada=tablaIndiceSedes.getSelectionModel().getSelectedItem();
	    	if(sede_tomada!=null) {
		    	FXMLLoader cargador = new FXMLLoader();
				cargador.setLocation(getClass().getResource("/Vistas/gerente_actualizar_sede2.fxml"));
				Parent GUI4 = (Parent)cargador.load();
				ControlActSedes2 controlador = cargador.getController();
				//CAMBIO____________________________________________________________________________________
				Pane panelCentral= (Pane)((Button)event.getSource()).getParent();
			    panelCentral.getChildren().clear();
				panelCentral.getChildren().add(GUI4);
				Scene scene = GUI4.getScene();			
				GUI4.translateXProperty().set(scene.getWidth());		
				Timeline timeline = new Timeline();
				KeyValue rango = new KeyValue(GUI4.translateXProperty(), 0, Interpolator.EASE_IN);
				KeyFrame duracion = new KeyFrame(Duration.seconds(0.4), rango);
				timeline.getKeyFrames().add(duracion);
				timeline.play();		
				controlador.iniciar(sede_tomada);
			}else { 
				labelAvisoTomaSede.setText("¡No ha seleccionado ninguna sede!");
			}
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }
}
