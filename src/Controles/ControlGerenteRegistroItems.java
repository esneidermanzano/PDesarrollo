package Controles;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.IntegerValidator;


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
    private JFXTextField referenciaItem;

    @FXML
    private JFXButton botonRegistroItems;
    

    @FXML
    private JFXComboBox<?> comboBoxSede;
    
    private String nombre;
    private String cantidad;
    private String color;
    private String material;
    private String costofab;
    private String costoVenta;
    private String referencia;

    void verificaionVisual() {
    	
    }
    boolean verificarCampos(){
    	//se verifican que los campos cumplan condiciones de aceptacion.
    	if(
    verificarNombre()
    &&
    verificarCantidad()
    &&
    verificarColor()
    &&
    verificarMaterial()
    &&
    verificarCostoFabricacion()
    &&
    verificarCostoVenta()
    &&
    verificarReferencia()
    && 
    verificarFecha()
    &&
    verificarSede()) {return true;}
    	return false;
    }
    @FXML
    private boolean verificarSede() {
		// TODO Auto-generated method stub
    	 // por definir, consultas a la base de datos.
    	
		return false;
	}
    @FXML
	private boolean verificarFecha() {
		// TODO Auto-generated method stub
    	if(fechaRegistroItem.getValue() != null) {return true;}
    	System.out.println("fecha vacia");
		return false;
	}
	@FXML
    private boolean verificarCostoVenta() {
    	RequiredFieldValidator validator = new RequiredFieldValidator();
    	IntegerValidator intValidator = new IntegerValidator();
    	validator.setMessage("Input Required");
    	intValidator.setMessage("solo numeros enteros.");
    	valorVentaItem.getValidators().add(validator);
    	valorVentaItem.getValidators().add(intValidator);
    	valorVentaItem.focusedProperty().addListener((o,oldVal,newVal)->{
    	    if(!newVal) valorVentaItem.validate();
    	});
    	//validation request for data base.
		// TODO Auto-generated method stub
    	String valorVenta= valorVentaItem.getText();
		if(valorVentaItem.getText().matches("[1-9]*") && valorVenta.matches("[^\t]*") && valorVenta.length()!=0){
			this.costoVenta= valorVenta;
			return true;}
		System.out.println("valor venta incorrecto");
		valorVentaItem.clear();
		return false;
	}
    
    @FXML
	private boolean verificarCostoFabricacion() {
		RequiredFieldValidator validator = new RequiredFieldValidator();
		IntegerValidator intValidator = new IntegerValidator();
    	validator.setMessage("Input Required");
    	intValidator.setMessage("solo numeros enteros.");
    	costofabItem.getValidators().add(validator);
    	costofabItem.getValidators().add(intValidator);
    	costofabItem.focusedProperty().addListener((o,oldVal,newVal)->{
    	    if(!newVal) costofabItem.validate();
    	});
    	//validation request for data base.
		String costofab = costofabItem.getText();
		if(costofab.matches("[1-9]*") && costofab.matches("[^\t]*") && costofab.length()!=0) {
			this.costofab = costofab;
			return true;}
		System.out.println("Costo fab incorrecto");
		costofabItem.clear();
		return false;
		// TODO Auto-generated method stub
		
	}
    
    @FXML
	private boolean verificarMaterial() {
		RequiredFieldValidator validator = new RequiredFieldValidator();
    	validator.setMessage("Input Required");
    	materialItem.getValidators().add(validator);
    	materialItem.focusedProperty().addListener((o,oldVal,newVal)->{
    	    if(!newVal) materialItem.validate();
    	});
    	//validation request for data base.
		String material = materialItem.getText();
		if(material.matches("[a-z]*") && material.matches("[^\t]*") && material.length()!=0) {
			this.material = material;
			return true;}
		System.out.println("material incorrecto");
		materialItem.clear();
		return false;
		// TODO Auto-generated method stub
		
	}
    
    @FXML
	private boolean verificarColor() {
		//validation feedback for the user.
		RequiredFieldValidator validator = new RequiredFieldValidator();
    	validator.setMessage("Input Required");
    	colorItem.getValidators().add(validator);
    	colorItem.focusedProperty().addListener((o,oldVal,newVal)->{
    	    if(!newVal) colorItem.validate();
    	});
    	//Validation for data base request.
		String color = colorItem.getText();
		if(color.matches("[a-z]*") && color.matches("[^\t]*") && color.length()!=0) {
			this.color = color;
			return true;}
		colorItem.clear();
		System.out.println("Color incorrecto");
		return false;
		// TODO Auto-generated method stub
		
	}
    
    @FXML
	private boolean verificarCantidad() {
		//validation feedback for the user.
		RequiredFieldValidator validator = new RequiredFieldValidator();
		IntegerValidator intValidator = new IntegerValidator();
    	validator.setMessage("Input Required");
    	intValidator.setMessage("solo numeros enteros.");
    	cantidadItems.getValidators().add(validator);
    	cantidadItems.getValidators().add(intValidator);
    	cantidadItems.focusedProperty().addListener((o,oldVal,newVal)->{
    	    if(!newVal) cantidadItems.validate();
    	});
    	//validation for data base request.
		String cantidad = cantidadItems.getText();
		if(cantidad.matches("[1-9]*") && cantidad.matches("[^\t]*") && cantidad.length() != 0) {
			this.cantidad=cantidad;
			return true;}
		cantidadItems.clear();
		System.out.println("cantidad incorrecta");
		return false;
		// TODO Auto-generated method stub
		
	}
    
    @FXML
	private boolean verificarNombre() {
		//validation feedback for the user.
		RequiredFieldValidator validator = new RequiredFieldValidator();
    	validator.setMessage("Input Required");
    	nombreItem.getValidators().add(validator);
    	nombreItem.focusedProperty().addListener((o,oldVal,newVal)->{
    	    if(!newVal) nombreItem.validate();
    	});
    	//validation for data base request.
		String nombre = nombreItem.getText();
		if(nombre.matches("[a-z]*") && nombre.matches("[^\t]*") && nombre.length() != 0) {
			this.nombre = nombre;
			return true;}
		nombreItem.clear();
		//feedback for user.
		System.out.println("nombre incorrecto");
		return false;
		// TODO Auto-generated method stub
		
	}
    
    @FXML
	private boolean verificarReferencia() {
		RequiredFieldValidator validator = new RequiredFieldValidator();
		IntegerValidator intValidator = new IntegerValidator();
    	validator.setMessage("Input Required");
    	intValidator.setMessage("solo numeros enteros");
		referenciaItem.getValidators().add(validator);
		referenciaItem.getValidators().add(intValidator);
		referenciaItem.focusedProperty().addListener((o,oldVal,newVal)->{
    	    if(!newVal) referenciaItem.validate();
    	});
		
		//validation for data base request.
		String referencia = referenciaItem.getText();
		if(nombre.matches("[a-z]*") && nombre.matches("[^\t]*") && nombre.length() != 0) {
			this.referencia = referencia;
			return true;}
		System.out.println("referencia incorrecta");
		referenciaItem.clear();
		return false;}

	@FXML
    void registrarItem(ActionEvent event) {
    if(verificarCampos()) {//shoul be :peticion a la base de datos.
    	System.out.println("se envio registro.");
    	//salida de todos los datos a la base de datos
    	}
    }


}
