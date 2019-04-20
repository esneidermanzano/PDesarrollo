package Controles;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoEmpleado;
import BaseDatos.DaoInventario;
import Clases.Item;
import Clases.ItemCotizacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControlVendedorRegistrarVentas {
	
	@FXML private TableView<Item> tablaProductos;
	@FXML private TableColumn<Item, String> cID;
	@FXML private TableColumn<Item, String> cNombre;
	@FXML private TableColumn<Item, String> cColor;
	@FXML private TableColumn<Item, String> cValorUnitario;
	@FXML private TableColumn<Item, String> cIngreso;
	@FXML private TableColumn<Item, String> cSede;
	@FXML private TableColumn<Item, String> cCantidad;

	@FXML private TableView<ItemCotizacion> tablaVenta;
	@FXML private TableColumn<ItemCotizacion, String> cIDV;
	@FXML private TableColumn<ItemCotizacion, String> cDescripcionV;
    @FXML private TableColumn<ItemCotizacion, String> cCantidadV;
    @FXML private TableColumn<ItemCotizacion, String> cValorUnitarioV;
    @FXML private TableColumn<ItemCotizacion, String> cValorTotalV;    

    @FXML private Label idVenta;
    @FXML private Label nombreProducto;
    @FXML private Label total;
    @FXML private JFXTextField nombreCliente;
    @FXML private JFXTextField idCliente;
    @FXML private JFXTextField telefonoCliente;
    @FXML private Label datosVendedor;
    @FXML private JFXButton quitarProducto;
    @FXML private JFXTextField cantidadProducto;
    @FXML private JFXButton iniciar;
    @FXML private JFXTextField buscador;
    @FXML private JFXButton imprimirFactura;
    @FXML private JFXButton anadir;
    @FXML private Label idProducto;
    @FXML private JFXButton existente;
    @FXML private JFXButton nuevo;
    
    private DaoInventario DI = new DaoInventario();
    private DaoEmpleado DE = new DaoEmpleado();
    private boolean tipoCliente;
    
    public void iniciar(String nombre, String cedula) {
    	idVenta.setText(Integer.toString(DI.siguienteVenta()));
    	datosVendedor.setText(nombre + "\nCC. " + cedula);
    	nombreCliente.setDisable(true);
    	idCliente.setDisable(true);
    	telefonoCliente.setDisable(true);
    	iniciar.setDisable(true);
    	cantidadProducto.setDisable(true);
    	anadir.setDisable(true);  
    	buscador.setDisable(true);
    	quitarProducto.setDisable(true);
    	imprimirFactura.setDisable(true);
    	tablaProductos.setDisable(true);
    	tablaVenta.setDisable(true);
    }
    
    @FXML void iniciarVenta(ActionEvent event) {
    	
    }

    @FXML
    void anadirProducto(ActionEvent event) {

    }

    @FXML
    void quitarProducto(ActionEvent event) {

    }

    @FXML
    void imprimirFactura(ActionEvent event) {

    }
    
    @FXML
    void clienteNuevo(ActionEvent event) {
    	existente.setDisable(true);
    	nuevo.setDisable(true);
    	nombreCliente.setDisable(false);
    	idCliente.setDisable(false);
    	telefonoCliente.setDisable(false);
    	iniciar.setDisable(false);
    	tipoCliente = false;
    }

    @FXML
    void clienteExistente(ActionEvent event) {
    	existente.setDisable(true);
    	nuevo.setDisable(true);
    	idCliente.setDisable(false);
    	iniciar.setDisable(false);
    	tipoCliente = true;
    }

}