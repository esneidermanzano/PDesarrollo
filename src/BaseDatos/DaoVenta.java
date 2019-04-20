package BaseDatos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
}
