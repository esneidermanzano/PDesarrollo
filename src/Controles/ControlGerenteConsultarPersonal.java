package Controles;

import BaseDatos.DaoEmpleado;
import Clases.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    
    public void initialize() {
    	
    	gerente = new DaoEmpleado();
    	
    	// Initialize the person table with the two columns.
    	columnaNombres.setCellValueFactory(cellData -> cellData.getValue().getNombreP());
    	columnaIdentificacion.setCellValueFactory(cellData -> cellData.getValue().getIdP());
    	
    	inicializarEmpleados();
    	/*
    	refrescarEtiquetas(null);*/
    	
    	tablaIndiceEmpleados.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> refrescarEtiquetas(newValue));
    	
    	refrescarEtiquetas(null);
    }
    
    public void inicializarEmpleados() {
    	empleados = gerente.consultarEmpleados();
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
