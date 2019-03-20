package Controles;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

import BaseDatos.DaoEmpleado;
import BaseDatos.DaoInventario;
import Clases.Item;
import Clases.ItemCotizacion;
import Clases.Validador;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;



public class ControlResgistrarCotizacion {
	private DaoInventario consultador;
	private FilteredList<Item> lista_filtro_items;
	private ObservableList<ItemCotizacion> lista_items_cotizados;
   
    @FXML
    private TableView<Item> tabla_lista_items;
    @FXML private TableColumn<Item, String> columnaId;
    @FXML private TableColumn<Item, String> columnaNombre;
    @FXML private TableColumn<Item, String> columnaColor;
    @FXML private TableColumn<Item, Integer> columnaIPrecioUnidad;
    @FXML private TableColumn<Item, String> columnaIngreso;
    @FXML private TableColumn<Item, Integer> columnaExistencias;
    @FXML private TableColumn<Item, Integer> columnaSede;
//_________________________________________________________________--

    @FXML
    private TableView<ItemCotizacion> tabla_lista_items_cotizados;
    @FXML private TableColumn<ItemCotizacion,String> columnaId1;
    @FXML private TableColumn<ItemCotizacion, String> columnaNombre1;
    @FXML private TableColumn<ItemCotizacion, Integer> columnaCantidad1;
    @FXML private TableColumn<ItemCotizacion, Double> columnaIPrecioUnidad1;
    @FXML private TableColumn<ItemCotizacion,Double> columnaValorTotal1;          
//______________________________________
    @FXML
    private JFXButton boton_borrar;
    @FXML
    private JFXButton boton_registrar;   
    @FXML
    private JFXButton boton_comenzar;
    @FXML
    private JFXTextField campo_nombre_item;
    @FXML
    private JFXButton boton_imprimir;
    @FXML
    private JFXTextField campo_identificador_item;
    @FXML
    private JFXTextField campo_nombre_cliente;
    @FXML
    private JFXTextField campo_identificacion_cliente;
    @FXML
    private JFXTextField campo_buscar_item;
    @FXML
    private JFXTextField campo_cantidad_item;
    
    @FXML private Label label_id_vendedor;
    @FXML private Label label_id_cotizacion;
    @FXML private Label label_valor_total;
    @FXML private Label label_aviso_cantidad;
    @FXML private Label label_aviso_cliente;
    @FXML private Label label_aviso_nombre;
    @FXML private Label label_aviso_imprimir;
    
    public void iniciar(String identificador) {
    	boton_registrar.setDisable(true);
    	boton_imprimir.setDisable(true);
    	boton_borrar.setDisable(true);
    	label_id_vendedor.setText(identificador);
    	
    	consultador = new DaoInventario();
    	lista_filtro_items = new FilteredList<>(consultador.obtenerItems(), p -> true);//Lleno la lista con los items existentes
    	SortedList<Item> sortedData = new SortedList<>(lista_filtro_items);
        sortedData.comparatorProperty().bind(tabla_lista_items.comparatorProperty());
        tabla_lista_items.setItems(sortedData);
        
    	columnaId.setCellValueFactory(new PropertyValueFactory<Item, String>("concatenado"));
    	columnaNombre.setCellValueFactory(new PropertyValueFactory<Item, String>("nombre"));
    	columnaColor.setCellValueFactory(new PropertyValueFactory<Item, String>("color"));
    	columnaIPrecioUnidad.setCellValueFactory(new PropertyValueFactory<Item, Integer>("valorCompra"));
    	columnaIngreso.setCellValueFactory(new PropertyValueFactory<Item, String>("fecha"));
    	columnaSede.setCellValueFactory(new PropertyValueFactory<Item, Integer>("sede"));
    	columnaExistencias.setCellValueFactory(new PropertyValueFactory<Item, Integer>("cantidad"));
    	configuracion_busqueda();
    	eventoSeleccion();
    
    	//__________________________________Segunda tabla
    	lista_items_cotizados= FXCollections.observableArrayList();
    	tabla_lista_items_cotizados.setItems(lista_items_cotizados);
    	
    	columnaId1.setCellValueFactory(new PropertyValueFactory<ItemCotizacion, String>("identificador"));
    	columnaNombre1.setCellValueFactory(new PropertyValueFactory<ItemCotizacion, String>("descripcion"));
    	columnaCantidad1.setCellValueFactory(new PropertyValueFactory<ItemCotizacion, Integer>("cantidad"));
    	columnaIPrecioUnidad1.setCellValueFactory(new PropertyValueFactory<ItemCotizacion, Double>("precio"));
    	columnaValorTotal1.setCellValueFactory(new PropertyValueFactory<ItemCotizacion, Double>("valorTotal"));
    	iniciarEscucha();
    }
    public void iniciarEscucha() {
    	campo_nombre_cliente.setOnKeyPressed((e)->{
    		label_aviso_nombre.setText("");
    	}); 
    	campo_identificacion_cliente.setOnKeyPressed((e)->{
    		label_aviso_cliente.setText("");
    	});
    	campo_cantidad_item.setOnKeyPressed((e)->{
    		label_aviso_cantidad.setText("");
    	});
    }
   
    public void eventoSeleccion() {
    	tabla_lista_items.getSelectionModel().selectedItemProperty().addListener((obs,viejo,nuevo)->{
    		if(nuevo!=null) {
    			campo_identificador_item.setText(nuevo.getConcatenado());
    			campo_nombre_item.setText(nuevo.getNombre());
    		}
    	}
    			
    	);
    	tabla_lista_items_cotizados.getSelectionModel().selectedItemProperty().addListener((obs,viejo,nuevo)->{
    		if(nuevo!=null) {
    			label_aviso_imprimir.setText("");
    		}
    	}
    			
    	);
    }
    
    public void configuracion_busqueda() {//Funcion para configurar la busqueda en la lista de la primera tabla
    	campo_buscar_item.setOnKeyReleased(e ->{
    		campo_buscar_item.textProperty().addListener((prop, old, text) ->{
    			
            lista_filtro_items.setPredicate( Item->{
            	tabla_lista_items.getSelectionModel().clearSelection();
            	tabla_lista_items.getSelectionModel().select(-1);
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
        SortedList<Item> items_ordenados = new SortedList<>(lista_filtro_items);
        items_ordenados.comparatorProperty().bind(tabla_lista_items.comparatorProperty());
        tabla_lista_items.setItems(items_ordenados);
    	});
    }
    
    void calcularTotal(ObservableList<ItemCotizacion> items) {
		double precioTotal=0;
		for(int i=0; i<lista_items_cotizados.size(); i++) {
			precioTotal+=lista_items_cotizados.get(i).getValorTotal();
		}
		label_valor_total.setText(""+precioTotal);
    }
    
    void actualizar_tabla_items_cotizados(String identificador, int candidad_solicitada) {
    	for(int i=0; i<lista_items_cotizados.size(); i++) {
			if(lista_items_cotizados.get(i).getIdentificador()==identificador) {
				lista_items_cotizados.get(i).setCantidad(lista_items_cotizados.get(i).getCantidad()+candidad_solicitada);
				tabla_lista_items_cotizados.refresh();
				calcularTotal(lista_items_cotizados);
			}
		}
    }
    public boolean validarCampo(String prueba, int longitud, int tipo, Label aviso) {
    	
    	boolean salida = true;    	    	
    	if(prueba.replace(" ","").equals("")) {
    		salida = false;
    		aviso.setText("Por favor llene el campo");
    	}else {
    		if(prueba.length() > longitud) {
    			salida = false;
    			aviso.setText("Máximo " + longitud + " caracteres");
    		}else {
    			if(prueba.charAt(prueba.length()-1) != ' ' && prueba.charAt(0) != ' ') {
	    		
	    			if(tipo==1) {
	    				if( !prueba.matches("[0-9]*")) {
	    					salida=false;
	    					aviso.setText("El campo no es un número");
	    				}
	    			}else {
	    				/*if(!prueba.matches("^[A-Z][a-z]*\\s[A-Z][a-z]*\\s[A-Z][a-z]*\\s[A-Z][a-z]*")||!prueba.matches("^[A-Z][a-z]*\\s[A-Z][a-z]*")) {
	    					aviso.setText("Debe ingresar almenos un nombre y un apellido");
	    					salida=false;
	    				}*/
	    			}
    			}else {
    				salida=false;
    				aviso.setText( "Campo con espacios");
    			}
    		}
    	}
		return salida;
    }
    @FXML
    void registrarItemCotizacion(ActionEvent event) {
    	consultador = new DaoInventario();
    	try {
    		Item item_seleccionado=tabla_lista_items.getSelectionModel().getSelectedItem();
    		String identificador= item_seleccionado.getConcatenado();
    		String id= item_seleccionado.getIdentificador();
    		String id2= item_seleccionado.getIdentificador2();
    		String nombre=item_seleccionado.getNombre();
    		String color= item_seleccionado.getColor();
    		
    		int valorCompra=item_seleccionado.getValorCompra();
    		int cantidad= item_seleccionado.getCantidad();
    		int candidad_solicitada = 0;
    		
    		boolean boolcantidad=validarCampo(campo_cantidad_item.getText(),3,1,label_aviso_cantidad);
    		if(boolcantidad) {
    			candidad_solicitada= Integer.parseInt(campo_cantidad_item.getText());
    			if(candidad_solicitada<=cantidad) {     
        			int tipo_insercion= consultador.registrarItemCotizacion(Integer.parseInt(label_id_cotizacion.getText()), Integer.parseInt(item_seleccionado.getIdentificador()), Integer.parseInt(item_seleccionado.getIdentificador2()),Integer.parseInt(campo_cantidad_item.getText()));
        			if(tipo_insercion==1) {
        				actualizar_tabla_items_cotizados(identificador,candidad_solicitada);
        				campo_nombre_item.setText("");
        				campo_cantidad_item.setText(""); 
        				campo_identificador_item.setText("");
        			}else {
    	    			if(tipo_insercion>0) {
    	    				lista_items_cotizados.add(new ItemCotizacion(id,id2,identificador,nombre +" color "+ color,candidad_solicitada,valorCompra));
    	    				calcularTotal(lista_items_cotizados);
    	    				campo_nombre_item.setText("");
            				campo_cantidad_item.setText(""); 
            				campo_identificador_item.setText("");
            				label_aviso_imprimir.setText("");
    	    			}else {
    	    				System.out.println("Fallo al añadir item al inventario");
    	    			}
        			}
        		}else{label_aviso_cantidad.setText("Cantidad no existente");}
    		}
    		
    	}catch(Exception e){
    			System.out.println("No ha seleccionado ningun item"+ e.getMessage());
    	}    	
    }
    public void regresarPanelPrincipal(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/Vistas/menu_inicio.fxml"));
    	efectoCambio(loader, (Pane)((Button)event.getSource()).getParent());
    	ControlMenuInicial C = loader.getController();
    	C.iniciar("vendedor");
    }
    @FXML
    void imprimir(ActionEvent event) {
    	
    	DaoEmpleado consultar_nombre= new DaoEmpleado();
    	String[] nombre_direccion_telefono=consultar_nombre.obtenerNombre(label_id_vendedor.getText());
    	JasperReport reporte; //objeto reporte
        URL path = getClass().getResource("/Reportes/Invoice.jasper"); // localizacion del reporte creado
         try {
        	 System.out.println(""+ lista_items_cotizados.isEmpty());
        	 if(!lista_items_cotizados.isEmpty()) {
        		 regresarPanelPrincipal(event);
	        	 HashMap parametros= new HashMap();
	        	 parametros.put("id_factura",label_id_cotizacion.getText());
	        	 parametros.put("nombre_cliente",campo_nombre_cliente.getText());
	        	 parametros.put("cedula_cliente",campo_identificacion_cliente.getText());
	        	 parametros.put("cedula_vendedor", label_id_vendedor.getText());
	        	 parametros.put("nombre_vendedor",nombre_direccion_telefono[0]);
	        	 parametros.put("direccion_sede",nombre_direccion_telefono[1]);
	        	 parametros.put("telefono_sede",nombre_direccion_telefono[2]);
	        	 parametros.put("total", label_valor_total.getText());
	        	 
	             reporte = (JasperReport) JRLoader.loadObject(path); //Se carga de reporte
	             JasperPrint jprint = JasperFillManager.fillReport(reporte, parametros, new JRBeanCollectionDataSource(lista_items_cotizados)); //Agregamos los parametros para llenar el reporte
	             JasperViewer viewer = new JasperViewer(jprint, false); //vista del reporte
	             viewer.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // permite que no se cierre el programa cuado cierren el pdf
	             viewer.setVisible(true); //Se muestra el repirte
             }else {
            	 label_aviso_imprimir.setText("¡No hay items que imprimir!"); 
             }
         } catch (JRException ex) {
            System.out.println(ex.getMessage());
         }
    }
    @FXML
    void registrarCliente(ActionEvent event) {    	
    	consultador = new DaoInventario();
    	
    	Date fecha= new Date();
    	long tiempo= fecha.getTime();
    	Timestamp time= new Timestamp(tiempo);
    	
    	String nombre_cliente=campo_nombre_cliente.getText();
    	String id_cliente=campo_identificacion_cliente.getText();
    	Validador validar_nombre= new Validador();
    	boolean bool_nombre=validar_nombre.validarCampo(campo_nombre_cliente,50, 2, label_aviso_nombre);
    	boolean bool_id_cliente= validarCampo(id_cliente, 50, 1,label_aviso_cliente);   
    	
    	if(bool_nombre && bool_id_cliente) {
    		boolean cliente_registrado=consultador.registrarCotizacion(label_id_vendedor.getText(), nombre_cliente,id_cliente, time);
    		boton_registrar.setDisable(false);
    	   	boton_imprimir.setDisable(false);
    	   	boton_borrar.setDisable(false);
    	   	campo_nombre_cliente.setEditable(false);
    	   	campo_identificacion_cliente.setEditable(false);
    	   	boton_comenzar.setDisable(true);
    		if(cliente_registrado) {
    			label_id_cotizacion.setText(consultador.consultarNumeroCotizacion( nombre_cliente,id_cliente));
    		}else {
    			System.out.println("No se pudo añadir cliente");
    		}
    	}
    	
    }


    @FXML
    void borrarProducto(ActionEvent event) {
    	consultador= new DaoInventario();
    	try {
    		ItemCotizacion item_seleccionado=tabla_lista_items_cotizados.getSelectionModel().getSelectedItem();
    		String id= item_seleccionado.getIdentificador();
    		String id2= item_seleccionado.getIdentificador2();
    		if(consultador.borrarItemCotizacion(Integer.parseInt(label_id_cotizacion.getText()), Integer.parseInt(id),Integer.parseInt(id2))) {
    			System.out.println("Producto elminado exitosamente");
    			lista_items_cotizados.remove(item_seleccionado);
    			calcularTotal(lista_items_cotizados);
    		}else {
    			System.out.println("No elimino");
    		}    		
    	}catch(Exception e){
    		label_aviso_imprimir.setText("No ha seleccionado ningun item");
    	}
    }
    public void efectoCambio(FXMLLoader cargador, Pane panelCentral) {
		try {
			Parent gui = (Parent)cargador.load();
			panelCentral.getChildren().clear();
			panelCentral.getChildren().add(gui);
			Scene scene = gui.getScene();						
			gui.translateXProperty().set(scene.getWidth());		
			Timeline timeline = new Timeline();
			KeyValue rango = new KeyValue(gui.translateXProperty(), 0, Interpolator.EASE_IN);
			KeyFrame duracion = new KeyFrame(Duration.seconds(0.4), rango);
			timeline.getKeyFrames().add(duracion);
			timeline.play();
		} catch (IOException e) {
			System.out.println("Se presento un problema con la carga del modulo: " + e.getMessage());
		}		
    }
    
}