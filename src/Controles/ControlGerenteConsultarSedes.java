package Controles;

import BaseDatos.DaoEmpleado;
import BaseDatos.DaoSede;
import Clases.Sede;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    
    public void initialize() {
    	
    	daoSedes = new DaoSede();
    	
    	// Initialize the Sedes table with the two columns.
    	columnaNombres.setCellValueFactory(cellData -> cellData.getValue().getNombreP());
    	columnaIdentificacion.setCellValueFactory(cellData -> cellData.getValue().getIdP());
    	
    	inicializarSedes();
    	/*
    	refrescarEtiquetas(null);*/
    	
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
}
