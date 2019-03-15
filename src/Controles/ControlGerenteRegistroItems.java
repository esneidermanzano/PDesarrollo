package Controles;

import java.awt.Label;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import BaseDatos.DaoInventario;
import BaseDatos.DaoSede;

import com.jfoenix.validation.IntegerValidator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;


public class ControlGerenteRegistroItems {
	
	@FXML
    private JFXTextField costofabItem;

    @FXML
    private JFXTextField nombreItem;

    @FXML
    private JFXTextField cantidadItems;

    @FXML
    private DatePicker fechaRegistroItem;

    @FXML
    private JFXTextField valorVentaItem;

    @FXML
    private JFXTextField colorItem;

    @FXML
    private JFXTextField materialItem;
    
    @FXML
    private JFXComboBox<String> referenciaItem;

    @FXML
    private JFXButton botonRegistroItems;
    

    @FXML
    private JFXComboBox<String> comboBoxSede;
    
    private boolean nuevo;
    private DaoSede cBox;
    
   
    private String nombre;
    private String cantidad;
    private String color;
    private String fecha;
    private String material;
    private String costofab;
    private String costoVenta;
    private String referencia;
    private String Sede;
    
    

    
    @FXML
    //se ejecuta al clickear el ComboBox que muestra las Sedes.
    void onClickedSedes() {
    	cBox = new DaoSede();
    	 List<String> listaSedes = cBox.obtenerNombresDeSedes();
    	 ObservableList<String> sedes = FXCollections.observableArrayList(listaSedes);
    	 comboBoxSede.setItems(sedes);
    }
    
    @FXML
    void switchCampos(boolean bit) {
    	
    	cantidadItems.setDisable(bit);
        costofabItem.setDisable(bit);
        colorItem.setDisable(bit);
        materialItem.setDisable(bit);
        valorVentaItem.setDisable(bit);
        fechaRegistroItem.setDisable(bit);
        comboBoxSede.setDisable(bit);
        
    }
    @FXML 
    //se ejecuta al clickear el combobox que muestra las referencias.
    void onClickedReferencias() {
    	DaoInventario inventario = new DaoInventario();
    	List<String> listaReferencias = inventario.obtenerReferencias();
    	listaReferencias.add(0, "Nuevo");
    	ObservableList<String> referencias = FXCollections.observableArrayList(listaReferencias);
    	referenciaItem.setItems(referencias);
    	
    	referenciaItem.valueProperty().addListener((o,oldVal,newVal)->{
    	     if(newVal != "Nuevo" && newVal != null){
    	    	 switchCampos(false);
    	    	 } else if(newVal == "Nuevo" && oldVal != "Nuevo") {
    	    		switchCampos(true); 
    	    	 }
    	     }
    	);
    }
    
    
    boolean verificarCampos(){
    	//se verifican que los campos cumplan condiciones de aceptacion.
    	if(
    verificarReferencia()
    &&
    verificarNombre()
    &&
    verificarColor()
    &&
    verificarMaterial()
    &&
    verificarCostoFabricacion()
    &&
    verificarCostoVenta()
    &&
    verificarCantidad()
    && 
    verificarFecha()
    &&
    verificarSede()
    ) {return true;}
    	return false;
    }
    @FXML
    private boolean verificarSede() {
		// TODO Auto-generated method stub
    	 // por definir, consultas a la base de datos.
    	if(nuevo) {
    		return true;
    	}else if(comboBoxSede.getValue() != null) {
    		this.Sede= comboBoxSede.getValue();
    		return true;
    	}
		return false;
	}
    @FXML
	private boolean verificarFecha() {
		// TODO Auto-generated method stub
    	if(nuevo) {
    		return true;
    	}else if(fechaRegistroItem.getValue() != null) {
    		String date=  fechaRegistroItem.getValue().toString();
    		this.fecha = date;
    		return true;}
    	System.out.println("fecha vacia");
		return false;
	}
	@FXML
    private boolean verificarCostoVenta() {
    	
    	//validation request for data base.
		// TODO Auto-generated method stub
    	String valorVenta= valorVentaItem.getText();
		if(nuevo) {
			return true;
		}else if(valorVentaItem.getText().matches("[0-9]*") && valorVenta.matches("[^\t]*") && valorVenta.length()!=0){
			this.costoVenta= valorVenta;
			return true;}
		System.out.println("valor venta incorrecto");
		valorVentaItem.clear();
		return false;
	}
    
    @FXML
	private boolean verificarCostoFabricacion() {
		
    	//validation request for data base.
		String costofab = costofabItem.getText();
		if(nuevo) {
			return true;
		}else if(costofab.matches("[0-9]*") && costofab.matches("[^\t]*") && costofab.length()!=0) {
			this.costofab = costofab;
			return true;}
		System.out.println("Costo fab incorrecto");
		costofabItem.clear();
		return false;
		// TODO Auto-generated method stub
		
	}
    
    @FXML
	private boolean verificarMaterial() {
		
    	//validation request for data base.
		String materialSeleccionado = materialItem.getText();
		if(nuevo) {
			return true;
		}else if(materialSeleccionado.matches("[a-z]*") && materialSeleccionado.matches("[^\t]*") && materialSeleccionado.length()!=0) {
			this.material = materialSeleccionado;
			return true;}
		System.out.println("material incorrecto");
		return false;
		// TODO Auto-generated method stub
		
	}
    
    @FXML
	private boolean verificarColor() {
		
    	//Validation for data base request.
		String color = colorItem.getText();
		if(nuevo){
    	return true;
    	}else if(color.matches("[a-z]*") && color.matches("[^\t]*") && color.length()!=0) {
			this.color = color;
			return true;}
		System.out.println("Color incorrecto");
		return false;
		// TODO Auto-generated method stub
		
	}
    
    @FXML
	private boolean verificarCantidad() {
		
    	//validation for data base request.
		String cantidad = cantidadItems.getText();
		if(nuevo) {
			return true;
		}else if(cantidad.matches("[0-9]*") && cantidad.matches("[^\t]*") && cantidad.length() != 0) {
			this.cantidad=cantidad;
			return true;}
		System.out.println("cantidad incorrecta");
		return false;
		// TODO Auto-generated method stub
		
	}
    
    @FXML
	private boolean verificarNombre() {
		
    	//validation for data base request.
		String nombre = nombreItem.getText();
		if(nombre.matches("[a-z]*") && nombre.matches("[^\t]*") && nombre.length() != 0) {
			this.nombre = nombre;
			return true;}
		
		//feedback for user.
		System.out.println("nombre incorrecto");
		return false;
		// TODO Auto-generated method stub
		
	}
    
    @FXML
	private boolean verificarReferencia() {
		
		
		//validation for data base request.
		String referenciaSeleccionada = referenciaItem.getValue();
		if(referenciaSeleccionada=="Nuevo") {
			nuevo = true;
			return true;
		}else if (referenciaSeleccionada != null) {
				this.referencia=referenciaSeleccionada;
				nuevo=false;
				return true;
			}
		System.out.println("referencia incorrecta");
		return false;
	}

	@FXML
    void registrarItem(ActionEvent event) {
    if(verificarCampos()) {
    	//shoul be :peticion a la base de datos.
    	//conexion mediante instancia de inventario.
    	DaoInventario inventario = new DaoInventario();
    	//salida de todos los datos a la base de datos.
    	if(nuevo) {
    		inventario.registrarItemEnInventaro(nombre);
    	}else {
    		inventario.registrarItemEnEjemplares(referencia, color, costofab, costoVenta, fecha, cBox.getId(Sede), cantidad );
    	}
    	
    	System.out.println("se envio registro.");
    	}
    }


}
