package Clases;

import java.io.IOException;

import Controles.ControlRaizGerente;
import Controles.ControlRaizJefeTaller;
import Controles.ControlRaizVendedor;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Principal extends Application {
	 private static Stage escenarioLogin;
	 private static Stage escenarioGerente;
	 private static Stage escenarioVendedor;
	 private static Stage escenarioJefeTaller;

	
	public static void iniciarLogin() throws IOException{
		FXMLLoader cargador = new FXMLLoader();
		cargador.setLocation(Principal.class.getResource("/Vistas/login.fxml"));
		Parent raiz = (Parent)cargador.load();
		Scene escenario = new Scene(raiz); 
		escenarioLogin.setScene(escenario);		
		escenarioLogin.show();
	}
    public static void iniciarGerente(String nombre) throws IOException {
    	escenarioGerente = new Stage();
		FXMLLoader cargador = new FXMLLoader();
		cargador.setLocation(Principal.class.getResource("/Vistas/gerente_GUI.fxml"));
		Parent raiz = (Parent)cargador.load();
		ControlRaizGerente controlador = cargador.getController();
		controlador.initialize(nombre);
		controlador.setStage(escenarioGerente);
		FadeTransition ft = new FadeTransition(Duration.millis(500), raiz);
		ft.setFromValue(0.0);
		ft.setToValue(1.0);
		ft.play();
		Scene escenario = new Scene(raiz); 
		escenarioLogin.close();
		escenarioGerente.setScene(escenario);
		escenarioGerente.initStyle(StageStyle.UNDECORATED );		
		escenarioGerente.show();
    }
    
    public static void iniciarVendedor(String nombre) throws IOException {
    	escenarioVendedor = new Stage();
		FXMLLoader cargador = new FXMLLoader();
		cargador.setLocation(Principal.class.getResource("/Vistas/vendedor_GUI.fxml"));
		Parent raiz = (Parent)cargador.load();
		ControlRaizVendedor controlador = cargador.getController();
		controlador.initialize(nombre);
		controlador.setStage(escenarioVendedor);
		Scene escenario = new Scene(raiz); 
		escenarioLogin.close();
		escenarioVendedor.setScene(escenario);
		//escenarioGerente.setOpacity(0.9);
		escenarioVendedor.initStyle(StageStyle.UNDECORATED );
		escenarioVendedor.show();
    }
    
    public static void iniciarJefeTaller(String nombre) throws IOException {
    	escenarioJefeTaller = new Stage();
		FXMLLoader cargador = new FXMLLoader();
		cargador.setLocation(Principal.class.getResource("/Vistas/jefeTaller_GUI.fxml"));
		Parent raiz = (Parent)cargador.load();
		ControlRaizJefeTaller controlador = cargador.getController();		
		controlador.initialize(nombre);
		controlador.setStage(escenarioJefeTaller);
		Scene escenario = new Scene(raiz); 
		escenarioLogin.close();
		escenarioJefeTaller.setScene(escenario);
		//escenarioGerente.setOpacity(0.9);
		escenarioJefeTaller.initStyle(StageStyle.UNDECORATED );
		escenarioJefeTaller.show();
    }
    
    public static void cerrarSesion(String panel) throws IOException {
    	switch(panel){
    	case "G": escenarioGerente.close();break;
    	case "V": escenarioVendedor.close(); break;
    	case "J": escenarioJefeTaller.close(); break;
    	}
    	iniciarLogin();
    }
    
	@Override
	public void start(Stage primaryStage) throws IOException {
		escenarioLogin = primaryStage;
		iniciarLogin();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
