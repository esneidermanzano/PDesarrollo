package Controles;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoEmpleado;
import Clases.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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