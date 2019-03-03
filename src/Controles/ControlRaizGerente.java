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

	public void initialize(String nombre){
		nombreGerente.setText(nombre);
	}
    @FXML
    void registrarPersonal(ActionEvent event) throws IOException {
    	FXMLLoader loader1 = new FXMLLoader();
		loader1.setLocation(getClass().getResource("/Vistas/gerente_registro_personal.fxml"));
		Parent gui2 = (Parent)loader1.load();
		panelRaiz.setCenter(gui2);
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
    public void setStage(Stage escenario) {
    	this.escenario = escenario;
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
