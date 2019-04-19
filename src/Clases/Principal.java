package Clases;

import java.io.IOException;

import Controles.ControlRaizAdministrador;
import Controles.ControlRaizGerente;
import Controles.ControlRaizJefeTaller;
import Controles.ControlRaizVendedor;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Principal extends Application {
	 private static Stage escenarioLogin;
	 private static Stage escenarioGerente;
	 private static Stage escenarioVendedor;
	 private static Stage escenarioJefeTaller;
	 private static Stage escenarioAdministrador;
	 private static Image icono = new Image("/Imagenes/logoIcono.png");
	 
	 public Image getIcono() {
		 return icono;
	 }

	 
	 public static void iniciarLogin() throws IOException{				
		FXMLLoader cargador = new FXMLLoader();
		cargador.setLocation(Principal.class.getResource("/Vistas/login.fxml"));
		Parent raiz = (Parent)cargador.load();
		Scene escenario = new Scene(raiz); 
		escenarioLogin.getIcons().add(icono);
		escenarioLogin.setScene(escenario);		
		escenarioLogin.show();	
	 }	  
	 
	 public static void iniciarEscenarios(String perfil) {
		 switch(perfil) {
		 	case "Gerente":
		 		escenarioGerente = new Stage();    	
				escenarioGerente.getIcons().add(icono);
				break;
		 	case "Vendedor":
		 		escenarioVendedor = new Stage();    	
		 		escenarioVendedor.getIcons().add(icono);
				break;
		 	case "Jefe de taller":
		 		escenarioJefeTaller = new Stage();    	
		 		escenarioJefeTaller.getIcons().add(icono);
				break;
		 	case "Administrador":
		 		escenarioAdministrador = new Stage();    	
		 		escenarioAdministrador.getIcons().add(icono);
				break;
		 }
	 }
	 
	 public static void initEfecto(Parent raiz) {
		 FadeTransition ft = new FadeTransition(Duration.millis(500), raiz);
		 ft.setFromValue(0.0);
		 ft.setToValue(1.0);
		 ft.play();				
	 }
	 //Valle: cambie a un arreglo para pasar el id al vendedor
	 public static void iniciarRaiz(String[] perfil, String fuente, String nombre) throws IOException {				 
		 iniciarEscenarios(perfil[0]);		 
		 FXMLLoader cargador = new FXMLLoader();
		 cargador.setLocation(Principal.class.getResource(fuente));
		 Parent raiz = (Parent)cargador.load();
		 
		 switch(perfil[0]) {
		 	case "Gerente":
		 		ControlRaizGerente CRG = cargador.getController();
				CRG.initialize(nombre, perfil[0]);
				CRG.setStage(escenarioGerente);
				initEfecto(raiz);
				Scene EG = new Scene(raiz); 
				escenarioLogin.close();
				escenarioGerente.setScene(EG);
				escenarioGerente.initStyle(StageStyle.UNDECORATED);		
				escenarioGerente.show();
				CRG.menuInicial();	
				break;
		 	case "Vendedor":
		 		ControlRaizVendedor CRV = cargador.getController();
		 		CRV.initialize(nombre, perfil);
		 		CRV.setStage(escenarioVendedor);
		 		initEfecto(raiz);
		 		Scene EV = new Scene(raiz); 
				escenarioLogin.close();
				escenarioVendedor.setScene(EV);
				escenarioVendedor.initStyle(StageStyle.UNDECORATED);		
				escenarioVendedor.show();
				CRV.menuInicial();	
				break;
		 	case "Jefe de taller":
		 		ControlRaizJefeTaller CRJ = cargador.getController();
		 		CRJ.initialize(nombre, perfil[0]);
		 		CRJ.setStage(escenarioJefeTaller);
		 		initEfecto(raiz);
		 		Scene EJ = new Scene(raiz); 
				escenarioLogin.close();
				escenarioJefeTaller.setScene(EJ);
				escenarioJefeTaller.initStyle(StageStyle.UNDECORATED);		
				escenarioJefeTaller.show();
				CRJ.menuInicial();	
				break;
		 	case "Administrador":
		 		ControlRaizAdministrador CRA = cargador.getController();
		 		CRA.initialize(nombre, perfil[0]);
		 		CRA.setStage(escenarioAdministrador);
		 		initEfecto(raiz);
		 		Scene EA = new Scene(raiz); 
				escenarioLogin.close();
				escenarioAdministrador.setScene(EA);
				escenarioAdministrador.initStyle(StageStyle.UNDECORATED);		
				escenarioAdministrador.show();
				CRA.menuInicial();	
				break;
		 }		 
		 	 
	 }
	 
	 public static void iniciarGerente(String nombre, String[] perfil_id) throws IOException {
		 iniciarRaiz(perfil_id, "/Vistas/gerente_GUI.fxml", nombre);    	
	 }
    	   
	 public static void iniciarVendedor(String nombre, String[] perfil_id) throws IOException {	
		 iniciarRaiz(perfil_id, "/Vistas/vendedor_GUI.fxml", nombre);		 
	 }
    
	 public static void iniciarJefeTaller(String nombre, String[] perfil_id) throws IOException {
		 iniciarRaiz(perfil_id, "/Vistas/jefeTaller_GUI.fxml", nombre);
	 }
	 public static void iniciarAdministrador(String nombre, String[] perfil_id) throws IOException {
		 iniciarRaiz(perfil_id, "/Vistas/administrador_GUI.fxml", nombre);
	 }
	 
    
    public static void cerrarSesion(String panel) throws IOException {
    	switch(panel){
    	case "G": escenarioGerente.close();break;
    	case "V": escenarioVendedor.close(); break;
    	case "J": escenarioJefeTaller.close(); break;
    	case "A": escenarioAdministrador.close(); break;
    	}
    	iniciarLogin();
    }
    
	@Override
	public void start(Stage primaryStage) throws IOException {
		escenarioLogin = primaryStage;
		//iniciarLogin();
		//iniciarGerente("Ximena Guzman", new String[]{"Gerente"});
		iniciarJefeTaller("Diana Lopez", new String[]{"Jefe de taller"});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
