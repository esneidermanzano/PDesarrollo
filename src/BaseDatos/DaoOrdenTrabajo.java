package BaseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import Clases.OrdenTrabajo;

public class DaoOrdenTrabajo {
	static FachadaDB fachada;
	public DaoOrdenTrabajo(){
		 fachada= new FachadaDB();
		
	}

	public ObservableList<OrdenTrabajo> obtenerOrdenes() {
		ObservableList<OrdenTrabajo> lista = FXCollections.observableArrayList();
		
		String sql_select = "SELECT * FROM ordenes_trabajo_view";
		try{
            Connection conn= fachada.getConnetion();
            
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
           
            while(tabla.next()){
            	lista.add(
            			new OrdenTrabajo(tabla.getInt(1), tabla.getString(2), 
            					tabla.getInt(3), tabla.getString(4), tabla.getInt(5), tabla.getDate(6),  
            					tabla.getDate(7), tabla.getString(8))
            			);
             }            
        }
		catch(SQLException e){ System.out.println(e); }
        catch(Exception e){ System.out.println(e); }
		System.out.println(lista);
		return lista;
	}
	
	public static void cambiarEstadoOrden(String id, String state) {
		String sql="update ordenes_trabajo set estado ='"+ state + "' where id=" + id;
		int n=0;
		 try{
	            Connection conn= fachada.getConnetion();
	            PreparedStatement sentencia = conn.prepareStatement(sql);
	            n = sentencia.executeUpdate();            
	        }
	        catch(SQLException e){
	            System.out.println(e); 
	            }
	        catch(Exception e){ 
	            System.out.println(e);
	        }
	        
	        System.out.println(n);
	}
	public void actualizarOrden(int id,String state, String des, LocalDate fechaEntrega) {
		
		String sql="update ordenes_trabajo set estado ='"+ state +"', descripcion ='"+des +
				"', fecha_entrega = '"+ fechaEntrega +"' where id=" +id;
		int n=0;
		 try{
	            Connection conn= fachada.getConnetion();
	            PreparedStatement sentencia = conn.prepareStatement(sql);
	            n = sentencia.executeUpdate();            
	        }
	        catch(SQLException e){
	            System.out.println(e); 
	            }
	        catch(Exception e){ 
	            System.out.println(e);
	        }
	        
	        System.out.println(n);
		
	}

}
