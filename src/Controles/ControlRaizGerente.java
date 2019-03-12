package Controles;

import java.io.IOException;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class ControlRaizGerente {
	private Stage escenario;
	private double x, y;

    @FXML
    private JFXButton botonRegistro;
    @FXML
    private Button cerrar;
    @FXML
    private Button minimizar;
    @FXML
    private Button registrar;
    @FXML
    private BorderPane panelRaiz;
    @FXML
    private Text nombreGerente;
    @FXML
    private JFXButton actualizacionPersonal; 

	public void initialize(String nombre){
		nombreGerente.setText(nombre);
	}
	
    public void setStage(Stage escenario) {
    	this.escenario = escenario;
    }
    
    @FXML
    void registrarPersonal(ActionEvent event) throws IOException {
    	FXMLLoader loader1 = new FXMLLoader();
		loader1.setLocation(getClass().getResource("/Vistas/gerente_registro_personal.fxml"));
		Parent gui2 = (Parent)loader1.load();
		panelRaiz.setCenter(gui2);
    }

    @FXML
//Carga la pantalla de registro de items cuando el boton "registrarItems" es pulsado.
    void load_reg_itm(ActionEvent event) {
    FXMLLoader reg_itm = new FXMLLoader();
    reg_itm.setLocation(getClass().getResource("/Vistas/gerente_registro_items.fxml"));
        try {
            Parent itm = (Parent)reg_itm.load();
            panelRaiz.setCenter(itm);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    @FXML
    void cambio(ActionEvent event) {		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Vistas/gerente_registro_sedes.fxml"));
		try {
			Parent gui = (Parent)loader.load();
			panelRaiz.setCenter(gui);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//primaryStage.setScene(escenario1);
    }
    
    @FXML
    void cambioActualizar(ActionEvent event) {
    	
    	FXMLLoader loader2 = new FXMLLoader();
		loader2.setLocation(getClass().getResource("/Vistas/gerente_actualizar_sede1.fxml"));
		try {
		Parent gui3 = (Parent)loader2.load();
		panelRaiz.setCenter(gui3);
		
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    void cargarInterfazAP1(ActionEvent event) {
    	FXMLLoader cargador = new FXMLLoader();
		cargador.setLocation(getClass().getResource("/Vistas/gerente_actualizar_personal1.fxml"));
		try {
		Parent GUI = (Parent)cargador.load();
		panelRaiz.setCenter(GUI);
		
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    }

    @FXML
    void copiarCoordenadas(MouseEvent event) {
    	x = event.getSceneX();
    	y = event.getSceneY();
    }
    @FXML
    void moverPanel(MouseEvent event) {
    	escenario.setX(event.getScreenX() - x);
    	escenario.setY(event.getScreenY() - y);
    }
    
    //Para Cerrar la ventana raiz
    @FXML
    void cerrarAplicacion(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	//alert.setTitle("Alerta");
    	alert.setHeaderText("Esta a punto de cerrar la aplicacion");
    	alert.setContentText("¿Esta seguro que desea salir?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    System.exit(1);
    	}
    }

    //Para minimizar la ventana raiz
    @FXML
    void minimizarAplicacion(ActionEvent event) {
    	Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
