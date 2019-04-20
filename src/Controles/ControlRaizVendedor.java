package Controles;

import java.io.IOException;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import Clases.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
public class ControlRaizVendedor {
	private Stage escenario;
	private double x, y;
	private String cargo;

    @FXML private Button cerrar;
    @FXML private Button minimizar;

    @FXML private BorderPane panelRaiz;    
    @FXML private Pane panelCentral;
    @FXML private Text nombreVendedor;
    @FXML private Button atras;
    @FXML private Text titulo;
    @FXML private Label identificadorGerente;  //AGREGO VALLE

    @FXML private Button botonCerrarSesion;
    @FXML private Button botonCotizacion;
    @FXML private JFXButton registrarVenta;

    //Para retroceder hacia la pantalla inicial:
    @FXML
    void retroceder(ActionEvent event) {
    	menuInicial();
    	atras.setVisible(false);
    	titulo.setText("");
    } 
    
	public void initialize(String nombre, String[] cargo_id){
		nombreVendedor.setText(nombre);
		this.cargo = cargo_id[0];
		identificadorGerente.setText(cargo_id[1]);  //AGREGO VALLE
		atras.setVisible(false);
	}
	
    public void setStage(Stage escenario) {
    	this.escenario = escenario;
    }
    
    public void efectoCambio(FXMLLoader cargador) {
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
    
    //Carga la interfaz y aplica la respectiva transición:
    public void cambiarVentana(String fuente) {
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fuente));
		efectoCambio(loader);	
		atras.setVisible(true);
    }
    
    //Carga la pantalla inicial de la aplicación:
    public void menuInicial() {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/Vistas/menu_inicio.fxml"));
    	efectoCambio(loader);
    	ControlMenuInicial C = loader.getController();
    	C.iniciar(cargo);
    }
    
    @FXML
    void realizarCotizacion(ActionEvent event) {
    	titulo.setText("Cotizacion");
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Vistas/vendedor_registrar_cotizaciones.fxml"));
		efectoCambio(loader);
		ControlResgistrarCotizacion controlador= loader.getController();
		controlador.iniciar(identificadorGerente.getText());
		atras.setVisible(true);
    }	
    
    @FXML
    void consultarInventario(ActionEvent event) {
		cambiarVentana("/Vistas/vendedor_listar_items.fxml");
		titulo.setText("Consulta de inventario");
    }
       
    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
    	Principal.cerrarSesion("V");
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
    	alert.setHeaderText("Está a punto de cerrar la aplicación");
    	alert.setContentText("¿Está seguro de que desea salir?");

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
    
    @FXML
    void registrarVenta(ActionEvent event) {
    	titulo.setText("Registro de venta");
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Vistas/vendedor_registrar_ventas.fxml"));
		efectoCambio(loader);
		ControlVendedorRegistrarVentas controlador= loader.getController();
		controlador.iniciar(nombreVendedor.getText(), identificadorGerente.getText());
		atras.setVisible(true);
    }
        
}
