package Controles;

import java.io.IOException;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import Clases.Principal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
public class ControlRaizGerente {
	private Stage escenario;
	private double x, y;
	private String cargo;

    @FXML private Button cerrar;
    @FXML private Button minimizar;

    @FXML private BorderPane panelRaiz;    
    @FXML private Pane panelCentral;
    @FXML private Text nombreGerente;
    @FXML private Text titulo;
 
    @FXML private JFXButton botonRegistroPersonal;
    @FXML private JFXButton botonRegistroSedes;
    @FXML private JFXButton botonActualizarPersonal;
    @FXML private JFXButton botonActualizarSede;
    @FXML private JFXButton botonActualizaritems;
    @FXML private JFXButton botonConsultarPersonal;    
    @FXML private JFXButton botonConsultarSedes;    
    @FXML private Button atras;
    @FXML private Button botonCerrarSesion;

    //Para retroceder hacia la pantalla inicial:
    @FXML
    void retroceder(ActionEvent event) {
    	menuInicial();
    	atras.setVisible(false);
    	titulo.setText("");
    }   
    
	public void initialize(String nombre, String cargo){
		nombreGerente.setText(nombre);
		this.cargo = cargo;
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
    void registrarPersonal(ActionEvent event) throws IOException {
    	cambiarVentana("/Vistas/gerente_registro_personal.fxml");	
    	titulo.setText("Registro de personal");
    }

    @FXML
    void registroSedes(ActionEvent event) {		
		cambiarVentana("/Vistas/gerente_registro_sedes.fxml");
		titulo.setText("Registro de sedes");
    }
    
    @FXML
    void actualizarSede(ActionEvent event) {
    	cambiarVentana("/Vistas/gerente_actualizar_sede1.fxml");
    	titulo.setText("Actualización de sedes");
    }
    
    @FXML
    void cargarInterfazAP1(ActionEvent event) {
    	cambiarVentana("/Vistas/gerente_actualizar_personal1.fxml");
    	titulo.setText("Actualización de personal");
    }
    
    @FXML
    void actualizarItems(ActionEvent event) {
    	cambiarVentana("/Vistas/gerente_listar_items.fxml");
    	titulo.setText("Actualización de inventario");
    }

    @FXML
    void consultarPersonal(ActionEvent event) {		
		cambiarVentana("/Vistas/gerente_consultar_personal.fxml");
		titulo.setText("Consulta de personal");
    }
    
    @FXML
    void consultarSedes(ActionEvent event) {		
		cambiarVentana("/Vistas/gerente_consultar_sedes.fxml");
		titulo.setText("Consulta de sedes");
    }
    
    @FXML
    void cerrarSesion(ActionEvent event) throws IOException {
    	Principal.cerrarSesion("G");
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
}
