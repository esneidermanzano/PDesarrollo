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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControlVendedorRegistrarVentas {
	
	@FXML private TableView<Item> tablaProductos;
	@FXML private TableColumn<Item, String> cID;
	@FXML private TableColumn<Item, String> cNombre;
	@FXML private TableColumn<Item, String> cColor;
	@FXML private TableColumn<Item, Integer> cValorUnitario;
	@FXML private TableColumn<Item, String> cIngreso;
	@FXML private TableColumn<Item, Integer> cSede;
	@FXML private TableColumn<Item, Integer> cCantidad;

	@FXML private TableView<ItemCotizacion> tablaVenta;
	@FXML private TableColumn<ItemCotizacion, String> cIDV;
	@FXML private TableColumn<ItemCotizacion, String> cDescripcionV;
    @FXML private TableColumn<ItemCotizacion, Integer> cCantidadV;
    @FXML private TableColumn<ItemCotizacion, Double> cValorUnitarioV;
    @FXML private TableColumn<ItemCotizacion, Double> cValorTotalV;    

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
    private DaoInventario DI = new DaoInventario();
    private Validador V = new Validador();
    private boolean tipoCliente;
    
    private FilteredList<Item> productos;
	private ObservableList<ItemCotizacion> productosVenta;
	
	private String nombre, cedula;
    
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
    
  	public void iniciarTablas() {
    	productos = new FilteredList<>(DI.obtenerItems(), p -> true);
    	SortedList<Item> sortedData = new SortedList<>(productos);
        sortedData.comparatorProperty().bind(tablaProductos.comparatorProperty());
        tablaProductos.setItems(sortedData);
        
    	cID.setCellValueFactory(new PropertyValueFactory<Item, String>("concatenado"));
    	cNombre.setCellValueFactory(new PropertyValueFactory<Item, String>("nombre"));
    	cColor.setCellValueFactory(new PropertyValueFactory<Item, String>("color"));
    	cValorUnitario.setCellValueFactory(new PropertyValueFactory<Item, Integer>("valorCompra"));
    	cIngreso.setCellValueFactory(new PropertyValueFactory<Item, String>("fecha"));
    	cSede.setCellValueFactory(new PropertyValueFactory<Item, Integer>("sede"));
    	cCantidad.setCellValueFactory(new PropertyValueFactory<Item, Integer>("cantidad"));
    	
    	productosVenta = FXCollections.observableArrayList();
    	tablaVenta.setItems(productosVenta);
    	
    	cIDV.setCellValueFactory(new PropertyValueFactory<ItemCotizacion, String>("identificador"));
    	cDescripcionV.setCellValueFactory(new PropertyValueFactory<ItemCotizacion, String>("descripcion"));
    	cCantidadV.setCellValueFactory(new PropertyValueFactory<ItemCotizacion, Integer>("cantidad"));
    	cValorUnitarioV.setCellValueFactory(new PropertyValueFactory<ItemCotizacion, Double>("precio"));
    	cValorTotalV.setCellValueFactory(new PropertyValueFactory<ItemCotizacion, Double>("valorTotal"));
  	}
  	
  	public void iniciarBuscador() {
    	buscador.setOnKeyReleased(e ->{
    		buscador.textProperty().addListener((prop, old, text) ->{
    			
            productos.setPredicate( Item->{
            	tablaProductos.getSelectionModel().clearSelection();
            	tablaProductos.getSelectionModel().select(-1);
                if(text == null || text.isEmpty()){
                    return true;
                }
                String busqueda = text.toLowerCase();
                if(Item.getConcatenado().contains(text)){
                    return true;
                }else if(Item.getNombre().toLowerCase().contains(busqueda)){
                    return true;
                }
                return false;
            });
        });
        SortedList<Item> items_ordenados = new SortedList<>(productos);
        items_ordenados.comparatorProperty().bind(tablaProductos.comparatorProperty());
        tablaProductos.setItems(items_ordenados);
    	});
    }
  	
  	public void eventoSeleccion() {
    	tablaProductos.getSelectionModel().selectedItemProperty().addListener((obs,viejo,nuevo)->{
    		if(nuevo != null) {
    			idProducto.setText(nuevo.getConcatenado());
    			nombreProducto.setText(nuevo.getNombre());
    			cantidadProducto.setDisable(false);
    			anadir.setDisable(false);
    		}
    	}
    			
    	);
  	}
  	
    public void iniciar(String nombre, String cedula) {
    	
    	this.nombre = nombre;
    	this.cedula = cedula;
    	
    	idVenta.setText(Integer.toString(DV.siguienteVenta()));
    	datosVendedor.setText(nombre + " CC. " + cedula);
    	
    	iniciarTablas();
    	iniciarBuscador();
    	eventoSeleccion();
    	initEscuchas();
    	
    	tablaProductos.setDisable(true);
    	tablaVenta.setDisable(true);
    	buscador.setDisable(true);
    	nombreCliente.setDisable(true);
    	idCliente.setDisable(true);
    	telefonoCliente.setDisable(true);
    	iniciar.setDisable(true);
    	cantidadProducto.setDisable(true);
    	anadir.setDisable(true);  
    	quitarProducto.setDisable(true);
    	imprimirFactura.setDisable(true);
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
    			tablaProductos.setDisable(false);
    	    	buscador.setDisable(false);
    		}else {
    			V.mostrarMensaje(3, "La cédula ingresada ya está registrada", "Error al registrar cliente");
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
            	tablaProductos.setDisable(false);
            	buscador.setDisable(false);
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
    
    public int existeProducto(ItemCotizacion IC) {
    	for(int x=0; x<productosVenta.size(); x++) {
    		if(productosVenta.get(x).getIdentificador() == IC.getIdentificador()) {
    			return x;
    		}
    	}
    	return -1;
    }
    
    public void calcularTotal() {
    	double costoTotal = 0;
    	for(int x=0; x<productosVenta.size(); x++) {
    		costoTotal += productosVenta.get(x).getValorTotal();
    	}
    	total.setText("" + costoTotal);
    }
    
    public int obtenerCantidad(String concatenado) {
    	int cantidad = 0;
    	for(int x=0; x<productos.size(); x++) {
    		if(productos.get(x).getConcatenado() == concatenado) {
    			cantidad = productos.get(x).getCantidad();
    		}
    	}
    	return cantidad;
    }
    
    public void insertarProducto(ItemCotizacion IC) {
    	int pos = existeProducto(IC);
    	if(pos != -1) {
    		ItemCotizacion I = productosVenta.get(pos);
    		int nuevaCantidad = I.getCantidad() + IC.getCantidad();
    		if(nuevaCantidad <= obtenerCantidad(I.getIdentificador())) {
    			I.setCantidad(nuevaCantidad);
        		tablaVenta.refresh();
        		calcularTotal();
        		tablaVenta.setDisable(false);
    		}else {
    			V.mostrarMensaje(3, "No hay suficientes existencias", "Error al registrar producto");
    		}
    	}else {
    		productosVenta.add(IC);
    		tablaVenta.refresh();
    		calcularTotal();
    		tablaVenta.setDisable(false);
    	}
    }

    @FXML
    void anadirProducto(ActionEvent event) {
    	
    	Item I = tablaProductos.getSelectionModel().getSelectedItem();
    	String id = I.getIdentificador();
    	String id2 = I.getIdentificador2();
    	String concatenado = I.getConcatenado();
    	String descripcion = I.getNombre() + " color " + I.getColor();
    	int cantidadSolicitada;
    	int valorCompra = I.getValorCompra();
    	
    	if(V.validarCampo(cantidadProducto, 3, 1, errorCantidadProducto)) {
    		cantidadSolicitada = Integer.parseInt(cantidadProducto.getText());
    		if(cantidadSolicitada != 0) {
    			if(cantidadSolicitada <=  I.getCantidad()) {
    				ItemCotizacion IC = new ItemCotizacion(id,id2,concatenado,descripcion,cantidadSolicitada,valorCompra);
        			insertarProducto(IC);
        		}else {
        			V.mostrarMensaje(3, "No hay suficientes existencias", "Error al registrar producto");
        		}    			
    		}else {
    			V.mostrarMensaje(3, "La cantidad ingresada debe ser mayor a 0", "Error al registrar producto");
    		}
    	}   	
    }

    @FXML
    void quitarProducto(ActionEvent event) {

    }

    @FXML
    void imprimirFactura(ActionEvent event) {

    }
    
    public void borrarError() {
    	errorNombreCliente.setText("");
    	errorIdCliente.setText("");
    	errorTelefonoCliente.setText("");
    }
    
    @FXML
    void clienteNuevo(ActionEvent event) {
    	nuevo.setDisable(true);
    	existente.setDisable(false);
    	nombreCliente.setDisable(false);
    	idCliente.setDisable(false);
    	telefonoCliente.setDisable(false);
    	nombreCliente.setEditable(true);
    	telefonoCliente.setEditable(true);
    	iniciar.setDisable(false);
    	tipoCliente = false;
    	borrarError();
    }

    @FXML
    void clienteExistente(ActionEvent event) {
    	existente.setDisable(true);
    	nuevo.setDisable(false);
    	idCliente.setDisable(false);
    	nombreCliente.setDisable(true);
    	telefonoCliente.setDisable(true);
    	iniciar.setDisable(false);
    	tipoCliente = true;
    	borrarError();
    }

}