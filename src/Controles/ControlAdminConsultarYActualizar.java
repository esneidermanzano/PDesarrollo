package Controles;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoEmpleado;
import Clases.Empleado;
import Clases.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.function.Function;
import java.util.function.Predicate;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ControlAdminConsultarYActualizar {
	
	private DaoEmpleado gerente;
	
	private ObservableList<Empleado> empleados = FXCollections.observableArrayList();

    @FXML
    private TableView<Empleado> tablaIndiceEmpleados;

    @FXML
    private JFXTextField campoCorreo;

    @FXML
    private JFXButton botonActualizarGerente;

    @FXML
    private JFXTextField campoTelefono;

    @FXML
    private TableColumn<Empleado, String> columnaIdentificacion;

    @FXML
    private JFXTextField campoGenero;

    @FXML
    private JFXTextField campoId;

    @FXML
    private JFXTextField campoSede;

    @FXML
    private TableColumn<Empleado, String> columnaNombres;

    @FXML
    private JFXTextField campoEstadoCivil;

    @FXML
    private JFXTextField campoEstado;

    @FXML
    private JFXTextField campoPerfil;

    @FXML
    private JFXTextField campoNombre;
    
    @FXML
    private JFXTextField buscarNombre;
    
    @FXML
    private JFXTextField buscarId;
    
    public void initialize() {
    	
    	gerente = new DaoEmpleado();
    	
    	/*FilteredList<Empleado> filteredData = new FilteredList<>(gerente.consultarGerentes(), p -> true);
    	SortedList<Empleado> sortedData = new SortedList<>(filteredData);
    	sortedData.comparatorProperty().bind(tablaIndiceEmpleados.comparatorProperty());
    	tablaIndiceEmpleados.setItems(sortedData);*/
    	
    	columnaNombres.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
    	columnaIdentificacion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("id"));
    
    	/*
	    buscarNombre.textProperty().addListener((prop, old, text) -> {
			
	    	refrescarEtiquetas(null);
	    	
		    filteredData.setPredicate(empleado -> {		    	
		        if(text == null || text.isEmpty()) return true;
		        
		        String name = empleado.getNombre().toLowerCase();  
		        return name.contains(text.toLowerCase());
		    });
	    });
	    buscarId.textProperty().addListener((prop, old, text) -> {
			
	    	refrescarEtiquetas(null);
	    	
		    filteredData.setPredicate(empleado -> {		    	
		        if(text == null || text.isEmpty()) return true;
		        
		        String name = empleado.getId().toLowerCase();  
		        return name.contains(text.toLowerCase());
		    });
	    });*/
    	
    	ObjectProperty<Predicate<Empleado>> filtroNombre = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Empleado>> filtroId = new SimpleObjectProperty<>();
        
        filtroNombre.bind(Bindings.createObjectBinding(() -> 
        empleado -> empleado.getNombre().toLowerCase().contains(buscarNombre.getText().toLowerCase()), 
        buscarNombre.textProperty()));


        filtroId.bind(Bindings.createObjectBinding(() ->
        empleado -> empleado.getId().toLowerCase().contains(buscarId.getText().toLowerCase()), 
        buscarId.textProperty()));
        
        FilteredList<Empleado> filteredItems = new FilteredList<>(gerente.consultarGerentes(), p -> true);
        tablaIndiceEmpleados.setItems(filteredItems);

        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(
                () -> filtroNombre.get().and(filtroId.get()), 
                filtroNombre, filtroId));
    	
    	////////AQUÍ
    	
    	// Initialize the person table with the two columns.
    	//columnaNombres.setCellValueFactory(cellData -> cellData.getValue().getNombreP());
    	//columnaIdentificacion.setCellValueFactory(cellData -> cellData.getValue().getIdP());
    	
    	//inicializarEmpleados();
    	/*
    	refrescarEtiquetas(null);*/
    	
    	tablaIndiceEmpleados.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> refrescarEtiquetas(newValue));
    	
    	refrescarEtiquetas(null);
    }
    
    public void inicializarEmpleados() {
    	empleados = gerente.consultarGerentes();
    	tablaIndiceEmpleados.setItems(empleados);
    }

    private void refrescarEtiquetas(Empleado empleado) {
        if (empleado != null) {
            // Fill the labels with info from the person object.
            campoNombre.setText(empleado.getNombre());
            campoId.setText(empleado.getId());
            campoTelefono.setText(empleado.getTelefono());
            campoEstado.setText(empleado.getEstado());
            campoSede.setText(empleado.getSede());
            campoCorreo.setText(empleado.getCorreo());
            campoPerfil.setText(empleado.getPerfil());
            campoEstadoCivil.setText(empleado.getEstadoCivil());
            campoGenero.setText(empleado.getGenero());

            // TODO: We need a way to convert the birthday into a String! 
            // birthdayLabel.setText(...);
        } else {
            // Person is null, remove all the text.
        	campoNombre.setText("");
            campoId.setText("");
            campoTelefono.setText("");
            campoEstado.setText("");
            campoSede.setText("");
            campoCorreo.setText("");
            campoPerfil.setText("");
            campoEstadoCivil.setText("");
            campoGenero.setText("");
        }
    }

}