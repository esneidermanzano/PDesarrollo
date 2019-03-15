package Clases;

import java.io.IOException;

import Controles.ControlRaizGerente;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Principal extends Application {
	 private static Stage escenarioLogin;
	 private static Stage escenarioGerente;

	
	public void iniciarLogin() throws IOException{
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
		Scene escenario = new Scene(raiz); 
		escenarioLogin.close();
		escenarioGerente.setScene(escenario);
		//escenarioGerente.setOpacity(0.9);
		escenarioGerente.initStyle(StageStyle.UNDECORATED );
		escenarioGerente.show();
    }

	@Override
	public void start(Stage primaryStage) throws IOException {
		escenarioLogin = primaryStage;
		iniciarLogin();
		//iniciarGerente("Ximena Guzman");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
