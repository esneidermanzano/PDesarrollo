package Controles;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoCliente;
import BaseDatos.DaoEmpleado;
import BaseDatos.DaoInventario;
import BaseDatos.DaoVenta;
import Clases.Cliente;
import Clases.Item;
import Clases.ItemCotizacion;
import Clases.Validador;
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
    
    @FXML private Label errorNombreCliente;
    @FXML private Label errorIdCliente;
    @FXML private Label errorTelefonoCliente;
    @FXML private Label errorCantidadProducto;
    
    private DaoVenta DV = new DaoVenta();
    private DaoCliente DC = new DaoCliente();
    private Validador V = new Validador();
    private boolean tipoCliente;
    
  //Inicializa las escuchas de los campos:
  	public void initEscuchas() {		
  		nombreCliente.setOnKeyPressed((e) -> {
  			errorNombreCliente.setText("");
  		});
  		idCliente.setOnKeyPressed((e) -> {
  			errorIdCliente.setText("");
  		});
  		telefonoCliente.setOnKeyPressed((e) -> {
  			errorTelefonoCliente.setText("");
  		});		
  		cantidadProducto.setOnKeyPressed((e) -> {
  			errorCantidadProducto.setText("");
  		});	
  	}
    
    public void iniciar(String nombre, String cedula) {
    	idVenta.setText(Integer.toString(DV.siguienteVenta()));
    	datosVendedor.setText(nombre + " CC. " + cedula);
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
    	initEscuchas();
    }
    
    public boolean validar() {
    	boolean A = V.validarCampo(nombreCliente, 50, 2, errorNombreCliente);
    	boolean B = V.validarCampo(idCliente, 10, 1, errorIdCliente);
    	boolean C = V.validarCampo(telefonoCliente, 10, 1, errorTelefonoCliente);
    	return A && B && C;
    }
    
    public void registrarCliente() {
    	if(validar()) {
    		Cliente C = new Cliente(nombreCliente.getText(), idCliente.getText(), telefonoCliente.getText());
    		int registro = DC.registrarCliente(C);
    		if(registro == 1) {
    			V.mostrarMensaje(1, "Cliente registrado con éxito", "Registro de cliente");
    			nombreCliente.setDisable(false);
    			telefonoCliente.setDisable(false);
    			nombreCliente.setText(C.getNombre());
            	telefonoCliente.setText(C.getTelefono());
            	nombreCliente.setEditable(false);
            	telefonoCliente.setEditable(false);
            	idCliente.setEditable(false);
            	iniciar.setDisable(true);
    		}else {
    			V.mostrarMensaje(3, "La cédula ya está registrada", "Error al registrar cliente");
    		}    		
    	}
    }
    
    public void cargarCliente() {
    	if(V.validarCampo(idCliente, 10, 1, errorIdCliente)) {
    		Cliente C = DC.buscarCliente(idCliente.getText());
    		if(C.getNombre() != null && C.getTelefono() != null) {
    			nombreCliente.setDisable(false);
    			telefonoCliente.setDisable(false);
    			nombreCliente.setText(C.getNombre());
            	telefonoCliente.setText(C.getTelefono());
            	nombreCliente.setEditable(false);
            	telefonoCliente.setEditable(false);
            	idCliente.setEditable(false);
            	iniciar.setDisable(true);
    		}else {
    			V.mostrarMensaje(3, "El cliente no está registrado", "Error al cargar cliente");
    		}
    	}
    }
    
    @FXML void iniciarVenta(ActionEvent event) {
    	if(tipoCliente) {
    		cargarCliente();
    	}else {
    		registrarCliente();
    	}
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