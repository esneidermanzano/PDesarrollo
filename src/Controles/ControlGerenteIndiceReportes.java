package Controles;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ControlGerenteIndiceReportes {

    @FXML
    private JFXButton reporte_ventas_mensuales;

    @FXML
    void cargarInterfazReporteVentas(ActionEvent event) throws IOException {
    	FXMLLoader cargador = new FXMLLoader();   	    		
		Pane panelCentral = (Pane)((Button)event.getSource()).getParent();
		efectoCambio(cargador, panelCentral, "/Vistas/gerente_reporte_ventas.fxml");
    }
    
  //Aplica el efecto de transición:
    public void efectoCambio(FXMLLoader cargador, Pane panelCentral, String vista) throws IOException {
		cargador.setLocation(getClass().getResource(vista));
		Parent GUI = (Parent)cargador.load();    	    		
		panelCentral.getChildren().clear();
		panelCentral.getChildren().add(GUI);
		Scene scene = GUI.getScene();
		GUI.translateXProperty().set(scene.getWidth());
		Timeline timeline = new Timeline();
		KeyValue rango = new KeyValue(GUI.translateXProperty(), 0, Interpolator.EASE_IN);
		KeyFrame duracion = new KeyFrame(Duration.seconds(0.4), rango);
		timeline.getKeyFrames().add(duracion);
		timeline.play();
		//ControlGerenteActPersonal controlador = cargador.getController();
		//controlador.cargar(E);
    }

}