package Controles;

import BaseDatos.DaoEmpleado;
import Clases.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.function.Function;
import java.util.function.Predicate;

import com.jfoenix.controls.JFXTextField;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ControlGerenteConsultarPersonal {
	
	private DaoEmpleado gerente;
	
	private ObservableList<Empleado> empleados = FXCollections.observableArrayList();
	
	@FXML
    private TableView<Empleado> tablaIndiceEmpleados;

    @FXML
    private Label labelSede;

    @FXML
    private Label labelEstadoCivil;

    @FXML
    private Label labelGenero;

    @FXML
    private Label labelId;

    @FXML
    private Label labelPerfil;

    @FXML
    private Label labelCorreo;

    @FXML
    private TableColumn<Empleado, String> columnaNombres;

    @FXML
    private Label labelTelefono;

    @FXML
    private Label labelEstado;

    @FXML
    private TableColumn<Empleado, String> columnaIdentificacion;

    @FXML
    private Label labelNombre;
    
    @FXML
    private JFXTextField buscarNombre;
    
    @FXML
    private JFXTextField buscarId;
    
    public void initialize() {
    	
    	gerente = new DaoEmpleado();
    	
    	// Initialize the person table with the two columns.
    	/*columnaNombres.setCellValueFactory(cellData -> cellData.getValue().getNombreP());
    	columnaIdentificacion.setCellValueFactory(cellData -> cellData.getValue().getIdP());*/
    	
    	columnaNombres.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
    	columnaIdentificacion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("id"));
    	/*
    	inicializarEmpleados();
    	/*
    	refrescarEtiquetas(null);*/
    	
    	ObjectProperty<Predicate<Empleado>> filtroNombre = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Empleado>> filtroId = new SimpleObjectProperty<>();
        
        filtroNombre.bind(Bindings.createObjectBinding(() -> 
        empleado -> empleado.getNombre().toLowerCase().contains(buscarNombre.getText().toLowerCase()), 
        buscarNombre.textProperty()));


        filtroId.bind(Bindings.createObjectBinding(() ->
        empleado -> empleado.getId().toLowerCase().contains(buscarId.getText().toLowerCase()), 
        buscarId.textProperty()));
        
        FilteredList<Empleado> filteredItems = new FilteredList<>(gerente.consultarEmpleados(false), p -> true);
        tablaIndiceEmpleados.setItems(filteredItems);

        filteredItems.predicateProperty().bind(Bindings.createObjectBinding(
                () -> filtroNombre.get().and(filtroId.get()), 
                filtroNombre, filtroId));
    	
    	tablaIndiceEmpleados.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> refrescarEtiquetas(newValue));
    	
    	refrescarEtiquetas(null);
    }
    
    public void inicializarEmpleados() {
    	empleados = gerente.consultarEmpleados(false);
    	tablaIndiceEmpleados.setItems(empleados);
    }
    
    private void refrescarEtiquetas(Empleado empleado) {
        if (empleado != null) {
            // Fill the labels with info from the person object.
            labelNombre.setText(empleado.getNombre());
            labelId.setText(empleado.getId());
            labelTelefono.setText(empleado.getTelefono());
            labelEstado.setText(empleado.getEstado());
            labelSede.setText(empleado.getSede());
            labelCorreo.setText(empleado.getCorreo());
            labelPerfil.setText(empleado.getPerfil());
            labelEstadoCivil.setText(empleado.getEstadoCivil());
            labelGenero.setText(empleado.getGenero());

            // TODO: We need a way to convert the birthday into a String! 
            // birthdayLabel.setText(...);
        } else {
            // Person is null, remove all the text.
        	labelNombre.setText("");
            labelId.setText("");
            labelTelefono.setText("");
            labelEstado.setText("");
            labelSede.setText("");
            labelCorreo.setText("");
            labelPerfil.setText("");
            labelEstadoCivil.setText("");
            labelGenero.setText("");
        }
    }

}
