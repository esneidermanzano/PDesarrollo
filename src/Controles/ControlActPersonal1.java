package Controles;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import BaseDatos.DaoEmpleado;
import Clases.Usuario;
import Clases.Validador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class ControlActPersonal1 {

    @FXML
    private JFXTextField idEntrada;
    
    @FXML
    void cargarInterfazAP2(ActionEvent event) throws IOException {
    	
    	Validador V = new Validador();
    	
    	if(V.validarID(idEntrada,"de búsqueda")) {
    		
    		Usuario U = buscarUsuario(idEntrada.getText());
        	FXMLLoader cargador = new FXMLLoader();
    		cargador.setLocation(getClass().getResource("/Vistas/gerente_actualizar_personal2.fxml"));
    		Parent GUI = (Parent)cargador.load();
    		ControlActPersonal2 controlador = cargador.getController();
    		controlador.cargar(U);
    		BorderPane panelRaiz = (BorderPane)((Button)event.getSource()).getScene().getRoot();
    		panelRaiz.setCenter(GUI);
    		
    	}    	
    	
    }
    
    public Usuario buscarUsuario(String idEntrada) {
    	DaoEmpleado consultador = new DaoEmpleado();
    	return consultador.consultarUsuario(idEntrada);
    }
        
}
