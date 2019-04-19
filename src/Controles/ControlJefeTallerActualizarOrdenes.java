package Controles;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoOrdenTrabajo;
import Clases.OrdenTrabajo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;

public class ControlJefeTallerActualizarOrdenes {
	  @FXML
	    private Text textoFecha;
	    private OrdenTrabajo orden;
	    private DaoOrdenTrabajo dao = new DaoOrdenTrabajo();;

	    @FXML
	    private Text textoJefe;

	    @FXML
	    private JFXTextField fieldDescripcion;

	    @FXML
	    private Text textoCantidad;

	    @FXML
	    private DatePicker pickerFecha;

	    @FXML
	    private Text textoOrden;

	    @FXML
	    private JFXComboBox<String> comboEstado;

	    @FXML
	    private Text textoArticulo;

	    @FXML
	    private JFXButton botonActualizar;
	    
		
	    
	   
		public void inicializar(OrdenTrabajo ord) {
	    	this.orden = ord;
	    	textoOrden.setText(Integer.toString(orden.getId()));
	    	textoFecha.setText(orden.getF_creacion());
	    	textoArticulo.setText(Integer.toString(orden.getId_articulo()));
	    	comboEstado.setPromptText(orden.getEstado());
	    	comboEstado.setValue(orden.getEstado());
	    	textoCantidad.setText(Integer.toString(orden.getCantidad()));
	    	fieldDescripcion.setText(orden.getDescripcion());
	    	LocalDate entrega= LocalDate.parse(orden.getF_entrega());
	    	pickerFecha.setValue(entrega);
	    	textoJefe.setText(orden.getJefe());
	    	comboEstado.getItems().addAll("recibida","desarrollo", "terminada");
	    	
	    	
	    }

    @FXML
    void actualizarItem(ActionEvent event) {
    	System.out.println(comboEstado.getValue());
    	System.out.println(fieldDescripcion.getText());
    	System.out.println(pickerFecha.getValue());
    	LocalDate date= pickerFecha.getValue();
    	dao.actualizarOrden( orden.getId(), comboEstado.getValue(), fieldDescripcion.getText(), date);
    }

   
}
