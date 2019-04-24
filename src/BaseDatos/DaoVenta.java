package BaseDatos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class DaoVenta {
	
	static FachadaDB fachada;
	
	public DaoVenta(){
		 fachada= new FachadaDB();		
	}
	
	public int siguienteVenta() {
		
		String SQL = "SELECT MAX(id) FROM ventas";
		int salida = 0;
		       
		try{
			
			Connection conn = fachada.getConnetion();
		    System.out.println("consultando en la bd");
		    Statement sentencia = conn.createStatement();
		    ResultSet tabla = sentencia.executeQuery(SQL);
		    
		    while(tabla.next()){            	
		    	salida = tabla.getInt(1);
		    } 
		                               
		}catch(SQLException e){
			System.out.println(e);
		}catch(Exception e){
			System.out.println(e);
		}
		       
		return salida + 1;        
		        
	}
	
	public int registrarVenta(String idVendedor, String idCliente, Timestamp fecha) {
		
		int salida = 0;
		String SQL;
        
        SQL = "INSERT INTO ventas VALUES(nextval('ventas_sequence'), '" + idVendedor + "', '" + idCliente + "', '" + fecha + "')";
        
         try{
        	 
            Connection conn = fachada.getConnetion();
            Statement sentencia = conn.createStatement();
            salida = sentencia.executeUpdate(SQL);
                                  
         }catch(SQLException e){
        	 System.out.println(e);
         }catch(Exception e){
        	 System.out.println(e);
         }
            
         return salida;
         
	}
	
	public int registrarDetalleVenta(String idVenta, String idItem, String idEjemplar, String cantidad) {
				
		int salida = 0;
		String SQL;
        
        SQL = "INSERT INTO detalle_ventas VALUES(" + idVenta + ", " + idEjemplar + ", " + idItem + ", " + cantidad + ")";
        
         try{
        	 
            Connection conn = fachada.getConnetion();
            Statement sentencia = conn.createStatement();
            salida = sentencia.executeUpdate(SQL);
                                  
         }catch(SQLException e){
        	 System.out.println(e);
         }catch(Exception e){
        	 System.out.println(e);
         }
            
         return salida;
         
	}
		
	public ArrayList<String> obtenerAns()
	{
		ArrayList<String> ans = new ArrayList<String>();
		String sql_select = "SELECT DISTINCT extract(year from fecha) as yyyy FROM ventas;";
		
		try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while(tabla.next()){
            	ans.add(tabla.getString(1));
               // System.out.println(resultado_consulta);
             }
            
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
		
		return ans;
	}
	
	public ResultSet obtenerNumVentasMes(String yyyy)
	{
		String sql_select = "SELECT to_char(fecha, 'Month') as month, extract(year from fecha) as yyyy, COUNT(id) as \"NumVentas\" FROM ventas WHERE extract(year from fecha) = "+yyyy+" GROUP BY 1,2 ORDER BY to_date(to_char(fecha, 'Month'), 'Month') ASC;";
		
		ResultSet tabla = null;
		
		try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando en la bd");
            Statement sentencia = conn.createStatement();
            tabla = sentencia.executeQuery(sql_select);
            
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
		
		return tabla;
	}
	
	public ResultSet obtenerDias(LocalDate localDate, LocalDate localDate2,int tipo){
		
		 ResultSet tabla = null;
		String sql_select = "SELECT EXTRACT(DAY FROM fecha) AS dia,COUNT(*) FROM ventas "+
								"WHERE DATE(fecha) BETWEEN '"+ localDate +"' AND '"+localDate2+ 
								"' GROUP BY fecha " +
								"ORDER BY DATE(fecha)  ASC ;";
		
		if(tipo==2) {
		 sql_select="WITH p_vendidos_dia AS (SELECT numero_de_ejemplar,id_item,fecha,SUM(cantidad) AS cant "+ 
											"FROM ventas JOIN detalle_ventas ON ventas.id=detalle_ventas.id_venta WHERE DATE(ventas.fecha) BETWEEN '"+ localDate +"' AND '"+localDate2+"' GROUP BY numero_de_ejemplar,id_item,fecha ORDER BY fecha ASC),"+
						" p_vendidos_dia_consumatotal AS( SELECT EXTRACT(DAY FROM fecha),SUM(cantidad*valor_venta) "+
														 "FROM p_vendidos_dia JOIN inventario ON p_vendidos_dia.id_item=inventario.id NATURAL JOIN ejemplares GROUP BY fecha ORDER BY fecha ASC) "+
					"SELECT * FROM p_vendidos_dia_consumatotal;";
				 
				 
		}else {
			if(tipo==3) {
				sql_select = "SELECT nombre,count(*) FROM ejemplares NATURAL JOIN "+
						"(SELECT numero_de_ejemplar,id_item,nombre "+
						"FROM ventas JOIN detalle_ventas ON ventas.id=detalle_ventas.id_venta JOIN inventario ON id_item=inventario.id WHERE DATE(fecha) BETWEEN '"+ localDate +"' AND '"+localDate2+ "') as p_vendidos"+
						" GROUP BY nombre;";
			}
			
		}	
		
		try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando en la bd");
            Statement sentencia = conn.createStatement();
            tabla = sentencia.executeQuery(sql_select);
                        
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
		
		return tabla;
	}
	
	public  ObservableList<PieChart.Data> obtenerObjetos(LocalDate localDate, LocalDate localDate2,int tipo){
		 ObservableList<PieChart.Data> datos  =  FXCollections.observableArrayList();
		
		 String sql_select = "SELECT EXTRACT(DAY FROM fecha) AS dia,COUNT(*) FROM ventas "+
					"WHERE DATE(fecha) BETWEEN '"+ localDate +"' AND '"+localDate2+ 
					"' GROUP BY fecha " +
					"ORDER BY DATE(fecha)  ASC ;";
		 String ayuda="día ";
		 
		if(tipo==2) {
			sql_select="WITH p_vendidos_dia AS (SELECT numero_de_ejemplar,id_item,fecha,SUM(cantidad) AS cant "+ 
											"FROM ventas JOIN detalle_ventas ON ventas.id=detalle_ventas.id_venta WHERE DATE(ventas.fecha) BETWEEN '"+ localDate +"' AND '"+localDate2+"' GROUP BY numero_de_ejemplar,id_item,fecha ORDER BY fecha ASC),"+
						" p_vendidos_dia_consumatotal AS( SELECT EXTRACT(DAY FROM fecha),SUM(cantidad*valor_venta) "+
														 "FROM p_vendidos_dia JOIN inventario ON p_vendidos_dia.id_item=inventario.id NATURAL JOIN ejemplares GROUP BY fecha ORDER BY fecha ASC) "+
					"SELECT * FROM p_vendidos_dia_consumatotal;";
				
		}else {
			if(tipo==3) {
				sql_select = "SELECT nombre,count(*) FROM ejemplares NATURAL JOIN "+
						"(SELECT numero_de_ejemplar,id_item,nombre "+
						"FROM ventas JOIN detalle_ventas ON ventas.id=detalle_ventas.id_venta JOIN inventario ON id_item=inventario.id WHERE DATE(fecha) BETWEEN '"+ localDate +"' AND '"+localDate2+ "') as p_vendidos"+
						" GROUP BY nombre;";
				ayuda="";
			}
			
		
		}	
		try{
            Connection conn= fachada.getConnetion();
            System.out.println("consultando en la bd");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
           System.out.println(tabla);
            while(tabla.next()){
            	 
            	datos.add(new PieChart.Data(ayuda+tabla.getString(1) ,tabla.getInt(2)));
            	
             }
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
		
		return datos;
	}
	
}
