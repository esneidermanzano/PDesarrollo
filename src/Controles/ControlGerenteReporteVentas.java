package Controles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import BaseDatos.DaoVenta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class ControlGerenteReporteVentas {
	
	private DaoVenta ventas;
	
	@FXML private BarChart<String, Number> salesCountGraph;
	
	@FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    @FXML
    private JFXComboBox<String> ComboBoxYear;
    
    @FXML
    private JFXButton botonGenerar;
	
    @FXML
    void generar(ActionEvent event) {
    	
    	salesCountGraph.getData().clear();
    	
    	XYChart.Series<String, Number> set1 = new XYChart.Series<>();
    	
    	String yyyy = ComboBoxYear.getValue();
    	
    	if(yyyy == null) {
    		yyyy = "2019";
    	}
    	
    	ObservableList<String> meses = FXCollections.observableArrayList();
    	
    	ResultSet tabla = ventas.obtenerNumVentasMes(yyyy);
    	
    	try {
			while(tabla.next()){
				set1.getData().add(new XYChart.Data(tabla.getString(1), tabla.getInt(3)));
				meses.add(tabla.getString(1));

			   // System.out.println(resultado_consulta);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	x.setCategories(FXCollections.observableArrayList(meses));
    	salesCountGraph.getData().addAll(set1);
    	salesCountGraph.setTitle("Ventas mensuales Año "+yyyy);
    }
    
    public void initialize() {
    	ventas = new DaoVenta();
    	
    	ArrayList<String> valores = ventas.obtenerAns();
		
		for (String valor : valores) {
			ComboBoxYear.getItems().addAll(valor);
		}
		/*
		x = new CategoryAxis();
		y = new NumberAxis();
		salesCountGraph = new BarChart<>(x, y);*/
		
		x.setLabel("Mes");
		y.setLabel("Número de Ventas");
    }
    
}
