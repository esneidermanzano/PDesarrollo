package BaseDatos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

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
		
		System.out.print("id1: " + idEjemplar + " id2: " + idItem);
		
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
	
}
