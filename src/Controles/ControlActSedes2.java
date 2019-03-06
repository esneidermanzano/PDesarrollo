package Controles;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import BaseDatos.DaoSede;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class ControlActSedes2 {
	private DaoSede actualizacion;
    @FXML
    private Button actualizar;
    @FXML
    private TextField direccion;
    @FXML
    private TextField nEmpleados;
    @FXML
    private TextField telefono;
    @FXML
    private TextField nombre;
    @FXML
    private Label aviso;
    @FXML
    private DatePicker apertura;
    @FXML
    private TextField tamanoSede;
    @FXML
    private Label identificador;


    public void iniciar(String[] resultado) {
    	identificador.setText(resultado[0]);
    	nombre.setText(resultado[1]);
    	telefono.setText(resultado[2]);
    	direccion.setText(resultado[3]);
    	tamanoSede.setText(resultado[4]);
    	nEmpleados.setText(resultado[5]);
    	DateTimeFormatter formater= DateTimeFormatter.ofPattern("yyyy-mm-dd");
    	formater.withLocale(Locale.CANADA_FRENCH);
    	LocalDate fecha = LocalDate.parse(resultado[6]);
    	apertura.setValue(fecha);
    	
    }
    public void mostrarMensaje(String titulo, String mensaje) {
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
    }
    public boolean validar(String cadena) {
    	if( cadena.matches("[0-9]*")) {
    		return true;
    	}else {
    		return false;
    	}
    }
    public void cargarInterfazAnterior(ActionEvent event)  {
    	FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(getClass().getResource("/Vistas/gerente_actualizar_sede1.fxml"));
			Parent GUI5;
			try {
				GUI5 = (Parent)cargador.load();
				ControlActSedes1 controlador = cargador.getController();
				controlador.setear();
				BorderPane panelRaiz = (BorderPane)((Button)event.getSource()).getScene().getRoot();
				panelRaiz.setCenter(GUI5);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			;
    }
    @FXML
    void obtenerDatos(ActionEvent event) {
    	aviso.setText("");
    	String nom= nombre.getText();
    	String tel= telefono.getText();
    	String add=direccion.getText();
    	String tam= tamanoSede.getText();
    	String nEm= nEmpleados.getText();
    	if( tel.equals("")||add.equals("")||tam.equals("")||nEm.equals("")) {
    		aviso.setText("Información faltante");
    	}else {
    		
    		if(validar(tel)) {
    			if(validar(tam)) {
    				int t =Integer.parseInt(tam);
    				if(validar(nEm)) {
    					int n  =Integer.parseInt(nEm);
    					actualizacion= new DaoSede();    		
    		    		if(actualizacion.actualizar(identificador.getText(),nom,tel,add,t,n)>0) {
    		    			mostrarMensaje("¡Registro exitoso!", "Datos actualizados correctamente");
    		    			cargarInterfazAnterior(event);
    		    		}
    				}else {
    					aviso.setText("N° Empleados incorrecto");
    				}
    			}else {
    				aviso.setText("Tamaño incorrecto");
    			}
    		}else {
    			 aviso.setText("Teléfono inválido");
    		}
    		
 
    	}

    }
}