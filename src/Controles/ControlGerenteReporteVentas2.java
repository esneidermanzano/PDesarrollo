package Controles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


import BaseDatos.DaoVenta;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ControlGerenteReporteVentas2 {
	

	private DaoVenta reporte= new DaoVenta();
	
	

    @FXML private NumberAxis yAxis;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxisl;
    @FXML private CategoryAxis xAxisl;
    
	@FXML private DatePicker fechaInicial;
	@FXML private PieChart graficoTorta;
	@FXML private Button botonMostrarGrafico;
	@FXML private Button botonReporteAno;

    @FXML private ToggleGroup group;

    @FXML private ToggleGroup group2;

	@FXML private BarChart<String, Number> graficoBarras;
	@FXML private LineChart<String, Number> graficoLineas;
	@FXML private RadioButton rbgraficoBarras;
	@FXML private DatePicker fechaFinal;
	@FXML private RadioButton rbgraficoTorta;
	@FXML private RadioButton rbgraficoLinea;
	@FXML private RadioButton rbNVenta;
    @FXML private RadioButton rbVTventa;
    @FXML private RadioButton rbCPodructosV;
    
    @FXML private Label aviso;
	
	public void initialize() {
		rbgraficoBarras.setUserData("barras");
		rbgraficoLinea.setUserData("linea");
		rbgraficoTorta.setUserData("torta");
		rbNVenta.setUserData("nVenta");
		rbVTventa.setUserData("vTotal");
		rbCPodructosV.setUserData("productosV");
		
		yAxis.setLabel("Cantidad");
		xAxis.setLabel("Días");
		yAxisl.setLabel("Cantidad");
		xAxisl.setLabel("Días");

	}

	@FXML
	void mostrarGLinea(ActionEvent event) {
		graficoLineas.getData().clear();
		graficoTorta.setVisible(false);
		graficoBarras.setVisible(false);
		graficoLineas.setVisible(true);
	}

	@FXML
	void mostrarGBarras(ActionEvent event) {
		graficoBarras.getData().clear();
		graficoLineas.setVisible(false);
		graficoTorta.setVisible(false);
		graficoBarras.setVisible(true);
	}

	@FXML
	void mostrarGTorta(ActionEvent event) {
		graficoTorta.getData().clear();
		graficoLineas.setVisible(false);
		graficoBarras.setVisible(false);
		graficoTorta.setVisible(true);
	}
	public void installTooltip(PieChart.Data d) {

	    String msg = String.format("%s : %s", d.getName(), d.getPieValue());

	    Tooltip tt = new Tooltip(msg);
	    tt.setStyle("-fx-background-color: gray; -fx-text-fill: whitesmoke;");
	    
	    Tooltip.install(d.getNode(), tt);
	}

    @FXML
    void mostrarGrafico(ActionEvent event) {
    	aviso.setText("");
    	LocalDate fechaI=fechaInicial.getValue();
    	LocalDate fechaF=fechaFinal.getValue();
    	if(fechaI != null && fechaI != null && fechaI.compareTo(fechaF)<=0) {
	    	int tipo=0;
	    	String mensaje = "Ventas de "+ fechaI + " a " + fechaF; 
	    	
	    		if(group.getSelectedToggle()!=null) {
		    		if(group2.getSelectedToggle()!=null) {
			    		if(group2.getSelectedToggle().getUserData().equals("nVenta")){
			    			tipo=1;
			    			mensaje = "Número de ventas de "+ fechaI + " a " + fechaF;
			    			xAxis.setLabel("Días");
			    			xAxisl.setLabel("Días");
			    		}else {
			    			if(group2.getSelectedToggle().getUserData().equals("vTotal")){
			        			tipo=2;
			        			mensaje = "Valor total ventas de "+ fechaI + " a " + fechaF;
			        			xAxis.setLabel("Días");
			        			xAxisl.setLabel("Días");
			        		}else {
			        			if(group2.getSelectedToggle().getUserData().equals("productosV")){
			            			tipo=3;
			            			mensaje = "Cantidad de productos vendidos de "+ fechaI + " a " + fechaF;
			            			xAxis.setLabel("Productos");
			            			xAxisl.setLabel("Productos");
			            		}
			        		}
			    		}
			    	}
			    	if(tipo==0) {
			    		aviso.setText("¡Seleccione el tipo de reporte!");
			    	}else {
			    		ResultSet tabla = reporte.obtenerDias(fechaI,fechaF,tipo);
			    		ObservableList<String> ejex = FXCollections.observableArrayList();
			    		XYChart.Series series = new XYChart.Series();
			    		series.setName("ventas " + fechaI +"a" + fechaF);
			    		 try {
							while(tabla.next()){
							 	series.getData().add(new XYChart.Data(tabla.getString(1), tabla.getInt(2)));
							 	ejex.add(tabla.getString(1));
							  }
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    		
			    		if(!series.getData().isEmpty()) {
				    		if(group.getSelectedToggle().getUserData().equals("barras")){
				    			graficoBarras.getData().clear();
				    			xAxis.setCategories(ejex);
				    			graficoBarras.setTitle(mensaje);
				    			graficoBarras.getData().addAll(series);
				    		}else {
				    			if(group.getSelectedToggle().getUserData().equals("linea")){
				    				graficoLineas.getData().clear();
				    				xAxisl.setCategories(ejex);
				    				graficoLineas.setTitle(mensaje);
				    				graficoLineas.getData().addAll(series);
				        		}else {
				        			ObservableList<PieChart.Data> datos  =reporte.obtenerObjetos(fechaI, fechaF,tipo);
				        			if(!datos.isEmpty()) {
					        			graficoTorta.getData().clear();
					        			graficoTorta.setTitle(mensaje);
					        			graficoTorta.getData().addAll(datos);
					        			graficoTorta.getData().forEach(this::installTooltip);
				        			}else {
				        				aviso.setText("No hay resultados para el rango escogido");
				        			}
				        		}
				    		}
			    		}else {
			    			aviso.setText("No hay resultados para el rango escogido");
			    		}
			    	}	
	    		}else {
			    		aviso.setText("¡Seleccione tipo de grafico!");
			    }	
		
	    }else {
    		aviso.setText("¡Seleccione bien los rangos de fecha!");
    	}
    }

    @FXML
    void reportePorAno(ActionEvent event) {
    	try {
	       	FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(getClass().getResource("/Vistas/gerente_reporte_ventas.fxml"));
			Parent GUI4 = (Parent)cargador.load();
			//CAMBIO____________________________________________________________________________________
			Pane panelCentral= (Pane)((Button)event.getSource()).getParent();
		    panelCentral.getChildren().clear();
			panelCentral.getChildren().add(GUI4);
			Scene scene = GUI4.getScene();			
			GUI4.translateXProperty().set(scene.getWidth());		
			Timeline timeline = new Timeline();
			KeyValue rango = new KeyValue(GUI4.translateXProperty(), 0, Interpolator.EASE_IN);
			KeyFrame duracion = new KeyFrame(Duration.seconds(0.4), rango);
			timeline.getKeyFrames().add(duracion);
			timeline.play();		
			
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }

    
    
}
